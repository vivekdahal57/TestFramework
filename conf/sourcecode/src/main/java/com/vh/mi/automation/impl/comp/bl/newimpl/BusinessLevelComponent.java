package com.vh.mi.automation.impl.comp.bl.newimpl;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.comp.bl.BL;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelSelectionComponent;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Represents a single business level
 *
 * @author nimanandhar
 */
public class BusinessLevelComponent extends AbstractWebComponent implements IBusinessLevelsComponent.IBusinessLevel {
    private final WebElements webElements;
    private final BL level;

    public BusinessLevelComponent(WebDriver driver, BL level) {
        super(driver);
        this.level = level;
        webElements = new WebElements(driver);
    }

    @Override
    public String getLabel() {
        return webElements.getLabelWebElement(level).getText();
    }

    @Override
    public String getSelected() {
        return webElements.getSelectionWebElement(level).getText();
    }

    @Override
    public IBusinessLevelSelectionComponent doOpenSelection() {
        WebElement selectionWebElement = webElements.getSelectionWebElement(level);
        SeleniumUtils.click(selectionWebElement, getDriver());
        BusinessLevelSelectionComponent businessLevelSelection = new BusinessLevelSelectionComponent(getDriver(), level);
        businessLevelSelection.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return businessLevelSelection;
    }

    @Override
    public boolean isViewSelectedComponentVisible() {
        return webElements.getViewSelectionWebElement(level).isPresent();
    }

    @Override
    public IViewSelected doOpenViewSelected() {
        //TODO why is this causing problrm frequently??????
        Optional<WebElement> viewSelectionWebElement = webElements.getViewSelectionWebElement(level);
        Preconditions.checkState(viewSelectionWebElement.isPresent());

        WebElement webElement = viewSelectionWebElement.get();
//        IE specific fix for webElement.click()
        SeleniumUtils.click(webElement, getDriver());
        ViewSelectedComponent viewSelectedComponent = new ViewSelectedComponent(getDriver());
        viewSelectedComponent.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return viewSelectedComponent;
    }

    @Override
    public boolean isFullyLoaded() {
        return true;
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindAll(
                {@FindBy(id = "d2Form:mainNKey_nKeyTable:tb"), @FindBy(id = "d2Form:nkeyobject_nKeyTable:tb")}
        )//for option page
                WebElement blTableBody;

        private WebElement getLabelWebElement(BL bl) {
            String xpath = "./tr[position()=" + bl.getIndex() + "]/td[1]";
            return blTableBody.findElement(By.xpath(xpath));
        }

        private WebElement getSelectionWebElement(BL bl) {
            String xpath = "./tr[position()=" + bl.getIndex() + "]/td[2]/div";
            return blTableBody.findElement(By.xpath(xpath));
        }

        private Optional<WebElement> getViewSelectionWebElement(BL bl) {
            String xpath = "./tr[position()=" + bl.getIndex() + "]/td[3]/a/img[@src='/vh/images/noteh.gif']";
            if (!SeleniumUtils.elementExists(blTableBody, xpath)) {
                return Optional.absent();
            }
            return Optional.of(blTableBody.findElement(By.xpath(xpath)));
        }
    }
}

package com.vh.mi.automation.impl.comp.bl.newimpl;

import com.vh.mi.automation.api.comp.ILoadingComp;
import com.vh.mi.automation.api.comp.bl.BL;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.exceptions.comp.bl.BusinessLevelNotAvailableException;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * BusinessLevels represent a collection of Individual Business Level
 *
 * @author nimanandhar
 */
public class BusinessLevelsComponent extends AbstractWebComponent implements IBusinessLevelsComponent {
    private final WebElements webElements;
    private final ILoadingComp loadingMessage;

    private Integer maxLevels;

    public BusinessLevelsComponent(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
        loadingMessage = new LoadingPopup(getDriver());
    }


    @Override
    public int getMaxLevels() {
        if (maxLevels == null)
            maxLevels = webElements.nKeyTableBody.findElements(By.className("d2-trackingLabel")).size();
        return maxLevels;
    }


    @Override
    public boolean isLevelAvailable(BL level) {
        return level.getIndex() <= getMaxLevels();
    }


    @Override
    public void doResetSelections() {
        Action hoverOverResetSelectionDrillMenu = new Actions(getDriver()).moveToElement(webElements.resetSelectionDrillButton).build();
        hoverOverResetSelectionDrillMenu.perform();
        SeleniumUtils.click(webElements.resetSelectionLink,getDriver());
        loadingMessage.waitTillDisappears();
    }


    @Override
    public IBusinessLevel getBusinessLevelComponent(BL level) {
        if (!isLevelAvailable(level)) {
            throw new BusinessLevelNotAvailableException(level);
        }
        return new BusinessLevelComponent(getDriver(), level);
    }

    @Override
    public Collection<IBusinessLevel> getBusinessLevels() {
        Collection<IBusinessLevel> businessLevels = new ArrayList<>();
        for (BL bl : BL.values()) {
            if (isLevelAvailable(bl)) {
                businessLevels.add(new BusinessLevelComponent(getDriver(), bl));
            }
        }
        return businessLevels;

    }


    @Override
    public boolean isFullyLoaded() {
        return webElements.nKeyTableBody.isEnabled() && webElements.nKeyTableBody.isDisplayed();
    }


    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindAll({@FindBy(xpath = "//div[starts-with(@id,'d2Form:mainNKey')]//a[@class='d2-drillbtn']"),@FindBy(xpath = "//div[starts-with(@id,'d2Form:nkeyobject_1_header')]//a[@class='d2-drillbtn']")})
        WebElement resetSelectionDrillButton;

       @FindAll({@FindBy(linkText = "Reset Selection"),@FindBy(xpath = "//div[@id='dropmenudiv']//a[contains(text(),'Reset Selection')]")})
        WebElement resetSelectionLink;

        @FindAll({@FindBy(id = "d2Form:mainNKey_nKeyTable:tb"),@FindBy(id = "d2Form:nkeyobject_nKeyTable:tb")})//for option page
        WebElement nKeyTableBody;
    }
}



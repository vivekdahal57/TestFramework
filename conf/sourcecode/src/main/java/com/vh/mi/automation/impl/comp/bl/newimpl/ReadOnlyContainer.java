package com.vh.mi.automation.impl.comp.bl.newimpl;

import com.vh.mi.automation.api.comp.bl.BL;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelSelectionComponent;
import com.vh.mi.automation.api.exceptions.comp.bl.BusinessLevelNotAvailableException;
import com.vh.mi.automation.impl.comp.LoadingPopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the Top Portion of the Business Level Selection Component
 *
 * @author nimanandhar
 */
public class ReadOnlyContainer extends AbstractWebComponent implements IBusinessLevelSelectionComponent.IReadOnlyContainer {
    private final WebElements webElements;
    private Map<BL, String> labels;
    private final LoadingPopup loadingPopup;

    public ReadOnlyContainer(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
        loadingPopup = new LoadingPopup(driver);
    }

    @Override
    public void doChangeSelectionTo(BL level) {
        assertThatBusinessLevelIsAvailable(level);

        WebElement drillButton = webElements.readOnlyContainerTableBody.findElement(By.xpath("./tr[position()=" + level.getIndex() + "]/td[3]/a[@title='Change']"));
        drillButton.click();
        loadingPopup.waitTillDisappears();

    }

    private void assertThatBusinessLevelIsAvailable(BL level) {
        if (level.getIndex() > getLabelTexts().size()) {
            throw new BusinessLevelNotAvailableException(level);
        }
    }


    @Override
    public Map<BL, String> getLabelTexts() {
        if (labels == null) {
            labels = new HashMap<>();

            int index = 1;
            List<WebElement> labelElements = webElements.readOnlyContainerTableBody.findElements(By.xpath("./tr/td[1]/span"));
            for (WebElement labelElement : labelElements) {
                labels.put(BL.get(index++), labelElement.getText());
            }
        }
        return labels;
    }


    @Override
    public Map<BL, String> getCurrentSelections() {
        Map<BL, String> currentSelections = new HashMap<>();

        int index = 1;
        List<WebElement> labelElements = webElements.readOnlyContainerTableBody.findElements(By.xpath("./tr/td[2]/span"));
        for (WebElement labelElement : labelElements) {
            currentSelections.put(BL.get(index++), labelElement.getText());
        }

        return currentSelections;
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.readOnlyContainerTableBody.isDisplayed();
    }


    private static class WebElements {

        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id='readOnlyContainer']/tbody")
        WebElement readOnlyContainerTableBody;
    }
}

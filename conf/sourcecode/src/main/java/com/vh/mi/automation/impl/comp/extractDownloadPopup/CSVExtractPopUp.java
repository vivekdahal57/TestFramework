package com.vh.mi.automation.impl.comp.extractDownloadPopup;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.impl.comp.AbstractCSVExtractPopUp;
import com.vh.mi.automation.impl.comp.LoadingPopup;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author nimanandhar
 */
public class CSVExtractPopUp extends AbstractCSVExtractPopUp {
    private final WebElements webElements;

    public CSVExtractPopUp(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public WebElement getCSVPopUpComponent() {
        return webElements.sendToCSVPopUp;
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.sendToCSVPopUp.isDisplayed();
    }

    @Override
    public IAmWebComponent doWaitTillFullyLoaded(long waitTimeInSecs) {
        logger.info("Waiting for __{}__ loading ...", this.getClass()
                .getSimpleName());

        new WebDriverWait(getDriver(), waitTimeInSecs).ignoring(StaleElementReferenceException.class).ignoring(WebDriverException.class)
                .until(new Function<WebDriver,Boolean>() {

                    @Override
                    public Boolean apply(WebDriver arg0) {
                        return isFullyLoaded();
                    }
                });

        return this;
    }

    @Override
    public void inputCSVName(String jobName) {
        webElements.txtJobName.sendKeys(jobName);
    }

    @Override
    public void selectDelimeterValue(DelimiterOption app) {
        select(DelimiterOption.COMMA);
    }

    @Override
    public void clickSendButton() {
        webElements.btnSendToCSV.click();
    }

    @Override
    public void select(DelimiterOption option) {
        switch (option) {
            case COMMA:
                apply(webElements.txtDelimiter);
            case PIPE:
                apply(webElements.txtDelimiter);
            default:
                break;
        }
    }

    private void apply(WebElement delmOption) {
//        used jsClick for IE
        SeleniumUtils.click(delmOption, getDriver());
        new LoadingPopup(getDriver()).waitTillDisappears();
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "d2Form:_panelSendToCSVCDiv")
        WebElement sendToCSVPopUp;

        @FindBy(id = "d2Form:_panelSendToCSVHeader")
        WebElement sendToCSVPopUpTitle;

        @FindBy(id = "d2Form:jobName")
        WebElement txtJobName;

        @FindBy(id = "d2Form:csvDelimiter")
        WebElement txtDelimiter;

        @FindBy(id = "d2Form:sendToCsv")
        WebElement btnSendToCSV;

    }
}

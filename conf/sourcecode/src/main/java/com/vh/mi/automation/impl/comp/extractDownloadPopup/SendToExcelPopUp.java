package com.vh.mi.automation.impl.comp.extractDownloadPopup;

import com.google.common.base.Function;
import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.impl.comp.AbstractSendToExcelPopUp;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by i10359 on 1/23/18.
 */
public class SendToExcelPopUp extends AbstractSendToExcelPopUp {
    private final WebElements webElements;
    private LoadingPopup loadingPopup;

    public SendToExcelPopUp(WebDriver driver){
        super(driver);
        webElements  = new WebElements(driver);
        loadingPopup = new LoadingPopup(getDriver());
    }


    @Override
    public void saveExcel(String jobName) {
        webElements.txtJobName.sendKeys(jobName);
        webElements.btnSendToExcel.click();
        loadingPopup.waitTillDisappears();
        webElements.btnOk.click();
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.sendToExcelPopUp.isDisplayed();
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
    public WebElement getExcelPopUpComponent() {
        return webElements.sendToExcelPopUp;
    }

    private static class WebElements{
        private WebElements(WebDriver driver){
            PageFactory.initElements(driver,this);
            }
                @FindBy(id= "d2Form:excelJobName")
                WebElement txtJobName;

                @FindBy(id="d2Form:sendToExcel_1")
                WebElement btnSendToExcel;

                @FindBy(id= "d2Form:_panelSendToExcelCDiv")
                WebElement sendToExcelPopUp;

                @FindBy(xpath = "//*[@id=\"d2Form:_excelJobProgressPanelContentTable\"]//input[@value=\"OK\"]")
                WebElement btnOk;

        }
    }


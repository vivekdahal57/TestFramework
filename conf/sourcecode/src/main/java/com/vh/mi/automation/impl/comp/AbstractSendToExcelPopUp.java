package com.vh.mi.automation.impl.comp;

import com.vh.mi.automation.api.comp.extractDownloadPopup.ISendToExcelPopUp;
import com.vh.mi.automation.api.constants.TimeOuts;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10359 on 1/23/18.
 */
public abstract class AbstractSendToExcelPopUp implements ISendToExcelPopUp {
    protected  Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebDriver driver;
    private int timeOutSeconds;

    public AbstractSendToExcelPopUp(WebDriver driver){
        this(driver, TimeOuts.THREE_MINUTES);
    }

    public AbstractSendToExcelPopUp(WebDriver driver, int timeOut){
        this.driver = driver;
        this.timeOutSeconds = timeOut;
    }

    public WebDriver getDriver(){
        return driver;
    }

    public boolean isDisplayed(){
        return getExcelPopUpComponent().isDisplayed();
    }

    public abstract WebElement getExcelPopUpComponent();

}

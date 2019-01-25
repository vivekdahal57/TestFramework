package com.vh.mi.automation.impl.comp;

import com.google.common.base.Predicate;
import com.vh.mi.automation.api.annotations.logging.LogMethodExecutionTime;
import com.vh.mi.automation.api.comp.ILoadingComp;
import com.vh.mi.automation.api.comp.extractDownloadPopup.ICSVExtractPopUp;
import com.vh.mi.automation.api.constants.TimeOuts;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Same as AbstractLoadingComp but does not require
 * a new instance to be created everytime. The old
 * AbstractLoadingComp will be slowly phased out
 *
 * @author nimanandhar
 */
public abstract class AbstractCSVExtractPopUp implements ICSVExtractPopUp {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebDriver driver;
    private int timeoutInSeconds;

    public AbstractCSVExtractPopUp(WebDriver driver) {
        this(driver, TimeOuts.THREE_MINUTES);
    }

    public AbstractCSVExtractPopUp(WebDriver driver, int timeout) {
        this.driver = driver;
        this.timeoutInSeconds = timeout;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public boolean isDisplayed() {
        return getCSVPopUpComponent().isDisplayed();
    }

    public abstract WebElement getCSVPopUpComponent();
}

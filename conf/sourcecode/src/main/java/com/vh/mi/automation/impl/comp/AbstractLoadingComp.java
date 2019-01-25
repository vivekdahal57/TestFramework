package com.vh.mi.automation.impl.comp;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.vh.mi.automation.api.annotations.logging.LogMethodExecutionTime;
import com.vh.mi.automation.api.comp.ILoadingComp;
import com.vh.mi.automation.api.constants.TimeOuts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i80448 on 9/10/2014.
 */
@Deprecated
public class AbstractLoadingComp implements ILoadingComp {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebDriver driver;
    private WebElement idElm;
    private int timeoutInSeconds;

    public AbstractLoadingComp(WebDriver driver, String compId) {
        this(driver, compId, TimeOuts.THREE_MINUTES);
    }

    public AbstractLoadingComp(WebDriver driver, String compId, int timeout) {
        this.driver = driver;
        idElm = driver.findElement(By.id(compId));
        this.timeoutInSeconds = timeout;
    }

    @Override
    @LogMethodExecutionTime
    public void waitTillDisappears() {
        logger.debug("Waiting for __{}__ component to disappear ...", this.getClass().getSimpleName());

        if (isDisplayed()) {
            new WebDriverWait(driver, timeoutInSeconds).until(new Function<WebDriver,Boolean>() {

                @Override
                public Boolean apply(WebDriver arg0) {
                    return !isDisplayed();
                }
            });
        }
    }

    public boolean isDisplayed() {
        return idElm.isDisplayed();
    }
}

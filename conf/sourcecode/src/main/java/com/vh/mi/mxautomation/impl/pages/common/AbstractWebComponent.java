package com.vh.mi.mxautomation.impl.pages.common;

import com.google.common.base.Function;
import com.vh.mi.automation.api.annotations.logging.LogMethodExecutionTime;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.mxautomation.api.features.IAmWebComponent;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Represents any MI page/form e.g. Login page, Welcome page, Claim 101
 * etc.</br> NOTE - Creating an instance of it's implementations does not mean
 * that web driver is actually in that page.
 *
 * @author i80448
 */
public abstract class AbstractWebComponent implements IAmWebComponent {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebDriver driver;

    public AbstractWebComponent(WebDriver driver) {
        checkArgument(driver != null);
        WaitUtils.waitForAngular(driver);
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Set the current driver.
     *
     * @param driver
     */
    public void setDriver(WebDriver driver) {
        checkArgument(driver != null);
        this.driver = driver;
    }

    @LogMethodExecutionTime
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
}

package com.vh.mi.automation.impl.comp;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.vh.mi.automation.api.annotations.logging.LogMethodExecutionTime;
import com.vh.mi.automation.api.comp.ILoadingComp;
import com.vh.mi.automation.api.constants.TimeOuts;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Same as AbstractLoadingComp but does not require
 * a new instance to be created everytime. The old
 * AbstractLoadingComp will be slowly phased out
 *
 * @author nimanandhar
 */
public abstract class AbstractLoadingCompNew implements ILoadingComp {
    private WebDriver driver;
    private int timeoutInSeconds;

    public AbstractLoadingCompNew(WebDriver driver) {
        this(driver, TimeOuts.THREE_MINUTES);
    }

    public AbstractLoadingCompNew(WebDriver driver, int timeout) {
        this.driver = driver;
        this.timeoutInSeconds = timeout;
    }

    @Override
    @LogMethodExecutionTime
    public void waitTillDisappears() {
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
        return getLoadingComponent().isDisplayed();
    }

    public abstract WebElement getLoadingComponent();
}

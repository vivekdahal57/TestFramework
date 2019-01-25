package com.vh.mi.automation.api.utils;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.paulhammant.ngwebdriver.NgWebDriver;
import com.vh.mi.automation.api.constants.TimeOuts;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * @author nimanandhar
 */
public class WaitUtils {

    public static void waitForSeconds(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ignored) {
        }

    }

    public static void waitForMilliSeconds(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public static void waitUntil(WebDriver webDriver, Integer waitTimeSeconds,Function predicate) {
        new WebDriverWait(webDriver, waitTimeSeconds)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class)
                .until((Function<? super WebDriver, Object>) predicate);
    }

    public static void waitUntilEnabled(WebDriver driver, final WebElement element) {
        WaitUtils.waitUntil(driver, TimeOuts.FIVE_SECONDS, new Function<WebDriver,Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return element.isEnabled();
            }
        });
    }

    public static void waitUntilDisplayed(WebDriver driver, final WebElement element, int timeOut) {
        WaitUtils.waitUntil(driver, timeOut, new Function<WebDriver,Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return element.isDisplayed();
            }
        });
    }

    public static void waitUntilDisappear(WebDriver driver, final WebElement element, int timeOut) {
        WaitUtils.waitUntil(driver, timeOut, new Function<WebDriver,Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return !element.isDisplayed();
            }
        });
    }

    public static void waitForAngular(WebDriver driver){
        new NgWebDriver((JavascriptExecutor) driver).waitForAngularRequestsToFinish();
    }

}

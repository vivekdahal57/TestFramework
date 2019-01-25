package framework.api.utils;

import com.google.common.base.Function;
import com.paulhammant.ngwebdriver.NgWebDriver;
import framework.api.constants.TimeOuts;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.util.concurrent.TimeUnit;

/**
 * @author bibdahal
 */
public class WaitUtils {


    public static void waitForSeconds(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
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

    public static void fluentWait(WebDriver driver,By locator,int timeOuts,int pollTime){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeOuts,TimeUnit.SECONDS)
                .pollingEvery(pollTime,TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement waitingElement = wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply( WebDriver driver) {
                return driver.findElement(locator);
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
        driver.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);
        new NgWebDriver((JavascriptExecutor) driver).waitForAngularRequestsToFinish();
    }

    public static void waitUntilVisiblityOfElement(WebDriver driver, WebElement element){
        WebDriverWait wait = new WebDriverWait(driver,60);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}

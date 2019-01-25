package framework.selenium;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import framework.api.config.IDriverConfiguration;
import framework.api.constants.TimeOuts;
import framework.api.utils.WaitUtils;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.fest.assertions.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * @author bibdahal
 */
public class SeleniumUtils {

    private static Logger logger = LoggerFactory.getLogger(SeleniumUtils.class);

    public static boolean ifElementFocused(WebDriver driver, WebElement element) {
        Preconditions.checkArgument(driver != null);
        Preconditions.checkArgument(element != null);

        return element.equals(driver.switchTo().activeElement());
    }

    /**
     * Check if the element exist in the current dom.
     * Note that this version is not effected by the implicit wait time
     * ie it does not wait for the element to be present and returns immediate even if the element is not present
     *
     * @param context The element relative to which the xpath is evaluated.
     * @param xpath   The xpath to evaluate.
     *                When using xpath be aware that webdriver follows standard conventions: a search prefixed with "//" will search the
     *                entire document, not just the children of this current node. Use ".//" to limit your search to
     *                the children of this WebElement.
     * @return
     */
    public static boolean elementExists(WebElement context, String xpath) {
        List<WebElement> elements = context.findElements(By.xpath(xpath));
        return elements.size() > 0;
    }

    public static void hoverOnElement(WebElement element, WebDriver driver) {
        if (isIE(driver)) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].onmouseover()", element);
        } else
            new Actions(driver).moveToElement(element).build().perform();

    }

    public static void click(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }

    public static void scrollAndClick(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement((element)).build().perform();
        WaitUtils.waitUntilVisiblityOfElement(driver, element);
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public static void closeCurrentWindowAndSwitchToNewWindow(WebDriver webDriver) {
        String newWindowHandle = getNextWindowHandle(webDriver);
        webDriver.close();
        webDriver.switchTo().window(newWindowHandle);
    }

    //switch to new window when there are two windows
    public static void switchToNewWindow(WebDriver webDriver) {
        WaitUtils.waitForSeconds(TimeOuts.ONE_SECOND);
        webDriver.switchTo().window(getNextWindowHandle(webDriver));
    }

    public static void closeChildWindowAndGoBackToMainWindow(WebDriver webDriver, boolean closeChildWindow) {
        int windowHandlesSize = webDriver.getWindowHandles().size();
        String currentWindowHandle = "";
        if (closeChildWindow == true) {
            currentWindowHandle = webDriver.getWindowHandle();
            webDriver.close();
        }
        try {
            WaitUtils.waitUntil(webDriver, TimeOuts.TEN_SECONDS, new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if (closeChildWindow) {
                        return Math.abs(windowHandlesSize - driver.getWindowHandles().size()) == 1;
                    } else {
                        return Math.abs(windowHandlesSize - driver.getWindowHandles().size()) == 0;
                    }
                }
            });
        } catch (TimeoutException timeoutException) {
            Set<String> windowHandles = webDriver.getWindowHandles();
            logger.error("Expected number of windows to be 1 but was " + windowHandles.size());
            logger.info("Forcefully closing window");
        }

        String prevHandle = "";
        for (String handle : webDriver.getWindowHandles()) {
            if (!handle.equals(currentWindowHandle)) {
                prevHandle = handle;
            }
        }
        webDriver.switchTo().window(prevHandle);
    }

    public static String getNextWindowHandle(WebDriver webDriver) {
        Preconditions.checkState(webDriver.getWindowHandles().size() == 2 || webDriver.getWindowHandles().size() > 2);
        String currentWindowHandle = webDriver.getWindowHandle();
        String newWindowHandle = null;
        for (String handle : webDriver.getWindowHandles()) {
            if (!handle.equals(currentWindowHandle)) {
                newWindowHandle = handle;
            }
        }
        Assertions.assertThat(newWindowHandle != null && !newWindowHandle.equals(currentWindowHandle));
        return newWindowHandle;
    }

    public static void waitUntilMoreThanOneWindowsIsOpen(WebDriver webDriver) {
        int attempts = 0;
        while (attempts < 5 && getNumberOfOpenWindows(webDriver) < 2) {
            WaitUtils.waitForSeconds(TimeOuts.FIVE_SECONDS);
            attempts++;
        }
    }

    public static void waitUntilNWindowsAreOpen(WebDriver webDriver, int reqNumberOfWindows) {
        int attempts = 0;
        while (attempts < 5 && getNumberOfOpenWindows(webDriver) < reqNumberOfWindows) {
            WaitUtils.waitForSeconds(TimeOuts.FIVE_SECONDS);
            attempts++;
        }
    }

    public static int getNumberOfOpenWindows(WebDriver webDriver) {
        int numberOfWindows = 0;
        for (String s : webDriver.getWindowHandles()) {
            if (StringUtils.isNotBlank(s)) {
                numberOfWindows++;
            }
        }
        return numberOfWindows;
    }

    public static boolean isAlertPresent(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static String getAlertTextAndDismissAlert(WebDriver driver) {
        String alertText = null;
        try {
            alertText = driver.switchTo().alert().getText();
            driver.switchTo().alert().dismiss();
        } catch (Exception e) {
            //dismiss
        }
        return alertText;
    }

    public static void clickOkOnAlert(WebDriver driver) {
        try {
            driver.switchTo().alert().accept();

        } catch (Exception e) {
            //dismiss
        }
    }

    public static boolean isIE(WebDriver driver) {
        return getBrowser(driver) == IDriverConfiguration.Browser.IE;
    }

    public static IDriverConfiguration.Browser getBrowser(WebDriver driver) {
        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();

        switch (caps.getBrowserName()) {
            case "internet explorer":
                return IDriverConfiguration.Browser.IE;
            case "chrome":
                return IDriverConfiguration.Browser.CHROME;
            case "firefox":
                return IDriverConfiguration.Browser.FIREFOX;
            default:
                throw new RuntimeException("Unknown Browser " + caps.getBrowserName());
        }

    }

    public static void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public static void sendKeysToInput(String text, WebElement element) {
        element.clear();
        element.sendKeys(text);
    }

    public static void doubleClick(WebDriver driver, WebElement element) {
        Actions act = new Actions(driver);
        act.doubleClick(element).build().perform();
        new NgWebDriver((JavascriptExecutor) driver).waitForAngularRequestsToFinish();
    }

    public static void dragElement(WebDriver driver, WebElement from, WebElement to) {
        Actions builder = new Actions(driver);
        builder.clickAndHold(from).moveByOffset(1, 1).moveToElement(to).moveByOffset(1, 1).release().perform();
    }


    public static boolean isCheckboxSelected(WebElement element) {
        return element.isSelected();
    }

    public static void hover(WebDriver driver, WebElement element) {
        new Actions(driver).moveToElement(element).build().perform();
    }

    //This method should be called where element not clickable exception occurs
    public static void hoverAndClick(WebDriver driver, WebElement element) {
        new Actions(driver).moveToElement(element).click().build().perform();
    }

    public static void selectByValue(WebElement selectElement, String value) {
        new Select(selectElement).selectByValue(value);
    }

    public static void selectByVisibleText(WebElement selectElement, String value) {
        new Select(selectElement).selectByVisibleText(value);
    }

    public static String getCurrentValueFromSelectElement(WebElement selectElement) {
        return new Select(selectElement).getFirstSelectedOption().getText();
    }

    public static WebElement findElementByXPath(WebDriver driver, String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public static WebElement findElementById(WebDriver driver, String id) {
        return driver.findElement(By.id(id));
    }

    public static WebElement findElementByCssSelector(WebDriver driver, String className) {
        return driver.findElement(By.cssSelector(className));
    }

    public static WebElement findElementByClassName(WebDriver driver, String className) {
        return driver.findElement(By.className(className));
    }

    public static List<WebElement> findElementsById(WebDriver driver, String id) {
        return driver.findElements(By.id(id));
    }

    public static List<WebElement> findElementsByXPath(WebDriver driver, String xpath) {
        return driver.findElements(By.xpath(xpath));
    }
}

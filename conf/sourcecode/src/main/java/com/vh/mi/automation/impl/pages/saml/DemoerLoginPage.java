package com.vh.mi.automation.impl.pages.saml;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.saml.IDemoerLoginPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by i81247 on 11/15/2016.
 */
public class DemoerLoginPage extends AbstractWebComponent implements IDemoerLoginPage {
    private final WebElements webElements;

    private DemoerLoginPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    public static DemoerLoginPage loadPage(WebDriver driver, String application) {
        validateApplication(application);
        driver.get(application);
        return new DemoerLoginPage(driver);
    }

    private static void validateApplication(String appUrl) {
        if (!appUrl.matches("^http://[^/]*$")) {
            throw new AutomationException("Invalid appUrl. Please enter an appUrl of the form http://nvscmlinq1.d2hawkeye.net:9500" +
                    "The port number is optional but it should not end with a /"
            );
        }
    }

    private void enterText(WebElement inputElement, String text) {
        inputElement.clear();
        if (!StringUtils.isBlank(text))
            inputElement.sendKeys(text);
    }

    @Override
    public void enteruserName(String userName) {
        enterText(webElements.userNameField, userName);
    }

    @Override
    public void enterPassword(String password) {
        enterText(webElements.passwordField, password);
    }

    @Override
    public DemoerAssistedInputPage doLogin(String userName, String password) {
        enteruserName(userName);
        enterPassword(password);
        webElements.loginButton.click();
        new WebDriverWait(getDriver(), 30).until(new Function<WebDriver,Boolean>() {
                                                                  public Boolean apply(WebDriver driver) {
                                                                      return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                                                                  }
                                                              }
        );
        return new DemoerAssistedInputPage(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.userNameField.isDisplayed();
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "username")
        WebElement userNameField;

        @FindBy(id = "password")
        WebElement passwordField;

        @FindBy(xpath = "/html/body/div/div/div/div/div/div[2]/form/div[3]/div/button")
        WebElement loginButton;
    }
}

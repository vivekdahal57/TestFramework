package com.vh.mi.automation.impl.pages.admin;

import com.vh.mi.automation.api.pages.admin.IAdminLoginPage;
import com.vh.mi.automation.api.pages.admin.IAdminPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author nimanandhar
 */
public class AdminLoginPage extends AbstractWebComponent implements IAdminLoginPage {
    private final WebElements webElements;

    public AdminLoginPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    public static AdminLoginPage loadPage(WebDriver driver, String adminURL) {
        driver.get(adminURL);
        return new AdminLoginPage(driver);
    }


    @Override
    public IAdminPage loginWith(String userName, String password) {
        webElements.userName.clear();
        webElements.password.clear();
        webElements.userName.sendKeys(userName);
        webElements.password.sendKeys(password);
        SeleniumUtils.click(webElements.loginButton,getDriver());
        return new AdminPage(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.userName.isDisplayed();
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "username")
        WebElement userName;

        @FindBy(id = "password")
        WebElement password;

        @FindBy(xpath = "//input[@value='Login']")
        WebElement loginButton;
    }


}

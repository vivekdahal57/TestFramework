package com.vh.mi.automation.impl.reportManager;

import com.vh.mi.automation.api.reportManager.IRMLoginPage;
import com.vh.mi.automation.api.reportManager.IReportManagerPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 8/9/2017.
 */
public class RMLoginPage extends AbstractWebComponent implements IRMLoginPage {
    private final RMLoginPage.WebElements webElements;

    public RMLoginPage(WebDriver driver) {
        super(driver);
        webElements = new RMLoginPage.WebElements(driver);
    }

    public static RMLoginPage loadPage(WebDriver driver, String rmUrl) {
        driver.get(rmUrl);
        return new RMLoginPage(driver);
    }


    @Override
    public IReportManagerPage loginWith(String userName, String password) {
        webElements.userName.clear();
        webElements.password.clear();
        webElements.userName.sendKeys(userName);
        webElements.password.sendKeys(password);
        SeleniumUtils.click(webElements.loginButton,getDriver());
        return new ReportAdministrator(getDriver());
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

package com.vh.mi.automation.impl.pages.ei;

import com.vh.mi.automation.api.pages.ei.IEIDashboard;
import com.vh.mi.automation.api.pages.ei.IEIDashboardLoginPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 5/30/2017.
 */
public class EIDashboardLoginPage  extends AbstractWebComponent implements IEIDashboardLoginPage {

    private final WebElements webElements;

    public EIDashboardLoginPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public IEIDashboard loginWith(String userName, String password) {
        webElements.userName.clear();
        webElements.password.clear();
        webElements.userName.sendKeys(userName);
        webElements.password.sendKeys(password);
        SeleniumUtils.click(webElements.loginButton,getDriver());
        return new EIDashboard(getDriver());
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

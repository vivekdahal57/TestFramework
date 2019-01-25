package com.vh.mi.automation.impl.pages.admin.pages.manageObjects;

import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IApplications;
import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IRegisterApplication;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 5/15/2017.
 */
public class RegisterApplication extends AbstractWebComponent implements IRegisterApplication{

    WebElements webElements;
    public RegisterApplication(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public IApplications registerApplication(String appId) {
        selectApplication(appId);
        SeleniumUtils.click(webElements.registerButton);
        return PageFactory.initElements(getDriver(), ApplicationsPage.class);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.titleBar.getText().contains("(Connection Successful!)");
    }

    public void selectApplication(String appId) {
        SeleniumUtils.selectByValue(webElements.selectApplicationElement, appId);
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(className = "Titlebar")
        WebElement titleBar;

        @FindBy(xpath = "(//td[text()[contains(.,'Select Application')]])[1]//following::select[1]")
        WebElement selectApplicationElement;

        @FindBy(xpath = "//input[@class = 'BackButton']")
        WebElement registerButton;

    }
}

package com.vh.mi.automation.impl.pages.admin.pages.manageObjects;

import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IUsers;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82716 on 5/25/2017.
 */
public class UsersPage extends AbstractWebComponent implements IUsers {
    private final WebElements webElements;

    public UsersPage(WebDriver webDriver){
        super(webDriver);
        webElements = new WebElements(webDriver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.titleBar.isDisplayed();
    }

    private void doFilterLoginName(String loginName){
        webElements.loginNameFilter.sendKeys(loginName);
        webElements.filterBtn.click();;
    }


    @Override
    public UsersRegistration goToUserRegistrationPage(String loginName, boolean isFilteringRequired){
        if (isFilteringRequired) {
            doFilterLoginName(loginName);
        }
        webElements.editBtn.click();
        return PageFactory.initElements(getDriver(),UsersRegistration.class);
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(className = "Titlebar")
        WebElement titleBar;

        @FindBy(name = "ffLoginName")
        WebElement loginNameFilter;

        @FindBy(xpath = "(//td[@class='NextButton' and text()[contains(.,'Edit')]])[1]")
        WebElement editBtn;

        @FindBy(xpath = "//tr[@class='FilterRow']/td[1]/img[@src='/images/filterBySelection.gif']")
        WebElement filterBtn;
    }
}

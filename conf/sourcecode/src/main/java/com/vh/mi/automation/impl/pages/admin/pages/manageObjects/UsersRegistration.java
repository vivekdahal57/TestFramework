package com.vh.mi.automation.impl.pages.admin.pages.manageObjects;


import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IUsers;
import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IUsersRegistration;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i82716 on 5/25/2017.
 */
public class UsersRegistration extends AbstractWebComponent implements IUsersRegistration {
    private final WebElements webElements;
    private static Logger logger = LoggerFactory.getLogger(UsersRegistration.class);

    public UsersRegistration(WebDriver webDriver){
        super(webDriver);
        webElements = new WebElements(webDriver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.titleBar.isDisplayed();
    }

    @Override
    public IUsers registerUserToGroup(String groupName){
        SeleniumUtils.selectByVisibleText(webElements.availableGroupsElement, groupName);
        webElements.addUsrToGroupBtn.click();
        if(SeleniumUtils.isAlertPresent(getDriver())){
            SeleniumUtils.clickOkOnAlert(getDriver());
            webElements.backBtn.click();
        }else {
            webElements.registerUsrBtn.click();
        }
        return PageFactory.initElements(getDriver(),UsersPage.class);
    }

    @Override
    public boolean isUserInSelectedGroup(String groupName){
        try{
            SeleniumUtils.selectByVisibleText(webElements.selectedGroupsElement,groupName);
            return true;
        }catch (NoSuchElementException ex){
            logger.info("The User isn't present in the " + groupName + " Group " + ex);
            return false;
        }
    }

    @Override
    public boolean removeUserFromGroup(String groupName){
        SeleniumUtils.selectByVisibleText(webElements.selectedGroupsElement,groupName);
        webElements.removeUsrFromGroupBtn.click();
        webElements.registerUsrBtn.click();
        if(SeleniumUtils.isAlertPresent(getDriver())) {
            SeleniumUtils.clickOkOnAlert(getDriver());
            return false;
        }else{
           return PageFactory.initElements(getDriver(),UsersPage.class).isFullyLoaded();
        }
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(className = "Titlebar")
        WebElement titleBar;

        @FindBy(name = "AllGroups")
        WebElement availableGroupsElement;

        @FindBy(name = "UserGroups")
        WebElement selectedGroupsElement;

        @FindBy(xpath = "(//td//input[@type = 'Button'])[1]")
        WebElement addUsrToGroupBtn;

        @FindBy(xpath = "(//td//input[@type = 'Button'])[2]")
        WebElement removeUsrFromGroupBtn;

        @FindBy(xpath = "//td[@class = 'BackButton' and text()[contains(.,'Register')]]")
        WebElement registerUsrBtn;

        @FindBy(xpath = "//td[@class = 'BackButton' and text()[contains(.,'Register')]]")
        WebElement backBtn;

    }
}

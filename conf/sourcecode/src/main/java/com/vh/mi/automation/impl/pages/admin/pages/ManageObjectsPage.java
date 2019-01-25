package com.vh.mi.automation.impl.pages.admin.pages;

import com.vh.mi.automation.api.pages.admin.pages.IManageObjects;
import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IApplications;
import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IUsers;
import com.vh.mi.automation.impl.pages.admin.pages.manageObjects.ApplicationsPage;
import com.vh.mi.automation.impl.pages.admin.pages.manageObjects.UsersPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 5/11/2017.
 */
public class ManageObjectsPage extends AbstractWebComponent implements IManageObjects {

    private final WebElements webElements;

    public ManageObjectsPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.titleBar.getText().contains("Manage Objects");
    }

    @Override
    public IApplications goToApplicationsPage() {
        SeleniumUtils.click(webElements.applicationButton);
        return PageFactory.initElements(getDriver(), ApplicationsPage.class);
    }

    @Override
    public IUsers goToUsersPage() {
        SeleniumUtils.click(webElements.usersButton);
        return PageFactory.initElements(getDriver(), UsersPage.class);
    }


    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(className = "Titlebar")
        WebElement titleBar;

        @FindBy(xpath = "(//td[@class='MainMenuButton'])[7]")
        WebElement applicationButton;

        @FindBy(xpath = "(//td[@class='MainMenuButton'])[1]")
        WebElement usersButton;
    }
}

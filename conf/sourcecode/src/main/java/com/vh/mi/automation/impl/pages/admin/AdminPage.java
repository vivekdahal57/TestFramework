package com.vh.mi.automation.impl.pages.admin;

import com.vh.mi.automation.api.pages.admin.IAdminPage;
import com.vh.mi.automation.api.pages.admin.pages.IManageObjects;
import com.vh.mi.automation.api.pages.admin.pages.ISecurity;
import com.vh.mi.automation.impl.pages.admin.pages.ManageObjectsPage;
import com.vh.mi.automation.impl.pages.admin.pages.SecurityPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Represents any generic admin Page
 *
 * @author nimanandhar
 */
public class AdminPage extends AbstractWebComponent implements IAdminPage {
    private final WebElements webElements;

    public AdminPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.titleBar.isDisplayed();
    }

    @Override
    public String getPageTitle() {
        return webElements.titleBar.getText().trim();
    }

    @Override
    public IManageObjects goToManageObjectsPage() {
        SeleniumUtils.click(webElements.manageObjectsButton);
        return PageFactory.initElements(getDriver(), ManageObjectsPage.class);
    }

    @Override
    public ISecurity goToSecurityPage() {
        SeleniumUtils.click(webElements.securityButton);
        return PageFactory.initElements(getDriver(), SecurityPage.class);
    }


    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(className = "Titlebar")
        WebElement titleBar;

        @FindBy(xpath = "(//td[@class='MainMenuButton'])[2]")
        WebElement manageObjectsButton;

        @FindBy(xpath = "(//td[@class='MainMenuButton'])[3]")
        WebElement securityButton;
    }

}

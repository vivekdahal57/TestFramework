package com.vh.mi.automation.impl.pages.admin.pages.Security;

import com.vh.mi.automation.api.pages.admin.pages.security.IFeatureControlForUser;
import com.vh.mi.automation.api.pages.admin.pages.security.ISecurityFramework;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 8/22/2017.
 */
public class FeatureControlForUserPage extends AbstractWebComponent implements IFeatureControlForUser{
    private final WebElements webElements;

    public FeatureControlForUserPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.titleBar.getText().contains("Security Frame Work For User");
    }

    @Override
    public ISecurityFramework applySFWForUser(String loginId) {
        SeleniumUtils.selectByVisibleText(webElements.selectUserElement, getLoginNameFromLoginId(loginId));
        SeleniumUtils.click(webElements.nextButton);
        return PageFactory.initElements(getDriver(), SecurityFrameworkPage.class);
    }

    private String getLoginNameFromLoginId(String loginId){
        return loginId.replaceFirst("_", " ");
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(className = "Titlebar")
        WebElement titleBar;

        @FindBy(name ="selectUser")
        WebElement selectUserElement;

        @FindBy(className = "MainMenuButton")
        WebElement nextButton;
    }
}

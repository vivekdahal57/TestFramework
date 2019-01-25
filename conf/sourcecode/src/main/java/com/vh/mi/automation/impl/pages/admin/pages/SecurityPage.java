package com.vh.mi.automation.impl.pages.admin.pages;

import com.vh.mi.automation.api.pages.admin.pages.ISecurity;
import com.vh.mi.automation.api.pages.admin.pages.security.IFeatureControlForUser;
import com.vh.mi.automation.impl.pages.admin.pages.Security.FeatureControlForUserPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 8/22/2017.
 */
public class SecurityPage extends AbstractWebComponent implements ISecurity {
    private final WebElements webElements;

    public SecurityPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.titleBar.getText().contains("Security");
    }

    @Override
    public IFeatureControlForUser goToFeatureControlForUserPage() {
        SeleniumUtils.click(webElements.featureControlForUserButton);
        return PageFactory.initElements(getDriver(), FeatureControlForUserPage.class);
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(className = "Titlebar")
        WebElement titleBar;

        @FindBy(xpath = "(//td[@class='MainMenuButton'])[4]")
        WebElement featureControlForUserButton;
    }

}

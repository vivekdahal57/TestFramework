package com.vh.mi.automation.impl.pages.saml;

import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82325 on 12/19/2016.
 */
public class NotAuthorizedPage extends AbstractWebComponent {
    private final WebElements webElements;

    public NotAuthorizedPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return getDriver().getTitle().equals("Medical Intelligence : Not Authorized");
    }

    public String getErrorMessage() {
        return webElements.notAuthorized.getText();
    }
    public String getErrorURL() {
        return getDriver().getCurrentUrl();
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(className = "d2-notAuthorized")
        private WebElement notAuthorized;
    }
}
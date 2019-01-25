package com.vh.mi.automation.api.pages.saml;

import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82325 on 1/19/2016.
 * This might be applicatable for others as well as this is a generic erro page for MI.
 * For now this file is available in saml pacakage which could be moved to other packages.
 */
public class ErrorPage extends AbstractWebComponent {

    private final WebElements webElements;

    public ErrorPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.getText().equals("Error Page");
    }

    public String getErrorMessage() {
        return webElements.errorMessage.getText();
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(className = "d2-err-head")
        private WebElement pageTitle = null;

        @FindBy(className = "err")
        private WebElement errorMessage = null;
    }
}

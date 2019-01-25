package com.vh.mi.automation.impl.pages.saml;

import com.vh.mi.automation.impl.pages.common.AbstractMIPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author nimanandhar
 */
public class SamlErrorPage extends AbstractWebComponent {
    private final WebElements webElements;

    public SamlErrorPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.getText().equals("Error!");
    }

    public String getErrorMessage() {
        return webElements.errorMessage.getText();
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(className = "d2-err-headTitle")
        private WebElement pageTitle = null;

        @FindBy(className = "d2-errSmallBlack")
        private WebElement errorMessage = null;
    }
}

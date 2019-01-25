package com.vh.mi.automation.impl.comp.loading;

import com.vh.mi.automation.impl.comp.AbstractLoadingCompNew;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author nimanandhar
 */
public class LoadingCustomization extends AbstractLoadingCompNew {
    private final WebElements webElements;

    public LoadingCustomization(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    public LoadingCustomization(WebDriver driver, int timeout) {
        super(driver, timeout);
        webElements = new WebElements(driver);
    }

    @Override
    public WebElement getLoadingComponent() {
        return webElements.loadingCustomizationElement;
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "_LoadingCustomization")
        WebElement loadingCustomizationElement;
    }
}

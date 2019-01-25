package com.vh.mi.automation.impl.comp.loading;

import com.vh.mi.automation.impl.comp.AbstractLoadingCompNew;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author nimanandhar
 */
public class LoadingPanelComponent extends AbstractLoadingCompNew {
    private final WebElements webElements;

    public LoadingPanelComponent(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    public LoadingPanelComponent(WebDriver driver, int timeout) {
        super(driver, timeout);
        webElements = new WebElements(driver);
    }

    @Override
    public WebElement getLoadingComponent() {
        return webElements.loadingPanel;
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "_LoadingPanel")
        WebElement loadingPanel;
    }
}

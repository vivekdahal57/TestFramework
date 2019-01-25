package com.vh.mi.automation.impl.comp.loading;

import com.vh.mi.automation.impl.comp.AbstractLoadingCompNew;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author nimanandhar
 */
public class LoadingPopup extends AbstractLoadingCompNew {
    private final WebElements webElements;

    public LoadingPopup(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    public LoadingPopup(WebDriver driver, int timeout) {
        super(driver, timeout);
        webElements = new WebElements(driver);
    }

    public LoadingPopup(WebDriver driver, String elementId) {
        super(driver);
        webElements = new WebElements(driver);
        webElements.loading = driver.findElement(By.id(elementId));
    }


    @Override
    public WebElement getLoadingComponent() {
        return webElements.loading;
    }
    

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "_Loading")
        WebElement loading;
    }
}

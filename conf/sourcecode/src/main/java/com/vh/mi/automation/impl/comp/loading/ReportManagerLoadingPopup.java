package com.vh.mi.automation.impl.comp.loading;

import com.vh.mi.automation.impl.comp.AbstractLoadingCompNew;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82716 on 5/12/2017.
 */
public class ReportManagerLoadingPopup extends AbstractLoadingCompNew {
    private final WebElements webElements;

    public ReportManagerLoadingPopup(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    public ReportManagerLoadingPopup(WebDriver driver, int timeout) {
        super(driver, timeout);
        webElements = new WebElements(driver);
    }

    public ReportManagerLoadingPopup(WebDriver driver, String elementId) {
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

        @FindBy(id = "systemWorking")
        WebElement loading;
    }
}

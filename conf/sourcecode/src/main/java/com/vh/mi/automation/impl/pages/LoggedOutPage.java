package com.vh.mi.automation.impl.pages;

import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82298 on 12/4/2015.
 */
public class LoggedOutPage extends AbstractWebComponent {

    private WebElements webElements;
    public LoggedOutPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.loggedOutTitle.isDisplayed();
    }

    public String getPageTitle() {
        return webElements.loggedOutTitle.getText();
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//div[@class='logout']/span")
        WebElement loggedOutTitle;



    }

}

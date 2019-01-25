package com.vh.mi.mxautomation.impl.pages.common;

import com.vh.mi.mxautomation.api.features.IMIPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public abstract class AbstractMIPage extends AbstractWebComponent implements IMIPage {

    private final WebElements webElements;

    public AbstractMIPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }


    @Override
    public String getDisplayedPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public boolean isFullyLoaded() {
        return getPageTitle().equalsIgnoreCase(getDisplayedPageTitle());
    }


    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(className = "header-title")
        private WebElement pageTitle = null;

    }

}

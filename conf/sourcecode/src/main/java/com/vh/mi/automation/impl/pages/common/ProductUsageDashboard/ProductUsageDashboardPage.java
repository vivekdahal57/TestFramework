package com.vh.mi.automation.impl.pages.common.ProductUsageDashboard;

import com.vh.mi.automation.api.pages.common.ProductUsageDashboard.IProductUsageDashboardPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10314 on 7/26/2017.
 */
public class ProductUsageDashboardPage extends AbstractWebComponent implements IProductUsageDashboardPage {
    private final WebElements webElements;
    public ProductUsageDashboardPage(WebDriver driver) {
        super(driver);
        this.webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.isDisplayed();
    }

    public static class WebElements{
        private WebElements (WebDriver webDriver){
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"page-wrapper\"]/div/div[1]/div/h1/small")
        private WebElement pageTitle;
    }
}

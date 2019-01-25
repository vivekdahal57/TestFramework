package com.vh.mi.automation.impl.pages.common;

import com.vh.mi.automation.api.pages.common.IOAMPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 8/28/2017.
 */
public class OAMPage extends AbstractWebComponent implements IOAMPage {
    private static String TITLE_NAME = "OAM Explorer";

    private final WebElements webElements;
    public OAMPage(WebDriver driver) {
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

        @FindBy(className = "Titlebar")
        private WebElement pageTitle;
    }
}

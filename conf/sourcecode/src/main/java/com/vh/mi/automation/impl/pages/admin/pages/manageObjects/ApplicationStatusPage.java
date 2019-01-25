package com.vh.mi.automation.impl.pages.admin.pages.manageObjects;

import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IApplicationStatus;
import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IChangeDatabase;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 5/11/2017.
 */
public class ApplicationStatusPage extends AbstractWebComponent implements IApplicationStatus {

    private final WebElements webElements;

    public ApplicationStatusPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    public IChangeDatabase changeDatabase(){
        SeleniumUtils.click(webElements.changeDatabaseLink);
        return PageFactory.initElements(getDriver(), ChangeDatabasePage.class);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.titleBar.getText().contains("Application Status");
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(className = "Titlebar")
        WebElement titleBar;

        @FindBy(xpath = "//a[text()='Change Database']")
        WebElement changeDatabaseLink;

    }
}

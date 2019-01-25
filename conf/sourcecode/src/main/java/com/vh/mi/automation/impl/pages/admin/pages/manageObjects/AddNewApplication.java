package com.vh.mi.automation.impl.pages.admin.pages.manageObjects;

import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IAddNewApplication;
import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IRegisterApplication;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 5/15/2017.
 */
public class AddNewApplication  extends AbstractWebComponent implements IAddNewApplication{

    WebElements webElements;

    public AddNewApplication(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.titleBar.getText().contains("Add New Application");
    }

    @Override
    public IRegisterApplication connectToApplications(String databaseType, String schemaName, String processingType) {
        SeleniumUtils.selectByValue(webElements.databaseTypeSelectElement, databaseType);
        SeleniumUtils.sendKeysToInput(schemaName, webElements.schemaNameTextBox);
        SeleniumUtils.selectByValue(webElements.processingTypeSelectElement, processingType);
        SeleniumUtils.click(webElements.connectButton);
        return PageFactory.initElements(getDriver(), RegisterApplication.class);
    }


    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(className = "Titlebar")
        WebElement titleBar;

        @FindBy(id = "DATABASETYPE")
        WebElement databaseTypeSelectElement;

        @FindBy(id = "databaseName")
        WebElement schemaNameTextBox;

        @FindBy(id = "processingType")
        WebElement processingTypeSelectElement;

        @FindBy(xpath = "(//td[@class = 'BackButton' and text()[contains(.,'Connect')]])")
        WebElement connectButton;

    }
}

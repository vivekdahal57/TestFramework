package com.vh.mi.automation.impl.pages.admin.pages.manageObjects;

import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IApplications;
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
public class ChangeDatabasePage extends AbstractWebComponent implements IChangeDatabase {
    private final WebElements webElements;

    public ChangeDatabasePage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.titleBar.getText().contains("Change Database");
    }

    @Override
    public void changeDatabaseTo(String databaseType, String databaseName) {
        selectDatabaseType(databaseType);
        selectDatabaseName(databaseName);
        SeleniumUtils.click(webElements.changeButton);
    }

    @Override
    public IApplications switchDatabase(String databaseType, String databaseOne, String databaseTwo) {
        String currentDatabaseName = SeleniumUtils.getCurrentValueFromSelectElement(webElements.databaseNameSelectComponent);
        if(databaseOne.equals(currentDatabaseName)){
            changeDatabaseTo(databaseType, databaseTwo);
        }else{
            changeDatabaseTo(databaseType, databaseOne);
        }
        return PageFactory.initElements(getDriver(), ApplicationsPage.class);
    }

    private void selectDatabaseType(String databaseType){
        SeleniumUtils.selectByValue(webElements.databaseTypeSelectComponent, databaseType);
    }

    private void selectDatabaseName(String databaseName){
        SeleniumUtils.selectByValue(webElements.databaseNameSelectComponent, databaseName);
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(className = "Titlebar")
        WebElement titleBar;

        @FindBy(xpath = "//select[@name='databaseType']")
        WebElement databaseTypeSelectComponent;

        @FindBy(xpath = "//select[@name='databaseName']")
        WebElement databaseNameSelectComponent;

        @FindBy(xpath = "//td[@class='NextButton']")
        WebElement changeButton;
    }
}

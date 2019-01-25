package com.vh.mi.automation.impl.pages.analytics.myDashboard;

import com.vh.mi.automation.api.pages.analytics.myDashboard.IMyDashboard;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.CohortSelection;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by i20345 on 11/13/2017.
 */
public class MyDashboard extends AbstractLandingPage implements IMyDashboard {
    public static final String MODULE_ID = "10000";
    private WebElements webElements;
    CohortSelection selectCohort;
    Actions action;
    LoadingPopup loading;


    public MyDashboard(WebDriver driver) {
        super(driver, MODULE_ID);
        selectCohort = new CohortSelection(getDriver());
        webElements = new WebElements(getDriver());
        loading = new LoadingPopup(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.fullyLoadedElement.isDisplayed();
    }


    public String addNewDashboard(String dashboardName) {
        webElements.dashboardName.sendKeys(dashboardName);
        webElements.okButton.click();
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Dashboard Help")));
        String name = webElements.newAddedDashboardName.getText();
        return name;
    }

    @Override
    public void deleteDashboard() {
        action = new Actions(getDriver());
        action.moveToElement(webElements.dashboardHover).build().perform();
        webElements.btnDeleteDashboard.click();
        webElements.btnDeleteOkDashboard.click();
        loading.waitTillDisappears();
    }

    public void addNewWidget() {
        action = new Actions(getDriver());
        action.moveToElement(webElements.addWidgetHover).build().perform();
        webElements.addWidgetbtn.click();
        loading.waitTillDisappears();
        webElements.checkWidgetCheckbox.click();
        webElements.addWidgetOkButton.click();
        loading.waitTillDisappears();
    }

    public void addExistingCohort(String cohortId) {
        selectCohort.doSelect();
        selectCohort.setCohort(cohortId);
        loading.waitTillDisappears();
    }

    @Override
    public String setCohort(String cohortId) {
        return cohortId;
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        //fully loaded element
        @FindBy(id = "dashboardName")
        private WebElement fullyLoadedElement = null;

        //Dashboard Name
        @FindBy(id = "dashboardName")
        private WebElement dashboardName = null;

        //Ok button
        @FindBy(id = "d2Form:newDashboard")
        private WebElement okButton = null;

        //name of the newly added dashboard
        @FindBy(id = "pageTitle")
        private WebElement newAddedDashboardName = null;


        //add widget hover
        @FindBy(xpath = "//*[@id=\"d2Form:addWidgetMenu\"]/table/tbody/tr/td/div[2]")
        private WebElement addWidgetHover = null;

        //add widget button
        @FindBy(xpath = "//*[@id=\"addWidgetMenu_menu\"]/div[1]")
        private WebElement addWidgetbtn = null;

        //add widget button
        @FindBy(id = "widgetItemSelection_1")
        private WebElement checkWidgetCheckbox = null;

        //add widget ok button
        @FindBy(xpath = "//*[@id=\"d2Form:_addWidgetContentTable\"]/tbody/tr[2]/td/div[2]/div[1]/input[1]")
        private WebElement addWidgetOkButton = null;

        //widget div element
        @FindBy(id = "da3c8ec404ff004045720db721ff0333bdaef60f")
        private WebElement widgetDivElement = null;

        //Dashboard Hover
        @FindBy(className = "d2-tglPanel-mark_1")
        private WebElement dashboardHover = null;

        //Delete Dashboard button
        @FindBy(id = "d2Form:deleteDashboard")
        private WebElement btnDeleteDashboard = null;

        //Delete Dashboard ok button
        @FindBy(id = "d2Form:deleteDashboardButton")
        private WebElement btnDeleteOkDashboard = null;
    }
}

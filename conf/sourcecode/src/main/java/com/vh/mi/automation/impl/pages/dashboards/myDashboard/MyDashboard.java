package com.vh.mi.automation.impl.pages.dashboards.myDashboard;

import com.vh.mi.automation.api.pages.dashboards.myDashboard.IMyDashboard;

import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class MyDashboard extends AbstractLandingPage implements IMyDashboard {
    public static final String MODULE_ID = "10000";
    private WebElements webElements;
    AnalysisPeriod period;
    LoadingPopup loading;

    public MyDashboard(WebDriver driver) {
        super(driver, MODULE_ID);
        webElements = new WebElements(driver);
        period = new AnalysisPeriod(getDriver());
        loading=new LoadingPopup(getDriver());

    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.fullyLoadedElement.isDisplayed();
    }


    public String AddDashboard(String dashboardName)
    {
        //getDriver().switchTo().window(getDriver().getWindowHandle());
        webElements.addDashboardTextBoxName.sendKeys(dashboardName);
        webElements.addDashboardOkBtn.click();
        loading.waitTillDisappears();

        return webElements.pageTitle.getText();
    }

    public void  AddWidgets() throws InterruptedException {
        SeleniumUtils.hover(getDriver(),webElements.addWidgetHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.addWidgetBtn);
        webElements.addWidgetBtn.click();


        webElements.addWidget1Btn.click();
        webElements.addWidget2Btn.click();
        webElements.addWidget3Btn.click();
        webElements.addWidget4Btn.click();
        webElements.addWidget5Btn.click();
        webElements.addWidget6Btn.click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollTop = arguments[1];",getDriver().findElement(By.id("d2Form:addWidget_panel")), 320);

        webElements.addWidget18Btn.click();
        webElements.addWidget10Btn.click();
        webElements.addWidget16Btn.click();
        webElements.addWidget15Btn.click();
        webElements.addWidget17Btn.click();
        webElements.addWidget11Btn.click();


        js.executeScript("arguments[0].scrollTop = arguments[1];",getDriver().findElement(By.id("d2Form:addWidget_panel")), 500);


        webElements.addWidget12Btn.click();
        webElements.addWidget13Btn.click();
        webElements.addWidget14Btn.click();
        webElements.addWidget9Btn.click();
        webElements.addWidget7Btn.click();
        webElements.addWidget8Btn.click();


        webElements.addWidgetSBtn.click();

        Thread.sleep(2000);

        loading.waitTillDisappears();

    }

    public void  DeleteDashboard() throws InterruptedException {
        SeleniumUtils.hover(getDriver(),webElements.dashboardHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.deleteDashboardBtn);

        webElements.deleteDashboardBtn.click();
        webElements.deleteDashboardOkBtn.click();

        loading.waitTillDisappears();
        Thread.sleep(4000);

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,300)");

    }




    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        //fully loaded element
        @FindBy(id = "d2Form:preferenceButton")
        private WebElement fullyLoadedElement;

        //My Dashboard Name Text Box
        @FindBy(id = "dashboardName")
        private WebElement addDashboardTextBoxName;

        //Add Dashboard Ok Button
        @FindBy(id = "d2Form:newDashboard")
        private WebElement addDashboardOkBtn;

        //Add Widget Hover
        @FindBy(xpath = "//*[@id=\"d2Form:addWidgetMenu\"]/table/tbody/tr/td/div[2]")
        private WebElement addWidgetHover;


        //Add Widget Button
        @FindBy(id = "d2Form:allwidget_list_panel")
        private WebElement addWidgetBtn;

        //Add widget 1 checkbox
        @FindBy(id = "widgetItemSelection_1")
        private WebElement addWidget1Btn;

        //Add widget 2 checkbox
        @FindBy(id = "widgetItemSelection_2")
        private WebElement addWidget2Btn;

        //Add widget 3 checkbox
        @FindBy(id = "widgetItemSelection_3")
        private WebElement addWidget3Btn;

        //Add widget 4 checkbox
        @FindBy(id = "widgetItemSelection_4")
        private WebElement addWidget4Btn;

        //Add widget 5 checkbox
        @FindBy(id = "widgetItemSelection_5")
        private WebElement addWidget5Btn;

        //Add widget 6 checkbox
        @FindBy(id = "widgetItemSelection_6")
        private WebElement addWidget6Btn;

        //Add widget 7 checkbox
        @FindBy(id = "widgetItemSelection_7")
        private WebElement addWidget7Btn;

        //Add widget 8 checkbox
        @FindBy(id = "widgetItemSelection_8")
        private WebElement addWidget8Btn;

        //Add widget 9 checkbox
        @FindBy(id = "widgetItemSelection_9")
        private WebElement addWidget9Btn;

        //Add widget 10 checkbox
        @FindBy(id = "widgetItemSelection_10")
        private WebElement addWidget10Btn;

        //Add widget 11 checkbox
        @FindBy(id = "widgetItemSelection_11")
        private WebElement addWidget11Btn;

        //Add widget 12 checkbox
        @FindBy(id = "widgetItemSelection_12")
        private WebElement addWidget12Btn;

        //Add widget 13 checkbox
        @FindBy(id = "widgetItemSelection_13")
        private WebElement addWidget13Btn;

        //Add widget 14 checkbox
        @FindBy(id = "widgetItemSelection_14")
        private WebElement addWidget14Btn;

        //Add widget 15 checkbox
        @FindBy(id = "widgetItemSelection_15")
        private WebElement addWidget15Btn;

        //Add widget 16 checkbox
        @FindBy(id = "widgetItemSelection_16")
        private WebElement addWidget16Btn;

        //Add widget 17 checkbox
        @FindBy(id = "widgetItemSelection_17")
        private WebElement addWidget17Btn;

        //Add widget 18 checkbox
        @FindBy(id = "widgetItemSelection_18")
        private WebElement addWidget18Btn;

        //Add widgets button
        @FindBy(xpath = "//*[@id=\"d2Form:_addWidgetCDiv\"]//*[@class=\"rich-panel add-button-panel\"]//*[@class='button' and @value='Add']")
        private WebElement addWidgetSBtn;

        //Dashboard Hover
        @FindBy(xpath = "//*[@id=\"d2Form:dashboard_menu\"]/table/tbody/tr/td/div[2]")
        private WebElement dashboardHover;

        //Delete Dashboard Btn
        @FindBy(id = "d2Form:deleteDashboard")
        private WebElement deleteDashboardBtn;

        //Delete Dashboard Ok Btn
        @FindBy(id = "d2Form:deleteDashboardButton")
        private WebElement deleteDashboardOkBtn;

        //Page title element
        @FindBy(id="pageTitle")
        private WebElement pageTitle;

        //find sorry message
        @FindBy(xpath="//*[contains(text(), 'Sorry the widget could not be loaded at this time. Click below to retry')]")
        private WebElement widgetsSorryErrorMessage;


        //Newly created dashboard name in the switch panel to whether the dashboard has been deleted or not
        @FindBy(xpath="//*[@id=\"Dashboards_content\"]//*[contains(text(), 'automation_')]")
        private WebElement newDashboardName;

    }

}

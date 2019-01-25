package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Month;


import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.Drill.Month.IProviderProfilerMonthDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 1/11/18.
 */
public class ProviderProfilerMonthDrillPage extends AbstractDrillPage implements IProviderProfilerMonthDrillPage {
    private WebElements webElements;
    private ProviderProfilerMonthDrillPageDataGrid dataGrid;


    public ProviderProfilerMonthDrillPage(WebDriver webDriver){
        super(webDriver);
        webElements = new WebElements(getDriver());
        dataGrid = new ProviderProfilerMonthDrillPageDataGrid(getDriver());

    }

    @Override
    public boolean isFullyLoaded() {
       return "(V044) Paid by Specialty".equals(webElements.pageTitle.getText());
    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public String getPageTitle() {
        return  webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }

    private static class WebElements {
        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        WebElement pageTitle;
    }
}

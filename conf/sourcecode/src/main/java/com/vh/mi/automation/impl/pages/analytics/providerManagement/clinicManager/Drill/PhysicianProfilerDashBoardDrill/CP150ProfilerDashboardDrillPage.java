package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianProfilerDashBoardDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.Drill.ICP150ProfilerDashBoardDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 11/22/17.
 */
public class CP150ProfilerDashboardDrillPage extends AbstractDrillPage implements ICP150ProfilerDashBoardDrillPage {
    private WebElements webElements;
    private CP150ProfilerDashboardDrillPageDataGrid dataGrid;

    public CP150ProfilerDashboardDrillPage(WebDriver webDriver){
        super(webDriver);
        webElements =  new WebElements(webDriver);
        dataGrid = new CP150ProfilerDashboardDrillPageDataGrid(getDriver());
    }


    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.getText().equalsIgnoreCase("(CP150) Profiler Dashboard");
    }

    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    private static class WebElements{

        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;
    }
}

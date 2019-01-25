package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianProfilerDashBoardDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 11/27/17.
 */
public class CP150ProfilerDashboardDrillPageDataGrid extends AbstractDataGrid {

    public CP150ProfilerDashboardDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);

    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return null;
    }
}

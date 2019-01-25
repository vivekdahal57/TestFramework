package com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.drill.drillPopulationRiskDrivers;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 1/29/18.
 */
public class AllPopulationDrillPageDataGrid extends AbstractDataGrid {

    public AllPopulationDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);;
    }
    @Override
    protected IDataGridColumn getColumn(String id) {
        return null;
    }
}

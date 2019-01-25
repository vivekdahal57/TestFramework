package com.vh.mi.automation.impl.pages.analytics.claims.DrillDimensions.ServiceDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 12/8/17.
 */
public class ServiceDrillPageDataGrid extends AbstractDataGrid {


    public ServiceDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return ServiceDrillPageDataGridColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck(){
        return ServiceDrillPageDataGridColumns.SERVICE;
    }
}

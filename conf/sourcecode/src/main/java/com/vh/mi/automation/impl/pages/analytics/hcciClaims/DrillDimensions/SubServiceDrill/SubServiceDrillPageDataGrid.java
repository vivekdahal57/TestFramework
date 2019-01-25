package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.SubServiceDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 12/8/17.
 */
public class SubServiceDrillPageDataGrid extends AbstractDataGrid {


    public SubServiceDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return SubServiceDrillPageDataGridColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck(){
        return SubServiceDrillPageDataGridColumns.SUB_SERVICE_CATEGORY;
    }
}

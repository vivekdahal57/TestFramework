package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.ProcedureDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 12/8/17.
 */
public class ProcedureDrillPageDataGrid extends AbstractDataGrid {


    public ProcedureDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return ProcedureDrillPageColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck(){
        return ProcedureDrillPageColumns.PROCEDURE;
    }
}

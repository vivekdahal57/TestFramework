package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.ProcedureGroupDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.ProcedureDrill.ProcedureDrillPageColumns;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 12/8/17.
 */
public class ProcedureGroupDrillPageDataGrid extends AbstractDataGrid {

    public ProcedureGroupDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
    }
    @Override
    protected IDataGridColumn getColumn(String id) {
        return ProcedureDrillPageColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck(){
        return ProcedureGroupDrillPageColumns.PROCEDURE_GROUP;
    }
}

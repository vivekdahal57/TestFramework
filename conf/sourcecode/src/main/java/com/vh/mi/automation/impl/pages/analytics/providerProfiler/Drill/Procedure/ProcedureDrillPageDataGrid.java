package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Procedure;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 1/12/18.
 */
public class ProcedureDrillPageDataGrid extends AbstractDataGrid {

    public ProcedureDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
        columnIdExtractor = new ProcedureDrillPageColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id)
    {
        return ProcedureDrillPageDataGridColumns.fromId(id);
    }
}

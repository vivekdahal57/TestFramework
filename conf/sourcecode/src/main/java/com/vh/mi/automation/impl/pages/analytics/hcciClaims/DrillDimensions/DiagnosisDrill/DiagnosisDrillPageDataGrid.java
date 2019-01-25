package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.DiagnosisDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 11/30/17.
 */
public class DiagnosisDrillPageDataGrid extends AbstractDataGrid {

    public DiagnosisDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return DiagnosisDrillPageColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck(){
        return DiagnosisDrillPageColumns.DIAGNOSIS;
    }
}

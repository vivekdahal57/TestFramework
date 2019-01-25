package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.DiagnosisGroupDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 11/30/17.
 */
public class DiagnosisGroupDrillPageDataGrid extends AbstractDataGrid {

    public DiagnosisGroupDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);

    }
    @Override
    protected IDataGridColumn getColumn(String id) {
        return DiagnosisGroupDrillPageColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck(){
        return DiagnosisGroupDrillPageColumns.DIAGNOSIS_GROUP;
    }
}

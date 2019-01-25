package com.vh.mi.automation.impl.pages.analytics.claims.DrillDimensions.DrillDrugStrength;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 12/12/17.
 */
public class DrugStrengthDrillPageDataGrid extends AbstractDataGrid {

    public DrugStrengthDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return DrugStrengthDrillPageColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck() {
        return DrugStrengthDrillPageColumns.DRUG_STRENGTH;
    }
}

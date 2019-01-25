package com.vh.mi.automation.impl.pages.analytics.claims.DrillDimensions.DrillDrug;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 12/12/17.
 */
public class DrugDrillPageDataGrid extends AbstractDataGrid{

    public DrugDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return DrugDrillPageColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck() {
        return DrugDrillPageColumns.DRUG;
    }
}

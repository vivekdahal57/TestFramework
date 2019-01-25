package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.DrillPrescriber;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 12/12/17.
 */
public class PrescriberDrillPageDataGrid extends AbstractDataGrid {

    public PrescriberDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);

    }
    @Override
    protected IDataGridColumn getColumn(String id) {
        return PrescriberDrillPageColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck() {
        return PrescriberDrillPageColumns.PRESCRIBER;
    }
}

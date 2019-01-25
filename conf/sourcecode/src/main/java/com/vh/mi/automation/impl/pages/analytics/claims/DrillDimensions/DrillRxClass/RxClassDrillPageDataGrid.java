package com.vh.mi.automation.impl.pages.analytics.claims.DrillDimensions.DrillRxClass;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 12/12/17.
 */
public class RxClassDrillPageDataGrid extends AbstractDataGrid {

    public RxClassDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return RxClassDrillPageColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck() {
        return RxClassDrillPageColumns.RX_CLASS;
    }
}

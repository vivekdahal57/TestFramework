package com.vh.mi.automation.impl.pages.analytics.claims.DrillDimensions.ProviderDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 12/8/17.
 */
public class ProviderDrillPageDataGrid extends AbstractDataGrid {

    public ProviderDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return ProviderDrillPageColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck(){
        return ProviderDrillPageColumns.PROVIDER;
    }
}

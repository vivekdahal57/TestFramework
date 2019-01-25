package com.vh.mi.automation.impl.pages.analytics.claims.DrillDimensions.FundTypeDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 12/6/17.
 */
public class FundTypeDrillPageDataGrid extends AbstractDataGrid {

    public FundTypeDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return FundTypeDrillPageColumns.fromId(id);
    }


    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck(){
        return FundTypeDrillPageColumns.FUND_TYPE;
    }
}

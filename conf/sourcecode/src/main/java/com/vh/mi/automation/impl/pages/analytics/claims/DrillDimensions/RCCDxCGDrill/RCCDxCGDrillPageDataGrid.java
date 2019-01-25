package com.vh.mi.automation.impl.pages.analytics.claims.DrillDimensions.RCCDxCGDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 11/30/17.
 */
public class RCCDxCGDrillPageDataGrid extends AbstractDataGrid {


    public RCCDxCGDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return RCCDxCGDrillPageDataGridColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck(){
        return RCCDxCGDrillPageDataGridColumns.DXCG_RCC;
    }


}

package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.CCDxCGDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 12/1/17.
 */
public class CCDxCGDrillPageDataGrid extends AbstractDataGrid {


    public CCDxCGDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
    }
    @Override
    protected IDataGridColumn getColumn(String id) {
        return CCDxCGDrillPageDataGridColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck(){
        return CCDxCGDrillPageDataGridColumns.DXCG_CC;
    }
}

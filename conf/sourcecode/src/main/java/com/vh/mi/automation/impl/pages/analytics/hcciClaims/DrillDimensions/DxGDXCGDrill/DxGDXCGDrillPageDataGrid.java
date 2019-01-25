package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.DxGDXCGDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 12/1/17.
 */
public class DxGDXCGDrillPageDataGrid  extends AbstractDataGrid{

    public DxGDXCGDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return DxGDXCGDrillPageColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck(){
        return DxGDXCGDrillPageColumns.DXCG_DXG;
    }
}

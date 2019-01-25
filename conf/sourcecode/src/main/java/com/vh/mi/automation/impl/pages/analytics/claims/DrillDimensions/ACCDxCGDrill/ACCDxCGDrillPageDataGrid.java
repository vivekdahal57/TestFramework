package com.vh.mi.automation.impl.pages.analytics.claims.DrillDimensions.ACCDxCGDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 11/30/17.
 */
public class ACCDxCGDrillPageDataGrid extends AbstractDataGrid{


    public ACCDxCGDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return ACCDxCGDrillPageDataGridColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck(){
        return ACCDxCGDrillPageDataGridColumns.DXCG_ACC;
    }
}

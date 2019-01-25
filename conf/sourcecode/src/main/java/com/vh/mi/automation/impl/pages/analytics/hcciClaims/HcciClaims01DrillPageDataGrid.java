package com.vh.mi.automation.impl.pages.analytics.hcciClaims;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 2/20/18.
 */
public class HcciClaims01DrillPageDataGrid extends AbstractDataGrid {

    public HcciClaims01DrillPageDataGrid(WebDriver driver){
        super(driver);

    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return HcciClaims01DrillPageDataGridColumns.fromId(id);
    }

}

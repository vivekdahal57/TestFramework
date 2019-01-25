package com.vh.mi.automation.impl.pages.analytics.claims;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 2/20/18.
 */
public class Claims01DrillPageDataGrid extends AbstractDataGrid {

    public Claims01DrillPageDataGrid(WebDriver driver){
        super(driver);

    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return Claims01DrillPageDataGridColumns.fromId(id);
    }

}

package com.vh.mi.automation.impl.pages.queryBuilder.stratifier;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.PredefinedCohortsDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10314 on 7/21/2017.
 */
public class PredefinedCohortsDataGrid extends AbstractDataGrid {

    public PredefinedCohortsDataGrid(WebDriver driver) {
        super(driver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return PredefinedCohortsDataGridColumn.fromId(id);
    }
}

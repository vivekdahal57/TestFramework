package com.vh.mi.automation.impl.pages.queryBuilder.mvca.basic;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.queryBuilder.mvca.basic.MVCABasic301BDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

public class MVCABasic301BDataGrid extends AbstractDataGrid {

    public MVCABasic301BDataGrid(WebDriver driver) {
        super(driver);
        columnIdExtractor = new MVCABasic301BColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return MVCABasic301BDataGridColumn.fromId(id);
    }
}

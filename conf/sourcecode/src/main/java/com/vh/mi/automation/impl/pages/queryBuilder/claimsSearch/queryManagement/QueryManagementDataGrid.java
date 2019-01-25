package com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.queryManagement;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.queryBuilder.claimsSearch.queryManagement.QueryManagementDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i81306 on 5/5/2017.
 */
public class QueryManagementDataGrid extends AbstractDataGrid {

    public QueryManagementDataGrid(WebDriver driver) {
        super(driver);
        columnIdExtractor = new QueryManagementColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return QueryManagementDataGridColumn.fromId(id);
    }
}

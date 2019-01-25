package com.vh.mi.automation.impl.pages.favorites.myJobs;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.pages.favorites.myJobs.MyJobsDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by msedhain on 02/16/2017.
 */
public class MyJobsDataGrid extends AbstractDataGrid {

    public MyJobsDataGrid(WebDriver driver) {
        super(driver);
        columnIdExtractor = new MyJobsColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return MyJobsDataGridColumn.fromId(id);
    }



}

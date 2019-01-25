package com.vh.mi.automation.impl.pages.outReach;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.favorites.myJobs.MyJobsDataGridColumn;
import com.vh.mi.automation.api.pages.outReach.MemberListSummaryDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.favorites.myJobs.MyJobsColumnIdExtractor;
import org.openqa.selenium.WebDriver;

/**
 * Created by msedhain on 02/16/2017.
 */
public class MemberListSummaryDataGrid extends AbstractDataGrid {

    public MemberListSummaryDataGrid(WebDriver driver) {
        super(driver);
        columnIdExtractor = new MemberListSummaryColumnExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return MemberListSummaryDataGridColumn.fromId(id);
    }
}

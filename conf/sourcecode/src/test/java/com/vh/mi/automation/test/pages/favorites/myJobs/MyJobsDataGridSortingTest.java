package com.vh.mi.automation.test.pages.favorites.myJobs;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.pages.favorites.myJobs.IMyJobs;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.favorites.myJobs.MyJobs;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

/**
 * Created by nimanandhar on 12/9/2014.
 */
public class MyJobsDataGridSortingTest extends AbstractDataGrid_SortingTest {

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        IMyJobs myJobs = navigationPanel.doNavigateTo(MyJobs.class, defaultWaitTime);

        IDataGridCustomizer customizer = myJobs.getDataGridCustomizer();
        customizer.doSelectAll();
        customizer.doApplySelection();
        new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();

        return myJobs.getDataGrid();
    }
}

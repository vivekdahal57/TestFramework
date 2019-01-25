package com.vh.mi.automation.test.pages.analytics.providerProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.claims.Drill.IMonthDrillPage;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.IProviderProfilerV044;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.ProviderProfilerV044;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

/**
 * Created by i10359 on 1/15/18.
 */
public class ProviderProfilerMonthSortingTest  extends AbstractDataGrid_SortingTest{
    IDrillPage monthDrillPage;

    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        IProviderProfilerV044 v044 = navigationPanel.doNavigateTo(ProviderProfilerV044.class, defaultWaitTime);
        v044.doWaitTillFullyLoaded(defaultWaitTime);
        dataGrid = v044.getDataGrid();
        WaitUtils.waitForSeconds(5);
        monthDrillPage = dataGrid.getRows().get(0).doDrillByOnSameWindow("Month");
        monthDrillPage.isFullyLoaded();
        dataGrid =  monthDrillPage.getDataGrid();
        return dataGrid;
    }

}

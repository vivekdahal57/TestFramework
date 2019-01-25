package com.vh.mi.automation.test.pages.analytics.providerProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.IProviderProfilerV044;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.ProviderProfilerV044;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

/**
 * Created by i10359 on 1/15/18.
 */
public class ProviderProfilerSortingTest extends AbstractDataGrid_SortingTest {

    @BeforeClass
    public void setUp(){
    super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        IProviderProfilerV044 v044 = navigationPanel.doNavigateTo(ProviderProfilerV044.class, defaultWaitTime);
        v044.doWaitTillFullyLoaded(defaultWaitTime);
        return v044.getDataGrid();
    }
}

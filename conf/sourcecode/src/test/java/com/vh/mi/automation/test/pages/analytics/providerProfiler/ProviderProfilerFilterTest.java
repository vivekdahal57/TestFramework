package com.vh.mi.automation.test.pages.analytics.providerProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.IProviderProfilerV044;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.ProviderProfilerV044;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_FilterTest;
import org.testng.annotations.BeforeClass;


import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 1/11/18.
 */
public class ProviderProfilerFilterTest extends AbstractDataGrid_FilterTest {
    private IProviderProfilerV044 v044;

    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        v044 = navigationPanel.doNavigateTo(ProviderProfilerV044.class, defaultWaitTime);
        v044.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(v044.getPageTitle());
        return v044.getDataGrid();
    }

    @Override
    public List<IDataGridColumn> getColumnsToTest() {
        return dataGrid.getColumns();
    }

}

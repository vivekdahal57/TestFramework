package com.vh.mi.automation.test.pages.analytics.providerProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.IProviderProfilerV044;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.ProviderProfilerV044;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 1/15/18.
 */
public class ProviderProfilerV046dDiagnosisSortingTest extends AbstractDataGrid_SortingTest {
    IDrillPage monthDrillPage;
    IDrillPage diagnosisCodeDrillPage;

    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        IProviderProfilerV044 v044 = navigationPanel.doNavigateTo(ProviderProfilerV044.class, defaultWaitTime);
        v044.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(v044.getPageTitle());
        monthDrillPage = v044.getDataGrid().getRows().get(0).doDrillByOnSameWindow("Month");
        monthDrillPage.doWaitTillPopUpDisappears();
        diagnosisCodeDrillPage = monthDrillPage.getDataGrid().getRows().get(0).doDrillByOnSameWindow("Diagnosis Code");
        diagnosisCodeDrillPage.isFullyLoaded();
        dataGrid = diagnosisCodeDrillPage.getDataGrid();
        return dataGrid;
    }
}

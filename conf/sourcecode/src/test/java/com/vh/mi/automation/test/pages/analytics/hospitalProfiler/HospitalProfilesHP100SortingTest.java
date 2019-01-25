package com.vh.mi.automation.test.pages.analytics.hospitalProfiler;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.pages.analytics.hospitalProfiler.IHospitalProfilesHP100;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.HospitalProfilesHP100;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

import static org.fest.assertions.Assertions.assertThat;

public class HospitalProfilesHP100SortingTest extends AbstractDataGrid_SortingTest {
    private IHospitalProfilesHP100 hp100;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        hp100 = navigationPanel.doNavigateTo(HospitalProfilesHP100.class, defaultWaitTime);
        hp100.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(hp100.getPageTitle());

        IDataGridCustomizer customizer = hp100.getDataGridCustomizer();
        customizer.doSelectAll();
        customizer.doApplySelection();
        new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();

        return hp100.getDataGrid();
    }
}

package com.vh.mi.automation.test.pages.analytics.hospitalProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.pages.analytics.hospitalProfiler.Drill.IHP120AdmissionDrillPage;
import com.vh.mi.automation.api.pages.analytics.hospitalProfiler.IHospitalProfilesHP100;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.Drill.HP120AdmissionDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.HospitalProfilesHP100;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class HospitalProfilesHP120SortingTest extends AbstractDataGrid_SortingTest {
    IHospitalProfilesHP100 hp100;
    IHP120AdmissionDrillPage hp120;

    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        HospitalProfilesHP100 hp100 = navigationPanel.doNavigateTo(HospitalProfilesHP100.class, defaultWaitTime);
        hp100.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(hp100.getPageTitle());

        hp120 = (HP120AdmissionDrillPage) hp100.getDataGrid().getRows().get(0).doDrillByOnSameWindow("Admission");

        IDataGridCustomizer customizer = hp120.getDataGridCustomizer();
        customizer.doSelectAll();
        customizer.doApplySelection();
        new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();

        dataGrid = hp120.getDataGrid();
        return dataGrid;
    }
}

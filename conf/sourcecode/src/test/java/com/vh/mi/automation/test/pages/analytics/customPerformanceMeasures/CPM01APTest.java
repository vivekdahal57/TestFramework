package com.vh.mi.automation.test.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 1/30/18.
 */
public class CPM01APTest extends AbstractAnalysisPeriodTest {
    private ICPM01 icpm01;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass
    public void setUp(){
        super.setUp();
       icpm01 =  navigationPanel.doNavigateTo(CPM01.class, defaultWaitTime);
       assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(icpm01.getPageTitle());
       ap = icpm01.getAnalysisPeriod();
       appCyclePeriod = ap.getFullCyclePeriod();

    }
    @Override
    protected IAnalysisPeriod getAnalysisPeriod() {
        return ap;
    }

    @Override
    protected IPeriod getAppCyclePeriod() {
        return appCyclePeriod;
    }

    @Override
    protected IDataGrid getDataGrid() {
        return super.getDataGrid();
    }
}

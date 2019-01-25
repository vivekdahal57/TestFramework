package com.vh.mi.automation.test.pages.analytics.acoCostAndUtilization;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.api.pages.analytics.acoCostAndUtilization.ICostUtilizationReportCU001;
import com.vh.mi.automation.impl.pages.analytics.acoCostAndUtilization.CostUtilizationReportCU001;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.ACO_COST_AND_UTILIZATION;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80448 on 11/20/2014.
 */
@Test (groups = "Deprecated-Tests")
public class CostUtilizationReportCU001APTest extends AbstractAnalysisPeriodTest {
    private ICostUtilizationReportCU001 cu001;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        cu001 = navigationPanel.doNavigateTo(CostUtilizationReportCU001.class, defaultWaitTime);
        cu001.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());

        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(ACO_COST_AND_UTILIZATION.getPageTitle());

        ap = cu001.getAnalysisPeriod();
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

    public void test_cu001_instance() {
        Assert.assertNotNull(cu001);
        Assert.assertTrue(cu001 instanceof IHaveAnalysisPeriod);
    }

    @Test
    public void test_page_title() {
        assertThat(cu001.getDisplayedPageTitle()).isEqualTo(ACO_COST_AND_UTILIZATION.getPageTitle());
    }
}

package com.vh.mi.automation.test.pages.analytics.utilizationMetrics;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.pages.analytics.utilizationMetrics.IUtilizationMetricsHM50ByGroupSize;
import com.vh.mi.automation.impl.pages.analytics.utilizationMetrics.UtilizationMetricsHM50;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;

import static com.vh.mi.automation.api.constants.MILandingPages.UTILIZATION_METRICS;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80448 on 11/20/2014.
 */
public class UtilizationMetricsHM50APTest extends AbstractAnalysisPeriodTest {
    private IUtilizationMetricsHM50ByGroupSize hm50;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        hm50 = navigationPanel.doNavigateTo(UtilizationMetricsHM50.class, defaultWaitTime);

        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(UTILIZATION_METRICS.getPageTitle());

        ap = hm50.getAnalysisPeriod();
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
}

package com.vh.mi.automation.test.pages.analytics.providerProfiler;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.IProviderProfilerV044;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.ProviderProfilerV044;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;

import static com.vh.mi.automation.api.constants.MILandingPages.PROVIDER_PROFILER;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80448 on 11/20/2014.
 */
public class ProviderProfilerV044APTest extends AbstractAnalysisPeriodTest {
    private IProviderProfilerV044 v044;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        v044 = navigationPanel.doNavigateTo(ProviderProfilerV044.class, defaultWaitTime);

        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(PROVIDER_PROFILER.getPageTitle());

        ap = v044.getAnalysisPeriod();
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

package com.vh.mi.automation.test.pages.analytics.networkUtilization;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.pages.analytics.networkUtilization.INetworkUtilizationNU105;
import com.vh.mi.automation.impl.pages.analytics.networkUtilization.NetworkUtilizationNU105;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;

import static com.vh.mi.automation.api.constants.MILandingPages.NETWORK_UTILIZATION;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80448 on 11/21/2014.
 */
public class NetworkUtilizationNU105APTest extends AbstractAnalysisPeriodTest {
    private INetworkUtilizationNU105 nu105;

    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        nu105 = navigationPanel.doNavigateTo(NetworkUtilizationNU105.class, defaultWaitTime);

        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(NETWORK_UTILIZATION.getPageTitle());

        ap = nu105.getAnalysisPeriod();
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

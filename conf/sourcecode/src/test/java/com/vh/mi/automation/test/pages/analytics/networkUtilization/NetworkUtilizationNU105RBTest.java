package com.vh.mi.automation.test.pages.analytics.networkUtilization;

import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.pages.analytics.networkUtilization.INetworkUtilizationNU105;
import com.vh.mi.automation.impl.pages.analytics.networkUtilization.NetworkUtilizationNU105;
import com.vh.mi.automation.test.comp.AbstractReportingByTest;
import org.testng.annotations.BeforeClass;

import static com.vh.mi.automation.api.constants.MILandingPages.NETWORK_UTILIZATION;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80448 on 11/21/2014.
 */
public class NetworkUtilizationNU105RBTest extends AbstractReportingByTest {
    private INetworkUtilizationNU105 nu105;

    private IReportingBy rb;

    @BeforeClass()
    public void setUp() {
        super.setUp();

        nu105 = navigationPanel.doNavigateTo(NetworkUtilizationNU105.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(NETWORK_UTILIZATION.getPageTitle());
        rb = nu105.getReportingBy();
    }

    @Override
    public IReportingBy getReportingBy() {
        return rb;
    }
}

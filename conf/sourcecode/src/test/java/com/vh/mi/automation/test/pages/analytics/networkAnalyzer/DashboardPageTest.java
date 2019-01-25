package com.vh.mi.automation.test.pages.analytics.networkAnalyzer;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.impl.pages.analytics.networkAnalyzer.DashboardSOF101;
import com.vh.mi.automation.impl.pages.analytics.networkAnalyzer.DetailedAnalyticsOU107;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.DASHBOARD;
import static com.vh.mi.automation.api.constants.MILandingPages.DETAILED_ANALYTIC;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80448 on 11/20/2014.
 */
public class DashboardPageTest extends BaseTest {
    private DashboardSOF101 sof101;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        sof101 = navigationPanel.doNavigateTo(DashboardSOF101.class, context.getDefaultWaitTimeout());
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(DASHBOARD.getPageTitle());
    }


    @Test(description = "Test to check if the graph is loaded and has Allowed Amount($) text in it.")
    public void graphLoadedTest() {
        assertThat(sof101.isGraphLoaded()).isTrue();
    }
}

package com.vh.mi.automation.test.pages.analytics.networkAnalyzer;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.impl.pages.analytics.networkAnalyzer.DetailedAnalyticsOU107;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.DETAILED_ANALYTIC;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80448 on 11/20/2014.
 */
public class DetailedAnalyticsOU107APTest extends AbstractAnalysisPeriodTest {
    private DetailedAnalyticsOU107 ou107;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass()
    @Override
    public void setUp() {
        super.setUp();
        ou107 = navigationPanel.doNavigateTo(DetailedAnalyticsOU107.class, context.getDefaultWaitTimeout());

        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(DETAILED_ANALYTIC.getPageTitle());

        ap = ou107.getAnalysisPeriod();
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

    public void test_hp101_instance() {
        Assert.assertNotNull(ou107);
        Assert.assertTrue(ou107 instanceof IHaveAnalysisPeriod);
    }

    @Test
    public void test_page_title() {
        assertThat(ou107.getDisplayedPageTitle()).isEqualTo(DETAILED_ANALYTIC.getPageTitle());
    }
}

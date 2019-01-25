package com.vh.mi.automation.test.pages.analytics.executiveSummary;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.pages.analytics.executiveSummary.IExecutiveSummaryByGroupSize;
import com.vh.mi.automation.impl.pages.analytics.executiveSummary.ExecutiveSummary;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.EXECUTIVE_SUMMARY;
import static org.fest.assertions.Assertions.assertThat;

@Test(groups = "ExecSummary")
public class ExecutiveSummaryAPTest extends AbstractAnalysisPeriodTest {

    private IExecutiveSummaryByGroupSize exeSummary;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        exeSummary = navigationPanel.doNavigateTo(ExecutiveSummary.class, defaultWaitTime);

        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(EXECUTIVE_SUMMARY.getPageTitle());
        ap = exeSummary.getAnalysisPeriod();
        appCyclePeriod = ap.getFullCyclePeriod();


    }

    public void test_instances() {
        Assert.assertNotNull(exeSummary);
        Assert.assertNotNull(ap);
    }

    public void testPageTitle() {
        assertThat(exeSummary.getDisplayedPageTitle()).isEqualTo(
                exeSummary.getPageTitle());
    }

    @Test(priority = -1)
    public void testAPCycleWIthNavPanelDate() {
       assertThat(exeSummary.compareCycleDate()).isTrue();
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

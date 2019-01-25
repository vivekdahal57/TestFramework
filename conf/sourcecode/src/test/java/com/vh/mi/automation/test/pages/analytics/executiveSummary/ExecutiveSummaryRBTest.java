package com.vh.mi.automation.test.pages.analytics.executiveSummary;

import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.pages.analytics.executiveSummary.IExecutiveSummaryByGroupSize;
import com.vh.mi.automation.impl.pages.analytics.executiveSummary.ExecutiveSummary;
import com.vh.mi.automation.test.comp.AbstractReportingByTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

@Test(groups = "ExecSummary")
public class ExecutiveSummaryRBTest extends AbstractReportingByTest {

    private IExecutiveSummaryByGroupSize exeSummary;
    private IReportingBy rb;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        exeSummary = navigationPanel.doNavigateTo(ExecutiveSummary.class, defaultWaitTime);
        rb = exeSummary.getReportingBy();
    }

    public void test_instances() {
        Assert.assertNotNull(exeSummary);
    }

    @Test
    public void testPageTitle() {
        assertThat(exeSummary.getDisplayedPageTitle()).isEqualTo(
                exeSummary.getPageTitle());
    }

    @Override
    protected IReportingBy getReportingBy() {
        return rb;
    }

}

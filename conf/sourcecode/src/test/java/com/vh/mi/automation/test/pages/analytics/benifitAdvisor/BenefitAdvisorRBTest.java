package com.vh.mi.automation.test.pages.analytics.benifitAdvisor;

import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.features.IHaveReportingBy;
import com.vh.mi.automation.api.pages.analytics.benifitAdvisor.IBenefitAdvisor;
import com.vh.mi.automation.impl.pages.analytics.benifitAdvisor.BenefitAdvisor;
import com.vh.mi.automation.test.comp.AbstractReportingByTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.BENEFIT_ADVISOR;
import static org.fest.assertions.Assertions.assertThat;

public class BenefitAdvisorRBTest extends AbstractReportingByTest {
    private IBenefitAdvisor ba;
    private IReportingBy rb;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        ba = navigationPanel.doNavigateTo(BenefitAdvisor.class, defaultWaitTime);
        rb = ba.getReportingBy();
    }

    public void test_ba_instance() {
        Assert.assertNotNull(ba);
        Assert.assertTrue(ba instanceof IHaveReportingBy);
    }

    @Test
    public void test_page_title() {
        assertThat(ba.getDisplayedPageTitle()).isEqualTo(BENEFIT_ADVISOR.getPageTitle());
    }

    @Override
    public IReportingBy getReportingBy() {
        return rb;
    }
}

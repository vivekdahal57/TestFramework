package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.constants.MILandingPages;
import com.vh.mi.automation.api.features.IHaveReportingBy;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.test.comp.AbstractReportingByTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class Indv301RBTest extends AbstractReportingByTest {
    private IIndv301 indv301;
    private IReportingBy rb;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        indv301.popupExists();
        rb = indv301.getReportingBy();
    }

    @Test
    public void test_claims101_instance() {
        Assert.assertNotNull(indv301);
        Assert.assertTrue(indv301 instanceof IHaveReportingBy);
    }

    @Test
    public void test_page_title() {
        assertThat(indv301.getDisplayedPageTitle()).isEqualTo(MILandingPages.INDIVIDUALS_301.getPageTitle());
    }

    @Override
    public IReportingBy getReportingBy() {
        return rb;
    }
}

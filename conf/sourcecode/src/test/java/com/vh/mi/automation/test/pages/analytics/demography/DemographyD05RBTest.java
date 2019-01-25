package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.comp.AbstractReportingByTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = "Demography")
public class DemographyD05RBTest extends AbstractReportingByTest {
    private IDemographyD05 demoD05;
    private IReportingBy rb;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        demoD05 = navigationPanel.doNavigateTo(DemographyD05.class, defaultWaitTime);
        rb = demoD05.getReportingBy();
    }

    @Override
    public IReportingBy getReportingBy() {
        return rb;
    }
}

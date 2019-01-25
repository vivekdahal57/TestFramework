package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.DEMOGRAPHY;
import static org.fest.assertions.Assertions.assertThat;

@Test(groups = "Demography")
public class DemographyD05APTest extends AbstractAnalysisPeriodTest {
    private IDemographyD05 demoD05;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        demoD05 = navigationPanel.doNavigateTo(DemographyD05.class, defaultWaitTime);

        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(DEMOGRAPHY.getPageTitle());

        ap = demoD05.getAnalysisPeriod();
        appCyclePeriod = ap.getFullCyclePeriod();
    }

    @Override
    public IAnalysisPeriod getAnalysisPeriod() {
        return ap;
    }

    @Override
    protected IPeriod getAppCyclePeriod() {
        return appCyclePeriod;
    }
}

package com.vh.mi.automation.test.pages.analytics.hospitalProfiler;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.pages.analytics.hospitalProfiler.IHospitalProfilesHP100;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.HospitalProfilesHP100;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;

import static com.vh.mi.automation.api.constants.MILandingPages.HOSPITAL_PROFILER;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80448 on 11/20/2014.
 */
public class HospitalProfilesHP100APTest extends AbstractAnalysisPeriodTest {
    private IHospitalProfilesHP100 hp100;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        hp100 = navigationPanel.doNavigateTo(HospitalProfilesHP100.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(HOSPITAL_PROFILER.getPageTitle());

        ap = hp100.getAnalysisPeriod();
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

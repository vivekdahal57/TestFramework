package com.vh.mi.automation.test.pages.queryBuilder.mvca.expert;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.impl.pages.queryBuilder.mvca.expert.MVCAExpert301E;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;

import static com.vh.mi.automation.api.constants.MILandingPages.MVCA_EXPERT;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80448 on 11/20/2014.
 */
public class MVCAExpert301EAPTest extends AbstractAnalysisPeriodTest {
    private MVCAExpert301E mvcaExpert;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        mvcaExpert = navigationPanel.doNavigateTo(MVCAExpert301E.class, defaultWaitTime);

        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(MVCA_EXPERT.getPageTitle());

        ap = mvcaExpert.getAnalysisPeriod();
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

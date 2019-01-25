package com.vh.mi.automation.test.pages.queryBuilder.mvca.basic;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.pages.queryBuilder.mvca.basic.IMVCABasic301B;
import com.vh.mi.automation.impl.pages.queryBuilder.mvca.basic.MVCABasic301B;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;

import static com.vh.mi.automation.api.constants.MILandingPages.MVCA_BASIC;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80448 on 11/20/2014.
 */
public class MVCABasic301BAPTest extends AbstractAnalysisPeriodTest {
    private IMVCABasic301B mvcaBasic;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        mvcaBasic = navigationPanel.doNavigateTo(MVCABasic301B.class, defaultWaitTime);

        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(MVCA_BASIC.getPageTitle());

        ap = mvcaBasic.getAnalysisPeriod();
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

package com.vh.mi.automation.test.pages.analytics.hcciClaims.drillToClaims;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciS865DrillPage;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * Created by i10105 on 05/10/2018.
 */

@Test(groups = "claims101", sequential = true)
public class HcciClaims01S865APTest extends AbstractAnalysisPeriodTest {
    HcciS865DrillPage claims01DrillPage;;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;


    @BeforeClass()
    public void setUp() {
        super.setUp();
        HcciClaims01 claims101  = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);

        claims01DrillPage = (HcciS865DrillPage) claims101.getDataGrid().getRows().get(0).doDrillBy("Claims");

        ap = claims01DrillPage.getAnalysisPeriod();
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


    @Override
    protected IDataGrid getDataGrid() {
        return claims01DrillPage.getDataGrid();
    }

    @Override
    public void test_trend_default_options_selection(IAnalysisPeriod.APOption option) {
        // TODO: Claims has different implementations of default options in trending - to be implemented.
    }
}
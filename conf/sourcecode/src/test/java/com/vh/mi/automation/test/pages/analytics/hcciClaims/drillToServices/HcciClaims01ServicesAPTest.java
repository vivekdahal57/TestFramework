package com.vh.mi.automation.test.pages.analytics.hcciClaims.drillToServices;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01DrillPage;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01DrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciServicesDrillPage;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Created by i10105 on 05/10/2018.
 */

@Test(groups = "claims101", sequential = true)
public class HcciClaims01ServicesAPTest extends AbstractAnalysisPeriodTest {
    HcciServicesDrillPage claims01DrillPage;;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;


    @BeforeClass()
    public void setUp() {
        super.setUp();
        HcciClaims01 claims101  = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
        claims01DrillPage = (HcciServicesDrillPage) claims101.getDataGrid().getRows().get(0).doDrillBy("Services");
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
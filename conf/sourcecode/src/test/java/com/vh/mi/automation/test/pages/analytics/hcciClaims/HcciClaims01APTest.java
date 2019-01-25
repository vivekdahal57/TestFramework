package com.vh.mi.automation.test.pages.analytics.hcciClaims;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by bchataut on 10/5/2017.
 */

@Test(groups = "claims101", sequential = true)
public class HcciClaims01APTest extends AbstractAnalysisPeriodTest {
    private HcciIClaims01 hcciClaims101;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;


    @BeforeClass()
    public void setUp() {
        super.setUp();
        hcciClaims101 = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(hcciClaims101.getPageTitle());

        ap = hcciClaims101.getAnalysisPeriod();
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
        return hcciClaims101.getDataGrid();
    }

    @Override
    public void test_trend_default_options_selection(IAnalysisPeriod.APOption option) {
        // TODO: Claims has different implementations of default options in trending - to be implemented.
    }

}


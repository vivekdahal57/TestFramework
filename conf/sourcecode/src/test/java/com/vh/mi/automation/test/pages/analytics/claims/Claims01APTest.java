
package com.vh.mi.automation.test.pages.analytics.claims;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.pages.analytics.claims.IClaims01;
import com.vh.mi.automation.impl.pages.analytics.claims.Claims01;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Created by bchataut on 10/5/2017.
 */

@Test(groups = "claims101", sequential = true)
public class Claims01APTest extends AbstractAnalysisPeriodTest {
    private IClaims01 claims101;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        claims101 = navigationPanel.doNavigateTo(Claims01.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(claims101.getPageTitle());

        ap = claims101.getAnalysisPeriod();
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
        return claims101.getDataGrid();
    }

    @Override
    public void test_trend_default_options_selection(IAnalysisPeriod.APOption option) {
        // TODO: Claims has different implementations of default options in trending - to be implemented.
    }

}


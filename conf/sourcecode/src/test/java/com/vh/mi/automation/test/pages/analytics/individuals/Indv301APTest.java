package com.vh.mi.automation.test.pages.analytics.individuals;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.INDIVIDUALS_301;
import static org.fest.assertions.Assertions.assertThat;

@Test(groups = "Indv301")
public class Indv301APTest extends AbstractAnalysisPeriodTest {
    private IIndv301 indv301;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(INDIVIDUALS_301.getPageTitle());
        ap = indv301.getAnalysisPeriod();
        appCyclePeriod = ap.getFullCyclePeriod();
        indv301.popupExists();
    }

    @Test
    public void test_indv301_instance() {
        Assert.assertNotNull(indv301);
        Assert.assertTrue(indv301 instanceof IHaveAnalysisPeriod);
    }

    @Test
    public void test_page_title() {
        assertThat(indv301.getDisplayedPageTitle()).isEqualTo(INDIVIDUALS_301.getPageTitle());
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
        return indv301.getDataGrid();
    }



}

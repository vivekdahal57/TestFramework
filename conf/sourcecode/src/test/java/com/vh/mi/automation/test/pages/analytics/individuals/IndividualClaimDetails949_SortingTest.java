package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardIndividualClaimDetails;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

import static com.vh.mi.automation.api.constants.MILandingPages.INDIVIDUALS_301;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 1/9/18.
 */
public class IndividualClaimDetails949_SortingTest extends AbstractDataGrid_SortingTest {

    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        Indv301 indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(INDIVIDUALS_301.getPageTitle());
        indv301.popupExists();
        IndividualDashboardIndividualClaimDetails individualClaimDetails =  indv301.doDrillFromIndv301();
        IDataGridCustomizer customizer = individualClaimDetails.getDataGridCustomizer();
        customizer.doSelectAll();
        customizer.doApplySelection();
        new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();
        return individualClaimDetails.getDataGrid();
    }
}

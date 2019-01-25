package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardIndividualClaimDetails;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_FilterTest;
import org.testng.annotations.BeforeClass;

import java.util.List;

import static com.vh.mi.automation.api.constants.MILandingPages.INDIVIDUALS_301;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 1/8/18.
 */
public class IndividualClaimDetails949_FilteringTest extends AbstractDataGrid_FilterTest {
    private Indv301 indv301;
    private IndividualDashboardIndividualClaimDetails individualDashboardIndividualClaimDetails;

    @Override
    protected IDataGrid getDataGrid() {
        indv301 = navigationPanel.doNavigateTo(Indv301.class,defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(INDIVIDUALS_301.getPageTitle());
        indv301.popupExists();
        individualDashboardIndividualClaimDetails = indv301.doDrillFromIndv301();
        IDataGrid dataGrid = individualDashboardIndividualClaimDetails.getDataGrid();
        IDataGridCustomizer customizer = individualDashboardIndividualClaimDetails.getDataGridCustomizer();
        customizer.doSelectAll();
        customizer.doApplySelection();
        new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();
        return dataGrid;
    }


    @Override
    public List<IDataGridColumn> getColumnsToTest() {
        return dataGrid.getColumns();
    }
}

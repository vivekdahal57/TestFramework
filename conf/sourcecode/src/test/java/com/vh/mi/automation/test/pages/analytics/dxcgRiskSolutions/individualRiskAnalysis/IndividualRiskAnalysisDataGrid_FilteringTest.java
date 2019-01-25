package com.vh.mi.automation.test.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.IIndividualRiskAnalysis;
import com.vh.mi.automation.impl.pages.analytics.claims.Claims01;
import com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.dxcgRiskSolutionsStaticReports.StaticReportsRP010;
import com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.IndividualRiskAnalysis;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_FilterTest;
import org.testng.annotations.BeforeClass;

import java.util.List;

import static com.vh.mi.automation.api.constants.MILandingPages.DXCG_RISK_SOLUTIONS_STATIC_REPORTS;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 12/29/17.
 */
public class IndividualRiskAnalysisDataGrid_FilteringTest extends AbstractDataGrid_FilterTest {
    IIndividualRiskAnalysis  iIndividualRiskAnalysis;

    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
        User user = getUser("miautomation_reports_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        iIndividualRiskAnalysis = navigationPanel.doNavigateTo(IndividualRiskAnalysis.class, defaultWaitTime);
        dataGrid = getDataGrid();
    }

    @Override
    protected IDataGrid getDataGrid() {
        IDataGrid dataGrid = iIndividualRiskAnalysis.getDataGrid();
        return dataGrid;
    }


    @Override
    public List<IDataGridColumn> getColumnsToTest() {

        return dataGrid.getColumns();
    }
}

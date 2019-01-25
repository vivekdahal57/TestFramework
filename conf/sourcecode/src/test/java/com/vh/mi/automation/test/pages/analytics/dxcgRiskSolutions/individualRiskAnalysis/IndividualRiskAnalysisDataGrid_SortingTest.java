package com.vh.mi.automation.test.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.IIndividualRiskAnalysis;
import com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.IndividualRiskAnalysis;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.junit.Before;
import org.testng.annotations.BeforeClass;

/**
 * Created by i10359 on 1/2/18.
 */
public class IndividualRiskAnalysisDataGrid_SortingTest extends AbstractDataGrid_SortingTest {
    IIndividualRiskAnalysis  individualRiskAnalysis;

    @Override
    protected boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp(){
        super.setUp();
        User user = getUser("miautomation_reports_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        individualRiskAnalysis = navigationPanel.doNavigateTo(IndividualRiskAnalysis.class, defaultWaitTime);
        dataGrid = getDataGrid();
    }

    @Override
    protected IDataGrid getDataGrid() {

        return individualRiskAnalysis.getDataGrid();
    }
}

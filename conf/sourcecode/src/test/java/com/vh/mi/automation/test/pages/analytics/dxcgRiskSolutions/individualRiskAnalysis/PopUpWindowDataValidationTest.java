package com.vh.mi.automation.test.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.IIndividualRiskAnalysis;
import com.vh.mi.automation.api.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.IndividualRiskAnalysisDataGridColumn;
import com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.IndividualRiskAnalysis;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by i10359 on 1/2/18.
 */

public class PopUpWindowDataValidationTest extends BaseTest {
    private static final int FIRST_ROW = 0;
    IIndividualRiskAnalysis individualRiskAnalysis;
    IDataGrid dataGrid;

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
        dataGrid = individualRiskAnalysis.getDataGrid();
    }

    @Test(dataProvider = "drillOptionProviderForVariables")
    public void valueTest(String drillOption){
        String value = dataGrid.getRows().get(FIRST_ROW).getValue(dataGrid.getColumnIndex(IndividualRiskAnalysisDataGridColumn.fromId(drillOption)));
        dataGrid.getRows().get(FIRST_ROW).doDrillByPopup(drillOption);
        individualRiskAnalysis.waitTillPopupTableAppears();
        assert(value).equals(individualRiskAnalysis.getRecordsCountInPopup());
        individualRiskAnalysis.closePopUp();
    }


   @DataProvider(name = "drillOptionProviderForVariables")
    public Object[][] drillOptionProviderForTrendGraphFor(){
        IDataGridRow firstRow = dataGrid.getRows().get(FIRST_ROW);
        ImmutableList<String> drillOptions = firstRow.getDrillOptionsAfter("Variables");
        List<String> drillOptionList = new ArrayList<>();
        for(String drillOption : drillOptions){
            drillOptionList.add(drillOption);
        }
        return DataProviderUtils.getObjects(drillOptionList);
    }
}
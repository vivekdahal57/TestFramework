package com.vh.mi.automation.test.pages.queryBuilder.stratifier.diagnosis;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.DiagnosisByComponent;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Diagnosis.DiagnosisCode;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Diagnosis.DiagnosisGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.QueryBuilder;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i20345 on 1/9/2017.
 */

@Test (groups = { "Product-Critical" })
public class DiagnosisCodeTest extends BaseTest {
    private static final String SELECTED_TABLE_RECORDS_COUNT_TEXT = "No record found";
    private static final String SELECTION_LIMIT_EXCEEDED_TEXT = "Selection Limit Exceeded!";
    private static final String DIAGNOSIS_CODE_FILTER_TEXT = "Bronchitis";
    private static final String YES_OR_NO = "Yes";
    DiagnosisGroup diagnosisGroup;
    DiagnosisByComponent diagnosisByComponent;
    DiagnosisCode diagnosisCode;
    QueryBuilder queryBuilder;

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @BeforeMethod
    public void beforeEach(){
        queryBuilder = navigationPanel.doNavigateTo(QueryBuilder.class, TimeOuts.THIRTY_SECONDS);
        diagnosisGroup = queryBuilder.selectCriteriaComponent(DiagnosisGroup.class);
        diagnosisByComponent = diagnosisGroup.getDiagnosisByComponent();
        diagnosisCode = diagnosisByComponent.selectDiagnosisCode();
    }

    @Test( description = "Query Builder => Stratifier => Query Builder => Diagnosis => Diagnosis Code, " +
            "Include all on page and assert if included all on page codes are actually included.")
    public void includeAllOnPageTest(){
        diagnosisCode.includeAllOnPage();
        String totalCountOnPageRemainingTableBeforeRefreshed = diagnosisCode.getTotalCountOnPageRemainingTable();
        diagnosisCode.clickRefreshButton();
        String totalCountOnPageSelectedTableAfterRefreshed = diagnosisCode.getTotalCountOnPageSelectedTable();
        assertThat(totalCountOnPageRemainingTableBeforeRefreshed).isEqualTo(totalCountOnPageSelectedTableAfterRefreshed);
    }

    @Test(description = "Query Builder => Stratifier => Query Builder => Diagnosis => Diagnosis Code, " +
            "Exclude all on page and assert if excluded all on page codes are actually excluded.")
    public void excludeAllOnPageTest(){

        diagnosisCode.excludeAllOnPage();
        String totalCountOnPageRemainingTableBeforeRefreshed = diagnosisCode.getTotalCountOnPageRemainingTable();
        diagnosisCode.clickRefreshButton();
        String totalCountOnPageSelectedTableAfterRefreshed = diagnosisCode.getTotalCountOnPageSelectedTable();
        assertThat(totalCountOnPageRemainingTableBeforeRefreshed).isEqualTo(totalCountOnPageSelectedTableAfterRefreshed);
    }

    @Test(description = "Query Builder => Stratifier => Query Builder => Diagnosis => Diagnosis Code, " +
            "Include all on page, with IPOP Yes To All and assert if members are removed after Remove All in Selected Table.")
    public void includeAllOnPageAndRemoveAllTestWithIPOPYes(){
        diagnosisCode.includeAllOnPage();
        diagnosisCode.applyIPOPYesToAll();
        diagnosisCode.clickRefreshButton();
        diagnosisCode.removeAll();
        diagnosisCode.clickRefreshButton();
        String selectedTableRecords = diagnosisCode.checkSelectedTableIsEmpty();
        assertThat(selectedTableRecords).isEqualTo(SELECTED_TABLE_RECORDS_COUNT_TEXT);
    }

    @Test( description = "Query Builder => Stratifier => Query Builder => Diagnosis => Diagnosis Code, " +
            "Include all on page with IPOP Yes To All and assert if IPOP is Yes for all selected codes.")
    public void ipopSelectedOptionComparisionTest(){
        diagnosisCode.includeAllOnPage();
        diagnosisCode.applyIPOPToAll(YES_OR_NO);
        String totalCountOnRemainingTable = diagnosisCode.getTotalCountOnPageRemainingTable();
        diagnosisCode.clickRefreshButton();
        String IOPOSelectedCountInSelectedTable = diagnosisCode.getIPOPCountInSelectedTable(YES_OR_NO);
        assertThat(totalCountOnRemainingTable).isEqualTo(IOPOSelectedCountInSelectedTable);
    }

    @Test(description = "Query Builder => Stratifier => Query Builder => Diagnosis => Diagnosis Code, " +
            "Include codes greater that the allowed limit and assert if Maximim Limit Exceeded popup appears.")
    public void maxLimitSelectionTest(){
        diagnosisCode.includeAll();
        String limitExceedText = diagnosisCode.getSelectionLimitText();
        assertThat(limitExceedText).isEqualTo(SELECTION_LIMIT_EXCEEDED_TEXT);
    }

    @Test(description = "Query Builder => Stratifier => Query Builder => Diagnosis => Diagnosis Code, " +
            "Filter for Bronchitis, then Include All and assert if all the codes for Bronchitis are included")
    public void includeAllTest(){
        diagnosisCode.filterDiagnosisCodeWith(DIAGNOSIS_CODE_FILTER_TEXT);
        diagnosisCode.includeAll();
        String totalCountOnPageRemainingTableBeforeRefreshed = diagnosisCode.getTotalCountOnPageRemainingTableAfterFiltering();
        diagnosisCode.clickRefreshButton();
        String totalCountOnPageSelectedTableAfterRefreshed = diagnosisCode.getTotalCountOnPageSelectedTable();
        assertThat(totalCountOnPageRemainingTableBeforeRefreshed).isEqualTo(totalCountOnPageSelectedTableAfterRefreshed);
    }

    @AfterMethod
    public void reset(){
        diagnosisCode.reset();
    }

}




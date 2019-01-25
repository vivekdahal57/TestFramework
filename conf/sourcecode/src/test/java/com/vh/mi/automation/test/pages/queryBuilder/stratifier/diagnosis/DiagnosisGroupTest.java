package com.vh.mi.automation.test.pages.queryBuilder.stratifier.diagnosis;

import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Diagnosis.DiagnosisGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.QueryBuilder;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i20345 on 1/10/2017.
 */

@Test (groups = { "Product-Critical" })
public class DiagnosisGroupTest extends BaseTest {
    private static final Integer TOP_N = 2;
    DiagnosisGroup diagnosisGroup;
    QueryBuilder queryBuilder;

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @BeforeMethod
    public void beforeEach(){
        queryBuilder = navigationPanel.doNavigateTo(QueryBuilder.class, defaultWaitTime);
        diagnosisGroup = queryBuilder.selectCriteriaComponent(DiagnosisGroup.class);
    }

    @Test(description = "Query Builder => Stratifier => Query Builder => Diagnosis => Diagnosis Group, " +
            "Include All and assert if included codes are actually included. ")
    public void includeAllTest() {
        diagnosisGroup.IncludeAll();
        String countBeforeRefreshed = diagnosisGroup.getTotalRecordsInRemainingTable();
        diagnosisGroup.clickRefreshButton();
        String countAfterRefreshed = diagnosisGroup.getTotalRecordsInSelectedTable();
        assertThat(countBeforeRefreshed).isEqualTo(countAfterRefreshed);
    }

    @Test(description = "Query Builder => Stratifier => Query Builder => Diagnosis => Diagnosis Group, " +
            "Exclude All and assert if excluded codes are actually excluded.")
    public void excludeAllTest() {
        diagnosisGroup.ExcludeAll();
        String countBeforeRefreshed = diagnosisGroup.getTotalRecordsInRemainingTable();
        diagnosisGroup.clickRefreshButton();
        String countAfterRefreshed = diagnosisGroup.getTotalRecordsInSelectedTable();
        assertThat(countBeforeRefreshed).isEqualTo(countAfterRefreshed);
    }


    @Test(description = "Query Builder => Stratifier => Query Builder => Diagnosis => Diagnosis Group, " +
            "Check Top N checkboxes and hence assert that all the selected codes are available in the selected table.")
    public void refreshFunctionalityTest()
    {
        String totalRecordsInRemainingTableBeforeRefreshed = diagnosisGroup.getTotalRecordsInRemainingTable();
        diagnosisGroup.selectTopNCheckBoxes(TOP_N);
        diagnosisGroup.clickRefreshButton();
        Integer totalRecordsInRemainingTableAfterRefreshed = Integer.parseInt(diagnosisGroup.getTotalRecordsInRemainingTable());
        Integer totalRecordsInSelectedTableAfterRefreshed = Integer.parseInt(diagnosisGroup.getTotalRecordsInSelectedTable());
        Integer sumOfRecordsAfterRefreshed = totalRecordsInRemainingTableAfterRefreshed + totalRecordsInSelectedTableAfterRefreshed;
        assertThat(totalRecordsInRemainingTableBeforeRefreshed).isEqualTo(Integer.toString(sumOfRecordsAfterRefreshed));
    }

    @AfterMethod
    public void resetToPreviousState(){
        diagnosisGroup.reset();
    }
}



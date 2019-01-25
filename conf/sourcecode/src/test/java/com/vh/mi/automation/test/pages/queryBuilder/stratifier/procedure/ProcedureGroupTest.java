package com.vh.mi.automation.test.pages.queryBuilder.stratifier.procedure;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.impl.pages.queryBuilder.Individual301;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.CohortDetails;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PopulationAnalyser;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.QueryBuilder;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.procedure.ProcedureGroup;
import com.vh.mi.automation.test.base.BaseTest;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i20345 on 2/1/2017.
 */

@Test (groups = { "Product-Critical" })
public class ProcedureGroupTest extends BaseTest{
    public static final String SAVED_COHORT_NAME = RefineLogic.getRandomCohortName();
    private static final String COHORT_NAME_PLACEHOLDER = "${Cohort_name}";

    ProcedureGroup procedureGroup;
    QueryBuilder queryBuilder;
    PopulationAnalyser populationAnalyser;
    Individual301 individual301;
    RefineLogic refineLogic;
    CohortDetails cohortDetails;

    private static final Integer topN = 3;

    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @BeforeMethod
    public void beforeEach(){
        queryBuilder=navigationPanel.doNavigateTo(QueryBuilder.class, TimeOuts.THIRTY_SECONDS);
        procedureGroup = queryBuilder.selectCriteriaComponent(ProcedureGroup.class);
    }

    @Test(priority = 1)
    public void procedureGroupMemberCountTest(){
        procedureGroup.includeTopNProcedureCode(topN);
        procedureGroup.excludeTopNProcedureCode(topN);
        String quickCountValue = procedureGroup.getQuickCountValue();
        refineLogic = procedureGroup.goToRefineLogicPage();
        String refineLogicMemberCount = refineLogic.getMemberCount();
        individual301 = refineLogic.goToIndividualPage();
        String individualCount = individual301.getIndividualCount();
        individual301.closeCurrentWindowAndSwitchToMainWindow();
        refineLogic.saveCohort(SAVED_COHORT_NAME);

        assertThat(refineLogic.getReportGenerationSuccessfulMessage()).isEqualTo(refineLogic.REPORT_GENERATION_SUCCESSFUL_MESSAGE.replace(COHORT_NAME_PLACEHOLDER,SAVED_COHORT_NAME));

        populationAnalyser = navigationPanel.doNavigateTo(PopulationAnalyser.class,defaultWaitTime);
        cohortDetails=populationAnalyser.goToCohortDetails(SAVED_COHORT_NAME);
        String cohortDetailsMemberCount = cohortDetails.getMemberCount();
        assertThat(quickCountValue).isEqualTo(individualCount).isEqualTo(refineLogicMemberCount).isEqualTo(cohortDetailsMemberCount);
    }

    @Test(priority = 2)
    public void includeAllTest(){
        procedureGroup.includeAll();
        String beforeInclude=procedureGroup.getTotalRecordsFromRemainingTable();
        procedureGroup.clickRefreshButton();
        String afterInclude=procedureGroup.getTotalRecordsFromUpperTable();
        assertThat(beforeInclude).isEqualTo(afterInclude);
        procedureGroup.resetPage();
    }

    @Test(priority = 3)
    public void excludeAllTest(){
        procedureGroup.ExcludeAll();
        String beforeExclude=procedureGroup.getTotalRecordsFromRemainingTable();
        procedureGroup.clickRefreshButton();
        String afterExclude=procedureGroup.getTotalRecordsFromUpperTable();
        assertThat(beforeExclude).isEqualTo(afterExclude);
        procedureGroup.resetPage();
    }

    @Test(priority = 4)
    public void refreshFunctionalityTest(){
        procedureGroup.includeTopNProcedureCode(topN);
        procedureGroup.clickRefreshButton();
        assertThat(procedureGroup.getTotalRecordsFromUpperTable()).isEqualTo(topN.toString());
        procedureGroup.resetPage();
    }

    @Test(priority = 5)
    public void includeAllOnPageTest(){
        procedureGroup.includeAllOnPage();
        String totalProcedureCountOnAPAge = procedureGroup.getTotalProcedureGroupsCountOnAPage();
        procedureGroup.clickRefreshButton();
        assertThat(totalProcedureCountOnAPAge).isEqualTo(procedureGroup.getTotalRecordsFromUpperTable());
        procedureGroup.resetPage();
    }

    @Test(priority = 6)
    public  void excludeAllOnPageTest(){
        procedureGroup.excludeAllOnPage();
        String totalProcedureCountOnAPAge= procedureGroup.getTotalProcedureGroupsCountOnAPage();
        procedureGroup.clickRefreshButton();
        assertThat(totalProcedureCountOnAPAge).isEqualTo(procedureGroup.getTotalRecordsFromUpperTable());
        procedureGroup.resetPage();
    }

    @Test(priority = 7)
    public void includeAllOnPageAndRemoveAllTest(){
        procedureGroup.includeAllOnPage();
        procedureGroup.clickRefreshButton();
        procedureGroup.removeAll();
        procedureGroup.clickRefreshButton();
        Assertions.assertThat(procedureGroup.checkUpperTableIsEmpty().equalsIgnoreCase("No record found"));
        procedureGroup.resetPage();
    }
}
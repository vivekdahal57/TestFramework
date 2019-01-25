package com.vh.mi.automation.test.pages.queryBuilder.stratifier.procedure;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.ProcedureByComponent;
import com.vh.mi.automation.impl.pages.queryBuilder.Individual301;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.CohortDetails;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PopulationAnalyser;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.QueryBuilder;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.procedure.ProcedureCode;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.procedure.ProcedureGroup;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i20345 on 2/1/2017.
 */

@Test (groups = { "Product-Critical" })
public class ProcedureCodeTest extends BaseTest {
    public static final String SAVED_COHORT_NAME = RefineLogic.getRandomCohortName();
    private static final String COHORT_NAME_PLACEHOLDER = "${Cohort_name}";

    ProcedureGroup procedureGroup;
    ProcedureCode procedureCode;
    RefineLogic refineLogic;
    Individual301 individual301;
    PopulationAnalyser populationAnalyser;
    QueryBuilder queryBuilder;
    CohortDetails cohortDetails;
    ProcedureByComponent procedureByComponent;

    private static final Integer topN = 2;

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @BeforeMethod
    public void beforeEachMethod(){
        queryBuilder= navigationPanel.doNavigateTo(QueryBuilder.class, TimeOuts.SIXTY_SECONDS);
        procedureGroup = queryBuilder.selectCriteriaComponent(ProcedureGroup.class);
        procedureByComponent = procedureGroup.getProcedureByComponent();
        procedureCode = procedureByComponent.selectProcedureCode();
    }


    @Test(priority=1)
    public void procedureCodeMemberCountTest() {

        procedureCode.includeTopNProcedureCode(topN);
        procedureCode.excludeTopNProcedureCode(topN);
        procedureCode.updateQuickCount();
        String quickCountValue = procedureCode.getQuickCountValue();

        refineLogic = procedureCode.goToRefineLogicStratifier();
        String refineLogicMemberCount=refineLogic.getMemberCount();

        assertThat(refineLogicMemberCount).isEqualTo(quickCountValue);

        individual301 = refineLogic.goToIndividualPage();
        String individualCount=individual301.getIndividualCount();
        individual301.closeCurrentWindowAndSwitchToMainWindow();
        refineLogic.saveCohort(SAVED_COHORT_NAME);

        assertThat(refineLogic.getReportGenerationSuccessfulMessage()).isEqualTo(refineLogic.REPORT_GENERATION_SUCCESSFUL_MESSAGE.replace(COHORT_NAME_PLACEHOLDER,SAVED_COHORT_NAME));


        assertThat(individualCount).isEqualTo(refineLogicMemberCount);

        populationAnalyser = navigationPanel.doNavigateTo(PopulationAnalyser.class,defaultWaitTime);
        cohortDetails = populationAnalyser.goToCohortDetails(SAVED_COHORT_NAME);
        String cohortDetailsMemberCount = cohortDetails.getMemberCount();

        assertThat(cohortDetailsMemberCount).isEqualTo(individualCount);

        //Deleting Cohorts
        cohortDetails.goBackToPopulationAnalyser();
        populationAnalyser.removeCohort(SAVED_COHORT_NAME);
    }

    @Test(priority=2)
    public void procedureGroupMemberCountTest(){
        procedureCode.includeTopNProcedureCode(topN);
        procedureCode.excludeTopNProcedureCode(topN);
        procedureCode.clickAndOrButton();
        procedureCode.updateQuickCount();

        String quickCountValue=procedureCode.getQuickCountValue();

        RefineLogic refineLogic=procedureCode.goToRefineLogicStratifier();
        String memberCount=refineLogic.getMemberCount();

        Individual301 individual301=refineLogic.goToIndividualPage();
        String individualCount=individual301.getIndividualCount();

        individual301.closeCurrentWindowAndSwitchToMainWindow();

        refineLogic.saveCohort(SAVED_COHORT_NAME);

        assertThat(refineLogic.getReportGenerationSuccessfulMessage()).isEqualTo(refineLogic.REPORT_GENERATION_SUCCESSFUL_MESSAGE.replace(COHORT_NAME_PLACEHOLDER,SAVED_COHORT_NAME));


        PopulationAnalyser populationAnalyser=navigationPanel.doNavigateTo(PopulationAnalyser.class,defaultWaitTime);
        CohortDetails cohortDetails=populationAnalyser.goToCohortDetails(SAVED_COHORT_NAME);
        String cohortDetailsMemberCount=cohortDetails.getMemberCount();

        assertThat(quickCountValue.equalsIgnoreCase(memberCount) && memberCount.equalsIgnoreCase(individualCount) && individualCount.equalsIgnoreCase(cohortDetailsMemberCount)).isTrue();

        //Deleting Cohorts
        cohortDetails.goBackToPopulationAnalyser();
        populationAnalyser.removeCohort(SAVED_COHORT_NAME);
    }



    @Test(priority=3)
    public void refreshFunctionalityTest(){
        procedureCode.includeTopNProcedureCode(topN);
        procedureCode.clickRefreshButton();
        assertThat(procedureCode.getTotalProcedureGroupsAfterIncludingOrExcluding()).isEqualTo(topN.toString());
        procedureCode.resetPage();

    }

    @Test(priority=4)
    public void includeAllOnPageTest(){
        String totalProcedureGroupsOnAPAge= procedureCode.getTotalProcedureGroupsOnAPage();
        procedureCode.includeAllOnPage();
        procedureCode.clickRefreshButton();
        assertThat(totalProcedureGroupsOnAPAge).isEqualTo(procedureCode.getTotalProcedureGroupsAfterIncludingOrExcluding());
        procedureCode.resetPage();
    }

    @Test(priority=5)
    public  void excludeAllOnPageTest(){
        String totalProcedureGroupsOnAPAge= procedureCode.getTotalProcedureGroupsOnAPage();
        procedureCode.excludeAllOnPage();
        procedureCode.clickRefreshButton();
        assertThat(totalProcedureGroupsOnAPAge).isEqualTo(procedureCode.getTotalProcedureGroupsAfterIncludingOrExcluding());
        procedureCode.resetPage();
    }

    @Test(priority=6)
    public void includeAllOnPageAndRemoveAllTest(){
        procedureCode.includeAllOnPage();
        procedureCode.clickRefreshButton();
        procedureCode.removeAll();
        procedureCode.clickRefreshButton();
        assertThat(procedureCode.checkUpperTableIsEmpty().equalsIgnoreCase("No record found"));
        procedureCode.resetPage();
    }

    @Test(priority=7)
    public void maxLimitSelectionTest(){
        procedureCode.includeAll();
        String limitTest = procedureCode.getSelectionLimitText();
        assertThat(limitTest.equalsIgnoreCase("Selection Limit Exceeded!"));
        procedureCode.clickMaxLimitOkButton();
        procedureCode.resetPage();
    }

    @Test(priority=8)
    public void includeAllTestWithFilter(){
        procedureCode.filterDiagnosisCodeWith("GROUP");
        String S1 = procedureCode.getTotalProcedureGroupsOnAPage();
        procedureCode.includeAll();
        procedureCode.clickRefreshButton();
        String S2 = procedureCode.getTotalProcedureGroupsAfterIncludingOrExcluding();
        assertThat(S1.equalsIgnoreCase(S2));
        procedureCode.resetPage();
    }
}

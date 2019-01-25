package com.vh.mi.automation.test.pages.queryBuilder.stratifier;

import com.vh.mi.automation.impl.pages.queryBuilder.Individual301;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.BiometricsORLabs.BiometricsORLabs;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.CohortDetailMoreMetrics.CohortDetailAddMoreMetrics;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.CohortDetails;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.DemographicsAndRisk.DemographicsAndRiskCode;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Diagnosis.DiagnosisGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.HEDISClaimsBased.HEDISClaimsBased;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PopulationAnalyser;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.QueryBuilder;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.STARMeasures.STARMeasures;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10314 on 7/5/2017.
 */

@Test (groups = { "Product-Critical" })
public class StratifierComponentsRelatedTests extends BaseTest{
    public static final String SAVED_COHORT_NAME = RefineLogic.getRandomCohortName();
    private static final String COHORT_NAME_PLACEHOLDER = "${Cohort_name}";

    private static final String MEMBER_TREND_HEADING = "Member Trend";
    private static final Integer LOWER_AGE=20;
    private static final Integer UPPER_AGE=60;
    private static final Integer LOWER_RI = 1;
    private static final Integer UPPER_RI = 500;
    private static final Integer Nth_SELECTION_IN_HEDIS_PAGE = 10;
    private static final Integer Nth_SELECTION_IN_STAR_PAGE = 8;
    private static final Integer Nth_SELECTION_IN_BIOMETRICS_PAGE = 4;
    private static final String IDS_OF_METRICES_TO_BE_ADDED = "QRM-9015:,QRM-9014:,QRM-9012:";

    DemographicsAndRiskCode demographicsAndRiskCode;
    DiagnosisGroup diagnosisGroup;
    RefineLogic refineLogic;
    QueryBuilder queryBuilder;
    CohortDetails cohortDetails;
    Individual301 individual301;
    PopulationAnalyser populationAnalyser;
    HEDISClaimsBased hedisClaimsBased;
    STARMeasures starMeasures;
    BiometricsORLabs biometricsORLabs;
    CohortDetailAddMoreMetrics cohortDetailAddMoreMetrics;

    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @BeforeMethod
    public void navigationToQueryBuilderPage(){
        queryBuilder = navigationPanel.doNavigateTo(QueryBuilder.class,defaultWaitTime);
    }

    @Test(priority = 1, description = "Query Builder => Stratifier => Population Analyser => Cohort Details" +
            "get Member Trend info section heading to ensure if that the section is present in that page.")
    public void memberTrendExistenceInCohortDetailTest() {

        diagnosisGroup = queryBuilder.selectCriteriaComponent(DiagnosisGroup.class);
        diagnosisGroup.IncludeAll();

        demographicsAndRiskCode = queryBuilder.selectCriteriaComponent(DemographicsAndRiskCode.class);
        demographicsAndRiskCode.enterAge(LOWER_AGE,UPPER_AGE);

        refineLogic = demographicsAndRiskCode.goToRefineLogicPage();
        refineLogic.saveCohort(SAVED_COHORT_NAME);

        assertThat(refineLogic.getReportGenerationSuccessfulMessage()).isEqualTo(refineLogic.REPORT_GENERATION_SUCCESSFUL_MESSAGE.replace(COHORT_NAME_PLACEHOLDER,SAVED_COHORT_NAME));


        populationAnalyser = navigationPanel.doNavigateTo(PopulationAnalyser.class, defaultWaitTime);
        cohortDetails = populationAnalyser.goToCohortDetails(SAVED_COHORT_NAME);

        String memberTrendHeading = populationAnalyser.getMemberTrendInfoSectionHeading();
        assertThat(memberTrendHeading).isEqualTo(MEMBER_TREND_HEADING);

        //Deleting created cohort
        populationAnalyser = cohortDetails.goBackToPopulationAnalyser();
        populationAnalyser.removeCohort(SAVED_COHORT_NAME);
    }

    @Test(priority = 2, description = "Query Builder => Stratifier => Population Analyser => Cohort Details" +
            "get member counts in Cohort Detail and Individual Pages for any existing cohort and"+
            "assert that the counts match.")
    public void matchMemberCountForCohortDetailsPageTest() {

        hedisClaimsBased = queryBuilder.selectCriteriaComponent(HEDISClaimsBased.class);
        hedisClaimsBased.includeNSelections(Nth_SELECTION_IN_HEDIS_PAGE);

        starMeasures = queryBuilder.selectCriteriaComponent(STARMeasures.class);
        starMeasures.includeNSelections(Nth_SELECTION_IN_STAR_PAGE);

        demographicsAndRiskCode = queryBuilder.selectCriteriaComponent(DemographicsAndRiskCode.class);
        demographicsAndRiskCode.selectGenderAndMemberStatus();
        demographicsAndRiskCode.enterRiskScores(LOWER_RI, UPPER_RI);

        refineLogic = demographicsAndRiskCode.goToRefineLogicPage();
        refineLogic.saveCohort(SAVED_COHORT_NAME);

        assertThat(refineLogic.getReportGenerationSuccessfulMessage()).isEqualTo(refineLogic.REPORT_GENERATION_SUCCESSFUL_MESSAGE.replace(COHORT_NAME_PLACEHOLDER,SAVED_COHORT_NAME));


        populationAnalyser = navigationPanel.doNavigateTo(PopulationAnalyser.class, defaultWaitTime);
        cohortDetails = populationAnalyser.goToCohortDetails(SAVED_COHORT_NAME);
        String memberCountInCohortDetail = cohortDetails.getMemberCount();
        cohortDetails.goBackToPopulationAnalyser();

        individual301 = populationAnalyser.goToViewIndividual(SAVED_COHORT_NAME);
        String memberCountInIndividual = individual301.getIndividualCount();
        assertThat(memberCountInIndividual).isEqualTo(memberCountInCohortDetail);

        //Deleting created cohort
        individual301.closeCurrentWindowAndSwitchToMainWindow();
        populationAnalyser.removeCohort(SAVED_COHORT_NAME);

   }

    @Test(priority = 3, description = " Query Builder => Stratifier => Population Analyser => Cohort Details =>" +
            " Add More Metrics(QRMs) to the existing cohort. ")
    public void addMoreMetricsInCohortDetailTest(){
        diagnosisGroup = queryBuilder.selectCriteriaComponent(DiagnosisGroup.class);
        diagnosisGroup.IncludeAll();

        demographicsAndRiskCode = queryBuilder.selectCriteriaComponent(DemographicsAndRiskCode.class);
        demographicsAndRiskCode.enterAge(LOWER_AGE,UPPER_AGE);

        refineLogic = demographicsAndRiskCode.goToRefineLogicPage();
        refineLogic.saveCohort(SAVED_COHORT_NAME);

        assertThat(refineLogic.getReportGenerationSuccessfulMessage()).isEqualTo(refineLogic.REPORT_GENERATION_SUCCESSFUL_MESSAGE.replace(COHORT_NAME_PLACEHOLDER,SAVED_COHORT_NAME));


        populationAnalyser = navigationPanel.doNavigateTo(PopulationAnalyser.class, defaultWaitTime);
        cohortDetails = populationAnalyser.goToCohortDetails(SAVED_COHORT_NAME);

        cohortDetailAddMoreMetrics = cohortDetails.addMoreMetrics();
        cohortDetailAddMoreMetrics.addMetricesWithTheseIDs(IDS_OF_METRICES_TO_BE_ADDED);

        assertThat(cohortDetails.checkWhetherQRMWithTheseIDsAreAdded(IDS_OF_METRICES_TO_BE_ADDED)).isTrue();

        populationAnalyser = cohortDetails.goBackToPopulationAnalyser();
        populationAnalyser.removeCohort(SAVED_COHORT_NAME);
    }

    @Test(priority = 4, description = "Query Builder =>Stratifier => Query Builder => Get member count in Query Builder, Individual and " +
            "Refine Logic pages and assert that all the counts match.")
    public void matchMemberCountAcrossPagesTest(){

        biometricsORLabs = queryBuilder.selectCriteriaComponent(BiometricsORLabs.class);
        biometricsORLabs.includeNSelectionsInTheFirstPage(Nth_SELECTION_IN_BIOMETRICS_PAGE);

        demographicsAndRiskCode = queryBuilder.selectCriteriaComponent(DemographicsAndRiskCode.class);
        demographicsAndRiskCode.enterRiskScores(LOWER_RI, UPPER_RI);

        String memberCountInQueryBuilderPage = demographicsAndRiskCode.getMembersAccordingToSelectedCriteria();

        individual301 = demographicsAndRiskCode.goToIndividualPage();
        String memberCountInIndividualPage = individual301.getIndividualCount();
        assertThat(memberCountInIndividualPage).isEqualTo(memberCountInQueryBuilderPage);
        individual301.closeCurrentWindowAndSwitchToMainWindow();

        refineLogic = demographicsAndRiskCode.goToRefineLogicPage();
        String memberCountInRefineLogicPage = refineLogic.getMemberCount();

        individual301 = refineLogic.goToIndividualPage();

        String individualCount = individual301.getIndividualCount();
        assertThat(individualCount).isEqualTo(memberCountInRefineLogicPage);

        individual301.closeCurrentWindowAndSwitchToMainWindow();
    }

}

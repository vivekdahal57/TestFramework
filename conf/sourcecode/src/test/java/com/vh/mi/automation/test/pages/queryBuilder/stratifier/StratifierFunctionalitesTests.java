package com.vh.mi.automation.test.pages.queryBuilder.stratifier;

import com.vh.mi.automation.impl.pages.queryBuilder.Individual301;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.*;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.DemographicsAndRisk.DemographicsAndRiskCode;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Diagnosis.DiagnosisGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.HEDISClaimsBased.HEDISClaimsBased;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PopulationAnalyzerCohortAnalyzer.CohortAnalyzer;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PredefinedCohortsCohortDetails.PredefinedCohortsCohortDetails;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.STARMeasures.STARMeasures;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10314 on 7/20/2017.
 */

public class StratifierFunctionalitesTests extends BaseTest {
    public static final String SAVED_COHORT_NAME = RefineLogic.getRandomCohortName();
    private static final String COHORT_NAME_PLACEHOLDER = "${Cohort_name}";
    private static final Integer LOWER_AGE=20;
    private static final Integer UPPER_AGE=60;
    private static final Integer Nth_SELECTION_IN_HEDIS_PAGE = 10;
    private static final Integer Nth_SELECTION_IN_STAR_PAGE = 8;
    private static final Integer LOWER_RI = 1;
    private static final Integer UPPER_RI = 500;
    private static final String COHORT_DESCRIPTION = "CAD - High Risk";
    private static final String WORD_DOWNLOADED_FILE = "IndividualCohortSummary.rtf";
    private static final String EXCEl_DOWNLOADED_FILE = "cohortDetailExcel.xlsx";
    private List<String> CREATED_COHORT_NAMES = new ArrayList<String>();


    DemographicsAndRiskCode demographicsAndRiskCode;
    DiagnosisGroup diagnosisGroup;
    RefineLogic refineLogic;
    QueryBuilder queryBuilder;
    PopulationAnalyser populationAnalyser;
    HEDISClaimsBased hedisClaimsBased;
    STARMeasures starMeasures;
    CohortAnalyzer cohortAnalyzer;
    PredefinedCohorts predefinedCohorts;
    PredefinedCohortsCohortDetails predefinedCohortsCohortDetails;
    Individual301 individual301;
    CohortDetails cohortDetails;

    @BeforeClass (alwaysRun = true)
    public void setup(){
       super.setUp();
    }

    @BeforeMethod (alwaysRun = true)
    public void navigateTo(){
        queryBuilder = navigationPanel.doNavigateTo(QueryBuilder.class,defaultWaitTime);
    }

    @Test(priority = 1, groups = { "Report-Download", "Product-Critical" }, description = "Navigate to Query Builder => Stratifier => Population Analyzer, select two of the created" +
            "cohorts and go to Analyzer Page then, assert if page loads and the details related to cohorts are" +
            "listed.")
    public void cohortAnalyzerFunctionalityTest(){

        CREATED_COHORT_NAMES.add(refineLogic.getRandomCohortName());
        diagnosisGroup = queryBuilder.selectCriteriaComponent(DiagnosisGroup.class);
        diagnosisGroup.IncludeAll();

        demographicsAndRiskCode = queryBuilder.selectCriteriaComponent(DemographicsAndRiskCode.class);
        demographicsAndRiskCode.enterAge(LOWER_AGE,UPPER_AGE);

        refineLogic = demographicsAndRiskCode.goToRefineLogicPage();
        refineLogic.saveCohort(CREATED_COHORT_NAMES.get(0));
        assertThat(refineLogic.getReportGenerationSuccessfulMessage()).isEqualTo(refineLogic.REPORT_GENERATION_SUCCESSFUL_MESSAGE.replace(COHORT_NAME_PLACEHOLDER,CREATED_COHORT_NAMES.get(0)));

        refineLogic.reset();

        CREATED_COHORT_NAMES.add(refineLogic.getRandomCohortName());
        hedisClaimsBased = queryBuilder.selectCriteriaComponent(HEDISClaimsBased.class);
        hedisClaimsBased.includeNSelections(Nth_SELECTION_IN_HEDIS_PAGE);

        starMeasures = queryBuilder.selectCriteriaComponent(STARMeasures.class);
        starMeasures.includeNSelections(Nth_SELECTION_IN_STAR_PAGE);

        demographicsAndRiskCode = queryBuilder.selectCriteriaComponent(DemographicsAndRiskCode.class);
        demographicsAndRiskCode.selectGenderAndMemberStatus();
        demographicsAndRiskCode.enterRiskScores(LOWER_RI, UPPER_RI);

        refineLogic = demographicsAndRiskCode.goToRefineLogicPage();
        refineLogic.saveCohort(CREATED_COHORT_NAMES.get(1));

        assertThat(refineLogic.getReportGenerationSuccessfulMessage()).isEqualTo(refineLogic.REPORT_GENERATION_SUCCESSFUL_MESSAGE.replace(COHORT_NAME_PLACEHOLDER,CREATED_COHORT_NAMES.get(1)));

        populationAnalyser = navigationPanel.doNavigateTo(PopulationAnalyser.class, defaultWaitTime);
        cohortAnalyzer = populationAnalyser.selectCreatedCohortsAndNavigateToCohortAnalyzer(CREATED_COHORT_NAMES);

        assertThat(cohortAnalyzer.checkWhetherTheseCohortRelatedDetailsAreShown(CREATED_COHORT_NAMES)).isTrue();

        populationAnalyser = cohortAnalyzer.goBackToPopulationAnalyser();
        populationAnalyser.removeCohort(CREATED_COHORT_NAMES.get(0));
        populationAnalyser.removeCohort(CREATED_COHORT_NAMES.get(1));
    }

    @Test(priority = 2, groups = { "Report-Download", "Product-Critical" }, description = "Navigate to Query Builder => Stratifier => Population Analyzer, mark any cohorts as favourite" +
            "and click favourite button then, assert if only the favourite cohorts are listed.")
    public void makeFavouriteFunctionalityTest(){

        diagnosisGroup = queryBuilder.selectCriteriaComponent(DiagnosisGroup.class);
        diagnosisGroup.IncludeAll();

        starMeasures = queryBuilder.selectCriteriaComponent(STARMeasures.class);
        starMeasures.includeNSelections(Nth_SELECTION_IN_STAR_PAGE);

        demographicsAndRiskCode = queryBuilder.selectCriteriaComponent(DemographicsAndRiskCode.class);
        demographicsAndRiskCode.selectGenderAndMemberStatus();
        demographicsAndRiskCode.enterRiskScores(LOWER_RI, UPPER_RI);

        refineLogic = demographicsAndRiskCode.goToRefineLogicPage();
        refineLogic.saveCohort(SAVED_COHORT_NAME);

        assertThat(refineLogic.getReportGenerationSuccessfulMessage()).isEqualTo(refineLogic.REPORT_GENERATION_SUCCESSFUL_MESSAGE.replace(COHORT_NAME_PLACEHOLDER,SAVED_COHORT_NAME));

        populationAnalyser = navigationPanel.doNavigateTo(PopulationAnalyser.class, defaultWaitTime);
        String cohortNumber = populationAnalyser.getCohortNumber(SAVED_COHORT_NAME);
        populationAnalyser.clickShowAllFavouriteCohorts();

        assertThat(populationAnalyser.checkIfListedCohortsAreAllMarkedFavourite()).isTrue();
        assertThat(populationAnalyser.checkIfCreatedCohortNumberExistsInFavouriteCohorts(cohortNumber)).isTrue();

        populationAnalyser.removeCohort(SAVED_COHORT_NAME);
    }

    @Test(priority = 3, groups = { "Report-Download", "Product-Critical" }, description = "Navigate to Query Builder => Stratifier => Population Analyzer, go to Cohort Details for any" +
            "created cohort, download Word and Excel files and then, assert if the files are successfully downloaded with required contents. ")
    public void filesDownloadTest() throws IOException {
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

        cohortDetails.downLoadWordFile();
        assertThat(cohortDetails.downloadWordAndValidate(WORD_DOWNLOADED_FILE,context)).isTrue();
        cohortDetails.downLoadExcelFile();
        assertThat(cohortDetails.downloadWordAndValidate(EXCEl_DOWNLOADED_FILE,context)).isTrue();
    }

    @Test(priority = 4, groups = { "Report-Download", "Product-Critical" }, description = "Navigate to Query Builder => Stratifier => Predefined Cohorts, select any of the created cohorts" +
            "and go to Cohort Details then, assert if Individual Cohort Summary page is loaded and members matching the " +
            "predefined criteria are listed.")
    public void predefinedCohortsFunctionalityTest(){
        predefinedCohorts = navigationPanel.doNavigateTo(PredefinedCohorts.class, defaultWaitTime);
        predefinedCohortsCohortDetails = predefinedCohorts.goToCohortDetailsForThisCohort(COHORT_DESCRIPTION);
        String memberCountInCohortDetailPage = predefinedCohortsCohortDetails.getMemberCountInCohortDetail();

        individual301 = predefinedCohortsCohortDetails.goToIndividualPage();
        String memberCountInIndividualPage = individual301.getIndividualCount();
        assertThat(memberCountInCohortDetailPage).isEqualTo(memberCountInIndividualPage);

        individual301.closeCurrentWindowAndSwitchToMainWindow();
    }
}

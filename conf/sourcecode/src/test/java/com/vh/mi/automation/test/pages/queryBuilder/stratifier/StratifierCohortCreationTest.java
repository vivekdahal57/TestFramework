package com.vh.mi.automation.test.pages.queryBuilder.stratifier;


import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.DemographicsAndRisk.DemographicsAndRiskCode;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.CohortDetails;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Diagnosis.DiagnosisGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PopulationAnalyser;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.QueryBuilder;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82716 on 4/24/2017.
 */

@Test (groups = { "Product-Critical", "Component-Interaction"})
public class StratifierCohortCreationTest extends BaseTest {
    public static final String SAVED_COHORT_NAME = RefineLogic.getRandomCohortName();
    private static final String COHORT_NAME_PLACEHOLDER = "${Cohort_name}";

    DemographicsAndRiskCode demographicsAndRiskCode;
    DiagnosisGroup diagnosisGroup;
    RefineLogic refineLogic;
    CohortDetails cohortDetails;
    QueryBuilder queryBuilder;
    private static final Integer LOWER_AGE=20;
    private static final Integer UPPER_AGE=60;

    @BeforeClass
    public void setUp(){
        super.setUp();
        queryBuilder = navigationPanel.doNavigateTo(QueryBuilder.class,defaultWaitTime);
    }

    @Test(description = "Navigate to Query Builder = > Stratifier => Query Builder, applying some criteria create and " +
            "save a cohort, get member counts in Refine Logic and Cohort details for that cohort and hence assert if " +
            "they are equal.")
    public void memberCountTest(){
        diagnosisGroup = queryBuilder.selectCriteriaComponent(DiagnosisGroup.class);
        diagnosisGroup.IncludeAll();

        demographicsAndRiskCode = queryBuilder.selectCriteriaComponent(DemographicsAndRiskCode.class);
        demographicsAndRiskCode.enterAge(LOWER_AGE,UPPER_AGE);

        refineLogic = demographicsAndRiskCode.goToRefineLogicPage();

        String memberCnt1 = refineLogic.getMemberCount();
        refineLogic.saveCohort(SAVED_COHORT_NAME);

        assertThat(refineLogic.getReportGenerationSuccessfulMessage()).isEqualTo(refineLogic.REPORT_GENERATION_SUCCESSFUL_MESSAGE.replace(COHORT_NAME_PLACEHOLDER,SAVED_COHORT_NAME));

        PopulationAnalyser populationAnalyser = navigationPanel.doNavigateTo(PopulationAnalyser.class, defaultWaitTime);
        cohortDetails = populationAnalyser.goToCohortDetails(SAVED_COHORT_NAME);
        String memberCnt2 = cohortDetails.getMemberCount();
        assertThat(memberCnt1).isEqualTo(memberCnt2);
    }

    @AfterClass
    public void removeCohort(){
        PopulationAnalyser goBackToPopulationAnalyser = cohortDetails.goBackToPopulationAnalyser();
        goBackToPopulationAnalyser.removeCohort(SAVED_COHORT_NAME);
    }

}



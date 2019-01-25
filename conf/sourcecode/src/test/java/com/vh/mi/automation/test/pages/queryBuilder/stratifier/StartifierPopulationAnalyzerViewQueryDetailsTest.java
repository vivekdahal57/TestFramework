package com.vh.mi.automation.test.pages.queryBuilder.stratifier;

import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.DemographicsAndRisk.DemographicsAndRiskCode;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Diagnosis.DiagnosisGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PopulationAnalyser;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.QueryBuilder;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Rx.RxCode;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by i10316 on 7/5/2017.
 */
@Test (groups = { "Product-Critical" })
public class StartifierPopulationAnalyzerViewQueryDetailsTest extends BaseTest {
    public static final String SAVED_COHORT_NAME = RefineLogic.getRandomCohortName();
    private static final Integer LOWER_AGE = 20;
    private static final Integer UPPER_AGE = 60;
    private static final Integer topN=10;

    PopulationAnalyser populationAnalyzer;
    QueryBuilder queryBuilder;
    DiagnosisGroup diagnosisGroup;
    RxCode rxCode;
    DemographicsAndRiskCode demographicsAndRiskCode;
    RefineLogic refineLogic;

    @BeforeClass
    public void setUp() {
        super.setUp();
        queryBuilder = navigationPanel.doNavigateTo(QueryBuilder.class, defaultWaitTime);
    }

    @Test
    public void viewQueryDetaisTest() {
        String memberCount = "";
        String querySummary = "";

        rxCode=queryBuilder.selectCriteriaComponent(RxCode.class);
        rxCode.includeDrugs(topN);

        diagnosisGroup = queryBuilder.selectCriteriaComponent(DiagnosisGroup.class);
        diagnosisGroup.IncludeAll();

        demographicsAndRiskCode = queryBuilder.selectCriteriaComponent(DemographicsAndRiskCode.class);
        demographicsAndRiskCode.enterAge(LOWER_AGE, UPPER_AGE);

        refineLogic = demographicsAndRiskCode.goToRefineLogicStratifier();
        memberCount = refineLogic.getMemberCount();
        querySummary = refineLogic.getQuerySummary();
        refineLogic.saveCohort(SAVED_COHORT_NAME);

        populationAnalyzer = navigationPanel.doNavigateTo(PopulationAnalyser.class, defaultWaitTime);
        refineLogic = populationAnalyzer.goToViewQueryDetails(SAVED_COHORT_NAME);
        Assert.assertEquals(memberCount, refineLogic.getMemberCount());
        Assert.assertEquals(querySummary, refineLogic.getQuerySummary());

        //Deleting cohorts
        populationAnalyzer = navigationPanel.doNavigateTo(PopulationAnalyser.class, defaultWaitTime);
        PopulationAnalyser goBackToPopulationAnalyser = refineLogic.goBackToPopulationAnalyser();
        goBackToPopulationAnalyser.removeCohort(SAVED_COHORT_NAME);
    }
}

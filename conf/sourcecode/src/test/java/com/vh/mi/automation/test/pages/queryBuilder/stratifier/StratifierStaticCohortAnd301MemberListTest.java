package com.vh.mi.automation.test.pages.queryBuilder.stratifier;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.DemographicsAndRisk.IDemographicsAndRiskCode;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.IQueryBuilder;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.DemographicsAndRisk.DemographicsAndRiskCode;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Diagnosis.DiagnosisGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PopulationAnalyser;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.QueryBuilder;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i81306 on 6/22/2017.
 */
@Test (groups = "Product-Critical")
public class StratifierStaticCohortAnd301MemberListTest extends BaseTest {
    private static final Integer LOWER_AGE=20;
    private static final Integer UPPER_AGE=60;
    private static final String STATIC_COHORT_NAME = "Stratifier_Static_Cohort_Test_" + Random.getRandomStringOfLength(4);
    private static final String COHORT_NAME_PLACEHOLDER = "${Cohort_name}";
    IDemographicsAndRiskCode demographicsAndRiskCode;
    RefineLogic refineLogic;
    IQueryBuilder queryBuilder;
    DiagnosisGroup diagnosisGroup;
    private IIndv301 indv301;

    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp(){
        super.setUp();
        User user = getUser("miautomation_memberlist_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        queryBuilder = navigationPanel.doNavigateTo(QueryBuilder.class,defaultWaitTime);
    }

    @Test(description = "Navigate to Query Builder => Stratifier => Query Builder, apply some criteria, go to " +
            "Refinelogic and save as static cohort, navgate to Individuals and assert if saved cohort is present" +
            "in the member list, finally delete that cohort and assert if deleted successfully.")
    public void createStaticChoortAndSeeIfItShowsIn301MemberListTest(){
        diagnosisGroup = queryBuilder.selectCriteriaComponent(DiagnosisGroup.class);
        diagnosisGroup.IncludeAll();

        demographicsAndRiskCode = queryBuilder.selectCriteriaComponent(DemographicsAndRiskCode.class);
        demographicsAndRiskCode.enterAge(LOWER_AGE,UPPER_AGE);

        refineLogic = demographicsAndRiskCode.goToRefineLogicPage();
        refineLogic.saveStaticCohort(STATIC_COHORT_NAME);
        assertThat(refineLogic.getReportGenerationSuccessfulMessage()).isEqualTo(refineLogic.REPORT_GENERATION_SUCCESSFUL_MESSAGE.replace(COHORT_NAME_PLACEHOLDER,STATIC_COHORT_NAME));

        indv301 = navigationPanel.doNavigateTo(Indv301.class,defaultWaitTime);
        assertThat(indv301.getMemberList().checkIfMemberListExists(STATIC_COHORT_NAME)).isTrue();

        //Delete memberList
        indv301.getMemberList().deleteMyMemberListWithName(STATIC_COHORT_NAME);
        assertThat(indv301.getMemberList().getOperationCompleteStatusMessage()).isEqualTo(indv301.getMemberList().getExpectedMessageForMemberListDeletion());

        //Delete static cohort
        PopulationAnalyser populationAnalyser = navigationPanel.doNavigateTo(PopulationAnalyser.class, defaultWaitTime);
        populationAnalyser.removeCohort(STATIC_COHORT_NAME);
    }
}

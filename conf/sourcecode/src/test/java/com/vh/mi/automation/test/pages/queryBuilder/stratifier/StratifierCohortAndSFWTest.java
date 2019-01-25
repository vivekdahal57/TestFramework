package com.vh.mi.automation.test.pages.queryBuilder.stratifier;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.admin.IAdminPage;
import com.vh.mi.automation.api.pages.admin.pages.security.IFeatureControlForUser;
import com.vh.mi.automation.api.pages.admin.pages.security.ISecurityFramework;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.CohortDetails;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.DemographicsAndRisk.DemographicsAndRiskCode;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Diagnosis.DiagnosisGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PopulationAnalyser;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.QueryBuilder;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i81306 on 8/22/2017.
 */
@Test(groups = {"Security", "Product-Critical"})
public class StratifierCohortAndSFWTest extends BaseTest {

    public static final String SAVED_COHORT_NAME = RefineLogic.getRandomCohortName();
    private static final String COHORT_NAME_PLACEHOLDER = "${Cohort_name}";
    private static final String DEFAULT_SCHEME_NAME = "tempSchemeUser";
    private static final Integer LOWER_AGE=30;
    private static final Integer UPPER_AGE=50;

    DemographicsAndRiskCode demographicsAndRiskCode;
    DiagnosisGroup diagnosisGroup;
    RefineLogic refineLogic;
    CohortDetails cohortDetails;
    QueryBuilder queryBuilder;
    PopulationAnalyser populationAnalyser;

    IAdminPage adminPage;
    IFeatureControlForUser featureControlForUserPage;
    ISecurityFramework securityFrameworkPage;

    @Override
    public boolean skipBrowserCreation() {
        return true;
    }

    @BeforeMethod
    public void beforeTestMethod() {
        createNewBrowserInstance();
    }


    @AfterMethod
    public void afterTestMethod() throws Exception {
        closeBrowserInstance();
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Test(priority = 0, description = "QueryBuilder => Stratifier => Create a cohort from user having full SFW access")
    public void createCohortTOTestSFW(){
        User user = getUser("miautomation_stratifier_sfw_user");
        loginAndSelectFront(user,context.getDefaultWaitTimeout());

        queryBuilder = navigationPanel.doNavigateTo(QueryBuilder.class,defaultWaitTime);

        diagnosisGroup = queryBuilder.selectCriteriaComponent(DiagnosisGroup.class);
        diagnosisGroup.IncludeAll();

        demographicsAndRiskCode = queryBuilder.selectCriteriaComponent(DemographicsAndRiskCode.class);
        demographicsAndRiskCode.enterAge(LOWER_AGE,UPPER_AGE);

        refineLogic = demographicsAndRiskCode.goToRefineLogicPage();
        refineLogic.saveCohort(SAVED_COHORT_NAME);

        assertThat(refineLogic.getReportGenerationSuccessfulMessage()).isEqualTo(refineLogic.REPORT_GENERATION_SUCCESSFUL_MESSAGE.replace(COHORT_NAME_PLACEHOLDER,SAVED_COHORT_NAME));
    }

    @Test(priority = 1, description = "Admin => Allow a user access to only certain Business Level")
    public void applySFWToUser(){
        User user = getUser("miautomation_super_user");
        User sfwUser = getUser("miautomation_stratifier_sfw_user");
        adminPage = loginToAdminApplication(user, defaultWaitTime);
        featureControlForUserPage = adminPage.goToSecurityPage().goToFeatureControlForUserPage();
        securityFrameworkPage = featureControlForUserPage.applySFWForUser(sfwUser.getUserId());
        securityFrameworkPage.selectScheme(DEFAULT_SCHEME_NAME);
        securityFrameworkPage.applyMinimumSFW();
    }

    @Test(priority = 2, description = "QueryBuilder => Stratifier => View cohort details after appliying SFW to user to see if the poput appears.")
    public void viewCohortDetailsAfterSFWChange(){
        User user = getUser("miautomation_stratifier_sfw_user");
        loginAndSelectFront(user,context.getDefaultWaitTimeout());
        populationAnalyser = navigationPanel.doNavigateTo(PopulationAnalyser.class, defaultWaitTime);
        cohortDetails = populationAnalyser.goToCohortDetails(SAVED_COHORT_NAME);
        assertThat(cohortDetails.isSFWChangePopupPresent()).isTrue();

        PopulationAnalyser goBackToPopulationAnalyser = cohortDetails.goBackToPopulationAnalyser();
        goBackToPopulationAnalyser.removeCohort(SAVED_COHORT_NAME);
    }

    @Test(priority = 3, description = "Admin => restore SFW back for the user.")
    public void restoreSFWOfUser(){
        User user = getUser("miautomation_super_user");
        User sfwUser = getUser("miautomation_stratifier_sfw_user");
        adminPage = loginToAdminApplication(user, defaultWaitTime);
        featureControlForUserPage = adminPage.goToSecurityPage().goToFeatureControlForUserPage();
        securityFrameworkPage = featureControlForUserPage.applySFWForUser(sfwUser.getUserId());
        securityFrameworkPage.selectScheme(DEFAULT_SCHEME_NAME);
        securityFrameworkPage.allowAllSFW();
    }
}

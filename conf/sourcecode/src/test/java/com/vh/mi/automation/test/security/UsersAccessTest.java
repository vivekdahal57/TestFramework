package com.vh.mi.automation.test.security;

import com.vh.mi.automation.api.comp.bl.BL;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelSelectionComponent;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.comp.navigation.INavigationPanel;
import com.vh.mi.automation.api.constants.MILandingPages;
import com.vh.mi.automation.api.dto.SFW;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.hospitalProfiler.IHospitalProfilesHP100;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.api.pages.analytics.networkUtilization.INetworkUtilizationNU105;
import com.vh.mi.automation.api.pages.common.ILoginPage;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.comp.pagination.PaginationComponentFacade;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.HospitalProfilesHP100;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.HospitalProfilesHP100Columns;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301Columns;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.NurseDesktop301ND;
import com.vh.mi.automation.impl.pages.analytics.networkUtilization.NetworkUtilizationNU105;
import com.vh.mi.automation.impl.pages.queryBuilder.mvca.basic.MVCABasic301B;
import com.vh.mi.automation.impl.pages.queryBuilder.mvca.expert.MVCAExpert301E;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.base.TestEnvironment;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82298 on 11/4/2015.
 */
public class UsersAccessTest extends BaseTest {
    ILoginPage loginPage;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        super.setUp();
    }

    @Override
    public boolean skipBrowserCreation() {
        return true;
    }


    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() {
        createNewBrowserInstance();
        loginPage = mi.open(context.getApplication());
        loginPage.doWaitTillFullyLoaded(defaultWaitTime);

    }


    @AfterMethod(alwaysRun = true)
    public void afterTestMethod() throws Exception {
        closeBrowserInstance();
    }


    @Test(groups = "Security", description = "TC36257,TC36258 : Allow two application to a user and assert if the user has access to only those two applications ")
    public void userShouldHaveAccessToApplicationsTheyAreAssignedTo() {
        User user = getUser("miautomation_security_test_TC1592_user");

        IWelcomePage welcomePage = loginPage.loginWith(user, defaultWaitTime);
        Collection<String> availableFronts = welcomePage.getAvailableFronts();

        List<String> assignedApplications = user.getAssignedApplications();
        assertThat(assignedApplications).hasSize(2);
        String app1 = assignedApplications.get(0);
        String app2 = assignedApplications.get(1);

        assertThat(availableFronts).contains(app1);
        assertThat(availableFronts).contains(app2);
        assertThat(availableFronts).containsOnly(app1, app2);

        INavigationPanel navPanel = welcomePage.selectFront(app1, defaultWaitTime);
        assertThat(navPanel.getCurrentPageTitle()).isNotEmpty();

        welcomePage = navPanel.chooseAnotherApplication(defaultWaitTime);

        navPanel = welcomePage.selectFront(app2, defaultWaitTime);
        assertThat(navPanel.getCurrentPageTitle()).isNotEmpty();
    }

    /*The above test covers what this test is trying to do
    @Test(groups = "Security", description = "TC36257: Allow particular application to a user and assert if the user can enter to the allowed application. ")
    public void usersShouldNotHaveAccessToApplicationsTheyAreNotAssigned() {
        User testUser = getUser("miautomation_security_test_TC1593_user");
        List<String> assignedApplicationsUser2 = testUser.getAssignedApplications();
        assertThat(assignedApplicationsUser2).hasSize(2);
        String appId1 = assignedApplicationsUser2.get(0);
        String appId2 = assignedApplicationsUser2.get(1);

        IWelcomePage welcomePage = loginPage.loginWith(testUser, defaultWaitTime);
        Collection<String> availableFronts = welcomePage.getAvailableFronts();
        assertThat(availableFronts).hasSize(2);
        assertThat(availableFronts).contains(appId1);
        assertThat(availableFronts).contains(appId2);
        assertThat(availableFronts).containsOnly(appId1, appId2);
    }*/


    @Test(groups = "Security", description = "TC36261: Allow certain modules to a user and assert if the user can " +
            "see the allowed modules")
    public void userShouldHaveAccessToModulesAssignedFromGlobalRightsForGroups() {
        User testUser = getUser("miautomation_security_test_TC1594_user");
        String appId = context.getApplication().getAppId();

        IWelcomePage welcomePage = loginPage.loginWith(testUser, defaultWaitTime);
        navigationPanel = welcomePage.selectFront(appId, defaultWaitTime);

        assertThat(navigationPanel.isPageAvailable(MILandingPages.NETWORK_UTILIZATION)).isTrue();
        INetworkUtilizationNU105 networkUtilizationPage = navigationPanel.doNavigateTo(NetworkUtilizationNU105.class, defaultWaitTime);
        assertThat(networkUtilizationPage.getDisplayedPageTitle()).isEqualTo(MILandingPages.NETWORK_UTILIZATION.getPageTitle());

        assertThat(navigationPanel.isPageAvailable(MILandingPages.MVCA_BASIC)).isTrue();
        MVCABasic301B mvcaBasicPage = navigationPanel.doNavigateTo(MVCABasic301B.class, defaultWaitTime);
        assertThat(mvcaBasicPage.isFullyLoaded()).isTrue();

        assertThat(navigationPanel.isPageAvailable(MILandingPages.MVCA_EXPERT)).isTrue();
        MVCAExpert301E mvcaExpertPage = navigationPanel.doNavigateTo(MVCAExpert301E.class, defaultWaitTime);
        assertThat(mvcaExpertPage.isFullyLoaded()).isTrue();
    }


    @Test(groups = "Security", description = "TC36261: Deny certain modules to a user and assert if the user cannot " +
            "see the denied modules")
    public void userShouldNotHaveAccessToModulesNotAssignedToTheirRespectiveGroup() {
        User testUser = getUser("miautomation_security_test_TC1595_user");
        String appId = context.getApplication().getAppId();

        IWelcomePage welcomePage = loginPage.loginWith(testUser, defaultWaitTime);
        navigationPanel = welcomePage.selectFront(appId, defaultWaitTime);

        assertThat(navigationPanel.isPageAvailable(MILandingPages.NETWORK_UTILIZATION)).isFalse();
        assertThat(navigationPanel.isPageAvailable(MILandingPages.MVCA_BASIC)).isFalse();
        assertThat(navigationPanel.isPageAvailable(MILandingPages.MVCA_EXPERT)).isFalse();

    }


    @Test(groups = "Security", description = "TC36259: Allow access on group app level and Verify that a user can see" +
            " PHI data based on Group App permission")
    public void testThatUserWithPHIdataRightShouldLandOn310NDpage() {
        String memberID = context.getMemberId();
        User testUser = getUser("miautomation_PHI_data_allowed_user");
        String[] expectedTabs = {"Summary", "Quality Measures", "Clinical Event Chart", "Individual Claim Details"};
        NurseDesktop301ND nurseDesktop301ND = loginPage.loginExpectingPage(NurseDesktop301ND.class, testUser, defaultWaitTime);

        nurseDesktop301ND = nurseDesktop301ND.enterMemberId(memberID);
        nurseDesktop301ND = nurseDesktop301ND.submit();
        List<String> availableTabs = nurseDesktop301ND.getAvailableTabs();

        assertThat(nurseDesktop301ND.isTabPresent()).isTrue();
        assertThat(availableTabs).contains(expectedTabs);
    }


    @Test(groups = "Security", description = "TC36263, TC2440: Verify Full Financial Access (allow) Miscellaneous " +
            "Right features")
    public void testThatClaimsAndMemberLevelVisibleForFullFinancialAccessAllowedUser() {
        IWelcomePage welcomePage = loginPage.loginWith(getUser("miautomation_TC2440_user_2"), defaultWaitTime);
        String appid = context.getApplication().getAppId();
        navigationPanel = welcomePage.selectFront(appid, defaultWaitTime);

        assertThat(navigationPanel.isPageAvailable(MILandingPages.CLAIMS_C01)).isTrue();

        IIndv301 indv301Page = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        showAllColumns(indv301Page.getDataGridCustomizer());
        List<IDataGridColumn> indv301DataGridColumns = indv301Page.getDataGrid().getColumns();
        assertThat(Indv301Columns.TOTAL_PAID).isIn(indv301DataGridColumns);
        assertThat(Indv301Columns.MEDICAL_PAID).isIn(indv301DataGridColumns);
        assertThat(Indv301Columns.PHARMACY_PAID).isIn(indv301DataGridColumns);

        IHospitalProfilesHP100 hospitalProfilesHP100 = navigationPanel.doNavigateTo(HospitalProfilesHP100.class, defaultWaitTime);
        showAllColumns(hospitalProfilesHP100.getDataGridCustomizer());

        List<IDataGridColumn> hospitalProfilesDataGridColumns = hospitalProfilesHP100.getDataGrid().getColumns();
        assertThat(HospitalProfilesHP100Columns.TOTAL_PAID_AMT).isIn(hospitalProfilesDataGridColumns);
    }

    @Test(groups = "Security", description = "TC1609: Verify user can access/see certain data based on his/her individual permission (NOT from Group permission)")
    public void userShouldOnlySeeLevelsThatAreAllowedInSfw() {
        User testUser = getUser("miautomation_security_test_TC1609_user");
        String appId = context.getApplication().getAppId();

        SFW sfw = TestEnvironment.find.sfwByUserAndApplication("miautomation_security_test_TC1609_user", appId);
        assertThat(sfw).isNotNull();


        IWelcomePage welcomePage = loginPage.loginWith(testUser, defaultWaitTime);
        navigationPanel = welcomePage.selectFront(appId, defaultWaitTime);
        Indv301 individualPage = navigationPanel.doNavigateTo(Indv301.class, context.getDefaultWaitTimeout());

        IBusinessLevelsComponent businessLevelsComponent = individualPage.getBusinessLevel();

        List<Integer> levelsToCheck = sfw.getLevelsForWhichExpectedValuesArePresent();
        assertThat(levelsToCheck).isNotEmpty();

        for (Integer level : levelsToCheck) {
            IBusinessLevelsComponent.IBusinessLevel businessLevelComponent = businessLevelsComponent.getBusinessLevelComponent(BL.get(level));
            IBusinessLevelSelectionComponent blSelectionComponent = businessLevelComponent.doOpenSelection();

            PaginationComponentFacade paginationComponent = new PaginationComponentFacade(blSelectionComponent.getPaginationComponent());

            IBusinessLevelSelectionComponent.ISelectionGridContainer selectionGridContainer = blSelectionComponent.getSelectionGridContainer();
            List<String> availableOptions = new ArrayList<>();
            do {
                availableOptions.addAll(selectionGridContainer.getSelectionOptionsOnCurrentPage());
            } while (paginationComponent.nextPage());


            SFW.ExpectedResults expectedResultForLevel = sfw.getExpectedResultForLevel(level);
            if (expectedResultForLevel.getRelation().equals("containsOnly")) {
                assertThat(availableOptions).containsOnly(expectedResultForLevel.getExpectedValues().toArray());
            } else if (expectedResultForLevel.getRelation().equals("doesNotContain")) {
                assertThat(availableOptions).excludes(expectedResultForLevel.getExpectedValues().toArray());
            } else {
                throw new Error("Unknown relation " + expectedResultForLevel.getRelation());
            }

            blSelectionComponent.doClose();
        }
    }

    @Test(groups = "Security", description = "TC36264: Verify user can access/see certain data based on his/her " +
            "individual permission (From Group permission)")
    public void userInGroupShouldOnlySeeAllowedLevels() {
        User testUser = getUser("miautomation_group_level_sfw_user");
        String appId = context.getApplication().getAppId();

        SFW sfw = TestEnvironment.find.sfwByGroupAndApplication("mta_group_level_sfw_allow_group", appId);
        assertThat(sfw).isNotNull();


        IWelcomePage welcomePage = loginPage.loginWith(testUser, defaultWaitTime);
        navigationPanel = welcomePage.selectFront(appId, defaultWaitTime);
        Indv301 individualPage = navigationPanel.doNavigateTo(Indv301.class, context.getDefaultWaitTimeout());

        IBusinessLevelsComponent businessLevelsComponent = individualPage.getBusinessLevel();

        List<Integer> levelsToCheck = sfw.getLevelsForWhichExpectedValuesArePresent();
        assertThat(levelsToCheck).isNotEmpty();

        for (Integer level : levelsToCheck) {
            IBusinessLevelsComponent.IBusinessLevel businessLevelComponent = businessLevelsComponent.getBusinessLevelComponent(BL.get(level));
            IBusinessLevelSelectionComponent blSelectionComponent = businessLevelComponent.doOpenSelection();

            PaginationComponentFacade paginationComponent = new PaginationComponentFacade(blSelectionComponent.getPaginationComponent());

            IBusinessLevelSelectionComponent.ISelectionGridContainer selectionGridContainer = blSelectionComponent.getSelectionGridContainer();
            List<String> availableOptions = new ArrayList<>();
            do {
                availableOptions.addAll(selectionGridContainer.getSelectionOptionsOnCurrentPage());
            } while (paginationComponent.nextPage());


            SFW.ExpectedResults expectedResultForLevel = sfw.getExpectedResultForLevel(level);
            if (expectedResultForLevel.getRelation().equals("containsOnly")) {
                assertThat(availableOptions).containsOnly(expectedResultForLevel.getExpectedValues().toArray());
            } else if (expectedResultForLevel.getRelation().equals("doesNotContain")) {
                assertThat(availableOptions).excludes(expectedResultForLevel.getExpectedValues().toArray());
            } else {
                throw new Error("Unknown relation " + expectedResultForLevel.getRelation());
            }

            blSelectionComponent.doClose();
        }
    }


    @Test(groups = "Security", description = "TC1610: Verify user can NOT access/see certain data based on his/her individual permission (NOT from Group permission)")
    public void userShouldNotSeeLevelsThatAreDeniedInSfw() {
        User testUser = getUser("miautomation_security_test_TC1610_user");
        String appId = context.getApplication().getAppId();

        SFW sfw = TestEnvironment.find.sfwByUserAndApplication("miautomation_security_test_TC1610_user", appId);
        assertThat(sfw).isNotNull();


        IWelcomePage welcomePage = loginPage.loginWith(testUser, defaultWaitTime);
        navigationPanel = welcomePage.selectFront(appId, defaultWaitTime);
        Indv301 individualPage = navigationPanel.doNavigateTo(Indv301.class, context.getDefaultWaitTimeout());

        IBusinessLevelsComponent businessLevelsComponent = individualPage.getBusinessLevel();

        List<Integer> levelsToCheck = sfw.getLevelsForWhichExpectedValuesArePresent();
        assertThat(levelsToCheck).isNotEmpty();

        for (Integer level : levelsToCheck) {
            IBusinessLevelsComponent.IBusinessLevel businessLevelComponent = businessLevelsComponent.getBusinessLevelComponent(BL.get(level));
            IBusinessLevelSelectionComponent blSelectionComponent = businessLevelComponent.doOpenSelection();

            PaginationComponentFacade paginationComponent = new PaginationComponentFacade(blSelectionComponent.getPaginationComponent());

            IBusinessLevelSelectionComponent.ISelectionGridContainer selectionGridContainer = blSelectionComponent.getSelectionGridContainer();
            List<String> availableOptions = new ArrayList<>();
            do {
                availableOptions.addAll(selectionGridContainer.getSelectionOptionsOnCurrentPage());
            } while (paginationComponent.nextPage());


            SFW.ExpectedResults expectedResultForLevel = sfw.getExpectedResultForLevel(level);
            if (expectedResultForLevel.getRelation().equals("containsOnly")) {
                assertThat(availableOptions).containsOnly(expectedResultForLevel.getExpectedValues().toArray());
            } else if (expectedResultForLevel.getRelation().equals("doesNotContain")) {
                assertThat(availableOptions).excludes(expectedResultForLevel.getExpectedValues().toArray());
            } else {
                throw new Error("Unknown relation " + expectedResultForLevel.getRelation());
            }

            blSelectionComponent.doClose();
        }
    }

    @Test(groups = "Security", description = "TC1610: Verify user can NOT access/see certain data based on his/her individual permission (NOT from Group permission)")
    public void userInGroupShouldNotSeeDeniedLevels() {
        User testUser = getUser("miautomation_group_level_sfw_user");
        String appId = context.getApplication().getAppId();

        SFW sfw = TestEnvironment.find.sfwByUserAndApplication("miautomation_security_test_TC1610_user", appId);
        assertThat(sfw).isNotNull();


        IWelcomePage welcomePage = loginPage.loginWith(testUser, defaultWaitTime);
        navigationPanel = welcomePage.selectFront(appId, defaultWaitTime);
        Indv301 individualPage = navigationPanel.doNavigateTo(Indv301.class, context.getDefaultWaitTimeout());

        IBusinessLevelsComponent businessLevelsComponent = individualPage.getBusinessLevel();

        List<Integer> levelsToCheck = sfw.getLevelsForWhichExpectedValuesArePresent();
        assertThat(levelsToCheck).isNotEmpty();

        for (Integer level : levelsToCheck) {
            IBusinessLevelsComponent.IBusinessLevel businessLevelComponent = businessLevelsComponent.getBusinessLevelComponent(BL.get(level));
            IBusinessLevelSelectionComponent blSelectionComponent = businessLevelComponent.doOpenSelection();

            PaginationComponentFacade paginationComponent = new PaginationComponentFacade(blSelectionComponent.getPaginationComponent());

            IBusinessLevelSelectionComponent.ISelectionGridContainer selectionGridContainer = blSelectionComponent.getSelectionGridContainer();
            List<String> availableOptions = new ArrayList<>();
            do {
                availableOptions.addAll(selectionGridContainer.getSelectionOptionsOnCurrentPage());
            } while (paginationComponent.nextPage());


            SFW.ExpectedResults expectedResultForLevel = sfw.getExpectedResultForLevel(level);
            if (expectedResultForLevel.getRelation().equals("containsOnly")) {
                assertThat(availableOptions).containsOnly(expectedResultForLevel.getExpectedValues().toArray());
            } else if (expectedResultForLevel.getRelation().equals("doesNotContain")) {
                assertThat(availableOptions).excludes(expectedResultForLevel.getExpectedValues().toArray());
            } else {
                throw new Error("Unknown relation " + expectedResultForLevel.getRelation());
            }

            blSelectionComponent.doClose();
        }
    }


    @Test(groups = "Security", description = "TC2440, TC2440: Verify Full Financial Access (deny) Miscellaneous " +
            "Right features")
    public void testThatClaimsAndMemberLevelNotVisibleToFullFinancialAccessDeniedUser() {
        IWelcomePage welcomePage = loginPage.loginWith(getUser("miautomation_TC2440_user_1"), defaultWaitTime);
        String appid = context.getApplication().getAppId();
        navigationPanel = welcomePage.selectFront(appid, defaultWaitTime);

        assertThat(navigationPanel.isPageAvailable(MILandingPages.CLAIMS_C01)).isFalse();
        assertThat(navigationPanel.isPageAvailable(MILandingPages.CONVERSION_ANALYZER)).isFalse();
        assertThat(navigationPanel.isPageAvailable(MILandingPages.DEMOGRAPHY)).isFalse();
        assertThat(navigationPanel.isPageAvailable(MILandingPages.NETWORK_UTILIZATION)).isFalse();
        assertThat(navigationPanel.isPageAvailable(MILandingPages.PROVIDER_PROFILER)).isFalse();
        assertThat(navigationPanel.isPageAvailable(MILandingPages.SPIKESS110A)).isFalse();


        IIndv301 indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        showAllColumns(indv301.getDataGridCustomizer());

        List<IDataGridColumn> indv301DataGridColumns = indv301.getDataGrid().getColumns();
        assertThat(Indv301Columns.TOTAL_PAID).isNotIn(indv301DataGridColumns);
        assertThat(Indv301Columns.MEDICAL_PAID).isNotIn(indv301DataGridColumns);
        assertThat(Indv301Columns.PHARMACY_PAID).isNotIn(indv301DataGridColumns);


        //Removed since the logic has now changed
//        IHospitalProfilesHP100 hospitalProfilesHP100 = navigationPanel.doNavigateTo(HospitalProfilesHP100.class, defaultWaitTime);
//        showAllColumns(hospitalProfilesHP100.getDataGridCustomizer());
//        List<IDataGridColumn> hospitalProfilesDataGridColumns = hospitalProfilesHP100.getDataGrid().getColumns();
//
//        assertThat(HospitalProfilesHP100Columns.TOTAL_PAID_AMT).isNotIn(hospitalProfilesDataGridColumns);
//        assertThat(HospitalProfilesHP100Columns.TOTAL_ALLOWED_AMT).isNotIn(hospitalProfilesDataGridColumns);
//        assertThat(HospitalProfilesHP100Columns.TOTAL_AUXILIARY_PAID_AMT).isNotIn(hospitalProfilesDataGridColumns);
//        assertThat(HospitalProfilesHP100Columns.TOTAL_BILLED_AMT).isNotIn(hospitalProfilesDataGridColumns);
//        assertThat(HospitalProfilesHP100Columns.TOTAL_FACILITY_PAID_AMT).isNotIn(hospitalProfilesDataGridColumns);
//        assertThat(HospitalProfilesHP100Columns.TOTAL_EMP_PAID_AMT).isNotIn(hospitalProfilesDataGridColumns);
//        assertThat(HospitalProfilesHP100Columns.PAID_AMT_PER_ADMISSION).isNotIn(hospitalProfilesDataGridColumns);

    }

    private void showAllColumns(IDataGridCustomizer dataGridCustomizer) {
        dataGridCustomizer.doSelectAll();
        dataGridCustomizer.doApplySelection();
        new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();
    }

}

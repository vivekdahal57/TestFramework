package com.vh.mi.automation.test.saml;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.comp.navigation.INavigationPanel;
import com.vh.mi.automation.api.constants.MILandingPages;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.SFW;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardIndividualClaimDetails;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardMemberSummary;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.ICP100;
import com.vh.mi.automation.api.pages.saml.IDemoerQAPage;
import com.vh.mi.automation.api.pages.saml.MsgTamperedErrorPage;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.comp.bl.newimpl.BLComponentFacade;
import com.vh.mi.automation.impl.pages.LoggedOutPage;
import com.vh.mi.automation.impl.pages.analytics.executiveSummary.ExecutiveSummary;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301Columns;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301DataGrid;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardIndividualClaimDetails;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardMemberSummary;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.CP100;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.physicianManager.CP110;
import com.vh.mi.automation.impl.pages.common.WelcomePage;
import com.vh.mi.automation.impl.pages.saml.*;
import com.vh.mi.automation.impl.reportManager.ReportAdministrator;
import com.vh.mi.automation.impl.reportManager.reportWizard.ReportWizard;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import com.vh.mi.automation.test.base.TestEnvironment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * All input for sso tests are taken from conf/fronts/${appId}/saml.groovy
 *
 * @author nimanandhar
 */
public class SSOTests extends SSOBaseTest {

    String samlUrl = context.getDemoerUrl();
    private String eiDashboardUrl = context.getEiDashboardUrl();
    String notBefore = ssoDateFrom();
    DemoerQAPage demoerQAPage;
    String notAfter = ssoDateTo();

    private String memberId;
    private String memberIdEncoded;
    private String memberId2;
    private String memberId2Encoded;

    private String lvl1Id;
    private String lvl2Id;
    private String lvl3Id;
    private String lvl1Desc;
    private String lvl2Desc;
    private String lvl3Desc;
    private String sfw_SSOMemId;
    private String wrongMemId;

    private WebElements webElements;

    public DemoerQAPage getLoggedIn() {
        demoerQAPage.clearAll();
        return this.demoerQAPage;
    }
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        User demoerSvcAcccount = getUser();
        DemoerLoginPage loginPage = DemoerLoginPage.loadPage(getWebDriver(), samlUrl);
        DemoerAssistedInputPage assistedInputPage = loginPage.doLogin(demoerSvcAcccount.getUserId(), demoerSvcAcccount.getPassword());
        demoerQAPage = assistedInputPage.loadQAPage();
        demoerQAPage.doWaitTillFullyLoaded(10000);
        if(context.getAppId().equals("008-101") || context.getAppId().equals("008-303") || context.getAppId().equals("981-001")) {
            memberId = "220232276";
            memberIdEncoded = "441444698";
            memberId2 = "220229779";
            memberId2Encoded = "747464694";
            lvl1Id = "UHC";
            lvl2Id = "4";
            lvl3Id = "FP";
            lvl1Desc = "ABC";
            lvl2Desc = "Capitol Group";
            lvl3Desc = "Family Practice";
            sfw_SSOMemId = "220228231";//for AppId 008-101
            wrongMemId = "341904997";
        }else if(context.getAppId().equals("982-001")){
            memberId = "220060691";
            memberIdEncoded = "940914791";
            memberId2="220000673";
            memberId2Encoded = "948914699";
            lvl1Id="AETNA";
            lvl2Id="3";
            lvl3Id="GP";
            lvl1Desc="LOCAL PAYOR";
            lvl2Desc="Hartsburg Physicians";
            lvl3Desc="General Practice";
            sfw_SSOMemId="220036207";//for AppId 982-001
            wrongMemId="320020998";
        }
        webElements = new WebElements(webDriver);
    }
    @AfterMethod (alwaysRun = true)
    public void closeSecondTab(){
        SeleniumUtils.closeCurrentWindowAndSwitchToNewWindow(webDriver);
    }

    public void popupExists() {
        if(webElements.closePopup.isDisplayed()){
            SeleniumUtils.click(webElements.closePopup, webDriver);
        }
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }
        @FindBy(id = "d2Form:_tryNewIndividualModal_controls_close")
        WebElement closePopup;
    }

    /**
     * TC2694
     * Positive test case
     */
    @Test(groups = {"SSO", "Security"}, description = "Test that member ID which falls under specific BL can login to system via SSO_TC2694.")
    public void memberIDOfSpecificBLCanLoginViaSSO_TC2694() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String random = randomValue();

        demoerQAPage.enterLoginName("mta_AUP_" + random);
        demoerQAPage.enterFirstName("first_" + random);
        demoerQAPage.enterLastName("last_" + random);
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterFormId("320");
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_aup_mi.com").getName());
        demoerQAPage.enterMemberId(memberId);
        demoerQAPage.enterAppId(context.getAppId());

        IIndividualDashboardMemberSummary memberSummaryPage = demoerQAPage.doSubmit(IndividualDashboardMemberSummary.class, defaultWaitTime);
        memberSummaryPage.doWaitTillFullyLoaded(30);
        String displayedPageTitle = memberSummaryPage.getDisplayedPageTitle();
        assertThat(displayedPageTitle).isEqualTo("Individual Dashboard: (320) Member Summary");
    }

    /**
     * TC2695
     * negative test case
     */
    @Test(groups = {"SSO", "Security"}, description = "Test that member Id which falls under specific BL cannot login to the system " +
            "via SSO_TC2695.")
    public void memberIdOfSpecificBLCannotLoginViaSSO_TC2695() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String random = randomValue();

        demoerQAPage.enterLoginName("mta_AUP_" + random);
        demoerQAPage.enterFirstName("first_" + random);
        demoerQAPage.enterLastName("last_" + random);
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_aup_mi.com").getName());
        demoerQAPage.enterMemberId(wrongMemId);
        demoerQAPage.enterFormId("949");

        SamlErrorPage errorPage = demoerQAPage.doSubmit(SamlErrorPage.class, defaultWaitTime);
        assertThat(errorPage.getErrorMessage()).matches(".*Member ID \\S+ does not exist.");

    }

    /**
     * TC2696
     */
    @Test (groups = {"SSO", "Security"})
    public void testThatSAMLUserCannotAccessRestrictedTabs() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String random = randomValue();

        demoerQAPage.enterLoginName("mta_AUP_" + random);
        demoerQAPage.enterFirstName("first_" + random);
        demoerQAPage.enterLastName("last_" + random);
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_aup_mi_TC2696.com").getName());
        demoerQAPage.enterMemberId(memberId);
        demoerQAPage.enterFormId("320");

        IIndividualDashboardMemberSummary indvDashboardMemberSummary = demoerQAPage.doSubmit(IndividualDashboardMemberSummary.class, defaultWaitTime);
        assertThat(indvDashboardMemberSummary.isTabAvailable("Trend")).isFalse();
    }

    /**
     * TC2700
     */
    @Test (groups = {"SSO", "Security"})
    public void testThatSAMLUserHaveFullAccessToSfwAssigned() {
        //saml login with no sfw assigned user
        DemoerQAPage demoerQAPage = getLoggedIn();

        demoerQAPage.enterLoginName(getUser("miautomation_sso_user").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_normal.com").getName());
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterFormId("949");
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterMemberId(memberId2);

        IndividualDashboardIndividualClaimDetails claimDetailsPage = demoerQAPage.doSubmit(IndividualDashboardIndividualClaimDetails.class, defaultWaitTime);
        assertThat(claimDetailsPage.getPageTitle()).isEqualTo("Individual Dashboard: (949) Individual Claim Details");

        //saml login with invalid member ID TC2700_invalid_member
        closeSecondTab();
        demoerQAPage.clearAll();
        demoerQAPage.enterLoginName(getUser("miautomation_sso_groupnapp_sfw_user").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_normal.com").getName());
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterFormId("949");
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterMemberId(wrongMemId);

        SamlErrorPage errorPage = demoerQAPage.doSubmit(SamlErrorPage.class, defaultWaitTime);
        assertThat(errorPage.getErrorMessage()).matches(".*Member ID \\S+ does not exist.");

        //saml login with valid member ID TC2700_valid_member
        closeSecondTab();
        demoerQAPage.clearAll();
        demoerQAPage.enterLoginName(getUser("miautomation_sso_groupnapp_sfw_user").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_normal.com").getName());
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterFormId("949");
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterMemberId(sfw_SSOMemId);

        claimDetailsPage = demoerQAPage.doSubmit(IndividualDashboardIndividualClaimDetails.class, defaultWaitTime);
        assertThat(claimDetailsPage.getPageTitle()).isEqualTo("Individual Dashboard: (949) Individual Claim Details");

    }

    /**
     * TC2701
     */
    @Test (groups = {"SSO", "Security"})
    public void testThatSSOUserLandsInIndividualDashboard() {
        DemoerQAPage demoerQAPage = getLoggedIn();

        demoerQAPage.enterLoginName(getUser("miautomation_sso_usernapp_sfw_user").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_normal.com").getName());
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterFormId("949");
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterMemberId(sfw_SSOMemId);

        IIndividualDashboardIndividualClaimDetails claimDetailsPage = demoerQAPage.doSubmit(IndividualDashboardIndividualClaimDetails.class, WAIT_TIME_SECONDS);
        assertThat(claimDetailsPage.getDisplayedPageTitle()).isEqualTo("Individual Dashboard: (949) Individual Claim Details");
        assertThat(claimDetailsPage.isNextButtonDisplayed()).isFalse();
        assertThat(isNavigationPanelAvailable()).isFalse();

    }

    /**
     * author: bibdahal
     * Testcase: TC2702 User cannot have access to restricted tab in Individual Dashboard
     */
    @Test (groups = {"SSO", "Security"})
    public void normalSAMLUserCannotAccessRestrictedTabs() {
        DemoerQAPage demoerQAPage = getLoggedIn();

        demoerQAPage.enterLoginName(getUser("mta_sso_trend_denied_user").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_normal.com").getName());
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterFormId("949");
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterMemberId(memberId2);

        IIndividualDashboardIndividualClaimDetails claimDetailsPage = demoerQAPage.doSubmit(IndividualDashboardIndividualClaimDetails.class, WAIT_TIME_SECONDS);
        assertThat(claimDetailsPage.isTabAvailable("Trend")).isFalse();

    }

    /**
     * author: bibdahal
     * Testcase: TC2702 (Negative scenario) User can have access to tab in Individual Dashboard
     */
    @Test (groups = {"SSO", "Security"})
    public void normalSAMLUserCanViewNonRestrictedTabs() {
        DemoerQAPage demoerQAPage = getLoggedIn();

        demoerQAPage.enterLoginName(getUser("miautomation_sso_user").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_normal.com").getName());
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterFormId("949");
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterMemberId(memberId);

        IIndividualDashboardIndividualClaimDetails claimDetailsPage = demoerQAPage.doSubmit(IndividualDashboardIndividualClaimDetails.class, WAIT_TIME_SECONDS);
        claimDetailsPage.doWaitTillFullyLoaded(30);
        assertThat(claimDetailsPage.isTabAvailable("Trend")).isTrue();

    }

    /**
     * TC2703
     */
    @Test(priority = 2, groups = {"SSO", "Security"})
    public void userShouldReachLoggedOutPageAfterClickingLogoutLinkForNormalSSO() {
        DemoerQAPage demoerQAPage = getLoggedIn();

        demoerQAPage.enterLoginName(getUser("miautomation_sso_user").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_normal.com").getName());
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterFormId("949");
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterMemberId(memberId2);

        IIndividualDashboardIndividualClaimDetails claimDetailsPage = demoerQAPage.doSubmit(IndividualDashboardIndividualClaimDetails.class, WAIT_TIME_SECONDS);
        LoggedOutPage loggedOutPaged = claimDetailsPage.logoutExpectingPage(LoggedOutPage.class, defaultWaitTime);
        assertThat(loggedOutPaged.getPageTitle()).isEqualTo("Logged Out");

    }


    /**
     * author: bibdahal
     * TC2705 Error Issuer is unknown, please send a message with valid issuer

     */
    @Test(groups = {"SSO", "Security"}, description = "Test that normal SAML user logins with business level selected.")
    public void normalSAMLUserLoginWithBusinessLevel() {
        //saml login with business level selected
        DemoerQAPage demoerQAPage = getLoggedIn();

        demoerQAPage.enterLoginName(getUser("mta_sso_trend_denied_user").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_normal.com").getName());
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterFormId("949");
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterMemberId(memberId2);
        demoerQAPage.enterLvl1Ids(lvl1Id);
        demoerQAPage.enterLvl2Ids(lvl2Id);
        demoerQAPage.enterLvl3Ids(lvl3Id);

        IIndividualDashboardIndividualClaimDetails claimDetailsPage = demoerQAPage.doSubmit(IndividualDashboardIndividualClaimDetails.class, WAIT_TIME_SECONDS);
        assertThat(claimDetailsPage.isTabAvailable("Trend")).isFalse();

    }


    /**
     * author: bibdahal
     * TC2705 negative test with invalid member id
     */
    @Test(groups = {"SSO", "Security"}, description = "Test that normal SAML user fails to login with invalid member Id and Business Level " +
            "selected.")
    public void normalSAMLUserLoginWithInvalidMemberIdAndBusinessLevel() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        //saml login with invalid in one of the business level selected

        String issuerName = getIssuer("www.webautomationtestissuer_normal.com").getName();
        demoerQAPage.enterLoginName(getUser("miautomation_sso_user").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(issuerName);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterFormId("949");
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterMemberId(wrongMemId);
        demoerQAPage.enterLvl1Ids(lvl1Id);
        demoerQAPage.enterLvl2Ids("1");
        demoerQAPage.enterLvl3Ids(lvl3Id);

        SamlErrorPage samlErrorPage = demoerQAPage.doSubmit(SamlErrorPage.class, defaultWaitTime);
        String errorMessage = samlErrorPage.getErrorMessage();
        assertThat(errorMessage).contains("Error ID");

    }

    /**
     * author: bibdahal
     * Testcase: TC2697
     * Scenario where user land to Physician Manager without getting to Welcome page
     */
    @Test (groups = {"SSO", "Security"})
    public void pisamlUserLandsDirectlyOnPhysicianManagerPage() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String random = randomValue();

        demoerQAPage.enterLoginName("mta_AUP_" + random);
        demoerQAPage.enterFirstName("first_" + random);
        demoerQAPage.enterLastName("last_" + random);
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_aup_pi_clinicaldenied.com").getName());
        demoerQAPage.enterFormId("CP110");
        demoerQAPage.enterLvl1Ids("UHC");
        demoerQAPage.enterLvl2Ids("2");

        CP110 cp110page = demoerQAPage.doSubmit(CP110.class, defaultWaitTime);
        assertThat(cp110page.getPageTitle()).isEqualTo("(CP110) Physician Manager");

    }

    /**
     * author: bibdahal
     * Testcase: TC2697
     * Scenario where user with partial sfw assigned land to Clinic Manager without getting to Welcome page
     */
    @Test (groups = {"SSO", "Security"})
    public void testThatPISAMLUserLandsOnClinicManagerPage() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String random = randomValue();

        demoerQAPage.enterLoginName("mta_AUP_1_" + random);
        demoerQAPage.enterFirstName("first_" + random);
        demoerQAPage.enterLastName("last_" + random);
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_aup_pi_partialsfw.com").getName());
        demoerQAPage.enterFormId("CP100");
        demoerQAPage.enterLvl1Ids("UHC");
        demoerQAPage.enterLvl2Ids("2");

        ICP100 cp100 = demoerQAPage.doSubmit(CP100.class, defaultWaitTime);
        assertThat(cp100.getPageTitle()).isEqualTo("(CP100) Clinic Manager");

    }

    /**
     * author: bibdahal
     * TC1532: [HIGHMARK PI] Verify on the landing page that a user has full access as the SFW is assigned.
     */
    @Test (groups = {"SSO", "Security"})
    public void pisamlUserWithFullSFWLandsOnClinicManagerPage() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String random = randomValue();

        demoerQAPage.enterLoginName("mta_AUP_" + random);
        demoerQAPage.enterFirstName("first_" + random);
        demoerQAPage.enterLastName("last_" + random);
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_aup_pi.com").getName());
        demoerQAPage.enterFormId("CP100");
        demoerQAPage.enterLvl1Ids("UHC");
        demoerQAPage.enterLvl2Ids("2");

        ICP100 cp100 = demoerQAPage.doSubmit(CP100.class, defaultWaitTime);
        assertThat(cp100.getPageTitle()).isEqualTo("(CP100) Clinic Manager");
        BLComponentFacade businessLevel = new BLComponentFacade(cp100.getBusinessLevel());
        List<String> values = businessLevel.getValues(1);
        assertThat(values.contains(lvl1Desc));
        values = businessLevel.getValues(2);
        assertThat(values.contains(lvl2Desc));

    }

    /**
     * author: bibdahal
     * TC1532: [HIGHMARK PI] Verify on the landing page that a user has full access as the SFW is assigned.
     * Scenario : Negative test (When different form id other than CP100 or CP110 is provided, It should show error page)
     */
//    @Test (groups = {"SSO", "Security"})
//    public void pidsmlUserLandsOnErrorPageWhenDifferentFormIdProvided() {
//        DemoerQAPage demoerQAPage = getLoggedIn();
//        String random = randomValue();
//
//        demoerQAPage.enterLoginName("mta_AUP_" + random);
//        demoerQAPage.enterFirstName("first_" + random);
//        demoerQAPage.enterLastName("last_" + random);
//        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
//        demoerQAPage.enterNotBefore(notBefore);
//        demoerQAPage.enterNotOnOrAfter(notAfter);
//        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_aup_pi.com").getName());
//        demoerQAPage.enterFormId("949");
//        demoerQAPage.enterLvl1Ids(lvl1Id);
//        demoerQAPage.enterLvl2Ids(lvl2Id);
//
//        SamlErrorPage samlErrorPage = demoerQAPage.doSubmit(SamlErrorPage.class, 200);
//        String errorMessage = samlErrorPage.getErrorMessage();
//
//        assertThat(errorMessage).contains("Error ID:(101) [MemeberId] is mandatory");
//
//    }

    /**
     * TC2698
     */
    @Test(groups = {"SSO", "Component-Interaction"}, description = "Test that Misc right is applied correctly for Saml user Highmark PI.")

    public void correctApplyOfmiscRightToSamlUserHighMarkPI() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String random = randomValue();

        demoerQAPage.enterLoginName("mta_AUP_" + random);
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterFirstName("first_" + random);
        demoerQAPage.enterLastName("last_" + random);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_aup_pi.com").getName());
        demoerQAPage.enterFormId("H10I");

        Indv301 indv301 = demoerQAPage.doSubmit(Indv301.class, defaultWaitTime);
        popupExists();
        IDataGridCustomizer dataGridCustomizer = indv301.getDataGridCustomizer();
        dataGridCustomizer.doSelectAll();
        dataGridCustomizer.doApplySelection();
        new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();

        Indv301DataGrid dataGrid = indv301.getDataGrid();
        List<IDataGridColumn> columns = dataGrid.getColumns();

        assertThat(columns).contains(Indv301Columns.MEMBER_ID, Indv301Columns.DOB);
        assertThat(columns).excludes(Indv301Columns.MEMNAME);

        for (String dob : dataGrid.getData(Indv301Columns.DOB)) {
            assertThat(dob).isEqualTo("Blinded");
        }

        assertThat(dataGrid.isSortable(Indv301Columns.MEMBER_ID)).isFalse();
        assertThat(dataGrid.isFilterable(Indv301Columns.MEMBER_ID)).isTrue();

        assertThat(dataGrid.isSortable(Indv301Columns.DOB)).isFalse();
        assertThat(dataGrid.isFilterable(Indv301Columns.DOB)).isFalse();

    }


    /**
     * TC1533
     * <p>
     * author msedhain
     */
    @Test(groups = {"SSO", "Security"}, description = "Test that Highmark PISAML user lands on Welcome Page.")
    public void highmarkPISAMLUserLandsOnWelcomePage() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String random = randomValue();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_aup_pi_TC1533.com").getName());
        demoerQAPage.enterLoginName("mta_AUP_" + random);
        demoerQAPage.enterFirstName("first_" + random);
        demoerQAPage.enterLastName("last_" + random);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterFormId("CP110");

        WelcomePage welcomePage = demoerQAPage.doSubmit(WelcomePage.class, defaultWaitTime);

        Collection<String> availableFronts = welcomePage.getAvailableFronts();
        assertThat(availableFronts).contains(context.getAppId());
        assertThat(availableFronts).contains(context.getAppId2());
        assertThat(availableFronts.size()).isEqualTo(2);

        INavigationPanel navigationPanel = welcomePage.selectFront(context.getAppId(), defaultWaitTime);

        ExecutiveSummary executiveSummary = navigationPanel.doNavigateTo(ExecutiveSummary.class, defaultWaitTime);
        assertThat(executiveSummary.getDisplayedPageTitle()).isEqualTo(MILandingPages.EXECUTIVE_SUMMARY.getPageTitle());

    }

    /**
     * author: bibdahal
     * Testcase: TC1534
     * application with selected levels
     */
    @Test(groups = {"SSO", "Security"}, description = "Test that Highmark PISAML user lands on page with business Level.")
    public void highmarkPISAMLUserLandsOnPageWithBusinessLevel() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String random = randomValue();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_aup_pi_TC1533.com").getName());
        demoerQAPage.enterFirstName("first_" + random);
        demoerQAPage.enterLastName("last_" + random);
        demoerQAPage.enterLoginName("mta_AUP_" + random);
        demoerQAPage.enterFormId("CP100");
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterLvl2Ids(lvl2Id);
        demoerQAPage.enterLvl3Ids(lvl3Id);

        ICP100 cp100 = demoerQAPage.doSubmit(CP100.class, defaultWaitTime);
        BLComponentFacade businessLevel = new BLComponentFacade(cp100.getBusinessLevel());
        List<String> values = businessLevel.getValues(2);
        assertThat(values.contains(lvl2Desc));
        values = businessLevel.getValues(3);
        assertThat(values.contains(lvl3Desc));

    }


    /**
     * TC1522
     * <p>
     * author msedhain
     */
    @Test (groups = {"SSO", "Security"})
    public void testThatSAMLUserCannotLandsOnPageWhichItDoesNotHaveAccess() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String loginName = getUser("mta_TC1522").getUserId();
        String issuerName = getIssuer("www.webautomationtestissuer_premiere.com").getName();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(issuerName);
        demoerQAPage.enterLoginName(loginName);
        demoerQAPage.enterFormId("CP100");
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);

        NotAuthorizedPage notAuthorizedPage =demoerQAPage.doSubmit(NotAuthorizedPage.class, defaultWaitTime);
        assertThat(notAuthorizedPage.getErrorURL().contains("errorId=(111)"));
        assertThat(notAuthorizedPage.getErrorMessage()).isEqualTo("You are not Authorized to view this page.");
    }


    /**
     * TC1519
     * <p>
     * author msedhain
     */
    @Test (groups = {"SSO", "Security"})
    public void testThatWrongKeyForSAMLUserResultsInErrorPage() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String loginName = getUser("mta_TC1519").getUserId();
        String issuerName = getIssuer("www.webautomationtestissuer_wrong_key.com").getName();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(issuerName);
        demoerQAPage.enterLoginName(loginName);
        demoerQAPage.enterFormId("CP110");
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);

        MsgTamperedErrorPage msgTamperedErrorPage = demoerQAPage.doSubmit(MsgTamperedErrorPage.class, 200);
        String errorMessage = msgTamperedErrorPage.getErrorMessage();
        assertThat(errorMessage).contains("Error 3 Message tampered");

    }

    @Test (groups = {"SSO", "Security"})
    public void testThatNavigationWorksInGeneralFlow(){
        DemoerQAPage demoerQAPage = getLoggedIn();
        String loginName = getUser("mta_TC1517").getUserId();
        String issuerName = getIssuer("www.webautomationtestissuer_prem_multipleapp.com").getName();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterLoginName(loginName);
        demoerQAPage.enterIssuer(issuerName);
        demoerQAPage.enterFormId("CP100");
        demoerQAPage.enterAppId(context.getAppId());

        CP100 cp100Page = demoerQAPage.doSubmit(CP100.class, defaultWaitTime);
        cp100Page.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        assertThat(cp100Page.getPageTitle()).isEqualToIgnoringCase("(CP100) Clinic Manager");

        assertThat(cp100Page.getNavigationPanel().
                isChooseAnotherApplicationLinkNameAvailable()).isFalse();

        ExecutiveSummary executiveSummary  = cp100Page.getNavigationPanel().doNavigateTo(ExecutiveSummary.class, defaultWaitTime);;
        assertThat(executiveSummary.getDisplayedPageTitle()).isEqualTo(MILandingPages.EXECUTIVE_SUMMARY.getPageTitle());
    }

    @Test(groups = {"SSO", "Security"}, description = "SAML PAGE(QA) => Select AppType(MI) => Provide Username => Provide invalid Issuer => Provide AppID Issuer => Provide FormId => Provide member Id => Submit")
    public void testThatInvalidIssuerResultsInErrorPage() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String loginName = getUser("mta_TC1743").getUserId();
        String issuerName = (getIssuer("www.webautomationtestissuer_normal.com").getName()) + "11"; //Invalid issuerName

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterLoginName(loginName);
        demoerQAPage.enterIssuer(issuerName);
        demoerQAPage.enterFormId("320");
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterMemberId(memberId2);

        MsgTamperedErrorPage msgTamperedErrorPage = demoerQAPage.doSubmit(MsgTamperedErrorPage.class, TimeOuts.SIXTY_SECONDS);
        String errorMessage = msgTamperedErrorPage.getErrorMessage();
        assertThat(errorMessage).contains("Error 3 Message tampered");
    }

    @Test( description = "SAML PAGE(QA) => Select AppType(MI) => Provide Username => Provide  Issuer => Provide Invalid AppID Issuer => Provide FormId => Provide member Id => Submit")
    public void testThatInvalidAppIdResultsInErrorPage() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String loginName = getUser("mta_TC1743").getUserId();
        String issuerName = (getIssuer("www.webautomationtestissuer_normal.com").getName());
        String appId = context.getAppId2(); //Invalid appId

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterLoginName(loginName);
        demoerQAPage.enterIssuer(issuerName);
        demoerQAPage.enterFormId("H10I");
        demoerQAPage.enterAppId(appId);
        demoerQAPage.enterMemberId(memberId2);

        SamlErrorPage samlErrorPage = demoerQAPage.doSubmit(SamlErrorPage.class, 200);
        String errorMessage = samlErrorPage.getErrorMessage();
        assertThat(errorMessage).contains("Error ID:(1) Selected application not available.");
    }

    @Test(groups = {"SSO", "Security"},  description = "SAML PAGE(QA) => Select AppType(MI) => Provide Username  => Provide AppID Issuer => Provide FormId => Submit")
    public void testThatIssuersMustBeProvidedForNonAUPUser() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String loginName = getUser("mta_TC1743").getUserId();
        String appId = context.getAppId();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterLoginName(loginName);
        demoerQAPage.enterFormId("CP110");
        demoerQAPage.enterAppId(appId);

        MsgTamperedErrorPage msgTamperedErrorPage = demoerQAPage.doSubmit(MsgTamperedErrorPage.class, TimeOuts.SIXTY_SECONDS);
        String errorMessage = msgTamperedErrorPage.getErrorMessage();
        assertThat(errorMessage).contains("Error Issuer is null, please send a valid request message");
    }

    @Test (groups = {"SSO", "Security"}, description = "SAML PAGE(QA) => Select AppType(MI) => Enter Issuer => Enter LoginName => Enter AppId => Enter Invalid FormId => Submit")
    public void testThatInvalidFormIdResultsInErrorPage(){
        DemoerQAPage demoerQAPage = getLoggedIn();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_normal.com").getName());
        demoerQAPage.enterLoginName(getUser("miautomation_sso_user").getUserId());
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterFormId("qwerty123");

        SamlErrorPage samlErrorPage = demoerQAPage.doSubmit(SamlErrorPage.class, defaultWaitTime);
        assertThat(samlErrorPage.getErrorMessage()).isEqualTo("Error ID:(7) Invalid formId.");
    }



    /**
     * TC1517
     * <p>
     * author msedhain
     */
    @Test (groups = {"SSO", "Security"}, description = "SAML PAGE(QA) => Select AppType(MI) => Provide Issuer => Provide Username => Provide FormId => Submit")
    public void testThatPremMulipleApplicationUserLandsOnWelcomePage() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String loginName = getUser("mta_TC1517").getUserId();
        String issuerName = getIssuer("www.webautomationtestissuer_prem_multipleapp.com").getName();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterLoginName(loginName);
        demoerQAPage.enterIssuer(issuerName);
        demoerQAPage.enterFormId("CP100");
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);

        WelcomePage welcomePage = demoerQAPage.doSubmit(WelcomePage.class, defaultWaitTime);

        Collection<String> availableFronts = welcomePage.getAvailableFronts();
        assertThat(availableFronts).contains(context.getAppId());
        assertThat(availableFronts).contains(context.getAppId2());
        assertThat(availableFronts.size()).isEqualTo(2);
        INavigationPanel navigationPanel = welcomePage.selectFront(context.getAppId(), defaultWaitTime);

        ExecutiveSummary executiveSummary = navigationPanel.doNavigateTo(ExecutiveSummary.class, defaultWaitTime);
        assertThat(executiveSummary.getDisplayedPageTitle()).isEqualTo(MILandingPages.EXECUTIVE_SUMMARY.getPageTitle());

    }

    /**
     * TC2697_CP100
     */
    @Test (groups = {"SSO", "Security"})
    public void testThatSAMLUserHavePartialAccessAsTheSFWAssigned() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String appId = context.getAppId();
        String random = randomValue();
        SFW sfw = TestEnvironment.find.sfwByGroupAndApplication("mta_sso_pi_partial_sfw", appId);

        demoerQAPage.enterLoginName("mta_AUP_" + random);
        demoerQAPage.enterFirstName("first_" + random);
        demoerQAPage.enterLastName("last_" + random);
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_aup_pi_partialsfw.com").getName());
        demoerQAPage.enterFormId("CP100");
        demoerQAPage.enterAppId(appId);

        ICP100 cp100Page =demoerQAPage.doSubmit(CP100.class, defaultWaitTime);
        cp100Page.doWaitTillFullyLoaded(30);
        BLComponentFacade businessLevel = new BLComponentFacade(cp100Page.getBusinessLevel());

        List<Integer> levelsToCheck = sfw.getLevelsForWhichExpectedValuesArePresent();
        for (Integer level : levelsToCheck) {
            List<String> availableOptions = businessLevel.getValues(level);

            SFW.ExpectedResults expectedResultForLevel = sfw.getExpectedResultForLevel(level);
            if (expectedResultForLevel.getRelation().equals("containsOnly")) {
                assertThat(availableOptions).containsOnly(expectedResultForLevel.getExpectedValues().toArray());
            } else if (expectedResultForLevel.getRelation().equals("doesNotContain")) {
                assertThat(availableOptions).excludes(expectedResultForLevel.getExpectedValues().toArray());
            } else {
                throw new Error("Unknown relation " + expectedResultForLevel.getRelation());
            }
        }

    }


    /**
     * TC1743
     * author msedhain
     */
    @Test (groups = {"SSO", "Security"})
    public void testThatNormalSAMLUserlogin() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String loginName = getUser("mta_TC1743").getUserId();
        String issuerName = (getIssuer("www.webautomationtestissuer_normal.com").getName());

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterLoginName(loginName);
        demoerQAPage.enterIssuer(issuerName);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterFormId("320");
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterMemberId(memberId2);

        IndividualDashboardMemberSummary memberSummary = demoerQAPage.doSubmit(IndividualDashboardMemberSummary.class, WAIT_TIME_SECONDS);
        assertThat(memberSummary.getPageTitle()).isEqualTo("Individual Dashboard: (320) Member Summary");

    }

    /**
     * author: bibdahal
     * Testcase: TC1744
     * Verify Report Manager SSO login: should get to RM page directly through saml page
     */
    @Test (groups = {"SSO", "Security"})
    public void testThatSAMLUserLandsOnReportManagerApplication() {
        DemoerQAPage demoerQAPage = getLoggedIn();

        demoerQAPage.enterLoginName(getUser("mta_RMSAML").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.REPORT_MANAGER);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_normal.com").getName());
        demoerQAPage.enterAppId(context.getAppId());

        ReportAdministrator reportAdministrator = demoerQAPage.doSubmit(ReportAdministrator.class, defaultWaitTime);
        assertThat(reportAdministrator.getDisplayedTitle()).isEqualTo("Report Administration");

        assertThat(reportAdministrator.getDisplayedTabs()).containsOnly(
                "Report Wizard", "Custom Sort"
        );
    }

    /**
     * TC1523
     * <p>
     * author msedhain
     */
    @Test(priority = 2, groups = {"SSO", "Security"})
    public void userReachLoggedOutPageAfterClickingLogoutLinkForPREMSSO() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String loginName = getUser("mta_TC1522").getUserId();
        String issuerName = getIssuer("www.webautomationtestissuer_premiere.com").getName();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(issuerName);
        demoerQAPage.enterLoginName(loginName);
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterFormId("CP110");
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterNotOnOrAfter(notAfter);

        CP110 cp110 = demoerQAPage.doSubmit(CP110.class, defaultWaitTime);
        assertThat(cp110.getPageTitle()).isEqualTo("(CP110) Physician Manager");

        LoggedOutPage loggedOutPaged = cp110.logoutExpectingPage(LoggedOutPage.class, defaultWaitTime);
        assertThat(loggedOutPaged.getPageTitle()).isEqualTo("Logged Out");

    }

    /**
     * author: bibdahal
     * Testcase: TC1744-ReportWizard Test
     * Verify Report Manager SSO login: should get to RM page directly through saml page
     */
//    @Test(groups = {"SSO", "Security"}, description = "SAML PAGE(QA) => Enter Login Name,FirstName,LastName => Select AppType(MI) => Enter NotBerfore and NotAfter => " +
//            "Provide  Issuer =>Provide App Id => Provide LvlIDs => Submit => Report Manager Page")
//    public void testThatAUPLandsOnReportManagerApplication() {
//        DemoerQAPage demoerQAPage = getLoggedIn();
//        String random = randomValue();
//        String appId = context.getAppId();
//
//        demoerQAPage.enterLoginName("mta_AUP_" + random);
//        demoerQAPage.enterFirstName("first_" + random);
//        demoerQAPage.enterLastName("last_" + random);
//        demoerQAPage.selectAppType(DemoerQAPage.AppType.REPORT_MANAGER);
//        demoerQAPage.enterNotBefore(notBefore);
//        demoerQAPage.enterNotOnOrAfter(notAfter);
//        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_aup_mi.com").getName());
//        demoerQAPage.enterAppId(appId);
//
//        ReportAdministrator reportAdministrator = demoerQAPage.doSubmit(ReportAdministrator.class, defaultWaitTime);
//        assertThat(reportAdministrator.getDisplayedTitle()).isEqualTo("Report Administration");
//        assertThat(reportAdministrator.getDisplayedTabs()).containsOnly(
//                 "Custom Sort"
//        );
//
//    }

    @Test (groups = {"SSO", "Security"})
    public void testThatSAMLUserLandsOnReportManagerApplication1() {
        DemoerQAPage demoerQAPage = getLoggedIn();

        demoerQAPage.enterLoginName(getUser("mta_RMSAML").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.REPORT_MANAGER);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_normal.com").getName());
        demoerQAPage.enterAppId(context.getAppId());

        ReportAdministrator reportAdministrator = demoerQAPage.doSubmit(ReportAdministrator.class, defaultWaitTime);
        assertThat(reportAdministrator.getDisplayedTitle()).isEqualTo("Report Administration");
        ReportWizard reportWizard = reportAdministrator.goToReportWizard(defaultWaitTime);
        assertThat(reportWizard.getDisplayedTabs()).containsOnly(
                "Back"
        );
        assertThat(reportWizard.getDisplayedTabRows()).containsOnly(
                "Data Selection", "Reports", "Generation", "User Access"
        );

    }

    /**
     * author: bibdahal
     * Testcase: TC2706
     * Verify SAML login with Business Level Selected
     */
    @Test (groups = {"SSO", "Security"})
    public void testThatSAMLUserLandsOnReportManagerWithBLSelected(){
        DemoerQAPage demoerQAPage = getLoggedIn();

        demoerQAPage.enterLoginName(getUser("mta_RMSAML").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.REPORT_MANAGER);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_normal.com").getName());
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterLvl1Ids(lvl1Id);
        demoerQAPage.enterLvl2Ids(lvl2Id);

        ReportAdministrator reportAdministrator = demoerQAPage.doSubmit(ReportAdministrator.class, defaultWaitTime);
        assertThat(reportAdministrator.getDisplayedTitle()).isEqualTo("Report Administration");
        ReportWizard reportWizard = reportAdministrator.goToReportWizard(defaultWaitTime);
        assertThat(reportWizard.getDisplayedTabs()).containsOnly(
                "Back"
        );
        assertThat(reportWizard.getDisplayedTabRows()).containsOnly(
                "Data Selection", "Reports", "Generation", "User Access"
        );

    }

    /**
     * author: bibdahal
     * Testcase: TC2707
     * Verify Logout by clicking a link
     */
    @Test(priority = 2, groups = {"SSO", "Security"})
    public void testThatSAMLUserLandsOnReportManagerApplicationAndToLogout() {
        DemoerQAPage demoerQAPage = getLoggedIn();

        demoerQAPage.enterLoginName(getUser("mta_RMSAML").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.REPORT_MANAGER);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_normal.com").getName());
        demoerQAPage.enterAppId(context.getAppId());

        ReportAdministrator reportAdministrator = demoerQAPage.doSubmit(ReportAdministrator.class, defaultWaitTime);
        assertThat(reportAdministrator.getDisplayedTitle()).isEqualTo("Report Administration");
        LoggedOutPage loggedOutPaged = reportAdministrator.logoutExpectingPage(LoggedOutPage.class, defaultWaitTime);
        assertThat(loggedOutPaged.getPageTitle()).isEqualTo("Logged Out");

    }

    /**
     * author: msedhain
     * Testcase: TC28111
     * Verify login by clicking Email Triage XML
     */
    @Test (groups = {"SSO", "Security"})
    public void testThatSAMLUserLandsOnReportManagerEmailTriageXML() {
        DemoerQAPage demoerQAPage = getLoggedIn();

        demoerQAPage.enterLoginName(getUser("mta_RMSAML").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.REPORT_MANAGER);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_normal.com").getName());
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.clickTriageEmail(true);

        ReportAdministrator reportAdministrator = demoerQAPage.doSubmit(ReportAdministrator.class, defaultWaitTime);
        assertThat(reportAdministrator.getDisplayedTitle()).isEqualTo("Report Administration");
        ReportWizard reportWizard = reportAdministrator.goToReportWizard(defaultWaitTime);
        assertThat(reportWizard.getDisplayedTabs()).containsOnly(
                "Back"
        );
        assertThat(reportWizard.getDisplayedTabRows()).containsOnly(
                "Data Selection", "Reports", "Generation", "User Access"
        );

    }

    /**
     * TC1521
     * <p>
     * author bibdahal
     * [PREM] Verify that a user has full access as the SFW is assigned (Checked up to level 3 only)
     */
    @Test (groups = {"SSO", "Security"})
    public void testThatPREMSAMLUserWithfullSFWLandsOnPhysicianManager() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String loginName = getUser("mta_TC1521").getUserId();
        String issuerName = getIssuer("www.webautomationtestissuer_premiere.com").getName();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(issuerName);
        demoerQAPage.enterLoginName(loginName);
        demoerQAPage.enterFormId("CP100");
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);

        ICP100 cp100 = demoerQAPage.doSubmit(CP100.class, defaultWaitTime);
        assertThat(cp100.getPageTitle()).isEqualTo("(CP100) Clinic Manager");

    }

    @Test (groups = {"SSO", "Security"}, description = "SAML PAGE(QA) => Enter Login Name,FirstName,LastName => Select AppType(MI) => Enter NotBerfore and NotAfter => " +
            "Provide  Issuer => Provide FormId =>Provide App Id => Provide LvlIDs => Submit")
    public void testThatAutoUserCreationWorksOnAUPGeneralFlow() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String random = randomValue();
        String appId = context.getAppId();

        demoerQAPage.enterLoginName("mta_AUP_" + random);
        demoerQAPage.enterFirstName("first_" + random);
        demoerQAPage.enterLastName("last_" + random);
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_aup_mi.com").getName());
        demoerQAPage.enterFormId("H10I");
        demoerQAPage.enterAppId(appId);
        demoerQAPage.enterLvl1Ids("UHC");
        demoerQAPage.enterLvl2Ids("4");

        Indv301 indv301Page = demoerQAPage.doSubmit(Indv301.class, defaultWaitTime);
        popupExists();
        assertThat(indv301Page.getPageTitle()).isEqualTo(MILandingPages.INDIVIDUALS_301.getPageTitle());
        BLComponentFacade businessLevel = new BLComponentFacade(indv301Page.getBusinessLevel());
        List<String> values = businessLevel.getValues(1);
        assertThat(values.contains(lvl1Desc));
        values = businessLevel.getValues(2);
        assertThat(values.contains(lvl2Desc));
    }

    @Test (groups = {"SSO", "Security"}, description = "SAML PAGE(QA) => Enter Login Name,FirstName,LastName => Enter AppType(MI) => Enter NotBerfore and NotAfter => " +
            "Provide  Issuer => Provide FormId =>Provide App Id => Provide member Id => Submit")
    public void testThatAutoUserCreationWorksOnAUPIndividualDashboardFlow() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String random = randomValue();
        String appId = context.getAppId();

        demoerQAPage.enterLoginName("mta_AUP_" + random);
        demoerQAPage.enterFirstName("first_" + random);
        demoerQAPage.enterLastName("last_" + random);
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_aup_mi.com").getName());
        demoerQAPage.enterFormId("320");
        demoerQAPage.enterAppId(appId);
        demoerQAPage.enterMemberId(memberId2);

        IndividualDashboardMemberSummary indvDashboardMemberSummary = demoerQAPage.doSubmit(IndividualDashboardMemberSummary.class,defaultWaitTime);
        assertThat(indvDashboardMemberSummary.getMemberId()).isEqualTo(memberId2Encoded);
        assertThat(indvDashboardMemberSummary.absenceOfNavigationPannel()).isTrue();
        assertThat(indvDashboardMemberSummary.loginSessionLimitedToSpecifiedMemberOnly()).isTrue();
    }

    @Test(priority = 1, groups = {"SSO", "Security"})
    public void testThatEncryptionWorksForAUPNormalFlow() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String random = randomValue();

        demoerQAPage.enterLoginName("mta_AUP_" + random);
        demoerQAPage.enterFirstName("first_" + random);
        demoerQAPage.enterLastName("last_" + random);
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_encrypted_aup.com").getName());
        demoerQAPage.enterMemberId(memberId);
        demoerQAPage.enterFormId("320");
        demoerQAPage.clickEncrypt(true);

        IIndividualDashboardMemberSummary indvDashboardMemberSummary = demoerQAPage.doSubmit(IndividualDashboardMemberSummary.class, defaultWaitTime);
        assertThat(indvDashboardMemberSummary.isTabAvailable("Trend")).isFalse();

    }

    @Test(priority = 1, groups = {"SSO", "Security"})
    public void testThatEncryptionWorksOnAUPGeneralFlow() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String random = randomValue();

        demoerQAPage.enterLoginName("mta_AUP_" + random);
        demoerQAPage.enterFirstName("first_" + random);
        demoerQAPage.enterLastName("last_" + random);
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_encrypted_aup.com").getName());
        demoerQAPage.enterFormId("CP110");
        demoerQAPage.enterLvl1Ids("UHC");
        demoerQAPage.enterLvl2Ids("3");
        demoerQAPage.clickEncrypt(true);

        CP110 cp110page = demoerQAPage.doSubmit(CP110.class, defaultWaitTime);
        assertThat(cp110page.getPageTitle()).isEqualTo("(CP110) Physician Manager");

    }

    @Test(priority = 1, groups = {"SSO", "Security"})
    public void testThatEncryptionWorksInIndividualFlow() {
        DemoerQAPage demoerQAPage = getLoggedIn();

        demoerQAPage.enterLoginName(getUser("miautomation_encrypted_test_user").getUserId());
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_encrypted.com").getName());
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterFormId("949");
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.enterMemberId(sfw_SSOMemId);
        demoerQAPage.clickEncrypt(true);

        IIndividualDashboardIndividualClaimDetails claimDetailsPage = demoerQAPage.doSubmit(IndividualDashboardIndividualClaimDetails.class, WAIT_TIME_SECONDS);
        assertThat(claimDetailsPage.getDisplayedPageTitle()).isEqualTo("Individual Dashboard: (949) Individual Claim Details");
        assertThat(claimDetailsPage.isNextButtonDisplayed()).isFalse();
        assertThat(isNavigationPanelAvailable()).isFalse();

    }


    @Test(priority = 1)
    public void testThatEncryptionWorksInNormalFlow() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String loginName = getUser("miautomation_encrypted_test_user").getUserId();
        String issuerName = getIssuer("www.webautomationtestissuer_encrypted.com").getName();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(issuerName);
        demoerQAPage.enterLoginName(loginName);
        demoerQAPage.enterFormId("CP100");
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterAppId(context.getAppId());
        demoerQAPage.clickEncrypt(true);

        CP100 cp100 = demoerQAPage.doSubmit(CP100.class, defaultWaitTime);

        assertThat(cp100.getPageTitle()).isEqualTo("(CP100) Clinic Manager");
    }

    @Test (groups = {"SSO", "Security"})
    public void testLandingOnEIDashboard() {
        DemoerQAPage demoerQAPage = getLoggedIn();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.ENTERPRISE_INTELLIGENCE);
        demoerQAPage.enterIssuer(getIssuer("www.webautomationtestissuer_ei.com").getName());
        demoerQAPage.enterLoginName( getUser("mta_EIUser").getUserId());
        demoerQAPage.submitForm();
        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(webDriver);
        SeleniumUtils.switchToNewWindow(webDriver);
        if (webDriver.getTitle().contains("End User License Agreement (EULA)")) {
            EULAPage eulaPage = new EULAPage(webDriver);
            eulaPage.agree(EIDashboardDummyPage.class, 30);
        }
        String currentURL = webDriver.getCurrentUrl();

        assertThat(currentURL.startsWith(eiDashboardUrl)).isTrue();
    }


    @Test (groups = {"SSO", "Security"}, description = "SAML PAGE(QA) => Select AppType(MI) => Provide Username => Provide  Issuer => Provide FormId =>Provide App Id => Provide member Id => Submit")
    public void testThatVerifySAMLUsersInIndividualDashboardFlow() {
        DemoerQAPage demoerQAPage = getLoggedIn();
        String loginName = getUser("mta_TC1743").getUserId();
        String issuerName = (getIssuer("www.webautomationtestissuer_normal.com").getName());
        String appId = context.getAppId();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterLoginName(loginName);
        demoerQAPage.enterIssuer(issuerName);
        demoerQAPage.enterFormId("320");
        demoerQAPage.enterAppId(appId);
        demoerQAPage.enterMemberId(memberId2);

        IndividualDashboardMemberSummary indvDashboardMemberSummary = demoerQAPage.doSubmit(IndividualDashboardMemberSummary.class,defaultWaitTime);
        assertThat(indvDashboardMemberSummary.getMemberId()).isEqualTo(memberId2Encoded);
        assertThat(indvDashboardMemberSummary.absenceOfNavigationPannel()).isTrue();
        assertThat(indvDashboardMemberSummary.loginSessionLimitedToSpecifiedMemberOnly()).isTrue();
    }

    @Test (groups = {"SSO", "Security"}, description ="SAML PAGE(QA)=> Provide AppType =>Provide UserName=>Provide Issuer=>Provide AppId=>Provide FordId=>Submit")
    public void LandingOnUnauthorisedPageWithInvalidFormId(){
        DemoerQAPage demoerQAPAge=getLoggedIn();
        String loginName=getUser("mta_TC1743").getUserId();
        String issuerName=getIssuer("www.webautomationtestissuer_normal.com").getName();
        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPAge.enterLoginName(loginName);
        demoerQAPAge.enterIssuer(issuerName);
        demoerQAPAge.enterAppId(context.getAppId());
        demoerQAPAge.enterFormId("EG900");
        NotAuthorizedPage notAuthorizedPage = demoerQAPage.doSubmit(NotAuthorizedPage.class,defaultWaitTime);
        assertThat(notAuthorizedPage.getErrorMessage().equals("You are not Authorized to view this page."));
    }


    /**
     * TC42268
     * [HM SSO]Verify that the user trying to perform SAML login can pass multiple appid using comma "," or pipe"|" separator
     */


    @Test(groups={"SSO", "Security"},description="SAML PAGE(QA)=>Provide AppType => Provide Issuer =>Provide UserName=>Provide NotBefore=> Provide NotOnOrAfter =>Provide Member Id =>Provide two app Id's separated by comma=> Provide FormId=> Submit=>opens the new window =>assert the title =>close the current window and go back to parent window=>repeat the same process with pipeline(|) between two appId's")


    public void SAMLLoginWithMultipleAppId(){
        DemoerQAPage demoerQAPage = getLoggedIn();
        String issuerName = getIssuer("www.webautomationtestissuer_normal.com").getName();
        String loginName = getUser("mta_TC1743").getUserId();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(issuerName);
        demoerQAPage.enterLoginName(loginName);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterMemberId(memberId);
        demoerQAPage.enterAppId(context.getAppId() + "," + context.getAppId2());
        demoerQAPage.enterFormId("320");

        IIndividualDashboardMemberSummary memberSummaryPage = demoerQAPage.doSubmit(IndividualDashboardMemberSummary.class, defaultWaitTime);
        memberSummaryPage.doWaitTillFullyLoaded(30);
        String displayedPageTitle = memberSummaryPage.getDisplayedPageTitle();
        assertThat(displayedPageTitle).isEqualTo("Individual Dashboard: (320) Member Summary");

        demoerQAPage.switchBackToParentWindowAndCloseChildWindow();
        demoerQAPage.clearAll();

        demoerQAPage.selectAppType(DemoerQAPage.AppType.MEDICAL_INTELLIGENCE);
        demoerQAPage.enterIssuer(issuerName);
        demoerQAPage.enterLoginName(loginName);
        demoerQAPage.enterNotBefore(notBefore);
        demoerQAPage.enterNotOnOrAfter(notAfter);
        demoerQAPage.enterMemberId(memberId);
        demoerQAPage.enterAppId(context.getAppId() + "|" + context.getAppId2());
        demoerQAPage.enterFormId("320");

        memberSummaryPage = demoerQAPage.doSubmit(IndividualDashboardMemberSummary.class, defaultWaitTime);
        memberSummaryPage.doWaitTillFullyLoaded(30);
        displayedPageTitle = memberSummaryPage.getDisplayedPageTitle();
        assertThat(displayedPageTitle).isEqualTo("Individual Dashboard: (320) Member Summary");
    }






}



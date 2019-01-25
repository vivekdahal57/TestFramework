package com.vh.mi.automation.test.saml;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardIndividualClaimDetails;
import com.vh.mi.automation.api.pages.saml.INormalSSO;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardIndividualClaimDetails;
import com.vh.mi.automation.impl.pages.saml.NormalSSO;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test Case TC1743: Verify Normal SSO login
 * https://rally1.rallydev.com/#/20126998288d/detail/testcase/24968948159
 * <p/>
 * Signed SAML Response should be generated. Public Key and Private Key should be shown.
 * 1. Lands on Individual Dashboard on the same form Id Specified by testUser. If form ID is not specified, testUser
 * should be shown 320 - summary form.
 * 2. User should not be able to navigate to next testUser.
 * 3. User Should not be change to other application and don't have access to Welcome Page.
 * 4. Logout link is not provided. (todo)? invalid
 *
 * @author nimanandhar
 */
public class NormalSSOTest extends SSOBaseTest {

    @Test (groups = "Deprecated-Tests")
    public void testThatUserLandsInIndividualDashboard() {
        INormalSSO normalSSOPage = NormalSSO.loadPage(webDriver, "https://nvscmlinq1.d2hawkeye.net:8200");

        normalSSOPage.enterLoginName("mta_normalSSOTest_user_local");
        normalSSOPage.enterIssuer("www.webautomationtestissuer.com");
        normalSSOPage.enterNotBefore("2015-01-01T00:00:00Z");
        normalSSOPage.enterNotOnOrAfter("2015-12-31T23:59:59Z");
        normalSSOPage.enterFormId("949");
        normalSSOPage.enterAppId("982-001");
        normalSSOPage.enterMemberId("220000673");
        normalSSOPage.doClickSamlDefaultAssertion();

        assertThat(normalSSOPage.getSignedSamlResponseText()).isNotEmpty();
        assertThat(normalSSOPage.getPrivateKeyText()).isNotEmpty();
        assertThat(normalSSOPage.getPublicKeyText()).isNotEmpty();


        IIndividualDashboardIndividualClaimDetails claimDetailsPage = normalSSOPage.doSubmit(IndividualDashboardIndividualClaimDetails.class, WAIT_TIME_SECONDS);

        assertThat(claimDetailsPage.getDisplayedPageTitle()).isEqualTo("Individual Dashboard: (949) Individual Claim Details");
        assertThat(claimDetailsPage.isNextButtonDisplayed()).isFalse();

        assertThat(isNavigationPanelAvailable()).isFalse();
    }



}


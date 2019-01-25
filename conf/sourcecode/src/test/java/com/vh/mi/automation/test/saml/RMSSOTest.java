package com.vh.mi.automation.test.saml;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.saml.IRMSSO;
import com.vh.mi.automation.impl.pages.saml.RMSSO;
import com.vh.mi.automation.impl.reportManager.ReportAdministrator;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test Case TC1744: Verify Report Manager SSO login
 * 1. Lands on Report Manager default page
 * 2. User should be able to generate report for only selected appid.
 * 3. User Should not be change to other application and don't have access to Welcome Page.
 * <p/>
 * 4. Logout link is not provided
 * https://nvscmlinq1.d2hawkeye.net:8200/SAML/Response/Sample/D2RMClientPage.jsp
 *
 * @author nimanandhar
 */
@Deprecated
public class RMSSOTest extends SSOBaseTest {

   @Test (groups = "Deprecated-Tests")
    public void testRMSSOPage() {
        IRMSSO rmSSOPage = RMSSO.loadPage(webDriver, "https://nvscmlinq1.d2hawkeye.net:8200");
        User user = getUser("miautomation_rmSSOTest_user");

        rmSSOPage.enterUrlToLogin("https://nvscmlinq1.d2hawkeye.net:8000/SAML/Response/index.jsp");
        rmSSOPage.enterProduct("D2RM");
        rmSSOPage.enterLoginName(user.getUserId());
        rmSSOPage.enterIssuer("www.webautomationtestissuer.com");
        rmSSOPage.enterNotBefore("2015-01-01T00:00:00Z");
        rmSSOPage.enterNotOnOrAfter("2015-12-31T23:59:59Z");
        rmSSOPage.enterAppId("982-001");


        assertThat(rmSSOPage.getSignedSamlResponseText()).isEmpty();
        rmSSOPage.doClickLoadValues();
        assertThat(rmSSOPage.getSignedSamlResponseText()).isNotEmpty();


        ReportAdministrator reportAdministrator = rmSSOPage.doSubmit(ReportAdministrator.class, WAIT_TIME_SECONDS);
        assertThat(reportAdministrator.getDisplayedTitle()).isEqualTo("Report Administration");
        assertThat(reportAdministrator.getSelectedApplication()).isEqualTo("982-001");
        assertThat(reportAdministrator.getApplications()).containsOnly("982-001");

        assertThat(reportAdministrator.getDisplayedTabs()).containsOnly(
                "Custom Sort", "Download Log", "Admin"
        );

    }
}

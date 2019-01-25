package com.vh.mi.automation.test.saml;

import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.ICP100;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.api.pages.saml.IPremierSSO;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.CP100;
import com.vh.mi.automation.impl.pages.common.WelcomePage;
import com.vh.mi.automation.impl.pages.saml.PremierSSO;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test Case TC1517: Verify the PremierConnect SAML page
 * <p/>
 * 1)  Open the link of SAML page URL: https//server:port/SAML/Response/Sample/SampleClientPage.jsp
 * Expected Result
 * The SAML page should be loaded with the following labels and input box: >Login Name >Issuer >NotBefore
 * (Put the values displayed along with for Issuer) >Not On Or Ater(Put the values displayed along with for Issuer) > Form
 * <p/>
 * 2)Check the default values for Issuer and Form ID
 * The default values : Issuer: 'www.testIssuer.com' Form: 'CP100' ????
 * <p/>
 * 3)Verify login feature and Landing Pages
 * 3.1 If testUser has access to multiple application, then lands on Welcome page Always
 * 3.2 Misc Rights are configured as per the default group
 *
 * @author nimanandhar
 */
public class PremierSSOTest extends SSOBaseTest {
    private static final String URL = "https://nvscmlinq1.d2hawkeye.net:8200";

    @Test (groups = "Deprecated-Tests")
    public void testThatUserLandsOnWelcomePage() {
        IPremierSSO premierSSOPage = PremierSSO.loadPage(webDriver, URL);

        premierSSOPage.enterLoginName("jshin_p_sso1");
        premierSSOPage.enterIssuer("testPremier.com");
        premierSSOPage.enterNotBefore("2015-01-01T00:00:00Z");
        premierSSOPage.enterNotOnOrAfter("2015-12-31T23:59:59Z");
        premierSSOPage.enterFormId("CP100");
        premierSSOPage.enterMemberId("220222675");
        premierSSOPage.doClickSamlDefaultAssertion();

        IWelcomePage welcomePage = premierSSOPage.doSubmit(WelcomePage.class, WAIT_TIME_SECONDS);

        Collection<String> availableFronts = welcomePage.getAvailableFronts();
        assertThat(availableFronts).containsOnly("983-001");

        welcomePage.selectFront("983-001");
        ICP100 cp100Page = PageFactory.initElements(webDriver, CP100.class);
        cp100Page.doWaitTillFullyLoaded(WAIT_TIME_SECONDS);

        assertThat(cp100Page.getDisplayedPageTitle()).isEqualTo(cp100Page.getPageTitle());
        assertThat(isNavigationPanelAvailable()).isTrue();
    }
}

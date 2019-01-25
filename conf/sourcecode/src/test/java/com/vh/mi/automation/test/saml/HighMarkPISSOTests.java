package com.vh.mi.automation.test.saml;

import com.vh.mi.automation.api.constants.MILandingPages;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.ICP100;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.api.pages.saml.IHighMarkPISSO;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.CP100;
import com.vh.mi.automation.impl.pages.common.WelcomePage;
import com.vh.mi.automation.impl.pages.saml.HighMarkPISSO;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author nimanandhar
 */
public class HighMarkPISSOTests extends SSOBaseTest {


    @Test (groups = "Deprecated-Tests")
    public void testThatUserLandsOnWelcomePage() {
        IHighMarkPISSO highMarkPISSOPage = HighMarkPISSO.loadPage(webDriver, "https://nvscmlinq1.d2hawkeye.net:8200");
        assertThat(highMarkPISSOPage.getSignedSamlResponseText()).isEmpty();

        highMarkPISSOPage.enterUrlToLogin("https://nvscmlinq1.d2hawkeye.net:8000/SAML/Response/index.jsp");
        highMarkPISSOPage.enterLoginName("web_automation_pi_sso ");
        highMarkPISSOPage.enterFirstName("SSO PI");
        highMarkPISSOPage.enterLastName("Automation One");
        highMarkPISSOPage.enterIssuer("www.webautomationtestissuer.com");
        highMarkPISSOPage.enterFormId("CP100");
        highMarkPISSOPage.doClickSamlDefaultAssertion();

        assertThat(highMarkPISSOPage.getSignedSamlResponseText()).isNotEmpty();

        IWelcomePage welcomePage = highMarkPISSOPage.doSubmit(WelcomePage.class, WAIT_TIME_SECONDS);

        Collection<String> availableFronts = welcomePage.getAvailableFronts();
        assertThat(availableFronts).containsOnly("983-001");

        welcomePage.selectFront("983-001");
        ICP100 cp100Page = PageFactory.initElements(webDriver, CP100.class);
        cp100Page.doWaitTillFullyLoaded(WAIT_TIME_SECONDS);

        assertThat(cp100Page.getDisplayedPageTitle()).isEqualTo(MILandingPages.CP100.getPageTitle());
        assertThat(isNavigationPanelAvailable()).isTrue();
    }
}

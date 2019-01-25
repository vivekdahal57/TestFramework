package com.vh.mi.automation.test.saml;

import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardMemberSummary;
import com.vh.mi.automation.api.pages.saml.IEULAPage;
import com.vh.mi.automation.api.pages.saml.IHighMarkMISSO;
import com.vh.mi.automation.impl.dataSources.DataSourceFactory;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardMemberSummary;
import com.vh.mi.automation.impl.pages.saml.EULAPage;
import com.vh.mi.automation.impl.pages.saml.HighMarkMISSO;
import org.aspectj.lang.annotation.DeclareAnnotation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * https://nvscmlinq1.d2hawkeye.net:8200/SAML/Response/Sample/HighMarkClientSamplePageAUP.jsp
 *
 * @author nimanandhar
 */
@Deprecated
public class HighMarkMISSOTests extends SSOBaseTest {
    public static final String AUP_USER_NAME = "selenium_run_aup_new_test_user";

    @Test (groups = "Deprecated-Tests")
    public void testThatUserLandsOnIndividualDashboard() {
        IHighMarkMISSO highMarkMISSOPage = HighMarkMISSO.loadPage(webDriver, "https://nvscmlinq1.d2hawkeye.net:8200");

        highMarkMISSOPage.enterUrlToLogin("https://nvscmlinq1.d2hawkeye.net:8000/SAML/Response/index.jsp");
        highMarkMISSOPage.enterLoginName("web_automation_mi_sso");
        highMarkMISSOPage.enterMemberId("220000673");
        highMarkMISSOPage.enterIssuer("www.webautomationtestissuer.com");

        IIndividualDashboardMemberSummary memberSummaryPage = highMarkMISSOPage.doSubmit(IndividualDashboardMemberSummary.class, WAIT_TIME_SECONDS);

        assertThat(memberSummaryPage.getDisplayedPageTitle()).isEqualTo("Individual Dashboard: (320) Member Summary");
        assertThat(memberSummaryPage.isNextButtonDisplayed()).isFalse();
        assertThat(isNavigationPanelAvailable()).isFalse();
    }


    @Test (groups = "Deprecated-Tests")
    public void testThatNewUserIsCreated() {
        IHighMarkMISSO highMarkMISSOPage = HighMarkMISSO.loadPage(webDriver, "https://nvscmlinq1.d2hawkeye.net:8200");
        deleteAupUser();

        highMarkMISSOPage.enterUrlToLogin("https://nvscmlinq1.d2hawkeye.net:8000/SAML/Response/index.jsp");
        highMarkMISSOPage.enterLoginName(AUP_USER_NAME);

        highMarkMISSOPage.enterFirstName("selenium");
        highMarkMISSOPage.enterLastName("run");
        highMarkMISSOPage.enterMemberId("220000673");
        highMarkMISSOPage.enterIssuer("www.webautomationtestissuer.com");

        //commented out since the group has promptEula rights disabled
//        IEULAPage eulaPage = highMarkMISSOPage.doSubmit(EULAPage.class, WAIT_TIME_SECONDS);
//        assertThat(eulaPage.getPageTitle()).isEqualTo("End User License Agreement (EULA)");
//        IndividualDashboardMemberSummary memberSummaryPage = eulaPage.agree(IndividualDashboardMemberSummary.class, WAIT_TIME_SECONDS);


        IndividualDashboardMemberSummary memberSummaryPage = highMarkMISSOPage.doSubmit(IndividualDashboardMemberSummary.class, WAIT_TIME_SECONDS);
        assertThat(memberSummaryPage.getDisplayedPageTitle()).isEqualTo("Individual Dashboard: (320) Member Summary");
        assertThat(memberSummaryPage.isNextButtonDisplayed()).isFalse();
        assertThat(isNavigationPanelAvailable()).isFalse();
    }

    private void deleteAupUser() {
//        DataSourceFactory dataSourceFactory = DataSourceFactory.getInstance(context);
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceFactory.getHeuserDataSource());
//        int affectedRows = jdbcTemplate.update("DELETE FROM HEUSER.USR_USERS WHERE LOGINNAME = ?", AUP_USER_NAME);
//        if (affectedRows != 1)
//            logger.warn("Unexpected size of update results when deleting " + AUP_USER_NAME + " from HEUSER.USR_USERS .Expected affected rows to be 1 but was " + affectedRows);
    }
}

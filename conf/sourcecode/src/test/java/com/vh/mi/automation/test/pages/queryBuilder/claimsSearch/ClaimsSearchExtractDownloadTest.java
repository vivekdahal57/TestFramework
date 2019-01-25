package com.vh.mi.automation.test.pages.queryBuilder.claimsSearch;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.queryManagement.QueryManagement;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.*;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i81306 on 5/5/2017.
 */
@Test (groups = { "Report-Download", "Component-Interaction", "Product-Critical" })
public class ClaimsSearchExtractDownloadTest extends BaseTest {
    private static final String DEFAULT_QUERY_NAME = "CLAIMS_SEARCH_EXTRACT_TEST";
    private static final String DEFAULT_MVCA_QUERY_NAME = "CS_MVCA_REPORT_TEST";

    QueryManagement queryManagement;

    @Override
    public boolean skipBrowserCreation() {
        return true;
    }

    @BeforeClass()
    public void setUp() {
        super.setUp();
    }

    @BeforeMethod
    public void beforeTestMethod() {
        createNewBrowserInstance();
    }


    @AfterMethod
    public void afterTestMethod() throws Exception {
        closeBrowserInstance();
    }

    @Test
    public void downloadAndVerifyReport() throws Exception{
        User user = getUser("miautomation_reports_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        queryManagement = navigationPanel.doNavigateTo(QueryManagement.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(queryManagement.getPageTitle());

        queryManagement.downloadExtractWithName(DEFAULT_QUERY_NAME, true);
        String downloadedReportName = "*_" + queryManagement.getQueryIdForQueryName(DEFAULT_QUERY_NAME, false) + ".zip";
        assertThat(queryManagement.validateDownloadedZip(downloadedReportName, context) == true);

        queryManagement.deleteExtractWithName(DEFAULT_QUERY_NAME, false);
    }

    @Test
    public void downloadAndVerifyReportFromMVCAMemberlist() throws Exception{
        User user = getUser("miautomation_memberlist_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        queryManagement = navigationPanel.doNavigateTo(QueryManagement.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(queryManagement.getPageTitle());

        queryManagement.downloadExtractWithName(DEFAULT_MVCA_QUERY_NAME, true);
        String downloadedReportName = "*_" + queryManagement.getQueryIdForQueryName(DEFAULT_MVCA_QUERY_NAME, false) + ".zip";
        assertThat(queryManagement.validateDownloadedZip(downloadedReportName, context) == true);

        queryManagement.deleteExtractWithName(DEFAULT_MVCA_QUERY_NAME, false);
    }
}

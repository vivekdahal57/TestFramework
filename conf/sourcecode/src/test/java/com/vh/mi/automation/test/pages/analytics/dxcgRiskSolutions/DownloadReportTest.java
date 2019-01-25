package com.vh.mi.automation.test.pages.analytics.dxcgRiskSolutions;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.dxcgRiskSolutionsStaticReports.StaticReportsRP010;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.DXCG_RISK_SOLUTIONS_STATIC_REPORTS;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i81306 on 3/29/2017.
 */
@Test (groups = { "Report-Download", "Component-Interaction", "Products-Critical" })
public class DownloadReportTest extends BaseTest {
    private static final String GENERAL_PRACTICE = "General Practice";
    private static final String MODEL_71_18 = "71,18";
    private static final String FAMILY_PRACTICE = "Family Practice";
    private static final String MODEL_56 = "56";

    private StaticReportsRP010 pageRP010;

    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass()
    public void setUp() {
        super.setUp();
        User user = getUser("miautomation_reports_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        pageRP010 = navigationPanel.doNavigateTo(StaticReportsRP010.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(DXCG_RISK_SOLUTIONS_STATIC_REPORTS.getPageTitle());
    }

    @Test
    public void downloadReportForBOBTest() throws Exception {
        assertThat(pageRP010.downloadAndValidateReportsForBOB(MODEL_71_18, context) == true);
    }

    @Test
    public void downloadReportForCompanySingleModelTest() throws Exception {
        assertThat(pageRP010.downloadAndValidateReportsForCompany(FAMILY_PRACTICE, MODEL_56, context) == true);
    }

    @Test
    public void downloadReportForCompanyMultipleModelTest() throws Exception {
        assertThat(pageRP010.downloadAndValidateReportsForCompany(GENERAL_PRACTICE, MODEL_71_18, context) == true);
    }
}

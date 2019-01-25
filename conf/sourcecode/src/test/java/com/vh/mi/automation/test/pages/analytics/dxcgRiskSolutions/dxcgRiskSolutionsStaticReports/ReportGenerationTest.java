package com.vh.mi.automation.test.pages.analytics.dxcgRiskSolutions.dxcgRiskSolutionsStaticReports;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.dxcgRiskSolutionsStaticReports.StaticReportsRP010;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.DXCG_RISK_SOLUTIONS_STATIC_REPORTS;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i81306 on 4/19/2017.
 */
@Test (groups = { "Report-Generation", "Component-Interaction", "Product-Critical" })
public class ReportGenerationTest extends BaseTest {
    private static final String GENERAL_PRACTICE = "General Practice";
    private static final String MODEL_71_18 = "71,18";
    private static final String FAMILY_PRACTICE = "Family Practice";
    private static final String MODEL_56 = "56";

    private StaticReportsRP010 pageRP010;

    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
        User user = getUser("miautomation_reports_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        pageRP010 = navigationPanel.doNavigateTo(StaticReportsRP010.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(DXCG_RISK_SOLUTIONS_STATIC_REPORTS.getPageTitle());
    }

    @Test
    public void generateStaticReportForBOBTest() {
        pageRP010.generateBOBReportForModels(StaticReportsRP010.getRandomReportName(), MODEL_71_18);
        assertThat(pageRP010.getTextFromReportGenerationPopupMessage().equals(pageRP010.REPORT_GENERATION_POPUP_MESSAGE));
        WaitUtils.waitForSeconds(TimeOuts.TEN_SECONDS);
    }

    @Test
    public void generateStaticReportForSingleModelsTest() {
        pageRP010.selectCompany(FAMILY_PRACTICE);
        pageRP010.generateCompanyReportForModels(StaticReportsRP010.getRandomReportName(), MODEL_56);
        assertThat(pageRP010.getTextFromReportGenerationPopupMessage().equals(pageRP010.REPORT_GENERATION_POPUP_MESSAGE));
        WaitUtils.waitForSeconds(TimeOuts.TEN_SECONDS);
    }

    @Test
    public void generateStaticReportForMultipleModelsTest() {
        pageRP010.selectCompany(GENERAL_PRACTICE);
        pageRP010.generateCompanyReportForModels(StaticReportsRP010.getRandomReportName(), MODEL_71_18);
        assertThat(pageRP010.getTextFromReportGenerationPopupMessage().equals(pageRP010.REPORT_GENERATION_POPUP_MESSAGE));
        WaitUtils.waitForSeconds(TimeOuts.TEN_SECONDS);
    }

}

package com.vh.mi.automation.test.pages.reportManager;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.reportManager.ReportAdministrator;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82716 on 5/12/2017.
 */
@Test (groups = {"Report-Download", "Component-Interaction"})
public class DownloadMIRReportTest extends BaseTest {

    private static final String RTF_FILE_PATTERN = "*.rtf";
    private static final String PDF_FILE_PATTERN = "*.pdf";
    private ReportAdministrator administrator;

    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
        User user = getUser("mta_RMSAML");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        administrator = navigationPanel.doNavigateToReportManagerPage(ReportAdministrator.class, defaultWaitTime);
    }

    @Test
    public void downloadRTFReportTest() throws Exception{
        administrator.doFilterReportTitle(GenerateMIRReportTest.DEFAULT_REPORT_NAME);
        administrator.downloadExtractRTFReport(GenerateMIRReportTest.DEFAULT_REPORT_NAME);
        assertThat(administrator.validateDownloadFile(RTF_FILE_PATTERN,context)).isTrue();
    }

    @Test
    public void downloadPDFReportTest() throws Exception{
        administrator.doFilterReportTitle(GenerateMIRReportTest.DEFAULT_REPORT_NAME);
        administrator.downloadExtractPDFReport(GenerateMIRReportTest.DEFAULT_REPORT_NAME);
        assertThat(administrator.validateDownloadFile(PDF_FILE_PATTERN,context)).isTrue();
    }

    @Test
    public void downloadPDFReportAtLOAtTest() throws Exception{
        administrator.doFilterReportTitle(GenerateMIRReportTest.DEFAULT_REPORT_NAME_AT_LOA);
        WaitUtils.waitForSeconds(TimeOuts.TWO_SECOND);
        int reportCnt = administrator.getMaxReportCnt();
        assertThat(reportCnt).isGreaterThan(0);
        for (int i = 1; i <= reportCnt; i++) {
            administrator.downloadExtractPDFReportAtLOA(GenerateMIRReportTest.DEFAULT_REPORT_NAME_AT_LOA, i);
            assertThat(administrator.validateDownloadFile(PDF_FILE_PATTERN, context)).isTrue();
        }
    }

    @Test
    public void downloadRTFReportAtLOAtTest() throws Exception{
        administrator.doFilterReportTitle(GenerateMIRReportTest.DEFAULT_REPORT_NAME_AT_LOA);
        WaitUtils.waitForSeconds(TimeOuts.TWO_SECOND);
        int reportCnt = administrator.getMaxReportCnt();
        assertThat(reportCnt).isGreaterThan(0);
        for (int i = 1; i <= reportCnt; i++) {
            administrator.downloadExtractRTFReportAtLOA(GenerateMIRReportTest.DEFAULT_REPORT_NAME_AT_LOA, i);
            assertThat(administrator.validateDownloadFile(RTF_FILE_PATTERN, context)).isTrue();
        }
    }

    @AfterClass
    public void deleteMIRReport(){
        administrator.deleteMIReport(GenerateMIRReportTest.DEFAULT_REPORT_NAME);
        administrator.deleteMIReport(GenerateMIRReportTest.DEFAULT_REPORT_NAME_AT_LOA);
    }
}
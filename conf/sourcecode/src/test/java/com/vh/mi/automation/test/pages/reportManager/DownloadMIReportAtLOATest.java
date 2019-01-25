/*

package com.vh.mi.automation.test.pages.reportManager;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.reportManager.ReportAdministrator;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

@Test (groups = "Report-Download")
public class DownloadMIReportAtLOATest extends BaseTest {

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
    public void downloadPDFReportAtLOAtTest() throws Exception{
        administrator.doFilterReportTitle(GenerateMIReportAtLOATest.DEFAULT_REPORT_NAME_AT_LOA);
        int reportCnt = administrator.getMaxReportCnt();
        assertThat(reportCnt).isGreaterThan(0);
        for (int i = 1; i <= reportCnt; i++) {
            administrator.downloadExtractPDFReportAtLOA(GenerateMIReportAtLOATest.DEFAULT_REPORT_NAME_AT_LOA, i);
            assertThat(administrator.validateDownloadFile(PDF_FILE_PATTERN, context)).isTrue();
        }
    }

    @Test
    public void downloadRTFReportAtLOAtTest() throws Exception{
        administrator.doFilterReportTitle(GenerateMIReportAtLOATest.DEFAULT_REPORT_NAME_AT_LOA);
        int reportCnt = administrator.getMaxReportCnt();
        assertThat(reportCnt).isGreaterThan(0);
        for (int i = 1; i <= reportCnt; i++) {
            administrator.downloadExtractRTFReportAtLOA(GenerateMIReportAtLOATest.DEFAULT_REPORT_NAME_AT_LOA, i);
            assertThat(administrator.validateDownloadFile(RTF_FILE_PATTERN, context)).isTrue();
        }
    }

    @AfterClass
    public void deleteMIRReport(){
        administrator.deleteMIReport(GenerateMIReportAtLOATest.DEFAULT_REPORT_NAME_AT_LOA);
    }
}

*/

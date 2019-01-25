package com.vh.mi.automation.test.pages.reportManager;

import com.vh.mi.automation.api.comp.bl.BL;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.reportManager.ReportAdministrator;
import com.vh.mi.automation.impl.reportManager.reportWizard.GenerationTab;
import com.vh.mi.automation.impl.reportManager.reportWizard.ReportWizard;
import com.vh.mi.automation.impl.reportManager.reportWizard.ReportsTab;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82716 on 5/11/2017.
 */
@Test (groups = { "Report-Generation", "Component-Interaction" })
public class GenerateMIRReportTest extends BaseTest {

    public static final String DEFAULT_REPORT_NAME = "AUTOMATION_MI_REPORT";
    public static final String DEFAULT_REPORT_NAME_AT_LOA = "LOA_TEST";
    private ReportWizard reportWizard;
    private ReportAdministrator administrator;


    @Override
    public boolean skipLogin() {
        return true;
    }

    @Override
    public boolean skipBrowserCreation() {
        return true;
    }


    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @BeforeMethod
    public void beforeMethod() {
        createNewBrowserInstance();
        User user = getUser("mta_RMSAML");
        loginAndSelectFront(user,context.getDefaultWaitTimeout());
        administrator = navigationPanel.doNavigateToReportManagerPage(ReportAdministrator.class,defaultWaitTime);
        reportWizard = administrator.goToReportWizard(defaultWaitTime);
        assertThat(reportWizard.getDisplayedTitle()).isEqualTo("Report Wizard");
        reportWizard.waitTillLoadingDisappears();
    }

    @Test
    public void generateMIReportTest(){
        reportWizard.getDataSelectionTab().selectApplicationFromDataSelectionTab(DEFAULT_REPORT_NAME);
        reportWizard.getDataSelectionTab().dataSelectionSaveAndContinue();

        reportWizard.getReportsTab().selectReportSet(ReportsTab.ReportSet.MI);
        reportWizard.getReportsTab().selectAllReportChapter();
        reportWizard.getReportsTab().reportChapterSaveAndContinue();

        reportWizard.getGenerationTab().selectReportFormat(true, true);
        reportWizard.getGenerationTab().reportGenerationSaveAndContinue();

        administrator = reportWizard.getUserAccessTab().saveAndPublish();
        assertThat(administrator.isMIRReportSuccessfullyGenerated(DEFAULT_REPORT_NAME)).isTrue();
    }

    @Test(groups = { "Report-Generation" })
    public void verifyMIReportAtLOA(){
        Map<BL,String> bl = blProvider();
        List<String> blCategoryLabel  = reportWizard.getDataSelectionTab().selectApplicationAndBusinessLvlFromDataSelectionTab(DEFAULT_REPORT_NAME_AT_LOA,bl);
        reportWizard.getDataSelectionTab().dataSelectionSaveAndContinue();

        reportWizard.getReportsTab().selectReportSet(ReportsTab.ReportSet.MI);
        reportWizard.getReportsTab().selectAllReportChapter();
        reportWizard.getReportsTab().reportChapterSaveAndContinue();

        reportWizard.getGenerationTab().selectTimePeriod(GenerationTab.TimePeriod.YEAR_TO_DATE);
        reportWizard.getGenerationTab().selectReportingBy(GenerationTab.ReportingBy.PAID);
        reportWizard.getGenerationTab().selectMonthlyCost(GenerationTab.MonthlyCost.PER_EMPLOYEE);
        reportWizard.getGenerationTab().selectReportFormat(true,true);
        reportWizard.getGenerationTab().reportGenerationSaveAndContinue();

        administrator = reportWizard.getUserAccessTab().saveAndPublish();

        blCategoryLabel.forEach(label->assertThat(administrator.isMIRReportSuccessfullyGeneratedAtLOA(DEFAULT_REPORT_NAME_AT_LOA, label)).isTrue());
    }

    private Map<BL,String> blProvider() {
        Map<BL,String> bl = new LinkedHashMap<>();
        bl.put(BL.LEVEL1,"1,2");
        bl.put(BL.LEVEL2,"1,2");
        return bl;
    }

    @AfterMethod
    public void afterTest() {
        closeBrowserInstance();
    }
}

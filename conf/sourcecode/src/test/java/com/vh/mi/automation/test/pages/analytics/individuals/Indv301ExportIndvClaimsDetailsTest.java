package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.IIndvClaimsDetailExtractPopUp;
import com.vh.mi.automation.api.comp.extractDownloadPopup.ICSVExtractPopUp;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.api.pages.favorites.myJobs.IMyJobs;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.favorites.myJobs.MyJobs;
import com.vh.mi.automation.test.base.BaseTest;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

@Test (groups = {"Report-Download"})
public class Indv301ExportIndvClaimsDetailsTest extends BaseTest{
    private static final String JOB_NAME = "INDV_CLAIMS_DETAIL_EXTRACT_" + Random.getRandomStringOfLength(4);
    private static final String EXTRACT_NAME = JOB_NAME + "*.zip";
    private static final String MEMBERS = "10";
    IIndv301 iIndv301;
    MyJobs myJobs;
    IIndvClaimsDetailExtractPopUp claimsDetailExtractPopup;



    @BeforeClass
    public void setUp(){
        super.setUp();
        iIndv301=navigationPanel.doNavigateTo(Indv301.class,defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(iIndv301.getPageTitle());
        iIndv301.popupExists();
    }

    @Test(description="(Individual => click Export Individual Claims Detail in the preference bar => validate the downloaded file")
    public void  exportIndividualClaimsDetailTest() throws IOException {
        claimsDetailExtractPopup = iIndv301.getPreferencesBar().indvClaimsDetailsExport();
        claimsDetailExtractPopup.exportToExcel(MEMBERS, JOB_NAME);

        myJobs = navigationPanel.doNavigateTo(MyJobs.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(myJobs.getPageTitle());
        myJobs.downloadExtractWithJobNameForIndividuals(JOB_NAME);
        myJobs.validateDownloadedExtract_zip(EXTRACT_NAME, context);
        myJobs.removeJobForJobName(JOB_NAME);

    }

}

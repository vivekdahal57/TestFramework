package com.vh.mi.automation.test.pages.analytics.hospitalProfiler;

import com.vh.mi.automation.api.pages.analytics.hospitalProfiler.IHospitalProfilesHP100;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.Drill.HP120AdmissionClaimDetailsDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.Drill.HP120AdmissionDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.HospitalProfilesHP100;
import com.vh.mi.automation.impl.pages.favorites.myJobs.MyJobs;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/21/18.
 */
@Test (groups = {"Report-Download"})
public class HospitalProfilesHP100SendToCSVTest extends BaseTest {
    private IHospitalProfilesHP100 hp100;
    private MyJobs myJobs;

    @BeforeClass
    public void setUp(){
        super.setUp();
        hp100 = navigationPanel.doNavigateTo(HospitalProfilesHP100.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(hp100.getPageTitle());
    }

    @Test
    public void  test() throws IOException{
       HP120AdmissionDrillPage hp120Admission = (HP120AdmissionDrillPage) hp100.getDataGrid().getRows().get(0).doDrillByOnSameWindow("Admission");
       hp120Admission.getPageTitle().equalsIgnoreCase("(HP120) Admissions");
       HP120AdmissionClaimDetailsDrillPage admissionClaimDetailsDrillPage = (HP120AdmissionClaimDetailsDrillPage) hp120Admission.getDataGrid().getRows().get(0).doDrillBy("Claim Details");
       hp120Admission.getPageTitle().equalsIgnoreCase(" Admission Claim Details");

       hp120Admission.getPreferencesBar().csv();
       admissionClaimDetailsDrillPage.generateCSV();

        myJobs = navigationPanel.doNavigateTo(MyJobs.class, defaultWaitTime);
        myJobs.downloadExtractWithJobName(admissionClaimDetailsDrillPage.getJobName());
        assertThat(myJobs.validateDownloadedExtract_File(admissionClaimDetailsDrillPage.getJobName()+".zip",context)).isTrue();
        myJobs.removeJobForJobName(admissionClaimDetailsDrillPage.getJobName());
    }
}

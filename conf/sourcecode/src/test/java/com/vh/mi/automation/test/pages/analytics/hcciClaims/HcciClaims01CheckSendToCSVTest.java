//package com.vh.mi.automation.test.pages.analytics.hcciClaims;
//
//import com.vh.mi.automation.api.dto.User;
//import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01;
//import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01DrillPage;
//import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
//import com.vh.mi.automation.impl.pages.favorites.myJobs.MyJobs;
//import com.vh.mi.automation.test.base.BaseTest;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.io.IOException;
//
//import static org.fest.assertions.Assertions.assertThat;
//
///**
// * Created by i10359 on 10/31/17.
// */
//
//@Test(groups = {"Report-Download" })
//public class HcciClaims01CheckSendToCSVTest extends BaseTest {
//    HcciIClaims01 hcciClaims;
//    private HcciIClaims01DrillPage medicalClaimDetailDrillPage;
//    private MyJobs myJobs;
//
//
//    @BeforeClass
//    public void setUp(){
//        super.setUp();
//        hcciClaims = navigationPanel.doNavigateTo(HcciClaims01.class,defaultWaitTime);
//    }
//
//    @Test(description="(HCCI)Claims => navigate to Claims =>Drill down to Claims from Medical =>opens 965MedicalClaimDetail page =>fill the job name then generate csv and switch" +
//            "back to parent window => navigate to myjobs => hover over the recently created job and select download Extracts =>validate the downloaded csv => remove the job")
//    public void checkSendToCSV() throws IOException{
//        medicalClaimDetailDrillPage = hcciClaims.drillDownToHcciClaims();
//        medicalClaimDetailDrillPage.filterPaidAmounts();
//
//        medicalClaimDetailDrillPage.getPreferencesBar().csv();
//        medicalClaimDetailDrillPage.generateCSV();
//
//        myJobs = navigationPanel.doNavigateTo(MyJobs.class, defaultWaitTime);
//        myJobs.downloadExtractWithJobName(medicalClaimDetailDrillPage.getJobName());
//        assertThat(myJobs.validateDownloadedExtract_File(medicalClaimDetailDrillPage.getJobName()+".zip",context)).isTrue();
//        myJobs.removeJobForJobName(medicalClaimDetailDrillPage.getJobName());
//
//    }
//
//}

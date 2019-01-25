package com.vh.mi.automation.test.pages.analytics.claims;

import com.vh.mi.automation.api.pages.analytics.claims.IClaims01;
import com.vh.mi.automation.api.pages.analytics.claims.IClaims01DrillPage;
import com.vh.mi.automation.impl.pages.analytics.claims.Claims01;
import com.vh.mi.automation.impl.pages.favorites.myJobs.MyJobs;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/7/18.
 */
@Test(groups = {"Report-Download" })
public class Claims01PharmacyCheckSendToCSVTest extends BaseTest {
    IClaims01 claims01;
    private IClaims01DrillPage pharmacyClaimDetailDrillPage;
    private MyJobs myJobs;

    @BeforeClass
    public void setUp(){
        super.setUp();
        claims01 = navigationPanel.doNavigateTo(Claims01.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(claims01.getPageTitle());
    }

    @Test(description = "(C01)Claims => navigate to claims =>Drill down to claims from Pharmacy=>opens 967PharmacyClaimDetail page =>fill the job name then generate csv and switch" +
            "back to parent window => navigate to myjobs => hover over the recently created job and select download Extracts =>validate the downloaded csv => remove the job")
    public void checkSendToCSV() throws IOException{
        pharmacyClaimDetailDrillPage = claims01.drillDownToClaimsFromPharmacy();

        pharmacyClaimDetailDrillPage.getPreferencesBar().csv();
        pharmacyClaimDetailDrillPage.generateCSV();

        myJobs = navigationPanel.doNavigateTo(MyJobs.class,defaultWaitTime);
        myJobs.downloadExtractWithJobName(pharmacyClaimDetailDrillPage.getJobName());
        assertThat(myJobs.validateDownloadedExtract_File(pharmacyClaimDetailDrillPage.getJobName()+".zip",context)).isTrue();
        myJobs.removeJobForJobName(pharmacyClaimDetailDrillPage.getJobName());
    }

}

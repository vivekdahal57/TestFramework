package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.impl.comp.extractDownloadPopup.SendToExcelPopUp;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.favorites.myJobs.MyJobs;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 10/26/17.
 */
@Test(groups = {"Report-Download"})
public class Indv301SendToExcelTest extends BaseTest {
    IIndv301 iIndv301;
    private static final String FILENAME = "Indv301*.xlsx";
    private static final String JOBNAME = "JOB" + randomValue();
    

    @BeforeClass
    public void setUp() {
        super.setUp();
        iIndv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(iIndv301.getPageTitle());
        iIndv301.popupExists();
    }

    public static String randomValue() {
        return String.valueOf(System.currentTimeMillis());
    }

    @Test(description = "(301)Individuals => click send to excel => validate the downloaded file")
    public void sendToExcel() throws IOException {
        if (iIndv301.isExcelOffline()) {
            iIndv301.getPreferencesBar().offlineExcel();
            SendToExcelPopUp sendToExcelPopUp = new SendToExcelPopUp(webDriver);
            sendToExcelPopUp.saveExcel(JOBNAME);
            MyJobs myJobs = navigationPanel.doNavigateTo(MyJobs.class, defaultWaitTime);
            myJobs.downloadExtractWithJobNameForIndividuals(JOBNAME);
            assertThat(iIndv301.sendToExcelAndValidateZipFile(JOBNAME + ".zip", context)).isTrue();
            myJobs.removeJobForJobName(JOBNAME);
        } else {
            iIndv301.getPreferencesBar().sendToExcel();
            assertThat(iIndv301.sendToExcelAndValidate(FILENAME, context)).isTrue();
        }
    }

}

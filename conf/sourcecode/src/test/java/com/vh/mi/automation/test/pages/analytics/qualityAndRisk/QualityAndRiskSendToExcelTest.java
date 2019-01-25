package com.vh.mi.automation.test.pages.analytics.qualityAndRisk;

import com.vh.mi.automation.api.pages.analytics.qualityMeasures.qualityAndRisk.IQualityAndRisk;
import com.vh.mi.automation.impl.pages.analytics.qualityMeasures.qualityAndRisk.QualityAndRisk670;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 10/26/17.
 */
@Test(groups = {"Report-Download" })
public class QualityAndRiskSendToExcelTest extends BaseTest{
    IQualityAndRisk iQualityAndRisk670;
    private static final String FILENAME = "Qrm670*.xlsx";

    @BeforeClass
    public void setUp(){
        super.setUp();
        iQualityAndRisk670 = navigationPanel.doNavigateTo(QualityAndRisk670.class,defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(iQualityAndRisk670.getPageTitle());
    }


    @Test(description="navigate to Quality and Risk =>click send to excel => validate the downloaded file")
    public void sendToExcel() throws IOException{
        iQualityAndRisk670.getPreferencesBar().sendToExcel();
        assertThat(iQualityAndRisk670.sendToExcelandValidate(FILENAME,context)).isTrue();
    }


}

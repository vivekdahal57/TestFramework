package com.vh.mi.automation.test.pages.analytics.hcciClaims.drillToClaims;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciS865DrillPage;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10105 on 05/10/2018.
 */
@Test(groups = {"Report-Download" })
public class HcciClaims01S865SendToExcelTest extends BaseTest {
    HcciS865DrillPage hcciS865DrillPage;
    private static final String FILENAME="S865*.xlsx";

    @BeforeClass
    public void setUp(){
        super.setUp();
        HcciClaims01 claims101  = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
        hcciS865DrillPage = (HcciS865DrillPage) claims101.getDataGrid().getRows().get(0).doDrillBy("Claims");
        hcciS865DrillPage.filterPaidAmounts();
    }


    @Test(description="(HCCI)HcciClaims => click send to excel => validate the downloaded file")
    public void sendToExcel() throws  IOException{
        hcciS865DrillPage.getPreferencesBar().sendToExcel();
        assertThat(hcciS865DrillPage.sendToExcelAndValidate(FILENAME, context)).isTrue();


    }
}
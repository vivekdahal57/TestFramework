package com.vh.mi.automation.test.pages.analytics.hcciClaims.drillToServices;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01DrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciServicesDrillPage;
import com.vh.mi.automation.test.base.BaseTest;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10105 on 05/10/2018.
 */
@Test(groups = {"Report-Download" })
public class HcciClaims01ServicesSendToExcelTest extends BaseTest {
    HcciServicesDrillPage claims01DrillPage;;
    private static final String FILENAME="Cost01*.xlsx";

    @BeforeClass
    public void setUp(){
        super.setUp();
        HcciClaims01 claims101  = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
        claims01DrillPage = (HcciServicesDrillPage) claims101.getDataGrid().getRows().get(0).doDrillBy("Services");
        claims01DrillPage.filterPaidAmounts();
    }


    @Test(description="(HCCI)HcciClaims => click send to excel => validate the downloaded file")
    public void sendToExcel() throws  IOException{
        claims01DrillPage.getPreferencesBar().sendToExcel();
        assertThat(claims01DrillPage.sendToExcelAndValidate(FILENAME, context)).isTrue();


    }
}
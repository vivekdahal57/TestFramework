package com.vh.mi.automation.test.pages.analytics.hcciClaims;

import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 10/26/17.
 */
@Test(groups = {"Report-Download" })
public class HcciClaims01SendToExcelTest extends BaseTest {
    HcciIClaims01 hcciClaims01;
    private static final String FILENAME="S101*.xlsx";

    @BeforeClass
    public void setUp(){
        super.setUp();
        hcciClaims01 = navigationPanel.doNavigateTo(HcciClaims01.class,defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(hcciClaims01.getPageTitle());
    }



    @Test(description="(HCCI)HcciClaims => click send to excel => validate the downloaded file")
    public void sendToExcel() throws  IOException{
        hcciClaims01.getPreferencesBar().sendToExcel();
        assertThat(hcciClaims01.sendToExcelAndValidate(FILENAME, context)).isTrue();
    }
}

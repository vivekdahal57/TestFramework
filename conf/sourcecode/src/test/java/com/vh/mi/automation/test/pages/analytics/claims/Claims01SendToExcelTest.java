package com.vh.mi.automation.test.pages.analytics.claims;

import com.vh.mi.automation.api.pages.analytics.claims.IClaims01;
import com.vh.mi.automation.impl.pages.analytics.claims.Claims01;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 10/26/17.
 */
@Test(groups = {"Report-Download" })
public class Claims01SendToExcelTest extends BaseTest {
    IClaims01 iClaims01;
    private static final String FILENAME="Cost01*.xlsx";


    @BeforeClass
    public void setUp(){
        super.setUp();
        iClaims01 = navigationPanel.doNavigateTo(Claims01.class,defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(iClaims01.getPageTitle());
    }



    @Test(description="(C01)Claims => click send to excel => validate the downloaded file")
    public void sendToExcel() throws  IOException{
        iClaims01.getPreferencesBar().sendToExcel();
        assertThat(iClaims01.sendToExcelAndValidate(FILENAME, context)).isTrue();
    }
}

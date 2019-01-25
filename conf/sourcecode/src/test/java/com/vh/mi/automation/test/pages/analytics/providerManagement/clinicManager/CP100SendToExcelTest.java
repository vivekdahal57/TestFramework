package com.vh.mi.automation.test.pages.analytics.providerManagement.clinicManager;

import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.ICP100;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.CP100;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/8/18.
 */
@Test(groups = {"Report-Download" })
public class CP100SendToExcelTest extends BaseTest {
    private ICP100 cp100;
    private static final String FILENAME = "CP100*.xlsx";

    @BeforeTest
    public void setUp(){
        super.setUp();
        cp100 =  navigationPanel.doNavigateTo(CP100.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(cp100.getPageTitle());
    }

    @Test
    public void checkSendToExcel() throws IOException{
        cp100.getPreferencesBar().sendToExcel();
        assertThat(cp100.sendToExcelAndValidate(FILENAME, context)).isTrue();
    }

}


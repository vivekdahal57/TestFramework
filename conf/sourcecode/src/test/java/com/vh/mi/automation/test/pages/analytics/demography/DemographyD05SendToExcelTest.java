package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/8/18.
 */
@Test(groups = {"Report-Download" })
public class DemographyD05SendToExcelTest extends BaseTest{
    private IDemographyD05 demographyD05;
    private static final String FILENAME = "DemD05*.xlsx";

    @BeforeClass
    public void setUp(){
        super.setUp();
        demographyD05 = navigationPanel.doNavigateTo(DemographyD05.class,defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(demographyD05.getPageTitle());
    }

    @Test
    public void checkSendToExcel() throws IOException{
        demographyD05.getPreferencesBar().sendToExcel();
        assertThat(demographyD05.sendToExcelAndValidate(FILENAME,context)).isTrue();
    }

}

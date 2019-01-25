package com.vh.mi.automation.test.pages.analytics.hospitalProfiler;

import com.vh.mi.automation.api.pages.analytics.hospitalProfiler.IHospitalProfilesHP100;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.HospitalProfilesHP100;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/8/18.
 */
@Test(groups = {"Report-Download" })
public class HospitalProfilesHP100SendToExcelTest extends BaseTest {
    private IHospitalProfilesHP100 hp100;
    private static final String FILENAME = "HosHP100.xlsx";

    @BeforeClass
    public void setUp(){
        super.setUp();
        hp100 = navigationPanel.doNavigateTo(HospitalProfilesHP100.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(hp100.getPageTitle());
    }

    @Test
    public void checkSendToExcel() throws IOException{
        hp100.getPreferencesBar().sendToExcel();
        assertThat(hp100.sendToExcelAndValidate(FILENAME, context));
    }
}

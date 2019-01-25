package com.vh.mi.automation.test.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/8/18.
 */
@Test(groups = {"Report-Download" })
public class CPM01SendToExcelTest extends BaseTest {
    private static final String FILENAME = "CPM01*.xlsx";
    private ICPM01 cpm01;

    @BeforeClass
    public void setUp() {
        super.setUp();
        cpm01 = navigationPanel.doNavigateTo(CPM01.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(cpm01.getPageTitle());
    }

    @Test
    public void checkSendToExcelTest() throws IOException{
        cpm01.getPreferencesBar().sendToExcel();
        assertThat(cpm01.sendToExcelAndValidate(FILENAME,context)).isTrue();
    }
}

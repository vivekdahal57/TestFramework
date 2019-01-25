package com.vh.mi.automation.test.pages.analytics.utilizationMetrics;

import com.vh.mi.automation.api.pages.analytics.utilizationMetrics.IUtilizationMetricsHM50ByGroupSize;
import com.vh.mi.automation.impl.pages.analytics.utilizationMetrics.UtilizationMetricsHM50;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/19/18.
 */
@Test(groups = {"Report-Download" })
public class UtilizationMetricsHM50SendToExcelTest extends BaseTest {
    private IUtilizationMetricsHM50ByGroupSize hm50;
    private static final String FILENAME = "HM50*.xlsx";

    @BeforeClass
    public void setUp(){
        super.setUp();
        hm50 = navigationPanel.doNavigateTo(UtilizationMetricsHM50.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(hm50.getPageTitle());
    }

    @Test
    public void checkSendToExcel() throws IOException{
        hm50.getPreferencesBar().sendToExcel();
        assertThat(hm50.sendToExcelAndValidate(FILENAME, context)).isTrue();
    }


}

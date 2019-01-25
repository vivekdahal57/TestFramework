package com.vh.mi.automation.test.pages.analytics.networkAnalyzer;

import com.vh.mi.automation.api.pages.analytics.networkAnalyzer.IDetailedAnalyticsOU107;
import com.vh.mi.automation.impl.pages.analytics.networkAnalyzer.DetailedAnalyticsOU107;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/8/18.
 */
@Test(groups = {"Report-Download" })
public class DetailedAnalyticsSendToExcelTest extends BaseTest {
    private IDetailedAnalyticsOU107 detailedAnalytics;
    private static final String FILENAME = "OU107.xlsx";

    @BeforeClass
    public void setUp(){
    super.setUp();
    detailedAnalytics = navigationPanel.doNavigateTo(DetailedAnalyticsOU107.class, defaultWaitTime);
    assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(detailedAnalytics.getPageTitle());
    }

    @Test
    public void checkSendToExcel() throws IOException{
        detailedAnalytics.getPreferencesBar().sendToExcel();
        assertThat(detailedAnalytics.sendToExcelAndValidate(FILENAME,context)).isTrue();
    }

}

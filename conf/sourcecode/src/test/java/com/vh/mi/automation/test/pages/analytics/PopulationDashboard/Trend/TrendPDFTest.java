package com.vh.mi.automation.test.pages.analytics.PopulationDashboard.Trend;

import com.vh.mi.automation.api.pages.analytics.populationDashboard.trend.ITrendPD002;
import com.vh.mi.automation.impl.pages.analytics.populationDashboard.trend.TrendPD002;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82716 on 7/20/2017.
 */
@Test (groups = {"Report-Download", "Product-Critical"})
public class TrendPDFTest extends BaseTest {
    ITrendPD002 trend;
    private static final String PDF_TO_UPLOAD = "PopDashboardPD002.pdf";

    @BeforeClass
    public void setUp(){
        super.setUp();
        trend = navigationPanel.doNavigateTo(TrendPD002.class,defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(trend.getPageTitle());
    }

    @Test
    public void verifyPDFDownload() throws IOException {
        trend.getPreferencesBar().exportTOPDF();
        assertThat(trend.downloadPDFAndValidate(PDF_TO_UPLOAD,context)).isTrue();
    }
}

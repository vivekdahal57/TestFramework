package com.vh.mi.automation.test.pages.analytics.PopulationDashboard.Summary;

import com.vh.mi.automation.api.pages.analytics.populationDashboard.summary.ISummaryPD001;
import com.vh.mi.automation.impl.pages.analytics.populationDashboard.summary.SummaryPD001;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82716 on 7/20/2017.
 */
@Test (groups = {"Report-Download", "Product-Critical"})
public class SummaryPDFTest extends BaseTest{

    ISummaryPD001 summary;
    private static final String PDF_TO_UPLOAD = "PopDashboardPD001.pdf";

    @BeforeClass
    public void setUp(){
        super.setUp();
        summary = navigationPanel.doNavigateTo(SummaryPD001.class,defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(summary.getPageTitle());
    }

    @Test
    public void verifyPDFDownload() throws IOException {
        summary.getPreferencesBar().exportTOPDF();
        assertThat(summary.downloadPDFAndValidate(PDF_TO_UPLOAD,context)).isTrue();
    }
}

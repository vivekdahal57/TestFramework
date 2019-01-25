package com.vh.mi.automation.test.pages.analytics.executiveSummary;

import com.vh.mi.automation.api.pages.analytics.executiveSummary.IExecutiveSummaryByGroupSize;
import com.vh.mi.automation.impl.pages.analytics.executiveSummary.ExecutiveSummary;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/8/18.
 */
@Test(groups = {"Report-Download" })
public class ExecutiveSummarySendToExcel extends BaseTest {
    private IExecutiveSummaryByGroupSize summaryByGroupSize;
    private static final String FILENAME = "exeSummaryExcel.xlsx";

    @BeforeClass
    public void setup(){
        super.setUp();
        summaryByGroupSize = navigationPanel.doNavigateTo(ExecutiveSummary.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(summaryByGroupSize.getPageTitle());
    }

    @Test
    public void checkSendToExcel() throws IOException{
        summaryByGroupSize.getPreferencesBar().sendToExcel();
        assertThat(summaryByGroupSize.sendToExcelAndValidate(FILENAME,context)).isTrue();
    }

}

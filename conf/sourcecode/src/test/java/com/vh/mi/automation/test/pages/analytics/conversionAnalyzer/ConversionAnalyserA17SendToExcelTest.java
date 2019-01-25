package com.vh.mi.automation.test.pages.analytics.conversionAnalyzer;

import com.vh.mi.automation.api.pages.analytics.conversionAnalyzer.IConversionAnalyzerA17;
import com.vh.mi.automation.impl.pages.analytics.conversionAnalyzer.ConversionAnalyzerA17;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/6/18.
 */
@Test(groups = {"Report-Download" })
public class ConversionAnalyserA17SendToExcelTest extends BaseTest {
    IConversionAnalyzerA17 conversionAnalyzerA17;
    private static  final String FILENAME = "CAnalyzerA17*.xlsx";

    @BeforeClass
    public void setUp(){
        super.setUp();
        conversionAnalyzerA17 = navigationPanel.doNavigateTo(ConversionAnalyzerA17.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(conversionAnalyzerA17.getPageTitle());
    }

    @Test
    public void sendToExcelTest() throws IOException{
            conversionAnalyzerA17.getPreferencesBar().sendToExcel();
            assertThat(conversionAnalyzerA17.sendToExcelAndValidate(FILENAME, context)).isTrue();

    }
}


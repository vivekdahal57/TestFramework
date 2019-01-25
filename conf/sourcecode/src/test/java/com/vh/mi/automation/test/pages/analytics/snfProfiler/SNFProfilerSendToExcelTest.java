package com.vh.mi.automation.test.pages.analytics.snfProfiler;

import com.vh.mi.automation.api.pages.analytics.snfProfiler.ISNFProfilesSP100;
import com.vh.mi.automation.impl.pages.analytics.snfProfiler.SNFProfilesSP100;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/8/18.
 */
public class SNFProfilerSendToExcelTest extends BaseTest {
    private ISNFProfilesSP100 sp100;
    private static final String FILENAME = "SP100*.xlsx";

    @BeforeClass
    public void setUp(){
        super.setUp();
        sp100 = navigationPanel.doNavigateTo(SNFProfilesSP100.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(sp100.getPageTitle());
    }


    @Test
    public void checkSendToExcel() throws IOException{
        sp100.getPreferencesBar().sendToExcel();
       assertThat(sp100.sendToExcelAndValidate(FILENAME, context)).isTrue();
    }
}

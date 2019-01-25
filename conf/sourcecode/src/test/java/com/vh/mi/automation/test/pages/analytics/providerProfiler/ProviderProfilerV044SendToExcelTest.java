package com.vh.mi.automation.test.pages.analytics.providerProfiler;

import com.vh.mi.automation.api.pages.analytics.providerProfiler.IProviderProfilerV044;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.ProviderProfilerV044;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/8/18.
 */
@Test(groups = {"Report-Download" })
public class ProviderProfilerV044SendToExcelTest extends BaseTest {
    private static final String FILENAME = "ProvV044*.xlsx";
    private IProviderProfilerV044 providerProfilerV044;

    @BeforeClass
    public void setUp(){
    super.setUp();
    providerProfilerV044 = navigationPanel.doNavigateTo(ProviderProfilerV044.class, defaultWaitTime);
    assertThat(navigationPanel.doNavigateTo(ProviderProfilerV044.class, defaultWaitTime));
    }

    @Test
    public void checkSendToExcel() throws IOException{
        providerProfilerV044.getPreferencesBar().sendToExcel();
        assertThat(providerProfilerV044.sendToExcelAndValidate(FILENAME, context));
    }
}

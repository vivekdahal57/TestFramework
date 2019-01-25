package com.vh.mi.automation.test.pages.analytics.spikes;

import com.vh.mi.automation.api.pages.analytics.spikes.ISpikes;
import com.vh.mi.automation.impl.pages.analytics.spikes.SpikesS110B;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/19/18.
 */
@Test(groups = {"Report-Download" })
public class SpikesSendToExcelTest extends BaseTest {
    private ISpikes spikesPage;
    private static final String FILENAME = "SpikeS110*xlsx";

    @BeforeClass
        public void setUp(){
            super.setUp();
            spikesPage = navigationPanel.doNavigateTo(SpikesS110B.class,defaultWaitTime);
        }

        @Test
        public void checkSendToExcel() throws IOException{
            spikesPage.getPreferencesBar().sendToExcel();
            assertThat(spikesPage.sendToExcelAndValidate(FILENAME,context)).isTrue();
        }

}

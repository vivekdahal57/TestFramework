package com.vh.mi.automation.test.pages.analytics.networkUtilization;

import com.vh.mi.automation.api.pages.analytics.networkUtilization.INetworkUtilizationNU105;
import com.vh.mi.automation.impl.pages.analytics.networkUtilization.NetworkUtilizationNU105;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/8/18.
 */
public class NetworkUtilizationSendToExcelTest extends BaseTest {
    private INetworkUtilizationNU105 networkUtilization;
    private static final String FILENAME = "NU105*.xlsx";

    @BeforeClass
    public void setUp(){
        super.setUp();
        networkUtilization = navigationPanel.doNavigateTo(NetworkUtilizationNU105.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(networkUtilization.getPageTitle());
    }

    @Test
    public void checkSendToExcel() throws IOException{
    networkUtilization.getPreferencesBar().sendToExcel();
    assertThat(networkUtilization.sendToExcelAndValidate(FILENAME, context)).isTrue();
    }
}

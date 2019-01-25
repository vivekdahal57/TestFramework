package com.vh.mi.automation.test.pages.analytics.providerManagement.physicianManager;

import com.vh.mi.automation.api.pages.analytics.providerManagement.PhysicianManager.ICP110;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.physicianManager.CP110;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/8/18.
 */
@Test(groups = {"Report-Download" })
public class CP110SendToExcelTest extends BaseTest {
    private ICP110 cp110;
    private static final String FILENAME = "CP110*.xlsx";

    @BeforeClass
    public void setUp(){
        super.setUp();
        cp110 =  navigationPanel.doNavigateTo(CP110.class, defaultWaitTime);
        assertThat(navigationPanel.doNavigateTo(CP110.class, defaultWaitTime));
    }

    @Test
    public void checkSendToExcel() throws IOException{
        cp110.getPreferencesBar().sendToExcel();
        assertThat(cp110.sendToExcelAndValidate(FILENAME , context)).isTrue();
    }
}

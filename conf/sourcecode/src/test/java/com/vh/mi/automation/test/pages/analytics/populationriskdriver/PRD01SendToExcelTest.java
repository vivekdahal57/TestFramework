package com.vh.mi.automation.test.pages.analytics.populationriskdriver;

import com.vh.mi.automation.impl.pages.analytics.populationriskdriver.PRD01;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/8/18.
 */
@Test(groups = {"Report-Download" })
public class PRD01SendToExcelTest extends BaseTest {
    private PRD01 prd01;
    private static final String FILENAME = "PRD01*.xlsx";

    @BeforeClass
    public void setUp(){
        super.setUp();
        prd01 =  navigationPanel.doNavigateTo(PRD01.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(prd01.getPageTitle());
    }

    @Test
    public void checkSendToExcel() throws IOException{
        prd01.getPreferencesBar().sendToExcel();
        assertThat(prd01.sendToExcelAndValidate(FILENAME, context)).isTrue();
    }
}

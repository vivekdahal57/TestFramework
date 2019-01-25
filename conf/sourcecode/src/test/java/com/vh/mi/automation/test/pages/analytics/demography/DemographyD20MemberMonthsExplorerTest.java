package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.comp.navigation.INavigationPanel;
import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.api.pages.analytics.demography.drill.ID20MMDrillPage;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Created by i10359 on 2/28/18.
 */
public class DemographyD20MemberMonthsExplorerTest  extends BaseTest {
    private IDemographyD05 demographyD05;
    private ID20MMDrillPage d20MMDrillPage;

    @BeforeClass
    public void setUp(){
        super.setUp();
        demographyD05 = navigationPanel.doNavigateTo(DemographyD05.class,defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle().equals(demographyD05.getPageTitle()));
    }

    @Test(description = "drill To MM => assert The title of the drilled Page")
    public void test(){
       d20MMDrillPage = (ID20MMDrillPage) demographyD05.getDataGrid().getRows().get(0).doDrillByOnSameWindow("MM");
       assertThat(d20MMDrillPage.getPageTitle().equals("(D20) Member Months Explorer")).isTrue();
    }
}

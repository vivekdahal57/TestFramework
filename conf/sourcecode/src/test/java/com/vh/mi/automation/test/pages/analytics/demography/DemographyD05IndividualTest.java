package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.api.pages.analytics.demography.drill.ID07AgeGroupDrillPage;
import com.vh.mi.automation.api.pages.analytics.demography.drill.ID09AgeGroupDrillPage;
import com.vh.mi.automation.api.pages.analytics.demography.drill.IDemographyIndividualDrillPage;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 3/2/18.
 */
public class DemographyD05IndividualTest  extends BaseTest {
    private IDemographyD05 demographyD05;
    private ID07AgeGroupDrillPage d07AgeGroupDrillPage;
    private ID09AgeGroupDrillPage d09AgeGroupDrillPage;
    private IDemographyIndividualDrillPage demographyIndividualDrillPage;

    @BeforeClass
    public void setUp(){
        super.setUp();
        demographyD05 = navigationPanel.doNavigateTo(DemographyD05.class, defaultWaitTime);
        assertThat(demographyD05.getPageTitle().equals("(D05) Demographic Analysis"));
    }

    @Test
    public void Test(){
       d07AgeGroupDrillPage =(ID07AgeGroupDrillPage) demographyD05.drillDownToPage("Age Group");
       assertThat(d07AgeGroupDrillPage.getPageTitle().equals("(D07) Total Paid by Age Group"));
       d09AgeGroupDrillPage = (ID09AgeGroupDrillPage) d07AgeGroupDrillPage.drillDownToPage("Age Sub-group");
       assertThat(d09AgeGroupDrillPage.getPageTitle().equals("(D09) Total Paid by Age Sub-Groups"));
       demographyIndividualDrillPage = (IDemographyIndividualDrillPage)d09AgeGroupDrillPage.drillDownToPage("Individual");
       assertThat(demographyIndividualDrillPage.getPageTitle().equals("(301) Individuals"));
    }


}

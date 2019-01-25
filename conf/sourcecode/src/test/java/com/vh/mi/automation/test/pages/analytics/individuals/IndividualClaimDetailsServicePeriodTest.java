package com.vh.mi.automation.test.pages.analytics.individuals;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardIndividualClaimDetails;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardIndividualClaimDetails;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 1/8/18.
 */
public class IndividualClaimDetailsServicePeriodTest extends BaseTest{
    IIndividualDashboardIndividualClaimDetails individualDashboardIndividualClaimDetails;

    @BeforeClass
    public void setUp(){
        super.setUp();
        Indv301 indv301 =  navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        indv301.popupExists();
        individualDashboardIndividualClaimDetails = indv301.doDrillFromIndv301();
    }

    @Test(dataProvider = "drillOptionProviderForServicePeriod")
    public void ServicePeriodTest(String drillOption){
    individualDashboardIndividualClaimDetails.clickDrillOptionForComponent(IndividualDashboardIndividualClaimDetails.Component.SERVICE_PERIOD.getId(), drillOption);
    individualDashboardIndividualClaimDetails.waitTillLoadingDisappears();
    assertThat(individualDashboardIndividualClaimDetails.isDrillPageValid()).isTrue();
    }

    @DataProvider(name = "drillOptionProviderForServicePeriod")
    public Object[][] drillOptionsProviderForServicePeriod(){
        ImmutableList<String>  hoverOptions = individualDashboardIndividualClaimDetails.getDrillOptionsFor(IndividualDashboardIndividualClaimDetails.Component.SERVICE_PERIOD.getId());
        List<String> hoverOptionsList = new ArrayList<>();
        for(String hoverOption : hoverOptions){
            if(hoverOptionsList.add(hoverOption));
        }
        return DataProviderUtils.getObjects(hoverOptionsList);
    }
}

package com.vh.mi.automation.test.pages.analytics.individuals;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardQualityMeasures;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardQualityMeasures;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 10/26/17.
 */
public class Indv301QualityMeasuresTest extends BaseTest {
    IIndv301 indv301;
    IIndividualDashboardQualityMeasures qualityMeasures;


    @BeforeClass
    public void setUp(){
        super.setUp();
        indv301 = navigationPanel.doNavigateTo(Indv301.class,defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(indv301.getPageTitle());
        indv301.popupExists();
        qualityMeasures = (IIndividualDashboardQualityMeasures) indv301.getDataGrid().getRows().get(0).doDrillBy("Quality Measures");
        qualityMeasures.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        assertThat(qualityMeasures.getPageTitle()).isEqualTo("Individual Dashboard: (325) Quality Measures");
    }

    @Test(dataProvider = "drillOptionProviderForGapsGroupBy", description="")
    public void  gapsGroupByTest(String drillOption) throws IOException{
        qualityMeasures.clickDrillOptionForComponent(IndividualDashboardQualityMeasures.Component.GAPS_GROUPBY.getMenuId(), drillOption);
        qualityMeasures.waitTillLoadingDisappears();
        assertThat(qualityMeasures.isDrillPageValid()).isTrue();
        assertThat(qualityMeasures.isGroupByColumnShown(IndividualDashboardQualityMeasures.Component.GAPS_GROUPBY, drillOption)).isTrue();
    }

    @Test(dataProvider = "drillOptionProviderForRiskGroupBy", description="")
    public void  riskGroupByTest(String drillOption) throws IOException{
        qualityMeasures.clickDrillOptionForComponent(IndividualDashboardQualityMeasures.Component.RISK_GROUPBY.getMenuId(), drillOption);
        qualityMeasures.waitTillLoadingDisappears();
        assertThat(qualityMeasures.isDrillPageValid()).isTrue();
        assertThat(qualityMeasures.isGroupByColumnShown(IndividualDashboardQualityMeasures.Component.RISK_GROUPBY, drillOption)).isTrue();
    }

    @Test(dataProvider = "drillOptionProviderForGapDetails", description="")
    public void  gapDetailsTest(String drillOption) throws IOException{
        qualityMeasures.clickDrillOptionForComponent(IndividualDashboardQualityMeasures.Component.GAPS_DETAILS.getMenuId(), drillOption);
        qualityMeasures.waitTillLoadingDisappears();
        assertThat(qualityMeasures.isStatusDetailsShown(IndividualDashboardQualityMeasures.Component.GAPS_DETAILS, drillOption)).describedAs("Details Not shown or No data in Table").isTrue();
    }

    @Test(dataProvider = "drillOptionProviderForRiskDetails", description="")
    public void  riskDetailsTest(String drillOption) throws IOException{
        qualityMeasures.clickDrillOptionForComponent(IndividualDashboardQualityMeasures.Component.RISK_DETAILS.getMenuId(), drillOption);
        qualityMeasures.waitTillLoadingDisappears();
        assertThat(qualityMeasures.isStatusDetailsShown(IndividualDashboardQualityMeasures.Component.RISK_DETAILS, drillOption)).describedAs("Details Not shown or No data in Table").isTrue();
    }




    @DataProvider(name = "drillOptionProviderForGapsGroupBy")
    public Object[][] drillOptionProviderForGapsGroupBy(){
        ImmutableList<String> hoverOptions = qualityMeasures.getDrillOptionsFor(IndividualDashboardQualityMeasures.Component.GAPS_GROUPBY.getMenuId());
        List<String> hoverOptionsList = new ArrayList<>();
        for(String hoverOption  : hoverOptions){
            hoverOptionsList.add(hoverOption);
        }
        return DataProviderUtils.getObjects(hoverOptionsList);
    }

    @DataProvider(name = "drillOptionProviderForRiskGroupBy")
    public Object[][] drillOptionProviderForRiskGroupBy(){
        ImmutableList<String> hoverOptions = qualityMeasures.getDrillOptionsFor(IndividualDashboardQualityMeasures.Component.RISK_GROUPBY.getMenuId());
        List<String> hoverOptionsList = new ArrayList<>();
        for(String hoverOption  : hoverOptions){
            hoverOptionsList.add(hoverOption);
        }
        return DataProviderUtils.getObjects(hoverOptionsList);
    }

    @DataProvider(name = "drillOptionProviderForGapDetails")
    public Object[][] drillOptionProviderForGapDetails(){
        ImmutableList<String> hoverOptions = qualityMeasures.getDrillOptionsFor(IndividualDashboardQualityMeasures.Component.GAPS_DETAILS.getMenuId());
        List<String> hoverOptionsList = new ArrayList<>();
        for(String hoverOption  : hoverOptions){
            hoverOptionsList.add(hoverOption);
        }
        return DataProviderUtils.getObjects(hoverOptionsList);
    }

    @DataProvider(name = "drillOptionProviderForRiskDetails")
    public Object[][] drillOptionProviderForRiskDetails(){
        ImmutableList<String> hoverOptions = qualityMeasures.getDrillOptionsFor(IndividualDashboardQualityMeasures.Component.RISK_DETAILS.getMenuId());
        List<String> hoverOptionsList = new ArrayList<>();
        for(String hoverOption  : hoverOptions){
            hoverOptionsList.add(hoverOption);
        }
        return DataProviderUtils.getObjects(hoverOptionsList);
    }


}

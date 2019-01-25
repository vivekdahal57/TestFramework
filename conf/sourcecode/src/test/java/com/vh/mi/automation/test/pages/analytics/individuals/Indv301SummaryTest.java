package com.vh.mi.automation.test.pages.analytics.individuals;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardMemberSummary;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardQualityMeasures;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardIndividualClaimDetails;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardMemberSummary;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardQualityMeasures;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.MemberTab.MedicalIntelligenceMemberSummary;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
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
public class Indv301SummaryTest extends BaseTest {
    private static final String FILE_NAME_PATTERN = "IndvQuality_*.rtf";
    IIndv301 indv301;
    IIndividualDashboardMemberSummary memberSummary;


    @BeforeClass
    public void setUp(){
        super.setUp();
        indv301 = navigationPanel.doNavigateTo(Indv301.class,defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(indv301.getPageTitle());
        indv301.popupExists();
        memberSummary = (IIndividualDashboardMemberSummary) indv301.getDataGrid().getRows().get(0).doDrillBy("Summary");
        memberSummary.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        assertThat(memberSummary.getPageTitle()).isEqualTo("Individual Dashboard: (320) Member Summary");
    }

    @Test(dataProvider = "drillOptionProviderForAnalysisPeriod", description="drill down to all the hover elements for analysis period and check if all the tables of summary are present in the page for each")
    public void  analysisPeriodTest(String drillOption) throws IOException{
        memberSummary.clickDrillOptionForComponent(IndividualDashboardMemberSummary.Component.ANALYSIS_PERIOD.getId(), drillOption);
        memberSummary.waitTillLoadingDisappears();
        assertThat(memberSummary.isDrillPageValid()).isTrue();
    }

    @Test(dataProvider = "drillOptionProviderForDiagGroup", description="drill down to all the hover elements for Diag Group and check if all the tables of summary are present in the page for each")
    public void  diagGroupTest(String drillOption) throws IOException{
        memberSummary.clickDrillOptionForComponent(IndividualDashboardMemberSummary.Component.DIAG_GROUP.getId(), drillOption);
        memberSummary.waitTillLoadingDisappears();
        assertThat(memberSummary.isDrillPageValid()).isTrue();
    }

    @Test(description = "click the Scorecard and validate the downloaded File")
    public void scoreCardTest() throws IOException {
        memberSummary.getPreferencesBar().scoreCard();
        assertThat(memberSummary.downLoadFileAndValidate(FILE_NAME_PATTERN, context)).isTrue();
    }


    @Test(description = "drill down to Individual claim Details => assert the title => navigate back")
    public void individualClaimDetailDrillTest(){
        memberSummary.goToIndividualClaimDetails();
        IndividualDashboardIndividualClaimDetails indvClaimDetails = new IndividualDashboardIndividualClaimDetails(getWebDriver());
        String pageTitle = indvClaimDetails.getPageTitle();
        assertThat(pageTitle.equalsIgnoreCase("Individual Dashboard: (949) Individual Claim Details"));
        getWebDriver().navigate().back();
    }

    @Test(description = "click the View Report and assert the title of the page")
    public void viewReportFunctionalityTest() {
        memberSummary.getPreferencesBar().viewReport();
        SeleniumUtils.switchToNewWindow(getWebDriver());
        String pageTitle = new MedicalIntelligenceMemberSummary(webDriver).getPageTitle();
        assertThat(pageTitle.equalsIgnoreCase("Medical Intelligence Member Summary"));
    }


    @DataProvider(name = "drillOptionProviderForAnalysisPeriod")
    public Object[][] drillOptionProviderForAnalysisPeriod(){
        ImmutableList<String> hoverOptions = memberSummary.getDrillOptionsFor(IndividualDashboardMemberSummary.Component.ANALYSIS_PERIOD.getId());
        List<String> hoverOptionsList = new ArrayList<>();
        for(String hoverOption  : hoverOptions){
            hoverOptionsList.add(hoverOption);
        }
        return DataProviderUtils.getObjects(hoverOptionsList);
    }

    @DataProvider(name = "drillOptionProviderForDiagGroup")
    public Object[][] drillOptionProviderForDiagGroup(){
        ImmutableList<String> hoverOptions = memberSummary.getDrillOptionsFor(IndividualDashboardMemberSummary.Component.DIAG_GROUP.getId());
        List<String> hoverOptionsList = new ArrayList<>();
        for(String hoverOption  : hoverOptions){
            hoverOptionsList.add(hoverOption);
        }
        return DataProviderUtils.getObjects(hoverOptionsList);
    }

}

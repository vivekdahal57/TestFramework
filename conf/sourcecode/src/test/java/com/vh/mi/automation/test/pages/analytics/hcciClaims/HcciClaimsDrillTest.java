package com.vh.mi.automation.test.pages.analytics.hcciClaims;
import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.pages.analytics.claims.IClaims01;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.analytics.claims.Claims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i20841 on 7/5/2018.
 */
@Test(groups = "hcciClaims101", sequential = true)
public class HcciClaimsDrillTest extends BaseTest {

    private HcciIClaims01 hcciClaims101;
    IDataGrid dataGrid;
    List<String> pagesContainingUnknownBlank = new ArrayList<>();

    @BeforeClass(description = "navigate to claims")
    public void setUp() {
        super.setUp();
        hcciClaims101 = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
        dataGrid = hcciClaims101.getDataGrid();
    }


    @Test(dataProvider = "drillOptionsProviderForBusinessLevels", priority = 0, description = "Drill Down to 'Plan Type' from Medical and check 'BLANK' => Drill Down to all values under 'Dimensions' and check 'BlANK' in each => Click 'Reset to Default View' and  Drill Down to 'Plan Type' from Pharmacy and repeat the same process as for medical and do repeat same for 'HMO','Company', and 'Unit' ")
    public void clickingOnDrillOptionShouldOpenDrillPage(String drillOption) {
        ImmutableList<IDataGridRow> businessLevelDrillPageList;
        businessLevelDrillPageList = dataGrid.getRows();

        System.out.println("businessLevel size - " + businessLevelDrillPageList.size());
        for (int i = 0; i < businessLevelDrillPageList.size(); i++) {
            System.out.println("Testing businessLevel " + i);
            IDrillPage businessLevelDrillPage;
            businessLevelDrillPage = dataGrid.getRows().get(i).doDrillByOnSameWindow(drillOption);

            businessLevelDrillPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
            IDataGrid businessLevelDrillPageDataGrid = businessLevelDrillPage.getDataGrid();
            Assertions.assertThat(businessLevelDrillPageDataGrid.checkUnknownBlanksForDefault()).isTrue();

            IDataGridRow firstRow = businessLevelDrillPageDataGrid.getRows().get(0);
            ImmutableList<String> drillOptions = firstRow.getDrillOptionsBetween("Dimensions", "View");

            for (String drillOptionValue : drillOptions) {
                System.out.println("---------->Testing drillOption " + drillOptionValue);
                IDrillPage dimensionsDrillPage = businessLevelDrillPageDataGrid.getRows().get(0).doDrillByOnSameWindow(drillOptionValue);
                dimensionsDrillPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
                IDataGrid dimensionsDrillPageDataGrid = dimensionsDrillPage.getDataGrid();
//                Assertions.assertThat(dimensionsDrillPageDataGrid.checkUnknownBlanksForDefault()).isTrue();
//                if(!dimensionsDrillPageDataGrid.checkUnknownBlanksForDefault()){
//                    pagesContainingUnknownBlank.add(drillOptionValue);
//                }
                getWebDriver().navigate().back();
                businessLevelDrillPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
            }

            businessLevelDrillPage.getPreferencesBar().resetToDefaultViewButton();
            dataGrid = hcciClaims101.getDataGrid();
        }

        assertThat(pagesContainingUnknownBlank.size()).describedAs("Unknown blanks found in page " + pagesContainingUnknownBlank.toString()).isEqualTo(0);
    }

    @Test(dataProvider = "drillOptionsProviderForTrendBy", priority = 1, description = "Drill Down to 'Month' from Medical and check if the page is loaded => Drill Down to all values under 'Dimensions' and check if each the page is loaded => Click 'Reset to Default View' and Drill Down to 'Month' from Pharmacy and repeat the same process as for medical and do repeat same for 'Quarter' and 'Year'")
    public void drillTestForTrendBy(String drillOption) {
        ImmutableList<IDataGridRow> trendByDrillPageList;
        trendByDrillPageList = dataGrid.getRows();
        for (int i = 0; i < trendByDrillPageList.size(); i++) {
            IDrillPage trendByDrillPage;
            trendByDrillPage = dataGrid.getRows().get(i).doDrillByOnSameWindow(drillOption);

            trendByDrillPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
            IDataGrid trendByDrillPageDataGrid = trendByDrillPage.getDataGrid();
            Assertions.assertThat(trendByDrillPage.getPageTitle().equalsIgnoreCase("(S101) Services"));

            IDataGridRow firstRow = trendByDrillPageDataGrid.getRows().get(0);
            ImmutableList<String> drillOptions = firstRow.getDrillOptionsBetween("Dimensions", "View");

            for (String drillOptionValue : drillOptions) {
                IDrillPage dimensionsDrillPage = trendByDrillPageDataGrid.getRows().get(0).doDrillByOnSameWindow(drillOptionValue);
                dimensionsDrillPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
                Assertions.assertThat(dimensionsDrillPage.getPageTitle().equalsIgnoreCase("(S101) Services"));
                getWebDriver().navigate().back();
                trendByDrillPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
            }

            trendByDrillPage.getPreferencesBar().resetToDefaultViewButton();
            dataGrid = hcciClaims101.getDataGrid();

        }
    }

    @Test(dataProvider = "drillOptionProviderForTrendGraphFor", priority = 2, description = "Drill down over all elements under 'Trend Graph For' and check  if the graph is loaded for Medical Claim Type")
    public void drillDownAndCheckIfTheGraphIsLoaded(String drillOption) {
        ImmutableList<IDataGridRow> trendGraphForDrillPageList;
        trendGraphForDrillPageList = dataGrid.getRows();

        for (int i = 0; i < trendGraphForDrillPageList.size(); i++) {
            if (dataGrid.getRows().get(i).getValue(2).equalsIgnoreCase("pharmacy")) {
                break;
            }
            IDrillPage trendGraphForDrillPage;
            trendGraphForDrillPage = dataGrid.getRows().get(i).doDrillBy(drillOption);
            assertThat(trendGraphForDrillPage.isImageFullyLoaded()).isTrue();
            trendGraphForDrillPage.closeCurrentWindowAndGoBackToParentWindow();

        }
    }


    @Test(dataProvider = "pharmacyDrillOptionProviderForTrendGraphFor", priority = 2, description = "Drill down over all elements under 'Trend Graph For' and check  if the graph is loaded for Pharmacy Claim Type")
    public void drillDownAndCheckIfTheGraphIsLoadedPharmacy(String rxDrillOption) {
        ImmutableList<IDataGridRow> trendGraphForDrillPageList;
        trendGraphForDrillPageList = dataGrid.getRows();

        for (int i = 0; i < trendGraphForDrillPageList.size(); i++) {
            if (dataGrid.getRows().get(i).getValue(2).equalsIgnoreCase("pharmacy")) {
                IDrillPage trendGraphForDrillPage;
                trendGraphForDrillPage = dataGrid.getRows().get(i).doDrillBy(rxDrillOption);
                assertThat(trendGraphForDrillPage.isImageFullyLoaded()).isTrue();
                trendGraphForDrillPage.closeCurrentWindowAndGoBackToParentWindow();

            }
        }
    }

    @DataProvider(name = "drillOptionsProviderForBusinessLevels")
    public Object[][] drillOptionsProviderForBusinessLevels() {
        IDataGridRow firstRow = dataGrid.getRows().get(0);
        ImmutableList<String> drillOptions = firstRow.getDrillOptionsBetween("Business Levels", "Dimensions");
        List<String> drillOptionsList = new ArrayList<>();
        for (String drillOption : drillOptions) {
            drillOptionsList.add(drillOption);
        }
        return DataProviderUtils.getObjects(drillOptionsList);
    }


    @DataProvider(name = "drillOptionsProviderForTrendBy")
    public Object[][] drillOptionProviderForTrendBy() {
        IDataGridRow firstRow = dataGrid.getRows().get(0);
        ImmutableList<String> drillOptions = firstRow.getDrillOptionsAfter("Trend by");
        List<String> drillOptionList = new ArrayList<>();
        for (String drillOption : drillOptions) {
            drillOptionList.add(drillOption);
        }
        return DataProviderUtils.getObjects(drillOptionList);
    }

    @DataProvider(name = "drillOptionProviderForTrendGraphFor")
    public Object[][] drillOptionProviderForTrendGraphFor() {
        IDataGridRow firstRow = dataGrid.getRows().get(0);
        ImmutableList<String> drillOptions = firstRow.getDrillOptionsBetween("Trend Graph for", "Trend by");
        List<String> drillOptionList = new ArrayList<>();
        for (String drillOption : drillOptions) {
            drillOptionList.add(drillOption);
        }
        return DataProviderUtils.getObjects(drillOptionList);
    }

    @DataProvider(name = "pharmacyDrillOptionProviderForTrendGraphFor")
    public Object[][] pharmacyDrillOptionProviderForTrendGraphFor() {
          List<String> rxDrillOptionList = new ArrayList<>();
          for (int i = 0; i < dataGrid.getRows().size(); i++) {
            if (dataGrid.getRows().get(i).getValue(2).equalsIgnoreCase("pharmacy")) {
                    IDataGridRow firstRow = dataGrid.getRows().get(i);
                    ImmutableList<String> rxDrillOptions = firstRow.getDrillOptionsBetween("Trend Graph for", "Trend by");
                for (String rxDrillOption : rxDrillOptions) {
                    rxDrillOptionList.add(rxDrillOption);
                }

            }
        }
        return DataProviderUtils.getObjects(rxDrillOptionList);
    }
}


package com.vh.mi.automation.test.pages.analytics.providerProfiler;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.IProviderProfilerV044;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.ProviderProfilerV044;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vh.mi.automation.api.constants.MILandingPages.PROVIDER_PROFILER;
import static org.fest.assertions.Assertions.assertThat;

public class ProviderProfilerDataGridDrillTest extends BaseTest {
    private IProviderProfilerV044 v044;
    private IDataGrid dataGrid;


    @BeforeClass()
    public void setUp() {
        super.setUp();
        v044 = navigationPanel.doNavigateTo(ProviderProfilerV044.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(PROVIDER_PROFILER.getPageTitle());
        dataGrid = v044.getDataGrid();
    }

    @Test(dataProvider = "drillOptionsProvider")
    public void clickingOnDrillOptionShouldOpenDrillPage(String drillOption) {
        IDrillPage drillPage;
        if(drillOption.equalsIgnoreCase("Claims")){
            drillPage = dataGrid.getRows().get(0).doDrillBy(drillOption);
        }else{
            drillPage =  dataGrid.getRows().get(0).doDrillByOnSameWindow(drillOption);
        }

        drillPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());

        String actualPageTitle = drillPage.getDisplayedPageTitle();
        String expectedPageTitle = drillPage.getPageTitle();
        boolean isDrillPageValid = drillPage.isDrillPageValid();

        //Note it is essential that we close the drill page before asserting
        //because we don't want the close to be skipped because an assertion failed
        if(drillOption.equalsIgnoreCase("Claims")) {
            drillPage.doClose();
        } else{
            drillPage.getPreferencesBar().backButton();
            v044.doWaitTillFullyLoaded(defaultWaitTime);
        }
        Assertions.assertThat(actualPageTitle).isEqualTo(expectedPageTitle);
        Assertions.assertThat(isDrillPageValid).isTrue();
    }


    @DataProvider(name = "drillOptionsProvider")
    public  Object[][] drillOptionsProvider() {
        List<String> valuesToBeRemoved = new ArrayList<>();
        valuesToBeRemoved.add("Month");
        valuesToBeRemoved.add("Quarter");
        valuesToBeRemoved.add("Year");
        IDataGridRow firstRow = dataGrid.getRows().get(0);
        ImmutableList<String> drillOptions = firstRow.getDrillOptions();

        if (!firstRow.hasDrillOptions()) {
            throw new RuntimeException("First Row does not have drill options");
        }

        List<String> drillListToBeUsed = drillOptions.stream()
                .filter(e -> (valuesToBeRemoved.stream().filter(d -> d.equals(e)).count())<1)
                .collect(Collectors.toList());

        List<String> drillOptionsList = new ArrayList<>();
        for (String drillOption : drillListToBeUsed) {
            drillOptionsList.add(drillOption);
        }
        return DataProviderUtils.getObjects(drillOptionsList);
    }
}


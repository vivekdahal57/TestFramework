package com.vh.mi.automation.test.pages.analytics.customPerformanceMeasures;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.vh.mi.automation.api.constants.MILandingPages.CUSTOM_PERFORMANCE_MEASURES;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 1/25/18.
 */
public class  CPM01DrillTest extends BaseTest {
    private ICPM01 icpm01;
    private IDataGrid dataGrid;
    IDrillPage drillPage;

    @BeforeClass
    public void setUp() {
        super.setUp();
        icpm01 = navigationPanel.doNavigateTo(CPM01.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(CUSTOM_PERFORMANCE_MEASURES.getPageTitle());
        dataGrid = icpm01.getDataGrid();
    }

    @Test(dataProvider =  "drillOptionsProviderForBusinessLevels")
    public void dataProviderOptionsDrillTest(String drillOption) {

        drillPage = dataGrid.getRows().get(0).doDrillByOnSameWindow(drillOption);
        drillPage.doWaitTillFullyLoaded(defaultWaitTime);
        assertThat("(CPM01) Custom Performance Measures".equalsIgnoreCase(drillPage.getPageTitle()));
    }

    @Test
    public void drillFromTop(){
        IDrillPage populationDrillPage = drillPage.getDataGrid().applyDrill("All Population");
        populationDrillPage.doWaitTillFullyLoaded(defaultWaitTime);
        populationDrillPage.isFullyLoaded();
        webDriver.navigate().back();
        drillPage.doWaitTillFullyLoaded(defaultWaitTime);
        drillPage = drillPage.getDataGrid().applyDrill("Top 1% LOH Population");
        drillPage.isFullyLoaded();
        webDriver.navigate().back();
    }

    @DataProvider(name = "drillOptionsProviderForBusinessLevels")
    public Object[][] drilloptionProviderForBusinessLevel(){
        IDataGridRow firstRow = dataGrid.getRows().get(0);
        ImmutableList<String> drillOptions = firstRow.getDrillOptionsBetween("Business Levels", "Drill To");
        List<String> drillOptionList = new ArrayList<>();

        for(String drillOption : drillOptions){
            drillOptionList.add(drillOption);
        }
        return DataProviderUtils.getObjects(drillOptionList);
    }
}
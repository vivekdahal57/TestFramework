package com.vh.mi.automation.test.pages.analytics.individuals;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGridTest;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import org.fest.assertions.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nimanandhar
 */
public abstract class AbstractDataGridDrillTest extends AbstractDataGridTest {

    @Test(dataProvider = "drillOptionsProvider")
    public void clickingOnDrillOptionShouldOpenDrillPage(IDataGridRow row, String drillOption) {
        IDrillPage drillPage = row.doDrillBy(drillOption);
        drillPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());

        String actualPageTitle = drillPage.getDisplayedPageTitle();
        String expectedPageTitle = drillPage.getPageTitle();

        boolean isDrillPageValid = drillPage.isDrillPageValid();

        //Note it is essential that we close the drill page before asserting
        //because we don't want the close to be skipped because an assertion failed
        drillPage.doClose();

        Assertions.assertThat(actualPageTitle).isEqualTo(expectedPageTitle);
        Assertions.assertThat(isDrillPageValid).isTrue();
    }


    @DataProvider(name = "drillOptionsProvider")
  public  Object[][] drillOptionsProvider() {
        IDataGridRow firstRow = dataGrid.getRows().get(0);

        if (!firstRow.hasDrillOptions()) {
            throw new RuntimeException("First Row does not have drill options");
        }

        ImmutableList<String> drillOptions = firstRow.getDrillOptions();

        List<IDataGridRow> rowsList = new ArrayList<>();
        List<String> drillOptionsList = new ArrayList<>();

        for (String drillOption : drillOptions) {
            rowsList.add(firstRow);
            drillOptionsList.add(drillOption);
        }

        return DataProviderUtils.getObjects(rowsList, drillOptionsList);
    }

}

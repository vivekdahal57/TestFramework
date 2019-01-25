package com.vh.mi.automation.test.pages.analytics.populationriskdriver;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.analytics.populationriskdriver.PRD01;
import com.vh.mi.automation.test.base.BaseTest;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82298 on 6/1/2017.
 */
public class PRD01CostDataGridDrillTest extends BaseTest {

    IDataGrid dataGrid;

    @BeforeClass
    public void setUp() {
        super.setUp();
        PRD01 prd01 = navigationPanel
                .doNavigateTo(PRD01.class, defaultWaitTime);
        prd01.waitTillDocumentReady(defaultWaitTime);
        dataGrid = prd01.getCostDataGrid();

    }

    @Test()
    public void clickingOnDrillOptionShouldOpenDrillPage() {
        IDataGridRow firstRow = dataGrid.getRows().get(0);
        IDrillPage drillPage = firstRow.doDrillBy("Individuals");
        drillPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        String actualPageTitle = drillPage.getDisplayedPageTitle();
        String expectedPageTitle = drillPage.getPageTitle();
        drillPage.doClose();
        Assertions.assertThat(actualPageTitle).isEqualTo(expectedPageTitle);
    }

}

package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.api.pages.analytics.demography.drill.ID07AgeGroupDrillPage;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_FilterTest;
import org.testng.annotations.BeforeClass;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/23/18.
 */
public class DemographyD07AgeGroupFilterTest extends AbstractDataGrid_FilterTest {
    private IDemographyD05 demographyD05;
    private ID07AgeGroupDrillPage d07AgeGroupDrillPage;

    @BeforeClass
    public void setUp(){
        super.setUp();
    }


    @Override
    protected IDataGrid getDataGrid() {
        demographyD05 = navigationPanel.doNavigateTo(DemographyD05.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(demographyD05.getPageTitle());
        d07AgeGroupDrillPage = (ID07AgeGroupDrillPage) demographyD05.drillDownToPage("Age Group");
        return d07AgeGroupDrillPage.getDataGrid();
    }

    @Override
    public List<IDataGridColumn> getColumnsToTest() {

        return dataGrid.getColumns();
    }
}

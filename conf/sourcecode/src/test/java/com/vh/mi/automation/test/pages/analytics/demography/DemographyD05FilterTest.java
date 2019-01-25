package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_FilterTest;
import org.testng.annotations.BeforeClass;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/27/18.
 */
public class DemographyD05FilterTest extends AbstractDataGrid_FilterTest {
    private IDemographyD05 demographyD05;

    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
       demographyD05 = navigationPanel.doNavigateTo(DemographyD05.class, defaultWaitTime);
       assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(demographyD05.getPageTitle());
       return demographyD05.getDataGrid();
    }

    @Override
    public List<IDataGridColumn> getColumnsToTest() {
        return dataGrid.getColumns();
    }
}

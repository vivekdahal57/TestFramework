package com.vh.mi.automation.test.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01DataGridColumn;
import com.vh.mi.automation.impl.utils.FileUtils;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_FilterTest;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 1/30/18.
 */
public class CPM01FilterTest extends AbstractDataGrid_FilterTest {
    private ICPM01 cpm01;
    private LoadingPopup loadingPopup;

    @BeforeClass
    public void setUp()
    {
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        cpm01 = navigationPanel.doNavigateTo(CPM01.class, defaultWaitTime);
        cpm01.doWaitTillFullyLoaded(defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(cpm01.getPageTitle());
        return cpm01.getDataGrid();
    }

    @Override
    public List<IDataGridColumn> getColumnsToTest() {
        List<IDataGridColumn> columnsToTest = new ArrayList<>();
        for (String label : FileUtils.getLinesFromResourceFile("columns/CPM01Columns.txt")) {
            IDataGridColumn column = CPM01DataGridColumn.fromLabel(label);
            if (column == null) {
                logger.error("Column is null for label  " + label);
            } else {
                columnsToTest.add(column);
            }
        }
        return columnsToTest;
    }
}

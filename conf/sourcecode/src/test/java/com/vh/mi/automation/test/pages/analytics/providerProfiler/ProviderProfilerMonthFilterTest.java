package com.vh.mi.automation.test.pages.analytics.providerProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.IProviderProfilerV044;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Month.ProviderProfilerMonthDrillPageDataGridColumns;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.ProviderProfilerV044;
import com.vh.mi.automation.impl.utils.FileUtils;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_FilterTest;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 1/11/18.
 */
public class ProviderProfilerMonthFilterTest extends AbstractDataGrid_FilterTest {
    IProviderProfilerV044 v044;
    IDrillPage monthDrillPage;

    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        IProviderProfilerV044 v044 =  navigationPanel.doNavigateTo(ProviderProfilerV044.class, defaultWaitTime);

        v044.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(v044.getPageTitle());
        monthDrillPage = v044.getDataGrid().getRows().get(0).doDrillByOnSameWindow("Month");
        monthDrillPage.doWaitTillPopUpDisappears();
        dataGrid =  monthDrillPage.getDataGrid();
        return dataGrid;
    }


    @Override
    public List<IDataGridColumn> getColumnsToTest() {
        List<IDataGridColumn> columnsToTest = new ArrayList<>();
        for (String label : FileUtils.getLinesFromResourceFile("columns/ProviderProfilerMonthDrillPageColumns.txt")) {
            IDataGridColumn column = ProviderProfilerMonthDrillPageDataGridColumns.fromLabel(label);
            if (column == null) {
                logger.error("Column is null for label  " + label);
            } else {
                columnsToTest.add(column);
            }
        }
        return columnsToTest;
    }
}

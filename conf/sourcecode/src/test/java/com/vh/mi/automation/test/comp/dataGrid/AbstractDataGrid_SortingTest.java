package com.vh.mi.automation.test.comp.dataGrid;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.IGridValue;
import com.vh.mi.automation.api.constants.DataType;
import com.vh.mi.automation.api.constants.SortOrder;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301Columns;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import com.vh.mi.automation.test.utils.TestUtils;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by nimanandhar on 11/4/2014.
 */
public abstract class AbstractDataGrid_SortingTest extends AbstractDataGridTest {
    private final List<Indv301Columns> ERROR_COLUMNS = ImmutableList.of(Indv301Columns.IPP);

    @Test(dataProvider = "sortableVisibleColumnsProvider")
    public void dataShouldBeSortedWhenSortableColumnIsClicked(IDataGridColumn column, SortOrder sortOrder) {
        if (ERROR_COLUMNS.contains(column))
            throw new SkipException("Skipping sorting test for column" + column);


        dataGrid.doSort(column, sortOrder);

        assertThat(dataGrid.getCurrentSortOrder())
                .as("Current sort order should be " + sortOrder)
                .isEqualTo(sortOrder);
        assertThat(dataGrid.getCurrentSortedColumn())
                .as("Expected currentSortedColumn to be " + column + " after sorting on the column")
                .isEqualTo(column);

        List<IGridValue> data = getData(column);
        List<IGridValue> sortedData = TestUtils.sortList(data, sortOrder);

        assertThat(data).isEqualTo(sortedData);
    }


    /********************************************************************************/
    /*                          DATA PROVIDERS                                      */

    /**
     * ****************************************************************************
     */

    @DataProvider(name = "sortableVisibleColumnsProvider")
    protected Object[][] sortableVisibleColumnsProvider() {
        List<IDataGridColumn> columns = new ArrayList<>();
        List<SortOrder> sortOrders = new ArrayList<>();

        for (IDataGridColumn columnToTest : filter(getSortableColumns())) {
            columns.add(columnToTest);
            sortOrders.add(SortOrder.DESC);
            columns.add(columnToTest);
            sortOrders.add(SortOrder.ASC);
        }


        return DataProviderUtils.getObjects(columns, sortOrders);
    }

    /**
     * Concrete test classes can remove some columns from this list
     * to limit the columns which are tested
     */
    protected List<IDataGridColumn> filter(List<IDataGridColumn> sortableColumns) {
        return sortableColumns;
    }

    private List<IDataGridColumn> getSortableColumns() {
        List<IDataGridColumn> columns = dataGrid.getColumns();
        List<IDataGridColumn> sortableColumns = new ArrayList<>();

        for (IDataGridColumn column : columns) {
            if (column.getDataType() == DataType.GRAPH) continue;

            if (dataGrid.isSortable(column))
                sortableColumns.add(column);
        }
        return sortableColumns;
    }
}

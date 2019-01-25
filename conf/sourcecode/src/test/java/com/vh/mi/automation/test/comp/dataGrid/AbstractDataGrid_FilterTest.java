package com.vh.mi.automation.test.comp.dataGrid;

import com.google.common.collect.Table;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.IGridValue;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.INumericGridValue;
import com.vh.mi.automation.api.constants.DataType;
import com.vh.mi.automation.api.exceptions.NoFilterSetException;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.comp.dataGrid.gridValue.GridValueFactory;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import org.fest.assertions.Assertions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static com.vh.mi.automation.impl.comp.dataGrid.gridValue.NullGridValue.NULL_GRID_VALUE;
import static com.vh.mi.automation.test.comp.dataGrid.Operator.*;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by nimanandhar on 11/4/2014.
 */
public abstract class AbstractDataGrid_FilterTest extends AbstractDataGridTest {
    public static final List<Operator> OPERATORS_TO_TEST = Arrays.asList(LESS_THAN, LESS_OR_EQUALS, GREATER_THAN, GREATER_OR_EQUALS, EQUALS);


    /**
     * Get the list of columns on which the filtering test will be applied.
     * Specific instance of dataGrid must provide this list
     *
     * @return the list of columns on which the filtering test will be applied
     */
    public abstract List<IDataGridColumn> getColumnsToTest();


    @AfterMethod
    public void setUpAfterMethod(ITestResult testResult) throws Exception {
        try {
            dataGrid.doClearRecentFilteredValue();

        } catch (NoFilterSetException ex) {
            //will get an exception if no filter is set
        }
    }


    /**
     * Purpose: Tests that filtering works when applied on all columns
     * <p/>
     * This test will grab the first row of data and applies
     * the filter on all columns that have nonempty data in the
     * first row.
     * <p/>
     * This test verifies the following
     * <li> Applying the filter generates at least one row of data
     * <li> The generated data matches the applied filter
     */
    @Test
    public void filteringShouldWorkWhenFilterIsAppliedToVisibleAllColumns() {
        Map<IDataGridColumn, String> firstRow = dataGrid.getData().row(AbstractDataGrid.FIRST_ROW);

        Assert.assertFalse(dataGrid.getData().isEmpty(), "The dataGrid is empty. Cannot test filtering");
        Map<IDataGridColumn, String> filters = removeEmptyAndNonFilterableCols(firstRow);

        dataGrid.doFilter(filters);

        Table<Integer, IDataGridColumn, String> filteredTableData = dataGrid.getData();
        Assert.assertFalse(filteredTableData.isEmpty(), " The data grid should not be empty after filtering");

        for (Integer row : filteredTableData.rowKeySet()) {
            for (IDataGridColumn column : filters.keySet()) {
                String actualValue = GridValueFactory.create(filteredTableData.get(row, column), column.getDataType()).getFilterableValue();
                assertThat(actualValue)
                        .as("The value of Column " + column + " was " + actualValue + " but expected to be " + filters.get(column))
                        .isEqualTo(filters.get(column));
            }
        }
    }

    protected Map<IDataGridColumn, String> removeEmptyAndNonFilterableCols(Map<IDataGridColumn, String> firstRow) {
        Map<IDataGridColumn, String> newRow = new HashMap<>();

        for (IDataGridColumn col : firstRow.keySet()) {
            IGridValue gridValue = GridValueFactory.create(firstRow.get(col), col.getDataType());
            if (gridValue.isDataValid() && dataGrid.isFilterable(col)) {
                newRow.put(col, gridValue.getFilterableValue());
            }
        }
        return newRow;
    }

    /**
     * Tests that filtering works for text columns
     * <p/>
     * Applies the provided filterText on the column and verifies
     * that all the filtered data contains the filtered text.
     * The test column and the filter text to be used is  provided
     * by the dataProvider {@link #textColumnAndFilterValueProvider}
     * <p/>
     * This test method applies the filter and verifies the following
     * <li>The data after applying the filter is non empty
     * <li>All the data in the column contains the filterText
     *
     * @param column The text column to be tested
     * @param filter The filter text to be applied on the column
     */
    @Test(dataProvider = "textColumnAndFilterValueProvider")
    public void filteringShouldWorkForStringColumns(IDataGridColumn column, IGridValue filter) {
        if (filter.isNull())
            return;

        dataGrid.doFilter(column, filter.getFilterableValue());

        for (IGridValue gridValue : getData(column)) {
            assertThat(gridValue.getValue()).contains(filter.getFilterableValue());
        }
    }


    /**
     * Tests that filtering works for Numeric Columns ie columns which displays integers, dollar amounts etc
     * <p/>
     * The column to be tested, the operator and filterText are provided through the data provider,
     * {@link #numericColumnOperatorAndFilterValueProvider()}
     * <p/>
     * This test applies the operator and filterText to the test column and verifies the following for the
     * <b>the first page of the data grid</b>
     * <li> The result of the filtering is non empty
     * <li> All the data for the column satisfies the operator
     *
     * @param columnToTest a numeric column to be tested
     * @param operator     the operator to be applied on the column
     * @param filterValue  the filter text to be applied
     */
    @Test(dataProvider = "numericColumnOperatorAndFilterValueProvider")
    public void filteringShouldWorkForNumericColumns(IDataGridColumn columnToTest, Operator operator, IGridValue filterValue) {
        if (filterValue.isNull())
            return;

        dataGrid.doFilter(columnToTest, operator.getSymbol() + filterValue.getFilterableValue());
        List<IGridValue> filteredData = getData(columnToTest);
        Assertions.assertThat(filteredData).as("Data should be present after filtering").isNotEmpty();

        for (IGridValue filteredValue : filteredData) {
            switch (operator) {
                case LESS_THAN:
                    assertThat(filteredValue.compareTo(filterValue))
                            .as(filteredValue + " should be less than " + filterValue)
                            .isLessThan(0);
                    break;
                case LESS_OR_EQUALS:
                    assertThat(filteredValue.compareTo(filterValue))
                            .as(filteredValue + " should be less or equal to " + filterValue)
                            .isLessThanOrEqualTo(0);
                    break;
                case GREATER_THAN:
                    assertThat(filteredValue.compareTo(filterValue))
                            .as(filteredValue + " should be greater than " + filterValue)
                            .isGreaterThan(0);
                    break;
                case GREATER_OR_EQUALS:
                    assertThat(filteredValue.compareTo(filterValue))
                            .as(filteredValue + " should be greater than or equal to " + filterValue)
                            .isGreaterThanOrEqualTo(0);
                    break;
                case EQUALS:
                    assertThat(filteredValue.compareTo(filterValue))
                            .as(filteredValue + " should be equal to" + filterValue)
                            .isEqualTo(0);
                    break;
                default:
                    throw new RuntimeException("Unsupported operator " + operator);
            }
        }
    }

    @Test(dataProvider = "numericColumnBetweenOperatorValuesProvider")
    public void filteringShouldWorkForBetweenOperator(IDataGridColumn columnToTest, IGridValue lower, IGridValue upper) {
        if (lower.isNull() || upper.isNull()) {
            return; //invalid or missing filter values
        }

        String filterText = ">" + lower.getFilterableValue() + "<" + upper.getFilterableValue();
        dataGrid.doFilter(columnToTest, filterText);
        Assertions.assertThat(dataGrid.getData(columnToTest)).as("Data should be present after filtering").isNotEmpty();

        for (IGridValue filteredValue : getData(columnToTest)) {
            assertThat(filteredValue.compareTo(lower))
                    .as("The filteredValue " + filteredValue + " must be >= than " + lower)
                    .isGreaterThanOrEqualTo(0);
            assertThat(filteredValue.compareTo(upper))
                    .as("The filteredValue " + filteredValue + " must be <= " + upper)
                    .isLessThanOrEqualTo(0);
        }
    }

    /*************************************************************************************************************
     *                                          DATA PROVIDERS                                                   *
     * ***********************************************************************************************************/

    /**
     * Provides test data to test filtering of text column of dataGrid
     * <p/>
     * Tests that use this data provider should take two parameters in
     * the following order
     * <li> column:{@link com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn} the column to be tested
     * <li> filterText:String the filterText to be applied for the column
     * <p/>
     * This dataProvider ensures that the filterText provided will
     * always return some data
     *
     * @return A array of test data
     */
    @DataProvider(name = "textColumnAndFilterValueProvider")
    public Object[][] textColumnAndFilterValueProvider() {
        List<IDataGridColumn> textColumns = new ArrayList<>();
        List<IGridValue> filterValues = new ArrayList<>();

        for (IDataGridColumn column : getColumnsToTest()) {
            if (column.getDataType() != DataType.STRING || !dataGrid.isSortable(column))
                continue;
            textColumns.add(column);
            filterValues.add(pickFirstValidData(getData(column)));
        }
        return DataProviderUtils.getObjects(textColumns, filterValues);
    }

    /**
     * Provides test data to test filtering of numeric columns of dataGrid
     * The dataProvider ensures that the operator and filterText provided
     * will always return some data
     * <p/>
     * Tests that use this data provider should take three parameters in
     * the following order
     * <li> column : {@link com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn} a numeric column to be tested</li>
     * <li> operator : String , a operator to be applied , eg <, <= etc</li>
     * <li> filterText : String, a operand for the operator</li>
     *
     * @return an array of test data
     */
    @DataProvider(name = "numericColumnOperatorAndFilterValueProvider")
    public Object[][] numericColumnOperatorAndFilterValueProvider() {
        List<IDataGridColumn> columnsToTest = new ArrayList<>();
        List<Operator> columnOperator = new ArrayList<>();
        List<IGridValue> filterValues = new ArrayList<>();

        for (IDataGridColumn column : getColumnsToTest()) {
            if (column == null || !dataGrid.isFilterable(column) || column.getDataType() == DataType.STRING) {
                continue;
            }

            IGridValue pickedFilterValue = pickFirstValidData(getData(column));
            for (Operator operator : OPERATORS_TO_TEST) {
                columnsToTest.add(column);
                columnOperator.add(operator);
                IGridValue actualFilterValue = getFilterValue(operator, pickedFilterValue); // get filter value so that the data is never empty
                filterValues.add(actualFilterValue);
            }
        }
        return DataProviderUtils.getObjects(columnsToTest, columnOperator, filterValues);
    }

    private IGridValue pickFirstValidData(List<IGridValue> data) {
        for (IGridValue value : data) {
            if (value.isDataValid()) {
                return value;
            }
        }
        return NULL_GRID_VALUE;
    }

    private IGridValue getFilterValue(Operator operator, IGridValue value) {
        if (value instanceof INumericGridValue) {
            INumericGridValue numericGridValue = (INumericGridValue) value;
            switch (operator) {
                case LESS_THAN:
                    return numericGridValue.plusOne();
                case GREATER_THAN:
                    return numericGridValue.minusOne();
                default:
                    return numericGridValue;
            }
        } else {
            throw new RuntimeException("Expected " + value + " to be of type INumericGridValue but was " + value.getClass());
        }
    }


    @DataProvider(name = "numericColumnBetweenOperatorValuesProvider")
    public Object[][] numericColumnBetweenOperatorValuesProvider() {
        List<IDataGridColumn> columnsToTest = new ArrayList<>();
        List<IGridValue> filterValuesLower = new ArrayList<>();
        List<IGridValue> filterValuesUpper = new ArrayList<>();

        for (IDataGridColumn column : getColumnsToTest()) {
            if (column == null || !dataGrid.isFilterable(column) || column.getDataType() == DataType.STRING) {
                continue;
            }

            INumericGridValue pickedFilterValue = (INumericGridValue) pickFirstValidData(getData(column));
            columnsToTest.add(column);
            filterValuesLower.add(pickedFilterValue.minusOne());
            filterValuesUpper.add(pickedFilterValue.plusOne());
        }
        return DataProviderUtils.getObjects(columnsToTest, filterValuesLower, filterValuesUpper);
    }

}

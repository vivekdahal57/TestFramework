package com.vh.mi.automation.api.comp.dataGrid;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;
import com.vh.mi.automation.api.constants.SortOrder;
import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.api.features.IHavePaginationComponent;
import com.vh.mi.automation.api.pages.common.IDrillPage;

import java.util.List;
import java.util.Map;

/**
 * Created by nimanandhar on 8/22/2014.
 */
public interface IDataGrid extends IAmWebComponent {

    public String getTableDataAsText();

    /**
     * get the data for a particular IColumn
     *
     * @param column
     * @return
     */
    List<String> getData(IDataGridColumn column);

    /**
     * select nth checkBox in datagrid
     *
     * @param topN
     */

    public void selectTopNCheckBoxInDataGrid(int topN);

    /**
     * get nth data for a particular IColumn
     *
     * @param column
     * @return
     */
    List<String> getNthData(IDataGridColumn column,int topN);


    /**
     * Filter the data grid on multiple IColumns
     *
     * @param filterValues a map of IColumns on which to filter and the corresponding filter value
     */
    void doFilter(Map<IDataGridColumn, String> filterValues);


    /**
     * Filter on a single IColumn
     *
     * @param column
     * @param filterValue
     */
    void doFilter(IDataGridColumn column, String filterValue);

    /**
     * sort the dataGrid on the specified IColumn
     * throws RuntimeException if the IColumn in not sortable
     *
     * @param column
     * @param sortOrder
     */
    void doSort(IDataGridColumn column, SortOrder sortOrder);


    /**
     * Reset all the filters applied on the dataGrid
     */
    public void doResetFilter();


    public void doFlushAllCache();


    public void doClearRecentFilteredValue();


    boolean isSortable(IDataGridColumn column);


    boolean isFilterable(IDataGridColumn column);

    /**
     * Query for columns currently visible in the data grid
     *
     * @return a collection of columns currently visible
     */
    List<IDataGridColumn> getColumns();

    /**
     * Query for the column in the dataGrid that is currently sorted
     *
     * @return the name of the column that is currently sorted
     */
    IDataGridColumn getCurrentSortedColumn();

    /**
     * Returns the index( starting at 1) of the column in the data grid
     *
     * @param column the column for which we want to query the index position in the grid
     * @return an index (starting at 1 for first column) of the column in the dataGrid
     * @throws com.vh.mi.automation.api.exceptions.AutomationException if the column is not displayed in DataGrid
     */
    Integer getColumnIndex(IDataGridColumn column);

    /**
     * Query for the sort order for the specified column
     *
     * @return the current Sort Order
     */
    SortOrder getCurrentSortOrder();

    /**
     * Query for all the data as currently visible in the dataGrid
     *
     * @return table representing the complete data Grid
     */
    Table<Integer, IDataGridColumn, String> getData();


    ImmutableList<IDataGridRow> getRows();

     boolean checkUnknownBlanksForColumn(IDataGridColumn  column);
     boolean checkUnknownBlanksForDefault();

    IDrillPage applyDrill(String drillOption);
}

package com.vh.mi.automation.api.comp.dataGrid.gridValue;

/**
 * Represents a cell value of a DataGrid. The actual instance
 * of IGridValue depends upon the data type of the column.
 * All instance of IGridValue are Immutable.
 * Created by nimanandhar on 11/19/2014.
 */
public interface IGridValue extends Comparable<IGridValue> {

    /**
     * @return the actual String value without any transformation
     */
    public String getValue();


    /**
     * Get the value in a format that can be used in the filter box
     * for eg negative values in the form (23) must be changed to -23
     *
     * @return An equivalent value that can be filtered
     */
    public String getFilterableValue();

    /**
     * Used to determine if the actual data represents a valid data for
     * the given data type, eg a - or N/A for numeric data is not valid
     * Only valid data are selected for filtering
     *
     * @return true if data is valid, false if the data is invalid or empty
     */
    public boolean isDataValid();


    /**
     * Based on NULL object pattern, all instance except those of NullGridValue
     * should return false
     *
     * @return true if the object is an instance of NullGridValue
     */
    public boolean isNull();


}

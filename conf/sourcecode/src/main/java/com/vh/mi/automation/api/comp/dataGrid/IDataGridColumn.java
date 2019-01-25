package com.vh.mi.automation.api.comp.dataGrid;

import com.vh.mi.automation.api.constants.DataType;

/**
 * Created by nimanandhar on 9/9/2014.
 */
public interface IDataGridColumn {

    public String getId();

    public DataType getDataType();

    public String getLabel();
}

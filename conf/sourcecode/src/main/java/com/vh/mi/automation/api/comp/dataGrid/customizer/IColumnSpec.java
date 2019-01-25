package com.vh.mi.automation.api.comp.dataGrid.customizer;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;

/**
 * Created by i80448 on 11/7/2014.
 */
public interface IColumnSpec {
    public IDataGridColumn getColumnByLabel(String colName);
}

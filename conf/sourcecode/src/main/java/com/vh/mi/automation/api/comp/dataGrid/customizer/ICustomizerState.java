package com.vh.mi.automation.api.comp.dataGrid.customizer;

import com.google.common.collect.ImmutableCollection;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;

/**
 * Created by i80448 on 11/6/2014.
 */
public interface ICustomizerState {

    /**
     * All available columns.
     *
     * @return
     */
    public ImmutableCollection<IDataGridColumn> getAvailaleOptions();

    /**
     * Currently selected columns.
     *
     * @return
     */
    public ImmutableCollection<IDataGridColumn> getSelectedOptions();

}

package com.vh.mi.automation.api.comp.dataGrid.customizer;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.features.IAmWebComponent;

/**
 * Created by i80448 on 11/14/2014.
 */

public interface IDxcgDataGridCustomizer
        extends
        IDxcgDataGridCustomizerState,
        IDataGridCustomizerEvents,
        IAmWebComponent
       {

    /**
     * Select a column in given section.
     *
     * @param section section name
     * @param column
     */
    public void doSelect(String section, IDataGridColumn column);

    /**
     * Select all options available in given section.
     *
     * @param section
     */
    public void doSelectAll(String section);

    /**
     * Un-select all options available in given section.
     *
     * @param section
     */
    public void doUnselectAll(String section);


    public void doSelectAll();
}

package com.vh.mi.automation.api.comp.dataGrid.customizer;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;

/**
 * Created by i80448 on 11/14/2014.
 */
public interface ICustomizer extends ICustomizerState {
    /**
     * Checks if given {@link com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn} is in selected status.
     *
     * @param col
     * @return
     */
    public boolean isSelected(IDataGridColumn col);


    /**
     * Selects given {@link com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn} if in un-selected status.
     *
     * @param col
     */
    public void doSelect(IDataGridColumn col);

    public void doSelectAll();

    public void doUnselectAll();

    /**
     * Un-selects given {@link com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn} if in selected status.
     *
     * @param col
     */
    public void doUnselect(IDataGridColumn col);
}

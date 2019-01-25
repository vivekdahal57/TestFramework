package com.vh.mi.automation.api.comp.dataGrid.customizer;

/**
 * Created by i80448 on 11/18/2014.
 */
public interface IDataGridCustomizerEvents {
    /**
     * Applies current selections.
     * <p><b><i>NOTE: Causes the component to transition into <u>DEAD</u> state</i></b></p>
     *
     * @return {@link com.vh.mi.automation.api.comp.dataGrid.IDataGrid}
     */
    public void doApplySelection();

    /**
     * Applies and saves the current selections.
     * <p><b><i>NOTE: Causes the component to transition into <u>DEAD</u> state</i></b></p>
     *
     * @return {@link com.vh.mi.automation.api.comp.dataGrid.IDataGrid}
     */
    public void doSaveAndApplySelection();

    /**
     * Reverts current selections into default state.
     * <p><b><i>NOTE: Causes the component to transition into <u>DEAD</u> state</i></b></p>
     */
    public void doResetSelections();

    /**
     * Ignores any selections and close the component.
     * <p><b><i>NOTE: Causes the component to transition into <u>DEAD</u> state</i></b></p>
     */
    public void doCancelAndClose();
}

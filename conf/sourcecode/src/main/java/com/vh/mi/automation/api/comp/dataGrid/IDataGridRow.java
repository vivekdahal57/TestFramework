package com.vh.mi.automation.api.comp.dataGrid;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.dataGrid.drill.IDataGridDrillMenu;
import com.vh.mi.automation.api.pages.common.IDrillPage;

/**
 * Interface to represent a row in data grid table.
 */
public interface IDataGridRow {

    /**
     * Gets values of all columns in displayed order.
     *
     * @return
     */
    public ImmutableList<String> getValues();

    /**
     * Gets value of a column at given index position.
     *
     * @param colPosition
     * @return
     */
    public String getValue(int colPosition);


    /**
     * Some rows have drill option while others do not
     *
     * @return true if this row has drill options, false otherwise
     */
    public boolean hasDrillOptions();

    /**
     * Gets drill by options available for this row.<br/>
     * <b>NOTE: before calling this, make sure that page has drill by feature, otherwise it may have random behaviors.</b>
     *
     * @return
     */
    public ImmutableList<String> getDrillOptions();


    public ImmutableList<String> getDrillOptionsBetween(String from, String to);

    public ImmutableList<String> getDrillOptionsAfter(String from);

    /**
     * Displays a drill by menu panel for this row. <br/>
     * <b>NOTE: before calling this, make sure that page has drill by feature, otherwise it may have random behaviors.</b>
     *
     * @return
     */
    public IDataGridDrillMenu doShowDrillMenu();

    /**
     * Performs drill by action for given option if available in the menu.<br/>
     * <b>NOTE: before calling this, make sure that page has drill by feature, otherwise it may have random behaviors.</b>
     *
     * @param option
     * @return
     */
    public IDrillPage doDrillBy(String option);

    public IDrillPage doDrillByOnSameWindow(String option);
    public void doDrillByPopup(String option);

}

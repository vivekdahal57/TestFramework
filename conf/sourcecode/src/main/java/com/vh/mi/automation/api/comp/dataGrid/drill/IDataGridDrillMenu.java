package com.vh.mi.automation.api.comp.dataGrid.drill;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.annotations.documentation.Immutable;

/**
 * Interface to represent drill by menu component in data grid row.
 */
public interface IDataGridDrillMenu {

    /**
     * Gets drill by options.
     *
     * @return
     */
    public ImmutableList<String> getDrillOptions();

    public ImmutableList<String> getDrillOptionsBetween(String from, String to);

    public ImmutableList<String> getDrillOptionAfter(String from);

    /**
     * Performs drill by action for given option if available in menu.
     *
     * @param option
     */
    public void doDrillBy(String option);

}

package com.vh.mi.automation.api.comp.dataGrid.customizer;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01DataGridColumn;

/**
 Created by i82298 on 2/27/2017. */
public interface ICPM01DataGridCustomizer extends IDataGridCustomizerEvents {



    /**
     * Checks if given {@link com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01DataGridColumn} is in selected status.
     *
     * @param col
     * @return
     */
    public boolean isSelected(ICPM01DataGridColumn col);


    /**
     * Selects given {@link com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01DataGridColumn} if in un-selected status.
     *
     * @param col
     */
    public void doSelect(ICPM01DataGridColumn col);

    public void doSelectAll();

    void doSelectAllForAllCategory();

    public void doUnselectAll();

    /**
     * Un-selects given {@link com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01DataGridColumn} if in selected status.
     *
     * @param col
     */
    public void doUnselect(ICPM01DataGridColumn col);

    public void doSelectMainCatagory(ICPM01DataGridColumn mainCatagoryColumn);

    public void doSelectSubCatagory(ICPM01DataGridColumn mainCatagoryColumn);




}

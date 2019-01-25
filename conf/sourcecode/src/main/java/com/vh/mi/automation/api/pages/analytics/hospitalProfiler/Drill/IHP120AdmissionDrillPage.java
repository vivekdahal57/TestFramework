package com.vh.mi.automation.api.pages.analytics.hospitalProfiler.Drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.features.IHavePaginationComponent;
import com.vh.mi.automation.api.pages.common.IDrillPage;

/**
 * Created by i10359 on 11/13/17.
 */
public interface IHP120AdmissionDrillPage extends   IDrillPage,IHavePaginationComponent{
    public IDataGrid getDataGrid();
    public boolean isDataGridCustomizable();
    public IDataGridCustomizer getDataGridCustomizer();
}

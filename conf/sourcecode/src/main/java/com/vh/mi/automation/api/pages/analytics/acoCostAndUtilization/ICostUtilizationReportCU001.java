package com.vh.mi.automation.api.pages.analytics.acoCostAndUtilization;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;

/**
 * Created by i80448 on 11/20/2014.
 */
public interface ICostUtilizationReportCU001 extends  IAmLandingPage, IHaveBusinessLevel, IHaveAnalysisPeriod {
    public IDataGrid getDataGrid();

    public boolean isDataGridCustomizable();

    public IDataGridCustomizer getDataGridCustomizer();
}

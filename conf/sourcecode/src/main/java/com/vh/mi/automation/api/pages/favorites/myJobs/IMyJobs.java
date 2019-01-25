package com.vh.mi.automation.api.pages.favorites.myJobs;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;
import com.vh.mi.automation.api.features.IHaveReportingBy;

/**
 * @author msedhain on 02/16/2017.
 */
public interface IMyJobs extends IAmLandingPage {
    public IDataGrid getDataGrid();

    public boolean isDataGridCustomizable();

    public IDataGridCustomizer getDataGridCustomizer();

    public void doJobFilter(String jobName);

}

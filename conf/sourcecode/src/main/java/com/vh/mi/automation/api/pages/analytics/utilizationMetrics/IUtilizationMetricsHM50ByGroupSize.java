package com.vh.mi.automation.api.pages.analytics.utilizationMetrics;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;

import java.io.IOException;

/**
 * Created by i80448 on 11/20/2014.
 */
public interface IUtilizationMetricsHM50ByGroupSize extends IAmLandingPage, IHaveAnalysisPeriod {
    IDataGrid getDataGrid();

    boolean isDataGridCustomizable();

    IDataGridCustomizer getDataGridCustomizer();

    IBusinessLevelsComponent getBusinessLevel();

    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException;


}
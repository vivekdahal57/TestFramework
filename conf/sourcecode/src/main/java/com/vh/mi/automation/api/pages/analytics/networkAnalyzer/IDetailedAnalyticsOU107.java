package com.vh.mi.automation.api.pages.analytics.networkAnalyzer;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;

import java.io.IOException;

/**
 * Created by i80448 on 11/20/2014.
 */
public interface IDetailedAnalyticsOU107 extends  IAmLandingPage, IHaveBusinessLevel, IHaveAnalysisPeriod {
    public IDataGrid getDataGrid();

    public boolean isDataGridCustomizable();

    public IDataGridCustomizer getDataGridCustomizer();

    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException;

}


package com.vh.mi.automation.api.pages.analytics.demography;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.*;
import com.vh.mi.automation.api.pages.common.IDrillPage;

import java.io.IOException;

/**
 * Created by nimanandhar on 9/10/2014.
 */
public interface IDemographyD05 extends IAmLandingPage, IHaveBusinessLevel, IHaveAnalysisPeriod, IHavePaginationComponent,
        IHaveReportingBy {
    IDataGrid getDataGrid();

    public boolean isDataGridCustomizable();

    public IDataGridCustomizer getDataGridCustomizer();

    public IDrillPage drillDownToPage(String page) ;

    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException;
}

package com.vh.mi.automation.api.pages.analytics.conversionAnalyzer;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;
import com.vh.mi.automation.api.features.IHavePaginationComponent;

import java.io.IOException;

/**
 * Created by i80448 on 11/20/2014.
 */
public interface IConversionAnalyzerA17 extends IAmLandingPage, IHaveBusinessLevel, IHaveAnalysisPeriod,IHavePaginationComponent {

    public IDataGrid getDataGrid();

    public boolean isDataGridCustomizable();

    public boolean isExcelOffline();

    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context)throws IOException;

}

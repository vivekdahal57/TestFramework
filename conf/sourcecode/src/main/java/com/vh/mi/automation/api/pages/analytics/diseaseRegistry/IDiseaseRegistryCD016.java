package com.vh.mi.automation.api.pages.analytics.diseaseRegistry;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveAdjustedNorm;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;
import com.vh.mi.automation.api.pages.common.IDrillPage;

import java.io.IOException;

/**
 * Created by i80448 on 9/2/2014.
 */
public interface IDiseaseRegistryCD016 extends IAmLandingPage, IHaveAnalysisPeriod, IHaveBusinessLevel, IHaveAdjustedNorm {
    IDataGrid getDataGrid();
    IDrillPage drillDownToPage(String page);
    IDrillPage drillDownToDifferentWindow(String page);
    public boolean isDataGridCustomizable();
    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context)throws IOException;
    public IDataGridCustomizer getDataGridCustomizer();


}

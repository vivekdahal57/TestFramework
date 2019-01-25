package com.vh.mi.automation.api.pages.analytics.providerManagement.PhysicianManager;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;

import java.io.IOException;

public interface ICP110 extends IAmLandingPage, IHaveBusinessLevel, IHaveAnalysisPeriod {
    public IDataGrid getDataGrid();
    boolean isDataGridCustomizable();
    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException;
}

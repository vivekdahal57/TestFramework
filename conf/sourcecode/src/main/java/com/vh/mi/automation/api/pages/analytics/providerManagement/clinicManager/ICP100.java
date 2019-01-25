package com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;
import com.vh.mi.automation.api.pages.common.IDrillPage;

import java.io.IOException;

/**
 * @author nimanandhar
 */
public interface ICP100 extends IAmLandingPage , IHaveBusinessLevel {
    public IDataGrid getDataGrid();
    public IDrillPage drillDownToPage(String page);
    public boolean isDataGridCustomizable();
    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException;

}

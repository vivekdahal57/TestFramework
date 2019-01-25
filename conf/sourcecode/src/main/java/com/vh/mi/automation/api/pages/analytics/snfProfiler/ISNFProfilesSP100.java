package com.vh.mi.automation.api.pages.analytics.snfProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;
import com.vh.mi.automation.api.features.IHavePaginationComponent;
import com.vh.mi.automation.api.pages.common.IDrillPage;

import java.io.IOException;

/**
 * Created by i10359 on 11/16/17.
 */
public interface ISNFProfilesSP100 extends IAmLandingPage, IHaveBusinessLevel, IHaveAnalysisPeriod,IHavePaginationComponent {
    public IDataGrid getDataGrid();
    public IDrillPage drillDownToPage(String page);
    boolean isDataGridCustomizable();
    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException;
}

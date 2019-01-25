package com.vh.mi.automation.api.pages.analytics.expenseDistribution;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;
import com.vh.mi.automation.api.features.IHavePaginationComponent;

import java.io.IOException;

/**
 * @author i80448
 */
public interface IExpenseDistribution extends IAmLandingPage,
        IHaveAnalysisPeriod, IHaveBusinessLevel, IHavePaginationComponent {

    IDataGrid getDataGrid();
    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException;

}

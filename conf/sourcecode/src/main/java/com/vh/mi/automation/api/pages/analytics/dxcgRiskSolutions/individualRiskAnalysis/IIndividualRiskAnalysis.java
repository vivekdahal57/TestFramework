package com.vh.mi.automation.api.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;
import com.vh.mi.automation.api.pages.common.IDrillPage;

import java.io.IOException;

/**
 * Created by nimanandhar on 12/30/2014.
 */
public interface IIndividualRiskAnalysis extends IHaveBusinessLevel,IAmLandingPage {
    IDataGrid getDataGrid();
    IDrillPage drillDownToPage(String page);
    boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context)throws IOException;
    String getRecordsCountInPopup();
    public void closePopUp();
    void waitTillPopupTableAppears();
}

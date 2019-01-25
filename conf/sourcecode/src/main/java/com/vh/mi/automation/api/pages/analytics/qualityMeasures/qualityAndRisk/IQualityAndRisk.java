package com.vh.mi.automation.api.pages.analytics.qualityMeasures.qualityAndRisk;

import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.comp.qrm.IFavoriteQRM;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;
import com.vh.mi.automation.api.features.IHaveDataGrid;
import com.vh.mi.automation.api.pages.common.IDrillPage;

import java.io.IOException;

/**
 * Created by nimanandhar on 12/30/2014.
 */
public interface IQualityAndRisk extends IAmLandingPage, IHaveBusinessLevel, IHaveDataGrid {
    IFavoriteQRM getFavoriteQRM();
    boolean isDataGridCustomizable();
    IDrillPage drillDownToPage(String page);
    IDataGridCustomizer getDataGridCustomizer();
    public void createListWithSelectedMember(int topN,String memListName);
    public String getMemberListSuccessfullyCreatedStatus();
    public boolean sendToExcelandValidate(String excelFileName, ExecutionContext context) throws IOException;
}

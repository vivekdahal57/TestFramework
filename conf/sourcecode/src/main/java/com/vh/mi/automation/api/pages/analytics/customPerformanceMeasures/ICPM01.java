package com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.ICPM01DataGridCustomizer;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by i82298 on 1/16/2017.
 */
public interface ICPM01 extends IAmLandingPage,IHaveBusinessLevel,IHaveAnalysisPeriod,IHavePaginationComponent,IHaveReportingBy,IHaveFavoriteQualityMeasures {

    public boolean isDataGridCustomizable();

    public ICPM01DataGridCustomizer getDataGridCustomizer();

    public void selectQNRCriterias(List<String> criterias);
    public void  selectHEDISCriterias(List<String> criterias);
    public String saveCreatedList(String listName);
    public boolean checkIfTheseCriteriasAreAppliedSuccessfully(List<String> criterias);
    public IDataGrid getDataGrid();
    public List<String> getNormSelectionList();
    public void hoverAndClickNormSelection(String drillOption);
    public void doWaitTillPopUpDisappears();
    public void saveNormSelection(String viewName);
    public void savedNormValidation(String drillOption, String savedName);
    public boolean  sendToExcelAndValidate(String excelFileName, ExecutionContext context)throws IOException;



}

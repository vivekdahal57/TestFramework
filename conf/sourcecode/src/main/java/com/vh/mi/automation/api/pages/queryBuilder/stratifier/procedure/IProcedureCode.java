package com.vh.mi.automation.api.pages.queryBuilder.stratifier.procedure;

import com.vh.mi.automation.api.pages.common.IQuerybuilderPage;

/**
 * Created by i20345 on 2/1/2017.
 */
public interface IProcedureCode extends IQuerybuilderPage{
    public void clickAndOrButton();
    public void includeTopNProcedureCode(int topN);
    public void excludeTopNProcedureCode(int topN);
    public void updateQuickCount();
    public String getQuickCountValue();
    public void includeAll();
    public void clickRefreshButton();
    public void includeAllOnPage();
    public void excludeAllOnPage();
    public void removeAll();
    public String getSelectionLimitText();
    public void filterDiagnosisCodeWith(String diagCode);

}
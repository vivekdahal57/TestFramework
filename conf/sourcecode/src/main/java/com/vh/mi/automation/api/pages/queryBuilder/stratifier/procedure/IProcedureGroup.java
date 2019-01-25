package com.vh.mi.automation.api.pages.queryBuilder.stratifier.procedure;

import com.vh.mi.automation.api.pages.common.IQuerybuilderPage;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic;

/**
 * Created by i20345 on 2/1/2017.
 */
public interface IProcedureGroup extends IQuerybuilderPage{
    public void includeTopNProcedureCode(int topN);
    public void excludeTopNProcedureCode(int topN);
    public String getQuickCountValue();
    public RefineLogic goToRefineLogicPage();
    public void ExcludeAll();
    public String getTotalRecordsFromRemainingTable();
    public void clickRefreshButton();
    public  String getTotalRecordsFromUpperTable();
    public void includeAll();
    public void excludeAllOnPage();
    public void includeAllOnPage();
    public void removeAll();
    public String checkUpperTableIsEmpty();
}
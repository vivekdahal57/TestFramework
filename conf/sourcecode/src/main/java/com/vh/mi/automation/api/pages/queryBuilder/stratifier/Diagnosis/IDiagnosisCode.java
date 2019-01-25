package com.vh.mi.automation.api.pages.queryBuilder.stratifier.Diagnosis;

import com.vh.mi.automation.api.pages.common.IQuerybuilderPage;

/**
 * Created by i20345 on 1/6/2017.
 */
public interface IDiagnosisCode extends IQuerybuilderPage{

    public void includeAllOnPage();
    public String getTotalCountOnPageRemainingTable();
    public String getTotalCountOnPageSelectedTable();
    public void clickRefreshButton();
    public void excludeAllOnPage();
    public void applyIPOPToAll(String option);
    public void removeAll();
    public String checkSelectedTableIsEmpty();
    public void filterDiagnosisCodeWith(String diagCode);
    public String getIPOPCountInSelectedTable(String iopoOPtion);
    public void applyIPOPYesToAll();
    public String getTotalCountOnPageRemainingTableAfterFiltering();

}

package com.vh.mi.automation.api.pages.queryBuilder.stratifier.Diagnosis;

import com.vh.mi.automation.api.pages.common.IQuerybuilderPage;

/**
 * Created by i20345 on 1/6/2017.
 */
public interface IDiagnosisGroup extends IQuerybuilderPage {

    public void IncludeAll();
    public String getTotalRecordsInRemainingTable();
    public void clickRefreshButton();
    public  String getTotalRecordsInSelectedTable();
    public void ExcludeAll();
    public void selectTopNCheckBoxes(Integer topN);
    public void reset();
}

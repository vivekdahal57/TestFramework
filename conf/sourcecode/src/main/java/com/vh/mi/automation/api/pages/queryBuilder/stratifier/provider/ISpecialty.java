package com.vh.mi.automation.api.pages.queryBuilder.stratifier.provider;

import com.vh.mi.automation.api.pages.common.IQuerybuilderPage;

/**
 * Created by i20345 on 1/23/2017.
 */
public interface ISpecialty extends IQuerybuilderPage{
    public  void selectSpecialtyTab();
    public void IncludeAll();
    public String getTotalRecordsInRemainingTable();
    public void clicRefreshButton();
    public  String getTotalRecordsInSelectedTable();
    public void ExcludeAll();
    public void IncludeTopNInFirstPage(Integer selectionNumber);
    public void ExcludeTopNInFirstPage(Integer selectionNumber);
    public void RemoveAllExclude();
    public  void  IncludeAllOnPage();
    public String getTotalONPageCountInRemainingTable();
    public  void RemoveAllSelectedInclude();
    public  void RemoveAllSelectedExclude();
    public void reset();
}

package com.vh.mi.automation.api.pages.queryBuilder.stratifier;

import com.vh.mi.automation.impl.pages.queryBuilder.Individual301;

/**
 * Created by i20345 on 2/8/2017.
 */
public interface IRefineLogic {
    public  void saveCohort(String cohortName);
    public Individual301 goToIndividualPage();
    public String getMemberCount();
    public void saveStaticCohort(String cohortName);
    public String getReportGenerationSuccessfulMessage();
   // public int gen();

}

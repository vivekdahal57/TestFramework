package com.vh.mi.automation.api.pages.queryBuilder.stratifier;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.CohortDetailMoreMetrics.CohortDetailAddMoreMetrics;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PopulationAnalyser;

import java.io.IOException;

/**
 * Created by i20345 on 2/15/2017.
 */
public interface ICohortDetails {
    public String getMemberCount();
    public PopulationAnalyser goBackToPopulationAnalyser();
    public CohortDetailAddMoreMetrics addMoreMetrics();
    public boolean checkWhetherQRMWithTheseIDsAreAdded(String ids);
    public void downLoadWordFile();
    public void downLoadExcelFile();
    public boolean downloadWordAndValidate(String downloadWordName,  ExecutionContext context) throws IOException;
}

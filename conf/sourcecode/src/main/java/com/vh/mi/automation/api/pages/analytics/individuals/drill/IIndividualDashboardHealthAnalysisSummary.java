package com.vh.mi.automation.api.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.pages.analytics.individuals.IIndividualDashboard;

/**
 * Created by i10314 on 8/2/2017.
 */
public interface IIndividualDashboardHealthAnalysisSummary extends IIndividualDashboard {
    public void updateData(String fieldName, String updateText);
    public String getUpdatedDataSavedSuccessfullyText();
    public boolean updatedDataSeenInHASPage(String fieldName, String updateText);
    public String checkLastSavedByUser();
    public void closeHASPageAndGoBackToIndividualPage();
}

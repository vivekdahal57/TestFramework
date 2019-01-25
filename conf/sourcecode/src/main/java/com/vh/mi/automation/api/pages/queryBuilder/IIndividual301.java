package com.vh.mi.automation.api.pages.queryBuilder;

import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardOutreachHistory;

/**
 * Created by i20345 on 2/10/2017.
 */
public interface IIndividual301 {
    public String getIndividualCount();
    public void closeCurrentWindowAndSwitchToMainWindow();
    public IndividualDashboardOutreachHistory goToOutreachHistoryPage();
}

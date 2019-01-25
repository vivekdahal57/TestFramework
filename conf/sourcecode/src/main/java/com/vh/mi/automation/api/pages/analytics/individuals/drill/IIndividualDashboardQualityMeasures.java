package com.vh.mi.automation.api.pages.analytics.individuals.drill;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndividualDashboard;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardQualityMeasures;

/**
 * @author nimanandhar
 */
public interface IIndividualDashboardQualityMeasures extends IIndividualDashboard {
    void waitTillLoadingDisappears();
    boolean isStatusDetailsShown(IndividualDashboardQualityMeasures.Component component, String option);
    boolean isGroupByColumnShown(IndividualDashboardQualityMeasures.Component component, String option);
}

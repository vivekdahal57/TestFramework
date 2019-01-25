package com.vh.mi.automation.api.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndividualDashboard;

/**
 * @author nimanandhar
 */
public interface IIndividualDashboardIndividualClaimDetails extends IIndividualDashboard {

    public boolean isDataGridCustomizable();
    public IDataGridCustomizer getDataGridCustomizer();
    void waitTillLoadingDisappears();
}


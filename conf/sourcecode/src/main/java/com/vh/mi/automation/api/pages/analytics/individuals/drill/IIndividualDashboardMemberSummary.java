package com.vh.mi.automation.api.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndividualDashboard;

import java.io.IOException;

/**
 * @author nimanandhar
 */
public interface IIndividualDashboardMemberSummary extends IIndividualDashboard {
    public String getMemberId();
    public boolean absenceOfNavigationPannel();
    public boolean loginSessionLimitedToSpecifiedMemberOnly();
    void waitTillLoadingDisappears();
    boolean downLoadFileAndValidate(String rtfFileName, ExecutionContext context) throws IOException;
    public void goToIndividualClaimDetails();


}

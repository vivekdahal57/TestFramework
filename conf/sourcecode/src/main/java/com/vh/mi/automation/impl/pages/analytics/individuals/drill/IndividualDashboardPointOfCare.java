package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardMemberDetailReport;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import org.openqa.selenium.WebDriver;

/**
 * @author nimanandhar
 */
public class IndividualDashboardPointOfCare extends AbstractIndividualDashboard implements IIndividualDashboardMemberDetailReport {
    public IndividualDashboardPointOfCare(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPageTitle() {
        return "Individual Dashboard: (175) Point of Care Summary";
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();
    }
}

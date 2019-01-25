package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardLabResults;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import org.openqa.selenium.WebDriver;

/**
 * @author nimanandhar
 */
public class IndividualDashboardLabResults extends AbstractIndividualDashboard implements IIndividualDashboardLabResults {

    public IndividualDashboardLabResults(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPageTitle() {
        return "Individual Dashboard: Lab Results";
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();

    }
}

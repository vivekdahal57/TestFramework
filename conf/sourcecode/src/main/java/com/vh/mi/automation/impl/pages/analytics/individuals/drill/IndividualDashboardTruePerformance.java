package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardTruePerformance;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import org.openqa.selenium.WebDriver;

/**
 * @author nimanandhar
 */
public class IndividualDashboardTruePerformance extends AbstractIndividualDashboard implements IIndividualDashboardTruePerformance {
    public IndividualDashboardTruePerformance(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPageTitle() {
        return "Individual Dashboard: (IndvTruePerformance) True Performance Measure";
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();
    }
}

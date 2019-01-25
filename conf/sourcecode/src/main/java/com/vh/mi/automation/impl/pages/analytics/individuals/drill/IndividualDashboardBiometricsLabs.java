package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardBiometricsLabs;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import org.openqa.selenium.WebDriver;

/**
 * @author nimanandhar
 */
public class IndividualDashboardBiometricsLabs extends AbstractIndividualDashboard implements IIndividualDashboardBiometricsLabs {
    public IndividualDashboardBiometricsLabs(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPageTitle() {
        return "Individual Dashboard: (BL001) Biometrics/Labs";
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();
    }
}

package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashBoardHRA;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import org.openqa.selenium.WebDriver;

public class IndividualDashBoardHRA extends AbstractIndividualDashboard  implements IIndividualDashBoardHRA{

    public IndividualDashBoardHRA(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPageTitle() {
        return "Individual Dashboard: (HRA1) Health Risk Assessment Data";
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();
    }

}

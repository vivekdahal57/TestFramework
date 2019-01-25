package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardClinicalEventChart;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nimanandhar
 */
public class IndividualDashboardClinicalEventChart extends AbstractIndividualDashboard implements IIndividualDashboardClinicalEventChart {

    public IndividualDashboardClinicalEventChart(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPageTitle() {
        return "Individual Dashboard: (350) Clinical Event Chart";
    }

    @Override
    public String getPageId() {
        throw new AutomationException("not implemented");
    }

    @Override
    public boolean isDrillPageValid() {
        logger.info("Drill Page is in valid state if 'Selected Cycle' and 'Full Cycle' is present in the section");
        logger.info("Checking if sections of 'Clinical Event Chart' exists or not");
        List<String> sectionIds = getChartSectionIds();
        for(String id: sectionIds) {
            if (SeleniumUtils.findElementsById(getDriver(),id).size() != 1 ) {
                logger.info("Section '" + id + "' Does Not Exists In The Page.Thus Page is not valid");
                return false;
            }
        }
        return true;
    }

    private List<String> getChartSectionIds() {
        List<String> sectionIds= new ArrayList<>();
        sectionIds.add("selectedCycleDesc");
        sectionIds.add("fullCycle");
        return sectionIds;
    }
}

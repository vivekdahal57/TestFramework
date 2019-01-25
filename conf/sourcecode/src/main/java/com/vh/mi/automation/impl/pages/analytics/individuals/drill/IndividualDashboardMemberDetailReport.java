package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardMemberDetailReport;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nimanandhar
 */
public class IndividualDashboardMemberDetailReport extends AbstractIndividualDashboard implements IIndividualDashboardMemberDetailReport {
    public IndividualDashboardMemberDetailReport(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPageTitle() {
        return "Individual Dashboard: (RP150) Member Detail Report";
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();
    }

    @Override
    public boolean isDrillPageValid() {
        logger.info("Drill Pgae is in valid state if all tables/sections exists in page.");
        logger.info("Checking if all tables of 'Member Detail Report' exists or not");
        List<String> tableXpath = getDetailReportsTableXpaths();
        for(String xPath: tableXpath) {
            if (SeleniumUtils.findElementsByXPath(getDriver(),xPath).size() != 1 ) {
                logger.info("Section with xpath: " + xPath + " Does Not Exists In The Page, Thus Page is in in valid state");
                return false;
            }
        }
        return true;
    }

    private List<String> getDetailReportsTableXpaths() {
        List<String> tableXpath= new ArrayList<>();
        tableXpath.add("//div[@id='d2Form:dxcgModelInfo']//div[@class='dr-pnl-b rich-panel-body']/table[@class='rich-table']");
        tableXpath.add("//div[@class='rich-panel-body']/table[@id='d2Form:simpleGrid']");
        tableXpath.add("//table[@id='d2Form:rdGrid']//table[@id='d2Form:costTable']");
        tableXpath.add("//table[@id='d2Form:rdGrid']//table[@id='d2Form:lohTable']");
        return tableXpath;
    }
}

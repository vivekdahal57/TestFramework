package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashBoardHAS;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class IndividualDashBoradHAS extends AbstractIndividualDashboard implements IIndividualDashBoardHAS {

    private static final Logger logger = LoggerFactory.getLogger(IndividualDashBoradHAS.class);
    private static final String TABLE_HEADER_NAME = "${tableHeader}";

    public IndividualDashBoradHAS(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPageTitle() {
        return "Individual Dashboard: (355) Health Analysis Summary";
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();
    }

    @Override
    public boolean isDrillPageValid() {
        logger.info("Drill Page is in valid state if and only if  all the tables of HAS are present in the page");
        logger.info("Checking if all tables  exists or not");
        List<String> tableIds = getTableHeaderList();
        for(String id: tableIds) {
            if (getTableHeaderElement(id).size() != 1 ) {
                logger.info("Table with header '" + id + "' Does Not Exists In The Page.Thus Drill page is in invalid state");
                return false;
            }
        }
        return true;
    }

    private List<String> getTableHeaderList() {
        List<String> headerList = new ArrayList<>();
        headerList.add("Individual Info");
        headerList.add("Disease Management");
        headerList.add("Case Management");
        headerList.add("Company Details");
        headerList.add("Report Tracking");
        headerList.add("Individual Info");
        return  headerList;
    }

    private List<WebElement> getTableHeaderElement(String tableHeaderName) {
        String xpath = "//table[@id='d2Form:grid']//div[@class='rich-panel-body']//div[text()[contains(.,'${tableHeader}')]]";
        return SeleniumUtils.findElementsByXPath(getDriver(), xpath.replace(TABLE_HEADER_NAME, tableHeaderName));
    }
}

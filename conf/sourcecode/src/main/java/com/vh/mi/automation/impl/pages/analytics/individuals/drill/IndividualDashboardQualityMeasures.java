package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardQualityMeasures;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nimanandhar
 */
public class IndividualDashboardQualityMeasures extends AbstractIndividualDashboard implements IIndividualDashboardQualityMeasures {

    private Logger logger = LoggerFactory.getLogger(IndividualDashboardQualityMeasures.class);
    LoadingPopup loading;

    public IndividualDashboardQualityMeasures(WebDriver driver) {
        super(driver);
        loading = new LoadingPopup(driver);
    }

    @Override
    public void waitTillLoadingDisappears() {
        loading.waitTillDisappears();
    }

    public enum Component {
        GAPS_GROUPBY("d2Form:maintable:tb", "gapGroupByMenu_drillMenu_menu"),
        GAPS_DETAILS("d2Form:maintable:tb", "gapDetailMenu_drillMenu_menu"),
        RISK_GROUPBY("d2Form:maintable1:tb", "riskGroupByMenu_drillMenu_menu"),
        RISK_DETAILS("d2Form:maintable1:tb", "riskDetailMenu_drillMenu_menu");

        Component(String tableId, String menuId) {
            this.menuId = menuId;
            this.tableId = tableId;
        }
        private String tableId;
        private String menuId;
        public String getMenuId() {
            return menuId;
        }
        public String getTableId() {
            return tableId;
        }
    }

    @Override
    public String getPageTitle() {
        return "Individual Dashboard: (325) Quality Measures";
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();
    }

    @Override
    public boolean isDrillPageValid() {
        logger.info("Drill Page is in valid state if and only if  all the tables of 'Quality Measures' are present in the page");
        logger.info("Checking if all tables of 'Quality Measures' exists or not");
        List<String> tableIds = getQualityMeasuresTableIds();
        for(String id: tableIds) {
            if (SeleniumUtils.findElementsById(getDriver(),id).size() != 1 ) {
                logger.info("Table with Id '" + id + "' Does Not Exists In The Page.Thus Drill page is not valid");
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isStatusDetailsShown(Component component, String option){
        WebElement table = SeleniumUtils.findElementById(getDriver(), component.getTableId());
        if("YES".equals(option)){
            return true == SeleniumUtils.elementExists(table, ".//td[6]//span[@class='FAIL' or @class='PASS']");
        }else{
            return false == SeleniumUtils.elementExists(table, ".//td[6]//span[@class='FAIL' or @class='PASS']");
        }
    }

    @Override
    public boolean isGroupByColumnShown(Component component, String option){
        WebElement table = SeleniumUtils.findElementById(getDriver(), component.getTableId());
        String xpath = ".//a[@class='dr-toolbar-int']";
        String sortableColumnName = table.findElement(By.xpath(xpath)).getText();
        return option.equals(sortableColumnName);
    }

    private List<String> getQualityMeasuresTableIds() {
        List<String> tableIds = new ArrayList<>();
        tableIds.add("d2Form:maintable");
        tableIds.add("d2Form:maintable1");
        return tableIds;
    }

}

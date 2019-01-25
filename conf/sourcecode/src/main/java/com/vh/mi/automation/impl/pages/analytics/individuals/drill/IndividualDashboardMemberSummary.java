package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardMemberSummary;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author nimanandhar
 */
public class IndividualDashboardMemberSummary extends AbstractIndividualDashboard implements IIndividualDashboardMemberSummary {

    private static Logger logger = LoggerFactory.getLogger(IndividualDashboardMemberSummary.class);
    private WebElements webElements;
    LoadingPopup loading;

    public IndividualDashboardMemberSummary(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
        loading = new LoadingPopup(driver);
    }

    public enum Component {
        ANALYSIS_PERIOD("anaPeriod_menu"),
        DIAG_GROUP("diagDropDown_menu");

        private Component(String id){
            this.id = id;
        }
        private   String id;
        public  String getId() {
            return id;
        }
    }

    @Override
    public String getPageTitle() {
        return "Individual Dashboard: (320) Member Summary";
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();
    }

    @Override
    public String getMemberId(){
        return webElements.memberID.getText();
    }

    @Override
    public boolean absenceOfNavigationPannel(){
        String xpath = "//*[@id=\"leftContent\"]/div";
        int navPannel = SeleniumUtils.findElementsByXPath(getDriver(),xpath).size();
        if (navPannel > 0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean loginSessionLimitedToSpecifiedMemberOnly(){
        String xpath ="//*[@id=\"d2Form:topExtraToolBar\"]/tbody/tr";
        int viewNxtMemberBtn = SeleniumUtils.findElementsByXPath(getDriver(),xpath).size();

        if (viewNxtMemberBtn > 0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void waitTillLoadingDisappears() {
        loading.waitTillDisappears();
    }

    @Override
    public boolean isDrillPageValid() {
        logger.info("Drill Page is in valid state if and only if  all the tables of summary are present in the page");
       logger.info("Checking if all tables of 'Summary' exists");
       List<String> tableIds = getSummaryTableIds();
       for(String id: tableIds) {
           if (SeleniumUtils.findElementsById(getDriver(),id).size() != 1 ) {
               logger.info("Table with Id '" + id + "' Does Not Exists In The Page.Thus Drill page is not valid");
               return false;
           }
       }
       return true;
    }

    @Override
    public boolean downLoadFileAndValidate(String rtfFileName, ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(rtfFileName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not download the file" + rtfFileName);
        }
        return true;
    }

    @Override
    public void goToIndividualClaimDetails() {
       String xpathForHoverElement = "//table[@id='d2Form:grid1']//tr[1]//td[2]//span[@class='d2-drill-menu']";
        WebElement hoverElement = SeleniumUtils.findElementByXPath(getDriver(), xpathForHoverElement);
        SeleniumUtils.hoverOnElement(hoverElement, getDriver());
        String xpathForIndividualMemberSummary = "//table[@id='d2Form:grid1']//tr[1]//td[2]//a";
        WebElement individualMemberSummaryLink = SeleniumUtils.findElementByXPath(getDriver(),xpathForIndividualMemberSummary);
        WaitUtils.waitUntilEnabled(getDriver(), individualMemberSummaryLink);
        SeleniumUtils.click(individualMemberSummaryLink);
    }


    private List<String> getSummaryTableIds() {
        List<String> tableIds = new ArrayList<>();
        tableIds.add("d2Form:grid1");
        tableIds.add("d2Form:grid2");
        tableIds.add("d2Form:grid3");
        tableIds.add("d2Form:grid4");
        return tableIds;
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {

            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"d2Form:indv_info\"]//td/span[.=\"ID\"]//following::td[1]/span[1]")
        private WebElement memberID;

        

    }

}

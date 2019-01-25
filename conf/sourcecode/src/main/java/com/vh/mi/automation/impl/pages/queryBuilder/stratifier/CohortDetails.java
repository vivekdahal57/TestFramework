package com.vh.mi.automation.impl.pages.queryBuilder.stratifier;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.ICohortDetails;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.CohortDetailMoreMetrics.CohortDetailAddMoreMetrics;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by i20345 on 12/27/2016.
 */
public class CohortDetails extends AbstractWebComponent implements ICohortDetails{
    private static Logger logger = LoggerFactory.getLogger(CohortDetails.class);
    WebElements webElements;
    PopulationAnalyser populationAnalyser;
    CohortDetailAddMoreMetrics cohortDetailAddMoreMetrics;
    LoadingPopup loadingPopup;

    public CohortDetails(WebDriver driver) {
        super(driver);
        webElements=new WebElements(getDriver());
        loadingPopup = new LoadingPopup(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.isDisplayed();
    }


    @Override
    public String getMemberCount()
    {
        return webElements.memberCount.getText();
    }


    public PopulationAnalyser goBackToPopulationAnalyser(){
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnBack);
        SeleniumUtils.click(webElements.btnBack);
        populationAnalyser = PageFactory.initElements(getDriver(),PopulationAnalyser.class);
        return populationAnalyser;
    }

    public CohortDetailAddMoreMetrics addMoreMetrics(){
        SeleniumUtils.click(webElements.btnAddMoreMetrics);
        cohortDetailAddMoreMetrics = new CohortDetailAddMoreMetrics(getDriver());
        SeleniumUtils.switchToNewWindow(getDriver());
        cohortDetailAddMoreMetrics.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return cohortDetailAddMoreMetrics;
    }

    public boolean checkWhetherQRMWithTheseIDsAreAdded(String ids){
        WaitUtils.waitForSeconds(TimeOuts.TEN_SECONDS);
        String xpath = "//*[@id=\"d2Form:simpleGrid_qrm:tb\"]//td[@class=\"rich-table-cell d2-align-left d2-nowrap\"]";
        List<WebElement> ListOfIDs = SeleniumUtils.findElementsByXPath(getDriver(),xpath);

        if(!(ids.split(",").length == ListOfIDs.size())){
            logger.info("Error caused because the count of added IDs didnot match the count in QRM Trend");
            return false;
        }
        for (WebElement element : ListOfIDs){
            if(!ids.contains(element.getText())){
                logger.info("Error caused because all added IDs are not in the QRM Trend");
                return false;
            }
        }
        return true;
    }

    public boolean isSFWChangePopupPresent(){
        if(webElements.updatePanel.isDisplayed()){
            WaitUtils.waitUntilDisplayed(getDriver(), webElements.updatePanelAcceptButton, TimeOuts.FIVE_SECONDS);
            SeleniumUtils.click(webElements.updatePanelAcceptButton);
            loadingPopup.waitTillDisappears();
            return true;
        }
        return false;
    }

    public void downLoadWordFile(){
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnDownloadWordFile);
        SeleniumUtils.click(webElements.btnDownloadWordFile);
    }
    public void downLoadExcelFile(){
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnDownloadExcelFile);
        SeleniumUtils.click(webElements.btnDownloadExcelFile);
    }

    public boolean downloadWordAndValidate(String downloadWordName,  ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(downloadWordName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not Download Word " + downloadWordName);
        }
        return true;
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"pageTitle\"]")
        private WebElement pageTitle;

        @FindBy(xpath = "//*[@id=\"d2Form:cohortStatistics\"]//tr/td[.=\"No. of Members\"]//following::td[1]")
        private WebElement memberCount;

        @FindBy(xpath="//*[@id=\"d2Form:topToolBar\"]/tbody/tr/td[2]/table/tbody/tr[2]/td/a")
        private WebElement btnBack;

        @FindBy(xpath = "//*[@id=\"d2Form:btnAddMoreMetrics\"]")
        private WebElement btnAddMoreMetrics;

        @FindBy(xpath = "//*[@id=\"topToolBarDiv\"]//a[.=\"Word\"]")
        private WebElement btnDownloadWordFile;

        @FindBy(xpath = "//*[@id=\"topToolBarDiv\"]//a[.=\"Excel\"]")
        private WebElement btnDownloadExcelFile;

        @FindBy(id = "d2Form:updatePanelCDiv")
        private WebElement updatePanel;

        @FindBy(id = "d2Form:updateButton")
        private WebElement updatePanelAcceptButton;
    }
}

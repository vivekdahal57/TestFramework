package com.vh.mi.automation.impl.reportManager;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.reportManager.IReportAdministrator;
import com.vh.mi.automation.api.reportManager.IReportManagerPage;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.reportManager.reportWizard.ReportWizard;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nimanandhar
 */
public class ReportAdministrator extends AbstractReportManagerPage implements IReportAdministrator {
    private static Logger logger = LoggerFactory.getLogger(ReportAdministrator.class);
    private final WebElements webElements;
    private static final String REPORT_NAME_PALCEHOLDER = "${reportName}";

    public ReportAdministrator(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return getDisplayedTitle().equals("Report Administration");
    }

    @Override
    public List<String> getApplications() {
        List<String> appIds = new ArrayList<>();
        for (WebElement webElement : new Select(webElements.applicationSection).getOptions()) {
            appIds.add(webElement.getAttribute("value"));
        }
        return appIds;
    }


    @Override
    public String getSelectedApplication() {
        WebElement selectedOption = new Select(webElements.applicationSection).getFirstSelectedOption();
        return selectedOption.getAttribute("value");
    }

    @Override
    public void goBack() {
        webElements.backButton.click();
    }

    @Override
    public ReportWizard goToReportWizard(int waitTimeSeconds) {
        clickReportWizard();

        ReportWizard reportWizard = new ReportWizard(getDriver());
        reportWizard.doWaitTillFullyLoaded(waitTimeSeconds);

        return reportWizard;
    }

    public void doFilterReportTitle(String reportTitle){
        SeleniumUtils.sendKeysToInput(reportTitle,webElements.reportTitleTextBox);
        applyFilter();
    }

    public void doFilterReportTitleAndDataSelection(String reportTitle,String dataSelection){
        SeleniumUtils.sendKeysToInput(reportTitle,webElements.reportTitleTextBox);
        SeleniumUtils.sendKeysToInput(dataSelection,webElements.dataSelectionTextBox);
        applyFilter();
    }

    private void applyFilter(){
        WaitUtils.waitUntilEnabled(getDriver(), webElements.filterButton);
        SeleniumUtils.click(webElements.filterButton);
        WaitUtils.waitUntilDisplayed(getDriver(),webElements.applicationName, TimeOuts.THIRTY_SECONDS);
    }

    public boolean validateDownloadFile(String fileName, ExecutionContext context) throws  Exception{
        if(!FileDownloadUtils.validateDownloadedFile(fileName,context, TimeOuts.SIXTY_SECONDS)){
            throw new AutomationException("Could not Download Report " + fileName);
        }
        return true;
    }

    public void downloadExtractRTFReport(String reportName){
        doFilterReportTitle(reportName);
        WebElement clickRTFDownloadLink = getRTFDownloadElement(reportName);
        SeleniumUtils.click(clickRTFDownloadLink);
    }

    public void downloadExtractPDFReport(String reportName){
        doFilterReportTitle(reportName);
        WaitUtils.waitForSeconds(TimeOuts.TWO_SECOND);
        WebElement clickPDFDownloadElement = getPDFDownloadElement(reportName);
        SeleniumUtils.click(clickPDFDownloadElement);
    }

    public void deleteMIReport(String reportName){
        doFilterReportTitle(reportName);
        int reportCnt  = getMaxReportCnt();
        for(int i = 1; i <= reportCnt; i++){
            doFilterReportTitle(reportName);
            WebElement clickDeleteDownloadElement = getDeleteDownloadElement(reportName);
            clickDeleteDownloadElement.click();
            if(SeleniumUtils.isAlertPresent(getDriver())){
                SeleniumUtils.clickOkOnAlert(getDriver());
            }
            WaitUtils.waitUntilDisplayed(getDriver(),webElements.applicationName, TimeOuts.THIRTY_SECONDS);
        }
    }

    public boolean isMIRReportSuccessfullyGenerated(String reportName){
        doFilterReportTitle(reportName);
        String status = "";
        try {
            WaitUtils.waitForSeconds(TimeOuts.TEN_SECONDS);
            status = getStatusElement(reportName).getText();
        }catch (NoSuchElementException ex){
            logger.info("The Report "+ "'" + reportName + "'" + " Doesn't Exists " + ex);
        }

        return (status.equalsIgnoreCase("In Progress") || status.equalsIgnoreCase("Queued") || status.equalsIgnoreCase("Completed"));

    }

    public boolean isMIRReportSuccessfullyGeneratedAtLOA(String reportName, String dataSelection){
        doFilterReportTitleAndDataSelection(reportName,dataSelection);
        String status = "";
        try {
            WaitUtils.waitForSeconds(TimeOuts.TEN_SECONDS);
            status = getStatusElement(reportName).getText();
        }catch (NoSuchElementException ex){
            logger.info("The Report "+ "'" + reportName + "'" + " Doesn't Exists " + ex);
        }

        return (status.equalsIgnoreCase("In Progress") || status.equalsIgnoreCase("Queued") || status.equalsIgnoreCase("Completed"));
    }

    public void downloadExtractPDFReportAtLOA(String reportName, int reportCnt){
        WebElement clickPDFDownloadElement = getPDFDownloadElementAtLOA(reportName,reportCnt);
        SeleniumUtils.click(clickPDFDownloadElement);
    }

    public void downloadExtractRTFReportAtLOA(String reportName, int reportCnt){
        WebElement clickRTFDownloadElement = getRTFDownloadElementAtLOA(reportName,reportCnt);
        SeleniumUtils.click(clickRTFDownloadElement);
    }

    private WebElement getRTFDownloadElement(String reportName){
        String xpath = "//form[@name='FilterSubmission']//table[@id='grid']//tr[td//text()[contains(.,'${reportName}')]]//table[@id='table_buttons']//img[@name='rtf']";
        return SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(REPORT_NAME_PALCEHOLDER,reportName));
    }

    private WebElement getPDFDownloadElement(String reportName){
        String xpath = "//form[@name='FilterSubmission']//table[@id='grid']//tr[td//text()[contains(.,'${reportName}')]]//table[@id='table_buttons']//img[@name='pdf']";
        return SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(REPORT_NAME_PALCEHOLDER,reportName));
    }

    private WebElement getDeleteDownloadElement(String reportName){
        String xpath = "//form[@name='FilterSubmission']//table[@id='grid']//tr[td//text()[contains(.,'${reportName}')]]//table[@id='table_buttons']//td[text()[contains(.,'Delete')]]";
        return SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(REPORT_NAME_PALCEHOLDER,reportName));
    }

    private WebElement getStatusElement(String reportName){
        String xpath = "//form[@name='FilterSubmission']//table[@id='grid']//tr[td//text()[contains(.,'${reportName}')]]//td[@title='Status']";
        return SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(REPORT_NAME_PALCEHOLDER,reportName));
    }

    private WebElement getPDFDownloadElementAtLOA(String reportName, int reportCnt){
        String xpath = "//form[@name='FilterSubmission']//table[@id='grid']/tbody/tr[" + (reportCnt + 2) + "][td//text()[contains(.,'${reportName}')]]//table[@id='table_buttons']//img[@name='pdf']";
        String xpathDataSection = "//form[@name='FilterSubmission']//table[@id='grid']/tbody/tr[" + (reportCnt + 2) + "]/td[5]";
        logger.info("Verifying PDF download of " + reportName + " " + SeleniumUtils.findElementByXPath(getDriver(),xpathDataSection).getText());
        return SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(REPORT_NAME_PALCEHOLDER,reportName));
    }

    private WebElement getRTFDownloadElementAtLOA(String reportName, int reportCnt){
        String xpath = "//form[@name='FilterSubmission']//table[@id='grid']/tbody/tr[" + (reportCnt + 2) + "][td//text()[contains(.,'${reportName}')]]//table[@id='table_buttons']//img[@name='rtf']";
        String xpathDataSection = "//form[@name='FilterSubmission']//table[@id='grid']/tbody/tr[" + (reportCnt + 2) + "]/td[5]";
        logger.info("Verifying RTF download of " + reportName + " " + SeleniumUtils.findElementByXPath(getDriver(),xpathDataSection).getText());
        return SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(REPORT_NAME_PALCEHOLDER,reportName));
    }


    public int getMaxReportCnt(){
        int cnt = webElements.reportTable.findElements(By.xpath("./tr")).size() - 2;
        return cnt;
    }

    public void clickReportWizard(){
        webElements.reportWizard.click();
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "applicationSection")
        WebElement applicationSection;

        @FindBy(id = "applicationName")
        WebElement applicationName;

        @FindBy(xpath="//a[text()='Back']")
        WebElement backButton;

        @FindBy(xpath="//a[@title='Report Wizard']")
        WebElement reportWizard;

        @FindBy(xpath="//form[@name='FilterSubmission']//input[@name='ffReportTitle']")
        WebElement reportTitleTextBox;

        @FindBy(xpath="//form[@name='FilterSubmission']//input[@name='ffDataSelection']")
        WebElement dataSelectionTextBox;

        @FindBy(xpath="//form[@name='FilterSubmission']//td[@title='Apply Filter']/img")
        WebElement filterButton;

        @FindBy(xpath = "//form[@name='FilterSubmission']//table[@id='grid']/tbody")
        WebElement reportTable;
    }

    /**
     * @author nimanandhar
     */
    public abstract static class AbstractReportManagerPage extends AbstractWebComponent implements IReportManagerPage {
        private final WebElements webElements;


        public AbstractReportManagerPage(WebDriver driver) {
            super(driver);
            webElements = new WebElements(driver);
        }

        @Override
        public void doSwitchToMainFrame() {
            new WebDriverWait(getDriver(), TimeOuts.SIXTY_SECONDS).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("mainFrame"));
        }


        @Override
        public String getDisplayedTitle() {
            getDriver().switchTo().defaultContent().switchTo().frame("mainFrame");
            return webElements.applicationName.getText();
        }

        public <T> T logoutExpectingPage(Class<T> expectedClass, Integer waitTime) {
            getDriver().switchTo().defaultContent().switchTo().frame("mainFrame");
            Actions actions = new Actions(getDriver());
            actions.moveToElement(webElements.logout).click().perform();
            T pageInstance = PageFactory.initElements(getDriver(), expectedClass);
            waitTillDocumentReady(waitTime);
            return pageInstance;
        }

        @Override
        public List<String> getDisplayedTabs() {
            List<String> displayedTabs = new ArrayList<>();
            for (WebElement webElement : webElements.globalNavigation.findElements(By.tagName("a"))) {
                displayedTabs.add(webElement.getText());
            }
            return displayedTabs;
        }

        private static class WebElements {
            private WebElements(WebDriver driver) {
                PageFactory.initElements(driver, this);
            }

            @FindBy(id = "applicationName")
            WebElement applicationName;

            @FindBy(id = "GlobalNavigation1")
            WebElement globalNavigation;

            @FindBy(linkText = "Logout")
            WebElement logout;
        }


    }
}

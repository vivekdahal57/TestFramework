package com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.dxcgRiskSolutionsStaticReports;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.dxcgRiskSolutions.dxcgRiskSolutionsStaticReports.IStaticReportsRP010;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author i80448
 */
public class StaticReportsRP010 extends AbstractLandingPage implements IStaticReportsRP010 {
    private static final String MODULE_ID = "52";
    private static final String COMPANY_NAME_PLACEHOLDER = "${companyName}";
    private static final String MODEL_NUMBER_PLACEHOLDER = "${ModelNumber}";
    private static final String BOOK_OF_BUSINESS = "BookOfBusiness";
    private static final String DOWNLOADED_FILE_NAME = "DxCGRiskSolutions_REPORT_MODEL_${ModelNumber}_${companyName}.zip";
    private final WebElements webElements;
    private String lastDownloadedFileName = "";

    public static final String REPORT_GENERATION_POPUP_MESSAGE = "Your request has been scheduled. You will get email notification after the request is completed.";


    public StaticReportsRP010(WebDriver driver) {
        super(driver, MODULE_ID);
        webElements = new WebElements(driver);
    }

    public void selectCompany(String companyName) {
        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr//td[text()='${companyName}']//preceding::input[@type='checkbox'][1]";
        WebElement companySelectCheckbox = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(COMPANY_NAME_PLACEHOLDER, companyName));
        SeleniumUtils.click(companySelectCheckbox);
    }

    public void selectModelsWithModelNumber(String modelNumbersString) {
        List<String> modelNumbersList = Arrays.asList(modelNumbersString.split("\\s*,\\s*"));
        for (String modelNumber : modelNumbersList) {
            WebElement modelSelectCheckbox = getModelSelectCheckbox(modelNumber);
            SeleniumUtils.click(modelSelectCheckbox);
        }
    }

    public void selectModelsWithModelNumberBOB(String modelNumbersString) {
        List<String> modelNumbersList = Arrays.asList(modelNumbersString.split("\\s*,\\s*"));
        for (String modelNumber : modelNumbersList) {
            WebElement modelSelectCheckbox = getModelSelectCheckboxBOB(modelNumber);
            SeleniumUtils.click(modelSelectCheckbox);
        }
    }

    public void hoverOnCompanyDrillElement(String companyName) {
        WebElement companyHoverElement = getCompanyHoverElement(companyName);
        SeleniumUtils.hover( getDriver(), companyHoverElement);
    }

    public void clickDownloadLinkForModel(String modelNumber) {
        WebElement downloadLink = getDownloadLink(modelNumber);
        SeleniumUtils.click(downloadLink);
    }

    public void generateCompanyReportForModels(String reportName, String modelNumbers) {
        SeleniumUtils.hover(getDriver(), webElements.drillMenuHead);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.generateReportLink);
        SeleniumUtils.click(webElements.generateReportLink);
        WaitUtils.waitUntilDisplayed(getDriver(),webElements.popupJobName, TimeOuts.TWO_SECOND);
        SeleniumUtils.sendKeysToInput(reportName, webElements.popupJobName);
        selectModelsWithModelNumber(modelNumbers);
        SeleniumUtils.click(webElements.popupSubmitButton);
        WaitUtils.waitUntilDisplayed(getDriver(),webElements.afterReportGenerationMessage, TimeOuts.SIXTY_SECONDS);
    }

    public void generateBOBReportForModels(String reportName, String modelNumbers){
        SeleniumUtils.hover(getDriver(), webElements.bobDrillMenuHead);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.generateReportLink);
        SeleniumUtils.click(webElements.generateReportLink);
        WaitUtils.waitUntilDisplayed(getDriver(),webElements.popupJobNameBOB, TimeOuts.TWO_SECOND);
        SeleniumUtils.sendKeysToInput(reportName, webElements.popupJobNameBOB);
        selectModelsWithModelNumberBOB(modelNumbers);
        SeleniumUtils.click(webElements.popupSubmitButtonBOB);
        WaitUtils.waitUntilDisplayed(getDriver(),webElements.afterReportGenerationMessage, TimeOuts.SIXTY_SECONDS);
    }

    public boolean downloadAndValidateReportsForCompany(String companyName, String modelNumbersString, ExecutionContext context) throws Exception{
        List<String> modelNumbersList = Arrays.asList(modelNumbersString.split("\\s*,\\s*"));
        for (String modelNumber : modelNumbersList) {
            String downloadedFileName = DOWNLOADED_FILE_NAME.replace(COMPANY_NAME_PLACEHOLDER,companyName.replace(" ", "_")).replace(MODEL_NUMBER_PLACEHOLDER,modelNumber);
            downloadReportForCompany(companyName, modelNumber);
            if(!FileDownloadUtils.validateDownloadedZipFile(downloadedFileName, context, TimeOuts.THIRTY_SECONDS)){
                throw new AutomationException("Report Downloaded for Company " + companyName + "and Model #" + modelNumber + "is not valid");
            }
        }
        return true;
    }

    private void downloadReportForCompany(String companyName, String modelNumber) throws Exception {
        hoverOnCompanyDrillElement(companyName);
        WaitUtils.waitUntilEnabled(getDriver(), getDownloadLink(modelNumber));
        clickDownloadLinkForModel(modelNumber);
    }

    public boolean downloadAndValidateReportsForBOB(String modelNumbersString, ExecutionContext context) throws Exception{
        List<String> modelNumbersList = Arrays.asList(modelNumbersString.split("\\s*,\\s*"));
        for (String modelNumber : modelNumbersList) {
            String downloadedFileName = DOWNLOADED_FILE_NAME.replace(COMPANY_NAME_PLACEHOLDER,BOOK_OF_BUSINESS).replace(MODEL_NUMBER_PLACEHOLDER,modelNumber);
            downloadReportForBOB(modelNumber);
            if(!FileDownloadUtils.validateDownloadedZipFile(downloadedFileName, context, TimeOuts.THIRTY_SECONDS)){
                throw new AutomationException("Could not Download Report for BOB for Model #" + modelNumber);
            }
        }
        return true;
    }

    private void downloadReportForBOB(String modelNumber) throws Exception {
        SeleniumUtils.hover(getDriver(), webElements.bobDrillMenuHead);
        WaitUtils.waitUntilEnabled(getDriver(), getDownloadLink(modelNumber));
        clickDownloadLinkForModel(modelNumber);
    }

    public String getTextFromReportGenerationPopupMessage() {
        return webElements.afterReportGenerationMessage.getText();
    }

    public String getLastDownloadedFileName() {
        return this.lastDownloadedFileName;
    }


    private WebElement getDownloadLink(String modelNumber) {
        String xpath = "//*[@id=\"dropmenudiv\"]//li[text()='Download Reports']/following::a[text() = 'Model #${ModelNumber} Report ']";
        return SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(MODEL_NUMBER_PLACEHOLDER, modelNumber));
    }

    private WebElement getCompanyHoverElement(String companyName){
        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr/td[text() = '${companyName}']/following::td[1]/a[1]";
        return SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(COMPANY_NAME_PLACEHOLDER, companyName));
    }

    private WebElement getModelSelectCheckbox(String modelNumber){
        String xpath = "//*[@id=\"d2Form:floatDiv\"]//td[text() = 'Model #${ModelNumber}']//preceding::td[1]/input[@type = 'checkbox']";
        return SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(MODEL_NUMBER_PLACEHOLDER, modelNumber));
    }

    private WebElement getModelSelectCheckboxBOB(String modelNumber){
        String xpath = "//*[@id=\"d2Form:floatDiv_1\"]//td[text() = 'Model #${ModelNumber}']//preceding::td[1]/input[@type = 'checkbox']";
        return SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(MODEL_NUMBER_PLACEHOLDER, modelNumber));
    }

    public static String getRandomReportName() {
        return "DXCG_REPORT_TEST_" + Random.getRandomStringOfLength(4);
    }

    private static class WebElements {
        public WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:drillMenuHead\"]/a[1]")
        WebElement drillMenuHead;

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid1:tb\"]/tr/td[3]/a[1]")
        WebElement bobDrillMenuHead;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]/a[text()='Generate Reports']")
        WebElement generateReportLink;

        @FindBy(xpath = "//*[@id=\"d2Form:jobName\"]")
        WebElement popupJobName;

        @FindBy(xpath = "//*[@id=\"d2Form:jobNameBOB\"]")
        WebElement popupJobNameBOB;

        @FindBy(xpath = "//*[@id=\"d2Form:generateReport\"]")
        WebElement popupSubmitButton;

        @FindBy(xpath = "//*[@id=\"d2Form:generateReportBOB\"]")
        WebElement popupSubmitButtonBOB;

        @FindBy(xpath = "//*[@id=\"ani_message\"]")
        WebElement afterReportGenerationMessage;
    }

}

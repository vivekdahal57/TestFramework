package com.vh.mi.automation.impl.pages.favorites.myJobs;

import com.google.common.base.Function;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.claims.Claims01DataGridColumn;
import com.vh.mi.automation.api.pages.favorites.myJobs.IMyJobs;
import com.vh.mi.automation.api.pages.favorites.myJobs.MyJobsDataGridColumn;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.dataGrid.customizer.DataGridCustomizer;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.sql.Time;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static com.vh.mi.automation.impl.selenium.SeleniumUtils.findElementByXPath;

/**
 * @author msedhain on 02/16/2017.
 */
public class MyJobs extends AbstractLandingPage implements IMyJobs {
    public static final String MODULE_ID = "290";
    private static final String JOB_TITLE_PLACEHOLDER = "${JobTitle}";
    private final WebElements webElements;
    private final IDataGrid dataGrid;
    private final LoadingPopup loadingPopup;


    public MyJobs(WebDriver driver) {
        super(driver, MODULE_ID);
        dataGrid = new MyJobsDataGrid(driver);
        webElements = new WebElements(driver);
        loadingPopup = new LoadingPopup(driver);
    }

    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public boolean isDataGridCustomizable() {
        return true;
    }

    @Override
    public IDataGridCustomizer getDataGridCustomizer() {
        if (isDataGridCustomizable()) {
            webElements.customizeLinkElement.click();
            return new DataGridCustomizer(getDriver(), new IColumnSpec() {
                @Override
                public IDataGridColumn getColumnByLabel(String colName) {
                    return Claims01DataGridColumn.fromLabel(colName);
                }
            });
        }

        return null;
    }

    @Override
    public void doJobFilter(String jobName) {
        getDataGrid().doFilter(MyJobsDataGridColumn.JOB_TITLE, jobName);
        waitForAjaxCallToFinish();
    }


    public int getDataTableSize() {
        return dataGrid.getData().size();
    }

    public void waitUntilStatusReadyForJobName(String jobTitle, int timeOut) {
        dataGrid.doFilter(MyJobsDataGridColumn.JOB_TITLE, jobTitle);
        waitForAjaxCallToFinish();
        if (getDataTableSize() == 0) {
            throw new Error("No Jobs with Job Title : " + jobTitle);
        }
        WaitUtils.waitUntil(getDriver(), timeOut, new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return "Ready".equals(dataGrid.getData().get(0, MyJobsDataGridColumn.fromId(MyJobsDataGridColumn.JOB_STATUS.getId())));
            }
        });
    }

    public void hoverOnDrillElementWithJobTitle(String jobTitle) {
        WebElement drillElementForJobTitle = getHoverElementForJobTitle(jobTitle);
        SeleniumUtils.hover(getDriver(), drillElementForJobTitle);
    }


    public void removeJobForJobName(String jobTitle) {
        hoverOnDrillElementWithJobTitle(jobTitle);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.deleteJobLink);
        SeleniumUtils.click(webElements.deleteJobLink);
        if (SeleniumUtils.isAlertPresent(getDriver())) {
            SeleniumUtils.clickOkOnAlert(getDriver());
        }
        WaitUtils.waitUntilDisplayed(getDriver(), webElements.jobDeletePopupMessage, TimeOuts.SIXTY_SECONDS);
    }

    private void waitForAjaxCallToFinish() {
        WaitUtils.waitForMilliSeconds(100);
        loadingPopup.waitTillDisappears();
        checkState(!loadingPopup.isDisplayed(), "The popup should have disappeared");
    }

    private WebElement getHoverElementForJobTitle(String jobTitle) {
        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr/td[text() = '${JobTitle}']/preceding::td[1]/a[1]";
        return findElementByXPath(getDriver(), xpath.replace(JOB_TITLE_PLACEHOLDER, jobTitle));
    }

    public void downloadExtractWithJobName(String jobName) {
        waitUntilStatusReadyForJobName(jobName, TimeOuts.THREE_MINUTES);
        hoverOnDrillElementWithJobTitle(jobName);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.DownLoadExtracts);
        SeleniumUtils.click(webElements.DownLoadExtracts, getDriver());
    }

    public void downloadExtractWithJobNameForIndividuals(String jobName){
        waitUntilStatusReadyForJobName(jobName, TimeOuts.THREE_MINUTES);
        hoverOnDrillElementWithJobTitle(jobName);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.DownLoadExtract);
        SeleniumUtils.click(webElements.DownLoadExtract, getDriver());
    }

    public boolean validateDownloadedExtract_File(String extractName, ExecutionContext context) throws IOException {
        if (!FileDownloadUtils.validateDownloadedFile(extractName, context, TimeOuts.SIXTY_SECONDS)) {
            throw new AutomationException(extractName + " File is not valid.");
        }
        return true;
    }

    public boolean validateDownloadedExtract_zip(String extractName, ExecutionContext context) throws IOException {
        if (!FileDownloadUtils.validateDownloadedZipFile(extractName, context, TimeOuts.SIXTY_SECONDS)) {
            throw new AutomationException(extractName + " Zip is not valid.");
        }
        return true;
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(linkText = "Customize")
        private WebElement customizeLinkElement;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]//li[text()='Apply Action']/following::a[text()='Delete Job']")
        private WebElement deleteJobLink;

        @FindBy(xpath = "//*[@id=\"ani_message\"]")
        WebElement jobDeletePopupMessage;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]//li[text()='Download']/following::a[text()='Download Extract']")
        WebElement DownLoadExtract;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]//li[text()='Download']/following::a[text()='Download Extracts']")
        WebElement DownLoadExtracts;

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]//td[text()='${jobName}']/preceding::a[1]")
        WebElement hoverElement;


    }

}
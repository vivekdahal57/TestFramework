package com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.queryManagement;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.queryBuilder.claimsSearch.queryManagement.IQueryManagement;
import com.vh.mi.automation.api.pages.queryBuilder.claimsSearch.queryManagement.QueryManagementDataGridColumn;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.google.common.base.Preconditions.checkState;
import static com.vh.mi.automation.impl.selenium.SeleniumUtils.findElementByXPath;

/**
 * Created by nimanandhar on 12/30/2014.
 */
public class QueryManagement extends AbstractLandingPage implements IQueryManagement {
    public static final String MODULE_ID = "210";
    private static final String QUERY_NAME_PLACEHOLDER = "${queryName}";

    private WebElements webElements;
    private final IDataGrid dataGrid;
    private final LoadingPopup loadingPopup;

    public QueryManagement(WebDriver driver) {
        super(driver, MODULE_ID);
        dataGrid = new QueryManagementDataGrid(driver);
        webElements = new WebElements(driver);
        loadingPopup = new LoadingPopup(driver);
    }

    public void downloadExtractWithName(String queryName, boolean isFilteringRequired){
        if(isFilteringRequired){
            dataGrid.doClearRecentFilteredValue();
            dataGrid.doFilter(QueryManagementDataGridColumn.QUERY_NAME, queryName);
            waitForAjaxCallToFinish();
        }
        hoverOnDrillElementWithQueryname(queryName);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.downloadExtractLink);
        SeleniumUtils.click(webElements.downloadExtractLink);
    }

    public boolean validateDownloadedZip(String downloadedZipFileName, ExecutionContext context) throws Exception{
        if(!FileDownloadUtils.validateDownloadedZipFile(downloadedZipFileName, context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not Download Report " + downloadedZipFileName);
        }
        return true;
    }

    public boolean deleteExtractWithName(String queryName, boolean isFilteringRequired){
        if(isFilteringRequired){
            dataGrid.doClearRecentFilteredValue();
            dataGrid.doFilter(QueryManagementDataGridColumn.QUERY_NAME, queryName);
            waitForAjaxCallToFinish();
        }
        hoverOnDrillElementWithQueryname(queryName);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.deleteExtractAndQueryLink);
        SeleniumUtils.click(webElements.deleteExtractAndQueryLink);
        if(SeleniumUtils.isAlertPresent(getDriver())){
            SeleniumUtils.clickOkOnAlert(getDriver());
        }
        waitForAjaxCallToFinish();
        dataGrid.doClearRecentFilteredValue();
        dataGrid.doFilter(QueryManagementDataGridColumn.QUERY_NAME, queryName);
        waitForAjaxCallToFinish();
        return dataGrid.getData().size() == 0;
    }

    public String getQueryIdForQueryName(String queryName, boolean isFilteringRequired){
        if(isFilteringRequired){
            dataGrid.doClearRecentFilteredValue();
            dataGrid.doFilter(QueryManagementDataGridColumn.QUERY_NAME, queryName);
            waitForAjaxCallToFinish();
        }
        return getQueryId(queryName);
    }

    public void hoverOnDrillElementWithQueryname(String queryName){
        WebElement drillElementForJobTitle = getHoverElementForQueryName(queryName);
        SeleniumUtils.hover( getDriver(),drillElementForJobTitle);
    }

    private WebElement getHoverElementForQueryName(String queryName){
        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr/td[text() = '${queryName}']/following::td[1]/a[1]";
        return findElementByXPath(getDriver(), xpath.replace(QUERY_NAME_PLACEHOLDER, queryName));
    }

    private String getQueryId(String queryName){
        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr/td[text() = '${queryName}']/preceding::td[1]";
        return findElementByXPath(getDriver(), xpath.replace(QUERY_NAME_PLACEHOLDER, queryName)).getText();
    }

    private void waitForAjaxCallToFinish() {
        WaitUtils.waitForMilliSeconds(100);
        loadingPopup.waitTillDisappears();
        checkState(!loadingPopup.isDisplayed(), "The popup should have disappeared");
    }

    private static class WebElements {

        public WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//div[@id='dropmenudiv']//a[text()='Download Extract']")
        private WebElement downloadExtractLink;

        @FindBy(xpath = "//div[@id='dropmenudiv']//a[text()='Delete Extract and Query']")
        private WebElement deleteExtractAndQueryLink;

    }
}

package com.vh.mi.automation.impl.pages.analytics.hcciClaims;


import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.api.features.IHaveReportingBy;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciClaims01S865DataGridColumn;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01DrillPage;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.ReportingBy;
import com.vh.mi.automation.impl.comp.dataGrid.customizer.DataGridCustomizer;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

/**
 * Created by i10359 on 10/27/17.
 */
public class HcciS865DrillPage extends AbstractDrillPage implements HcciIClaims01DrillPage,IHaveAnalysisPeriod,IHaveReportingBy {
    private static final String JOBNAME = "CSV_JOBNAME_" + Random.getRandomStringOfLength(3);
    private HcciS865DataGrid dataGrid;
    private WebElements webElements;
    private LoadingPopup loadingPopup;
    private AnalysisPeriod analysisPeriod;
    private ReportingBy reportingBy;



    public HcciS865DrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new HcciS865DataGrid(getDriver());
        webElements = new WebElements(getDriver());
        loadingPopup = new LoadingPopup(getDriver());
        analysisPeriod = new AnalysisPeriod(getDriver());
        reportingBy = PageFactory.initElements(getDriver(), ReportingBy.class);
    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }

    @Override
    public boolean isFullyLoaded(){
        return webElements.pageTitle.isDisplayed();
    }

    @Override
    public void generateCSV() {
        WaitUtils.waitForSeconds(TimeOuts.ONE_SECOND);

        SeleniumUtils.sendKeysToInput(JOBNAME, webElements.inputBox);
        SeleniumUtils.click(webElements.sendBtn, getDriver());
        loadingPopup.waitTillDisappears();
        SeleniumUtils.click(webElements.okBtn, getDriver());
        SeleniumUtils.closeChildWindowAndGoBackToMainWindow(getDriver(),true);
    }

    @Override
    public String getJobName() {
        return JOBNAME;
    }

    @Override
    public void filterPaidAmounts() {
        dataGrid.doFilter(HcciClaims01DrillPageDataGridColumns.PAID_AMOUNT, ">10000");
    }

    @Override
    public IAnalysisPeriod getAnalysisPeriod() {
        return analysisPeriod;
    }

    @Override
    public IReportingBy getReportingBy(){
        return reportingBy;
    }



    public IDataGridCustomizer getDataGridCustomizerS865(){
        webElements.customizeLinkElement.click();
        return new DataGridCustomizer(getDriver(), new IColumnSpec() {
            @Override
            public IDataGridColumn getColumnByLabel(String colName) {
                return HcciClaims01S865DataGridColumn.fromLabel(colName);
            }
        });
    }

    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(excelFileName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not export to excel " + excelFileName);
        }
        return true;
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;

        @FindBy(id="d2Form:sendToCsv")
        private WebElement sendBtn;

        @FindBy(id="d2Form:jobName")
        private WebElement inputBox;


        @FindBy(xpath="//*[@id=\"d2Form:csvJobProgressPanel_body\"]//input[@value='OK']")
        private WebElement okBtn;


        @FindBy(linkText = "Customize")
        private WebElement customizeLinkElement;


    }
}

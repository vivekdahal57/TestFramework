package com.vh.mi.automation.impl.pages.analytics.hcciClaims;

import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciClaims01DataGridColumn;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01DrillPage;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.ReportingBy;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.comp.dataGrid.customizer.DataGridCustomizer;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

/**
 * @author i80448
 */
public class HcciClaims01 extends AbstractLandingPage implements HcciIClaims01 {
    public static final String MODULE_ID = "188";
    private final WebElements webElements;
    private final IDataGrid dataGrid;
    private IAnalysisPeriod analysisPeriod;
    private IBusinessLevelsComponent businessLevelsComponent;
    private IReportingBy reportingBy;
    private LoadingPopup loadingPopup;

    public HcciClaims01(WebDriver driver) {
        super(driver, MODULE_ID);
        dataGrid = new HcciClaims01DataGrid(driver);
        webElements = new WebElements(driver);
        analysisPeriod = new AnalysisPeriod(getDriver());
        businessLevelsComponent = new BusinessLevelsComponent(getDriver());
        reportingBy = PageFactory.initElements(getDriver(), ReportingBy.class);
        loadingPopup=new LoadingPopup(getDriver());
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
                    return HcciClaims01DataGridColumn.fromLabel(colName);
                }
            });
        }

        return null;
    }

    @Override
    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(excelFileName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not export to excel " + excelFileName);
        }
        return true;
    }


    @Override
    public IDrillPage drillDownToPage(String page) {
        IDrillPage drillPage = dataGrid.getRows().get(0).doDrillByOnSameWindow(page);
        drillPage.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return  drillPage;
    }

    @Override
    public HcciIClaims01DrillPage drillDownToHcciClaims() {
        HcciClaims01DrillPage drillPage = (HcciClaims01DrillPage) dataGrid.getRows().get(0).doDrillBy("Claim Lines");
        drillPage.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return drillPage;
    }

    @Override
    public HcciIClaims01DrillPage drillDownToClaimsFromPharmacy() {
        HcciClaims01DrillPage drillPage = (HcciClaims01DrillPage) dataGrid.getRows().get(1).doDrillBy("Claim Lines");
        drillPage.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return drillPage;
    }


    @Override
    public IAnalysisPeriod getAnalysisPeriod() {
        return analysisPeriod;
    }

    @Override
    public IBusinessLevelsComponent getBusinessLevel() {
        return businessLevelsComponent;
    }

    @Override
    public IReportingBy getReportingBy() {
        return reportingBy;
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(linkText = "Customize")
        private WebElement customizeLinkElement;


    }

}

package com.vh.mi.automation.impl.pages.analytics.diseaseRegistry;

import com.vh.mi.automation.api.comp.adjustedNorm.IAdjustedNorm;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.ICPM01DataGridCustomizer;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.diseaseRegistry.DiseaseRegistryDataGridColumn;
import com.vh.mi.automation.api.pages.analytics.diseaseRegistry.IDiseaseRegistryCD016;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.adjustedNorm.AdjustedNorm;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.comp.dataGrid.customizer.DataGridCustomizer;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

/**
 * Created by i80448 on 9/2/2014.
 */
public class DiseaseRegistryCD016 extends AbstractLandingPage implements IDiseaseRegistryCD016 {
    public static final String MODULE_ID = "250";
    private final IAdjustedNorm adjustedNorm;
    private IBusinessLevelsComponent businessLevelsComponent;
    private IAnalysisPeriod analysisPeriod;
    private IDataGrid dataGrid;
    private WebElements webElements;


    public DiseaseRegistryCD016(WebDriver driver) {
        super(driver, MODULE_ID);
        this.adjustedNorm = new AdjustedNorm(driver);
        analysisPeriod = new AnalysisPeriod(getDriver());
        businessLevelsComponent = new BusinessLevelsComponent(getDriver());
        dataGrid = new DiseaseRegistryDataGrid(getDriver());
        webElements = new WebElements(driver);

    }



    @Override
    public IAdjustedNorm getAdjustedNorm() {
        return adjustedNorm;
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
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public IDrillPage drillDownToPage(String page) {
        IDrillPage drillPage = dataGrid.getRows().get(0).doDrillByOnSameWindow(page);
        drillPage.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return drillPage;
    }

    @Override
    public IDrillPage drillDownToDifferentWindow(String page) {
        IDrillPage drillPage = dataGrid.getRows().get(0).doDrillBy(page);
        drillPage.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return drillPage;
    }

    @Override
    public boolean isDataGridCustomizable() {
        return true;
    }

    @Override
    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(excelFileName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not export to excel " + excelFileName);
        }
        return true;
    }

    @Override
    public IDataGridCustomizer getDataGridCustomizer() {
        if (isDataGridCustomizable()) {
            webElements.customizeLinkElement.click();
            return new DataGridCustomizer(getDriver(), new IColumnSpec() {
                @Override
                public IDataGridColumn getColumnByLabel(String colName) {
                    return DiseaseRegistryDataGridColumn.fromLabel(colName);
                }
            });
        }

        return null;
    }

    @Override
    public void doCustomizeAllColumns(){
        IDataGridCustomizer dataGridCustomizer = getDataGridCustomizer();
        dataGridCustomizer.doSelectAll();
        dataGridCustomizer.doApplySelection();
        WaitUtils.waitUntilDisappear(getDriver(), webElements.customizationLoading, TimeOuts.THIRTY_SECONDS);
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(linkText = "Customize")
        private WebElement customizeLinkElement;

        @FindBy(id = "_LoadingCustomizationMultiDT")
        private WebElement customizationLoading;
    }
}

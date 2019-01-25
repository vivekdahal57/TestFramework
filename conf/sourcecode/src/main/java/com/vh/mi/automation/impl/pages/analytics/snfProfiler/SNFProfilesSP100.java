package com.vh.mi.automation.impl.pages.analytics.snfProfiler;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.snfProfiler.ISNFProfilesSP100;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.impl.comp.dataGrid.customizer.DataGridCustomizer;
import com.vh.mi.automation.impl.comp.pagination.PaginationComponent;
import com.vh.mi.automation.impl.pages.analytics.conversionAnalyzer.ConversionAnalyzerColumns;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

/**
 * Created by i10359 on 11/16/17.
 */
public class SNFProfilesSP100 extends AbstractLandingPage implements ISNFProfilesSP100 {
    public static final String MODULE_ID = "190";
    private IPaginationComponent paginationComponent;
    IDataGrid dataGrid;
    WebElements webElements;

    public SNFProfilesSP100(WebDriver driver) {
        super(driver, MODULE_ID);
        dataGrid = new SNFProfilesSP100DataGrid(getDriver());
        paginationComponent = PaginationComponent.newD2FormPaginationComponent(getDriver());
        webElements = new WebElements(driver);
    }


    @Override
    public IAnalysisPeriod getAnalysisPeriod() {
        return null;
    }

    @Override
    public IBusinessLevelsComponent getBusinessLevel() {
        return null;
    }

    @Override
    public IPaginationComponent getPaginationComponent() {
        return paginationComponent;
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
                    return ConversionAnalyzerColumns.fromLabel(colName);
                }
            });
        }

        return null;
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(linkText = "Customize")
        private WebElement customizeLinkElement;
    }
}

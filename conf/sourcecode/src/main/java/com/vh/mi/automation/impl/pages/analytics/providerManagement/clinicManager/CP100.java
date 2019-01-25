package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.ICPM01DataGridCustomizer;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.ICP100;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01DataGridCustomizer;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

/**
 * @author nimanandhar
 */
public class CP100 extends AbstractLandingPage implements ICP100 {
    public static final String MODULE_ID = "261";
    private IBusinessLevelsComponent businessLevelsComponent;
    IDataGrid dataGrid;
    WebElements webElements;

    public CP100(WebDriver driver) {
        super(driver, MODULE_ID);
        businessLevelsComponent = new BusinessLevelsComponent(getDriver());
        dataGrid = new CP100DataGrid(getDriver());
        webElements = new WebElements(driver);
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
    public ICPM01DataGridCustomizer getDataGridCustomizer() {
        if (isDataGridCustomizable()) {
            webElements.customizeLinkElement.click();
            new LoadingPopup(getDriver()).waitTillDisappears();
            return new CPM01DataGridCustomizer(getDriver(), new IColumnSpec() {
                @Override
                public IDataGridColumn getColumnByLabel(String colName) {
                    return CP100Columns.fromLabel(colName);
                }
            });
        }

        return null;
    }

    @Override
    public void doCustomizeAllColumns(){
        ICPM01DataGridCustomizer dataGridCustomizer = getDataGridCustomizer();
        dataGridCustomizer.doSelectAllForAllCategory();
        dataGridCustomizer.doApplySelection();
        new LoadingPopup(getDriver()).waitTillDisappears();
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(linkText = "Customize")
        private WebElement customizeLinkElement;
    }

}

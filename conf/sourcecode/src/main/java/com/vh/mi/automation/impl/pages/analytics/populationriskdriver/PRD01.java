package com.vh.mi.automation.impl.pages.analytics.populationriskdriver;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.pages.analytics.populationriskdriver.datagrids.CostDataGrid;
import com.vh.mi.automation.impl.pages.analytics.populationriskdriver.datagrids.IHavePRD01DataGrid;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

/**
 * Created by i82298 on 5/31/2017.
 */
public class PRD01 extends AbstractLandingPage
        implements IHaveBusinessLevel, IHavePRD01DataGrid {

    private static final String MODULE_ID = "500";
    private IBusinessLevelsComponent businessLevelsComponent;
    private IDataGrid costDataGrid;

    public PRD01(WebDriver driver) {
        super(driver, MODULE_ID);
        businessLevelsComponent = new BusinessLevelsComponent(driver);
        costDataGrid = new CostDataGrid(getDriver());
    }

    @Override public IBusinessLevelsComponent getBusinessLevel() {
        return businessLevelsComponent;
    }

    @Override public IDataGrid getCostDataGrid() {
        return costDataGrid;
    }

    @Override public IDataGrid getLOEDDataGrid() {
        return null;
    }

    @Override public IDataGrid getLOHDataGrid() {
        return null;
    }

    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(excelFileName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not export to excel " + excelFileName);
        }
        return true;
    }
}

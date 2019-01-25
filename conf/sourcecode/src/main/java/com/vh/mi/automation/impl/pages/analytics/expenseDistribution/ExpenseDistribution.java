package com.vh.mi.automation.impl.pages.analytics.expenseDistribution;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.expenseDistribution.IExpenseDistribution;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.comp.pagination.PaginationComponent;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

/**
 * @author i80448
 */
public class ExpenseDistribution extends AbstractLandingPage implements IExpenseDistribution {
    public static final String MODULE_ID = "15";
    private final IDataGrid dataGrid;
    private IAnalysisPeriod analysisPeriod;
    private IBusinessLevelsComponent businessLevelsComponent;
    private IPaginationComponent paginationComponent;

    public ExpenseDistribution(WebDriver driver) {
        super(driver, MODULE_ID);
        dataGrid = new ExpenseDistributionDataGrid(driver);
        analysisPeriod = new AnalysisPeriod(getDriver());
        businessLevelsComponent = new BusinessLevelsComponent(getDriver());
        paginationComponent = PaginationComponent.newD2FormPaginationComponent(getDriver());
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
    public IPaginationComponent getPaginationComponent() {
        return paginationComponent;
    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(excelFileName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not export to excel " + excelFileName);
        }
        return true;
    }

}

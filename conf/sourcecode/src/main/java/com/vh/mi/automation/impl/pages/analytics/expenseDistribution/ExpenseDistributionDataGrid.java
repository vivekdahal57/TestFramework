package com.vh.mi.automation.impl.pages.analytics.expenseDistribution;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by nimanandhar on 9/9/2014.
 */
public class ExpenseDistributionDataGrid extends AbstractDataGrid {

    public ExpenseDistributionDataGrid(WebDriver driver) {
        super(driver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        throw new RuntimeException("Not Implemented");
    }

}


package com.vh.mi.automation.test.pages.analytics.expenseDistribution;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.expenseDistribution.IExpenseDistribution;
import com.vh.mi.automation.impl.pages.analytics.expenseDistribution.ExpenseDistribution;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author i80448
 */
public class ExpenseDistributionBLComponentTest extends AbstractBLCompTest {
    private IExpenseDistribution expenseDistributionPage;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        expenseDistributionPage = navigationPanel.doNavigateTo(ExpenseDistribution.class, defaultWaitTime);
    }

    @Override
    protected IBusinessLevelsComponent getBusinessLevelsComponent() {
        return expenseDistributionPage.getBusinessLevel();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return expenseDistributionPage.getDataGrid();
    }
}

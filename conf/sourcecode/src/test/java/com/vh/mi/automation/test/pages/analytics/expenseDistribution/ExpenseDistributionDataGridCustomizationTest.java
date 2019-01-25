package com.vh.mi.automation.test.pages.analytics.expenseDistribution;

import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.pages.analytics.expenseDistribution.IExpenseDistribution;
import com.vh.mi.automation.impl.pages.analytics.expenseDistribution.ExpenseDistribution;
import com.vh.mi.automation.test.comp.dataGrid.DataGridCustomizationTest;
import org.testng.annotations.BeforeClass;

/**
 * Created by i80448 on 11/20/2014.
 */
public class ExpenseDistributionDataGridCustomizationTest extends DataGridCustomizationTest {
    IExpenseDistribution expenseDistribution;

    @BeforeClass
    public void setUp() {
        super.setUp();
        expenseDistribution = navigationPanel.doNavigateTo(ExpenseDistribution.class, defaultWaitTime);
    }


    @Override
    public IDataGridCustomizer getDataGridCustomizer() {
        return null;
    }

    @Override
    public boolean isDataGridCustomizable() {
        return false;
    }

}

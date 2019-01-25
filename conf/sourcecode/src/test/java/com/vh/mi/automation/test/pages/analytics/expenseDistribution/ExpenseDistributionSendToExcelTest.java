package com.vh.mi.automation.test.pages.analytics.expenseDistribution;

import com.vh.mi.automation.api.pages.analytics.expenseDistribution.IExpenseDistribution;
import com.vh.mi.automation.impl.pages.analytics.expenseDistribution.ExpenseDistribution;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/8/18.
 */
@Test(groups = {"Report-Download" })
public class ExpenseDistributionSendToExcelTest extends BaseTest {
    private IExpenseDistribution expenseDistribution;
    private static final String FILENAME = "ExpenseDist310.xlsx";

    @BeforeClass
    public void setUp(){
        super.setUp();
        expenseDistribution = navigationPanel.doNavigateTo(ExpenseDistribution.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(expenseDistribution.getPageTitle());
    }

    @Test
    public void checkSendToExcel() throws IOException{
        expenseDistribution.getPreferencesBar().sendToExcel();
        assertThat(expenseDistribution.sendToExcelAndValidate(FILENAME, context)).isTrue();
    }
}

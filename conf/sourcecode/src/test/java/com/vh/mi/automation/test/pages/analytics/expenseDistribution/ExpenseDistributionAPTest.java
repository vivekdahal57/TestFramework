package com.vh.mi.automation.test.pages.analytics.expenseDistribution;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.pages.analytics.expenseDistribution.IExpenseDistribution;
import com.vh.mi.automation.impl.pages.analytics.expenseDistribution.ExpenseDistribution;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.EXPENSE_DISTRIBUTION;
import static org.fest.assertions.Assertions.assertThat;

/**
 * @author i80448
 */
@Test(groups = "ExpenseDistribution")
public class ExpenseDistributionAPTest extends AbstractAnalysisPeriodTest {

    private IExpenseDistribution expenseDistrib;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        expenseDistrib = navigationPanel.doNavigateTo(ExpenseDistribution.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(EXPENSE_DISTRIBUTION.getPageTitle());

        ap = expenseDistrib.getAnalysisPeriod();
        appCyclePeriod = ap.getFullCyclePeriod();
    }


    @Test
    public void testPageTitle() {
        assertThat(expenseDistrib.getDisplayedPageTitle()).isEqualTo(
                expenseDistrib.getPageTitle());
    }

    @Override
    protected IAnalysisPeriod getAnalysisPeriod() {
        return ap;
    }

    @Override
    protected IPeriod getAppCyclePeriod() {
        return appCyclePeriod;
    }

}

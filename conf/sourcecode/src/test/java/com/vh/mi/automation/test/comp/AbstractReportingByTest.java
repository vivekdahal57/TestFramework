package com.vh.mi.automation.test.comp;

import com.google.common.collect.Table;
import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.comp.IReportingBy.RBOption;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.utils.TestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author i80448
 */
public abstract class AbstractReportingByTest extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    protected abstract IReportingBy getReportingBy();

    //the data grid is used to check whether the data changes when AP Options are changed
    //override this and return valid data grid instance to activate the test
    protected IDataGrid getDataGrid() {
        return null;
    }

    @Test
    public void test_rb_instance() {
        assertThat(getReportingBy()).isNotNull();
    }

    @Test(dataProvider = "rb-provider")
    public void test_rb_selection(RBOption option) {
        if (getReportingBy() != null) {
            logger.info("option = {}, expected = {}", option, option);
            boolean optionAvailable = getReportingBy()
                    .isOptionAvailable(option);
            if (optionAvailable) {
                getReportingBy().select(option);
                RBOption currentSelection = getReportingBy()
                        .getSelectedOption();
                assertThat(currentSelection).isEqualTo(option);
            } else {
                logger.info("{} not available", option);
            }
        } else {
            logger.info(
                    "ReportingBy component is not available for __{}__ page",
                    this.getClass().getSimpleName());
        }

    }

    @Test
    public void test_currentSelection() {
        RBOption currentSelection = getReportingBy().getSelectedOption();
        assertThat(currentSelection).isNotNull();
    }

    @DataProvider(name = "rb-provider")
    public Object[][] provideSelectOptions() {
        //@formatter:off
        return new Object[][]{
                {RBOption.PAID},
                {RBOption.INCURRED},
                {RBOption.INCURREDPAID}
        };
        //@formatter:on
    }

    @Test
    public void testThatDataChangesOnRBSelection() {
        IDataGrid dataGrid = getDataGrid();
        if (dataGrid == null) { //no valid dataGrid Instance present
            return;         //skip test
        }

        IReportingBy reportingBy = getReportingBy();
        reportingBy.select(RBOption.PAID);
        assertThat(reportingBy.getSelectedOption()).isEqualTo(RBOption.PAID);
        Table<Integer, IDataGridColumn, String> paidData = dataGrid.getData();

        reportingBy.select(RBOption.INCURRED);
        assertThat(reportingBy.getSelectedOption()).isEqualTo(RBOption.INCURRED);
        Table<Integer, IDataGridColumn, String> incurredData = dataGrid.getData();

        reportingBy.select(RBOption.INCURREDPAID);
        assertThat(reportingBy.getSelectedOption()).isEqualTo(RBOption.INCURREDPAID);
        Table<Integer, IDataGridColumn, String> incurredandpaidData = dataGrid.getData();

        assertThat(TestUtils.isEqual(paidData, incurredData)).isFalse();
        assertThat(TestUtils.isEqual(paidData, incurredandpaidData)).isFalse();
    }
}

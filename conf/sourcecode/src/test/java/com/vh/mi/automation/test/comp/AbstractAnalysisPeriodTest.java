package com.vh.mi.automation.test.comp;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption;
import com.vh.mi.automation.api.comp.ap.ICustomPeriodComp;
import com.vh.mi.automation.api.comp.ap.ITrendingPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.comp.state.ITrendPeriod;
import com.vh.mi.automation.impl.comp.state.Period;
import com.vh.mi.automation.test.base.BaseTest;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption.*;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Base class for pages which requires tests on analysis period component.
 *
 * @author i80448
 */
@Test(groups = "AP")
public abstract class AbstractAnalysisPeriodTest extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    protected abstract IAnalysisPeriod getAnalysisPeriod();

    protected abstract IPeriod getAppCyclePeriod();

    //the data grid is used to check whether the data changes when AP Options are changed
    //override this and return valid data grid instance to activate the test
    protected IDataGrid getDataGrid() {
        return null;
    }

    @Test
    public void test_ap_instance() {
        Assert.assertNotNull(getAnalysisPeriod());
    }

    public void test_ap_defaultSelection() {
        APOption curSelection = getAnalysisPeriod().getSelectedOption();
        assertThat(curSelection).isNotNull();
    }

    @Test
    public void test_ap_availableOptions() {
        Collection<APOption> options = getAnalysisPeriod()
                .getAvailableOptions();
        assertThat(options).isNotNull();
        assertThat(options.size()).isGreaterThan(0);
    }

    @Test(dataProvider = "ap-provider")
    public void test_ap_selection(APOption select) {
        getAnalysisPeriod().doSelect(select);
        APOption curSelection = getAnalysisPeriod().getSelectedOption();
        assertThat(curSelection).isEqualTo(select);
    }

    @DataProvider(name = "ap-provider")
    public Object[][] provideSelectOptions() {

        Collection<APOption> options = getAnalysisPeriod()
                .getAvailableOptions();

        // filter out custom & trend if exists, they have their own test cases.
        Collection<APOption> filteredOps = Collections2.filter(options,
                new Predicate<APOption>() {
                    @Override
                    public boolean apply(APOption arg0) {
                        return (!(arg0 == CUSTOM_PERIOD || arg0 == TREND_PERIODS));
                    }
                });

        int count = filteredOps.size();
        APOption[] array = filteredOps.toArray(new APOption[count]);

        Object[][] data = new Object[count][1];
        for (int i = 0; i < count; i++) {
            APOption op = array[i];
            data[i][0] = op;
        }
        return data;
    }

    @Test
    public void test_default_custom_period_selection() {
        if (isCustomOptionExists()) {

            ICustomPeriodComp cp = (ICustomPeriodComp) getAnalysisPeriod().doSelect(
                    CUSTOM_PERIOD);
            DateTime selectedFromDate = cp.getSelectedFromDate();
            DateTime selectedToDate = cp.getSelectedToDate();
            cp.doApplyDefaultSelects();
            verifyCustomPeriodSelection(selectedFromDate, selectedToDate);
        } else {
            logger.info(CUSTOM_PERIOD + " : N/A");
        }
    }

    @Test( dataProvider = "custom-period-provider")
    public void test_random_custom_period_selection(IPeriod p) {
        if (isCustomOptionExists()) {
            Preconditions.checkArgument(p != null,
                    "Argument p should not be null.");

            ICustomPeriodComp cp = (ICustomPeriodComp) getAnalysisPeriod().doSelect(
                    CUSTOM_PERIOD);

            Preconditions.checkState(cp != null,
                    "CP component can not be null.");

            DateTime fromDate = p.getFromDate();
            DateTime toDate = p.getToDate();
            cp.doSelectAndApply(fromDate, toDate);
            verifyCustomPeriodSelection(fromDate, toDate);
        }
    }

    private void verifyCustomPeriodSelection(DateTime selectedFromDate, DateTime selectedToDate) {
        APOption selectedOption = getAnalysisPeriod().getSelectedOption();
        assertThat(selectedOption).isEqualTo(CUSTOM_PERIOD);

        Optional<IPeriod> selectedPeriod = getAnalysisPeriod().getSelectedPeriod();
        assertThat(selectedPeriod.isPresent()).isTrue();

        IPeriod period = selectedPeriod.get();
        assertThat(period.getFromDate()).isEqualTo(selectedFromDate);
        assertThat(period.getToDate()).isEqualTo(selectedToDate);
    }

    @DataProvider(name = "custom-period-provider")
    public Object[][] randomCustomPeriodProvider() {

        if (isCustomOptionExists()) {
            int count = 2;
            Object[][] data = new Object[count][1];
            for (int mth = 1; mth <= count; mth++) {
                DateTime from = getAppCyclePeriod().getFromDate().plusMonths(
                        mth);
                DateTime to = getAppCyclePeriod().getToDate().minusMonths(mth);

                data[mth - 1][0] = new Period(from, to);
            }
            return data;
        } else {
            logger.info("_CUSTOM_ option N/A");
            return new Object[0][0];
        }
    }

    private boolean isCustomOptionExists() {
        return getAnalysisPeriod().isOptionExists(CUSTOM_PERIOD);
    }

    @Test( dataProvider = "trend-default-options-provider")
    public void test_trend_default_options_selection(APOption option) {
        ITrendingPeriod tp = (ITrendingPeriod) getAnalysisPeriod().doSelect(TREND_PERIODS);
        ImmutableList<APOption> defaultOptions = tp.getDefaultOptions();

        if (defaultOptions.contains(option)) {
            tp = (ITrendingPeriod) getAnalysisPeriod().doSelect(TREND_PERIODS);
            tp.doSelectDefaultOption(option);
        } else {
            logger.info(option + " : N/A");
        }
    }

    @DataProvider(name = "trend-default-options-provider")
    public Object[][] trendDefaultOptionsProvider() {
        if (isTrendOptionExists()) {
            return new APOption[][]{
                    {APOption.FULL_CYCLE},
                    {APOption.ROLLING_YEAR},
                    {APOption.CONTRACT_YEAR}
            };
        } else {
            return new Object[0][0];
        }
    }

    private boolean isTrendOptionExists() {
        return getAnalysisPeriod().getAvailableOptions().contains(TREND_PERIODS);
    }

    @Test
    public void test_trend_default_custom_periods_selection() {
        if (isTrendOptionExists()) {
            ITrendingPeriod tpComp = (ITrendingPeriod) getAnalysisPeriod().doSelect(TREND_PERIODS);
            IPeriod selectedPeriodOne = tpComp.getSelectedPeriodOne();
            IPeriod selectedPeriodTwo = tpComp.getSelectedPeriodTwo();

            tpComp.doApplyPeriodsSelection();

            assertThat(getAnalysisPeriod().getSelectedOption()).isEqualTo(TREND_PERIODS);
            Optional<ITrendPeriod> trendPeriod = getAnalysisPeriod().getTrendPeriodIfSelected();
            Assert.assertTrue(trendPeriod.isPresent());

            ITrendPeriod tp = trendPeriod.get();
            assertThat(tp.getPeriod1()).isEqualTo(selectedPeriodOne);
            assertThat(tp.getPeriod2()).isEqualTo(selectedPeriodTwo);
        }
    }

    @Test
    public void test_trend_random_custom_periods_selection() {
        if (isTrendOptionExists()) {

            DateTime cycleStart = getAppCyclePeriod().getFromDate();
            DateTime cycleEnd = getAppCyclePeriod().getToDate();

            ITrendingPeriod tpComp = (ITrendingPeriod) getAnalysisPeriod().doSelect(TREND_PERIODS);
            tpComp.doSelectPeriodOne(cycleStart.plusMonths(1), cycleStart.plusYears(1));
            tpComp.doSelectPeriodTwo(cycleEnd.minusYears(1), cycleEnd.minusMonths(1));

            IPeriod selectedPeriodOne = tpComp.getSelectedPeriodOne();
            IPeriod selectedPeriodTwo = tpComp.getSelectedPeriodTwo();
            tpComp.doApplyPeriodsSelection();

            assertThat(getAnalysisPeriod().getSelectedOption()).isEqualTo(TREND_PERIODS);
            Optional<ITrendPeriod> trendPeriod = getAnalysisPeriod().getTrendPeriodIfSelected();
            assertThat(trendPeriod.isPresent()).isTrue();

            ITrendPeriod tp = trendPeriod.get();
            assertThat(tp.getPeriod1()).isEqualTo(selectedPeriodOne);
            assertThat(tp.getPeriod2()).isEqualTo(selectedPeriodTwo);
        }
    }

    @Test
    public void testDataChangesOnAPSelection() throws Exception {
        IDataGrid dataGrid = getDataGrid();
        if (dataGrid == null) //no valid dataGrid instance
            return;           //cannot check that data changes

        IAnalysisPeriod analysisPeriod = getAnalysisPeriod();
        ImmutableCollection<APOption> availableOptions = analysisPeriod.getAvailableOptions();

        if (!analysisPeriod.getSelectedOption().equals(FULL_CYCLE)) {
            analysisPeriod.doSelect(FULL_CYCLE);
        }
        assertThat(analysisPeriod.getSelectedOption()).isEqualTo(FULL_CYCLE);

        List<APOption> apOptions = Arrays.asList(CONTRACT_YEAR, ROLLING_YEAR);

        String fullCycleData = dataGrid.getTableDataAsText();
        for (APOption apOption : apOptions) {
            if (!availableOptions.contains(apOption)) continue; //the AP Option is not available ;skip

            analysisPeriod.doSelect(apOption);
            assertThat(analysisPeriod.getSelectedOption()).isEqualTo(apOption);

            String newData = dataGrid.getTableDataAsText();
            assertThat(fullCycleData).isNotEqualTo(newData);
        }
    }
}

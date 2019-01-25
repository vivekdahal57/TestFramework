package com.vh.mi.automation.test.comp.dataGrid;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Lists;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80448 on 11/19/2014.
 */
public abstract class DataGridCustomizationTest extends BaseTest {

    public abstract IDataGridCustomizer getDataGridCustomizer();

    public abstract boolean isDataGridCustomizable();

    @Test
    public void test_getDataGridCustomizer_works() {
        IDataGridCustomizer dgc = getDataGridCustomizer();

        if (isDataGridCustomizable()) {
            assertThat(dgc).isNotNull();

            // close customizer
            dgc.doCancelAndClose();
        }
    }

    @Test
    public void test_getAvailaleOptions_works() {
        if (isDataGridCustomizable()) {
            IDataGridCustomizer dgc = getDataGridCustomizer();
            ImmutableCollection<IDataGridColumn> opts = dgc.getAvailaleOptions();

            // close customizer
            dgc.doCancelAndClose();
            dgc = null;

            // current impl : there must be some options available.
            assertThat(opts).isNotEmpty();
        }
    }

    @Test
    public void test_doSelectAll_event_works() {
        if (isDataGridCustomizable()) {
            IDataGridCustomizer dgc = getDataGridCustomizer();

            Collection<IDataGridColumn> availaleOptions = dgc.getAvailaleOptions();
            Collection<IDataGridColumn> prevS = dgc.getSelectedOptions();

            dgc.doSelectAll();
            Collection<IDataGridColumn> currS = dgc.getSelectedOptions();

            // close customizer
            dgc.doResetSelections();
            dgc = null;

            assertThat(currS.size()).isGreaterThanOrEqualTo(prevS.size());
            assertThat(currS.size()).isEqualTo(availaleOptions.size());
        }
    }

    @Test
    public void test_doUnselectAll_event_works() {
        if (isDataGridCustomizable()) {
            IDataGridCustomizer dgc = getDataGridCustomizer();
            Collection<IDataGridColumn> prevSelecOptions = dgc.getSelectedOptions();

            dgc.doUnselectAll();
            Collection<IDataGridColumn> currSelecOptions = dgc.getSelectedOptions();

            dgc.doResetSelections();
            dgc = null;

            assertThat(currSelecOptions.size()).isZero();
            assertThat(currSelecOptions.size()).isLessThan(prevSelecOptions.size());
        }
    }

    @Test
    public void test_doUnselect_event_works() {
        if (isDataGridCustomizable()) {
            IDataGridCustomizer dgc = getDataGridCustomizer();
            Collection<IDataGridColumn> prev = dgc.getSelectedOptions();

            ArrayList<IDataGridColumn> iColumns = Lists.newArrayList(prev);

            dgc.doUnselect(iColumns.get(0));
            ImmutableCollection<IDataGridColumn> curr = dgc.getSelectedOptions();

            assertThat(curr.size()).isNotEqualTo(prev.size());

            dgc.doResetSelections();
        }
    }

    private void test_doCancelAndClose_event_works() {

        // FAILS - in current implementation

        if (isDataGridCustomizable()) {
            IDataGridCustomizer dgc1 = getDataGridCustomizer();
            ImmutableCollection<IDataGridColumn> prev = dgc1.getSelectedOptions();

            dgc1.doSelectAll();
            dgc1.doCancelAndClose();
            dgc1 = null;

            IDataGridCustomizer dgc2 = getDataGridCustomizer();
            ImmutableCollection<IDataGridColumn> curr = dgc2.getSelectedOptions();

            dgc2.doResetSelections();
            dgc2 = null;

            assertThat(curr.size())
                    .as("After cancelling selection in Customization, number of selected options should be unchanged. Original selected Options = " + prev.size() + " current selected Options = " + curr.size())
                    .isEqualTo(prev.size());

        }
    }

//    public void test_doApplySelection_event_works() {
//        if (isDataGridCustomizable()) {
//            IDataGridCustomizer dgc1 = getDataGridCustomizer();
//            ImmutableCollection<IColumn> prevSelecOptions = dgc1.getSelectedOptions();
//
//            // un-select one of the selected option
//            dgc1.doUnselect(Lists.newArrayList(prevSelecOptions).get(0));
//            ImmutableCollection<IColumn> curSelecOptions = dgc1.getSelectedOptions();
//
//            dgc1.doResetSelections();
//            dgc1 = null;
//
//            assertThat(curSelecOptions.size()).isLessThan(prevSelecOptions.size());
//        }
//    }


    @Test
    public void test_doApplySelection_event_works() {
        if (isDataGridCustomizable()) {
            IDataGridCustomizer dgc1 = getDataGridCustomizer();
            ImmutableCollection<IDataGridColumn> prevSelecOptions = dgc1.getSelectedOptions();

            // un-select one of the selected option
            dgc1.doSelectAll();
            ImmutableCollection<IDataGridColumn> curSelecOptions = dgc1.getSelectedOptions();

            dgc1.doResetSelections();
            dgc1 = null;

            assertThat(curSelecOptions.size()).
                    as("The size of selection after doSelectAll= " + curSelecOptions.size() + " must be greater or equals to before doSelectAll size " + prevSelecOptions.size())
                    .isGreaterThanOrEqualTo(prevSelecOptions.size());
        }
    }

    @Test
    public void test_doResetSelections_event_works() {

        // reset event resets to app defaults, user may have saved some extra columns, those will be removed.
        // so unless we get app defaults from DB we can't verify that this event.

        if (isDataGridCustomizable()) {

            // reset and get the defaults.
            IDataGridCustomizer dgc1 = getDataGridCustomizer();
            dgc1.doResetSelections();
            dgc1 = null;

            IDataGridCustomizer dgc2 = getDataGridCustomizer();
            ImmutableCollection<IDataGridColumn> defaults = dgc2.getSelectedOptions();

            if (!defaults.isEmpty()) {
                dgc2.doUnselect(Lists.newArrayList(defaults).get(0));
                dgc2.doUnselect(Lists.newArrayList(defaults).get(defaults.size() - 1));

                ImmutableCollection<IDataGridColumn> curr = dgc2.getSelectedOptions();
                assertThat(curr.size()).isNotEqualTo(defaults.size());

                dgc2.doResetSelections();
                dgc2 = null;

                IDataGridCustomizer dgc3 = getDataGridCustomizer();
                ImmutableCollection<IDataGridColumn> currDefaults = dgc3.getSelectedOptions();

                dgc3.doResetSelections();
                dgc3 = null;

                assertThat(currDefaults.size()).isEqualTo(defaults.size());
                assertThat(currDefaults).isEqualTo(defaults);
            }
        }
    }
}

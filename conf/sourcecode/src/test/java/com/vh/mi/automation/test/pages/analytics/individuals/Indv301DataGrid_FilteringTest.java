package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301Columns;
import com.vh.mi.automation.impl.utils.FileUtils;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_FilterTest;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption;

/**
 * Created by nimanandhar on 11/4/2014.
 */
public class Indv301DataGrid_FilteringTest extends AbstractDataGrid_FilterTest {

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        Indv301 indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        indv301.popupExists();
        IAnalysisPeriod analysisPeriod = indv301.getAnalysisPeriod();
        if (analysisPeriod.getAvailableOptions().contains(APOption.FULL_CYCLE)) {
            analysisPeriod.doSelect(APOption.FULL_CYCLE);
        }


        IDataGrid dataGrid1 = indv301.getDataGrid();
        if (indv301.isDataGridCustomizable()) {
            IDataGridCustomizer columnCustomizer = indv301.getDataGridCustomizer();
            columnCustomizer.doSelectAll();
            columnCustomizer.doApplySelection();
            new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();
            dataGrid1.doFlushAllCache();
        }

        return dataGrid1;
    }

    @Override
    protected Map<IDataGridColumn, String> removeEmptyAndNonFilterableCols(Map<IDataGridColumn, String> firstRow) {
        Map<IDataGridColumn, String> filterValues = super.removeEmptyAndNonFilterableCols(firstRow);

        if (filterValues.containsKey(Indv301Columns.HOME_PHONE)) //don't filter on HOME PHONE column
            filterValues.remove(Indv301Columns.HOME_PHONE);
        return filterValues;
    }

    @Override
    public List<IDataGridColumn> getColumnsToTest() {
        List<IDataGridColumn> columnsToTest = new ArrayList<>();
        for (String label : FileUtils.getLinesFromResourceFile("columns/indv301Columns.txt")) {
            IDataGridColumn column = Indv301Columns.fromLabel(label);
            if (column == null) {
                logger.error("Column is null for label  " + label);
            } else {
                columnsToTest.add(column);
            }
        }
        return columnsToTest;

    }
}

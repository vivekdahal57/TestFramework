package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption;

/**
 * Spike
 * Purpose: To divide  individual sorting test into half
 * Created by nimanandhar on 10/29/2014.
 */
public class Indv301DataGrid_SortingTestPart2 /*extends AbstractDataGrid_SortingTest*/ {

   /* @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        Indv301 indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        indv301.getAnalysisPeriod().doSelect(APOption.FULL_CYCLE);
        IDataGrid dataGrid = indv301.getDataGrid();

        if (indv301.isDataGridCustomizable()) {
            IDataGridCustomizer columnCustomizer = indv301.getDataGridCustomizer();
            columnCustomizer.doSelectAll();
            columnCustomizer.doApplySelection();
            new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();
        }
        return dataGrid;
    }

    *//**
     * Given a list of columns, this method sorts them and then choose only the first half
     * to test
     *//*
    @Override
    protected List<IDataGridColumn> filter(final List<IDataGridColumn> sortableColumns) {
        Comparator<IDataGridColumn> comparator = new Comparator<IDataGridColumn>() {
            @Override
            public int compare(IDataGridColumn o1, IDataGridColumn o2) {
                return o1.getId().compareTo(o2.getId());
            }
        };

        List<IDataGridColumn> sortedColumn = new ArrayList<>(sortableColumns);
        Collections.sort(sortedColumn, comparator);
        return sortedColumn.subList(sortedColumn.size() / 2, sortedColumn.size());
    }*/
}

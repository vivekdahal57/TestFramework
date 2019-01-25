package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

import static com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption;

/**
 * Created by nimanandhar on 10/29/2014.
 */
public class Indv301DataGrid_SortingTest extends AbstractDataGrid_SortingTest {

    @BeforeClass
    public void setUp() {
        super.setUp();
    }


    @Override
    protected IDataGrid getDataGrid() {
        Indv301 indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        indv301.popupExists();
        indv301.getAnalysisPeriod().doSelect(APOption.FULL_CYCLE);
        IDataGrid dataGrid = indv301.getDataGrid();

        if (indv301.isDataGridCustomizable()) {
            IDataGridCustomizer columnCustomizer = indv301.getDataGridCustomizer();

            columnCustomizer.doSelectAll();
            columnCustomizer.doApplySelection();
            dataGrid.doFlushAllCache();
            new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();
        }
        return dataGrid;
    }

}

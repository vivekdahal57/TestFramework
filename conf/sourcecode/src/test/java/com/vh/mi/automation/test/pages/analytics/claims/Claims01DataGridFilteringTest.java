package com.vh.mi.automation.test.pages.analytics.claims;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.pages.analytics.claims.IClaims01;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.claims.Claims01;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_FilterTest;
import org.testng.annotations.BeforeClass;

import java.util.List;

import static com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption;

/**
 * Created by nimanandhar on 12/9/2014.
 */
public class Claims01DataGridFilteringTest extends AbstractDataGrid_FilterTest {

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        IClaims01 claims01 = navigationPanel.doNavigateTo(Claims01.class, defaultWaitTime);
        claims01.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());

        IAnalysisPeriod analysisPeriod = claims01.getAnalysisPeriod();
        if (analysisPeriod.getAvailableOptions().contains(APOption.FULL_CYCLE)) {
            analysisPeriod.doSelect(APOption.FULL_CYCLE);
        }

        IDataGridCustomizer customizer = claims01.getDataGridCustomizer();
        customizer.doSelectAll();
        customizer.doApplySelection();
        new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();

        return claims01.getDataGrid();
    }

    @Override
    public List<IDataGridColumn> getColumnsToTest() {
        return dataGrid.getColumns(); //test all columns
    }
}

package com.vh.mi.automation.test.pages.analytics.hcciClaims;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

import static com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption;

/**
 * Created by nimanandhar on 12/9/2014.
 */
public class HcciClaims01DataGridSortingTest extends AbstractDataGrid_SortingTest {

    HcciClaims01 claims101;

    @BeforeClass
    public void setUp() {
        super.setUp();
        }

    @Override
    protected IDataGrid getDataGrid() {
        claims101  = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
        IAnalysisPeriod analysisPeriod = claims101.getAnalysisPeriod();
        if (analysisPeriod.getAvailableOptions().contains(APOption.FULL_CYCLE)) {
            analysisPeriod.doSelect(APOption.FULL_CYCLE);
        }

        IDataGridCustomizer customizer = claims101.getDataGridCustomizer();
        customizer.doSelectAll();
        customizer.doApplySelection();
        new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();

        return claims101.getDataGrid();
    }
}

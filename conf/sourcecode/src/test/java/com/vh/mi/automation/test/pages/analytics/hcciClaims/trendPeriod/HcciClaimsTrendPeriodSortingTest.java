package com.vh.mi.automation.test.pages.analytics.hcciClaims.trendPeriod;


import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.ap.ITrendingPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption.TREND_PERIODS;


/**
 * Created by i20841 on 7/5/2018.
 */
@Test(groups = "hcciClaims101", sequential = true)
public class HcciClaimsTrendPeriodSortingTest extends AbstractDataGrid_SortingTest {

    private HcciClaims01 hcciClaims101;

    @BeforeClass(description = "navigate to claims")
    public void setUp() {
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {

        hcciClaims101 = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);

        IAnalysisPeriod analysisPeriod = hcciClaims101.getAnalysisPeriod();
        ITrendingPeriod tpComp = (ITrendingPeriod) analysisPeriod.doSelect(TREND_PERIODS);
        tpComp.doApplyPeriodsSelection();

        IDataGridCustomizer customizer = hcciClaims101.getDataGridCustomizer();
        customizer.doSelectAll();
        customizer.doApplySelection();
        new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();
        return hcciClaims101.getDataGrid();
    }
}

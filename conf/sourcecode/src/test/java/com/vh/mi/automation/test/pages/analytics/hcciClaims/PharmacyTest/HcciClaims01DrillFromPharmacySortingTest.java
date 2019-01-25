package com.vh.mi.automation.test.pages.analytics.hcciClaims.PharmacyTest;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * Created by i20841 on 7/26/2018.
 */
@Test(groups = "hcciClaims101", sequential = true)
public class HcciClaims01DrillFromPharmacySortingTest extends AbstractDataGrid_SortingTest {
    private HcciClaims01 hcciClaims101;
    //IDataGrid dataGrid;

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {

        hcciClaims101 = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
        dataGrid = hcciClaims101.getDataGrid();
        for (int i = 0; i < dataGrid.getRows().size(); i++) {
            if (dataGrid.getRows().get(i).getValue(2).equalsIgnoreCase("pharmacy")) {
                System.out.println("---------->Testing Pharmacy");
                dataGrid.getRows().get(i).doDrillByOnSameWindow("Drug");
                break;
            }
        }

        IAnalysisPeriod analysisPeriod = hcciClaims101.getAnalysisPeriod();
        if (analysisPeriod.getAvailableOptions().contains(IAnalysisPeriod.APOption.FULL_CYCLE)) {
            analysisPeriod.doSelect(IAnalysisPeriod.APOption.FULL_CYCLE);
        }

        IDataGridCustomizer customizer = hcciClaims101.getDataGridCustomizer();
        customizer.doSelectAll();
        customizer.doApplySelection();
        new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();
        return dataGrid;
    }
}

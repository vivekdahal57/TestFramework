package com.vh.mi.automation.test.pages.analytics.hcciClaims.PharmacyTest;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_FilterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;


/**
 * Created by i20841 on 7/26/2018.
 */
@Test(groups = "hcciClaims101", sequential = true)
public class HcciClaims01DrillFromPharmacyFilteringTest extends AbstractDataGrid_FilterTest {
    private HcciClaims01 hcciClaims101;

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

    @Override
    public List<IDataGridColumn> getColumnsToTest() {
        return dataGrid.getColumns(); //test all columns
    }
}

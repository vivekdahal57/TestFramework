package com.vh.mi.automation.test.pages.analytics.hcciClaims.drillToServices;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciClaims01ServicesDataGridColumn;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01DrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciServicesDrillPage;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

import static com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption;

/**
 * Created by i10105 on 05/10/2018.
 */
public class HcciServicesDataGridSortingTest extends AbstractDataGrid_SortingTest {

    HcciServicesDrillPage claims01DrillPage;

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        HcciClaims01 claims101  = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);

        claims01DrillPage = (HcciServicesDrillPage) claims101.getDataGrid().getRows().get(0).doDrillBy("Services");

        IAnalysisPeriod analysisPeriod = claims01DrillPage.getAnalysisPeriod();
        if (analysisPeriod.getAvailableOptions().contains(APOption.FULL_CYCLE)) {
            analysisPeriod.doSelect(APOption.FULL_CYCLE);
        }

        IDataGridCustomizer customizer = claims01DrillPage.getDataGridCustomizerServices();
        customizer.doSelectAll();
        customizer.doApplySelection();
        new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();
        return claims01DrillPage.getDataGrid();
    }
}
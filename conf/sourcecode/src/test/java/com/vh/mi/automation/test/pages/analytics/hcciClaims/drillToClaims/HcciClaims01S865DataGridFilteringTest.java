package com.vh.mi.automation.test.pages.analytics.hcciClaims.drillToClaims;

import com.google.common.collect.Table;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.comp.dataGrid.gridValue.GridValueFactory;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciS865DataGrid;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciS865DrillPage;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_FilterTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.Map;

import static com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by nimanandhar on 12/9/2014.
 */
public class HcciClaims01S865DataGridFilteringTest extends AbstractDataGrid_FilterTest {

    HcciS865DrillPage hcciS865DrillPage;

    @BeforeClass
    public void setUp() {
        super.setUp();
    }


    @Override
    protected IDataGrid getDataGrid() {

        HcciClaims01 claims101  = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
        hcciS865DrillPage = (HcciS865DrillPage) claims101.getDataGrid().getRows().get(0).doDrillBy("Claims");
        IAnalysisPeriod analysisPeriod = hcciS865DrillPage.getAnalysisPeriod();
        if (analysisPeriod.getAvailableOptions().contains(APOption.FULL_CYCLE)) {
            analysisPeriod.doSelect(APOption.FULL_CYCLE);
        }

        IDataGridCustomizer customizer = hcciS865DrillPage.getDataGridCustomizerS865();
        customizer.doSelectAll();
        customizer.doApplySelection();
        new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();

        return hcciS865DrillPage.getDataGrid();
    }

    @Override
    public List<IDataGridColumn> getColumnsToTest() {
        return dataGrid.getColumns(); //test all columns
    }
}

package com.vh.mi.automation.test.pages.analytics.utilizationMetrics;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.utilizationMetrics.IUtilizationMetricsHM50ByGroupSize;
import com.vh.mi.automation.impl.pages.analytics.utilizationMetrics.UtilizationMetricsHM50;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;


/**
 * Test BL Component on Utilization Metrics Page
 * Created by nimanandhar on 1/16/2015.
 */
public class UtilizationMetricsBLComponentTest extends AbstractBLCompTest {
    private IUtilizationMetricsHM50ByGroupSize umPage;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        umPage = navigationPanel.doNavigateTo(UtilizationMetricsHM50.class, defaultWaitTime);
    }

    @Override
    protected IBusinessLevelsComponent getBusinessLevelsComponent() {
        return umPage.getBusinessLevel();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return umPage.getDataGrid();
    }

}

package com.vh.mi.automation.test.pages.analytics.networkUtilization;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.impl.pages.analytics.networkUtilization.NetworkUtilizationNU105;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;

/**
 * @author nimanandhar
 */
public class NetworkUtilizationNU105BLComponentTest extends AbstractBLCompTest {
    private NetworkUtilizationNU105 networkUtilizationNU105Page;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        networkUtilizationNU105Page = navigationPanel.doNavigateTo(NetworkUtilizationNU105.class, context.getDefaultWaitTimeout());
    }

    @Override
    protected IBusinessLevelsComponent getBusinessLevelsComponent() {
        return networkUtilizationNU105Page.getBusinessLevel();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return networkUtilizationNU105Page.getDataGrid();
    }
}

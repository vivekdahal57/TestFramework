package com.vh.mi.automation.test.pages.analytics.providerProfiler;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.ProviderProfilerV044;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;

/**
 * @author nimanandhar
 */
public class ProviderProfilerV044BLComponentTest extends AbstractBLCompTest {
    private ProviderProfilerV044 providerProfilerV044Page;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        providerProfilerV044Page = navigationPanel.doNavigateTo(ProviderProfilerV044.class, context.getDefaultWaitTimeout());
    }

    @Override
    protected IBusinessLevelsComponent getBusinessLevelsComponent() {
        return providerProfilerV044Page.getBusinessLevel();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return providerProfilerV044Page.getDataGrid();
    }
}

package com.vh.mi.automation.test.pages.analytics.spikes;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.impl.pages.analytics.spikes.SpikesS110B;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;

/**
 * @author nimanandhar
 */
public class SpikesS110BBLComponentTest extends AbstractBLCompTest {
    private SpikesS110B spikesS110BPage;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }
    @Override
    protected void initializePage() {
        spikesS110BPage = navigationPanel.doNavigateTo(SpikesS110B.class, context.getDefaultWaitTimeout());
    }

    @Override
    protected IBusinessLevelsComponent getBusinessLevelsComponent() {
        return spikesS110BPage.getBusinessLevel();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return null;
    }
}

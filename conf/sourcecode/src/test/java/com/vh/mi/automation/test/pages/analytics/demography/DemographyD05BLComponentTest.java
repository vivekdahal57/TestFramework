package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * @author nimanandhar
 */
public class DemographyD05BLComponentTest extends AbstractBLCompTest {
    private DemographyD05 demographyD05;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        demographyD05 = navigationPanel.doNavigateTo(DemographyD05.class, context.getDefaultWaitTimeout());
    }

    @Override
    protected IBusinessLevelsComponent getBusinessLevelsComponent() {
        return demographyD05.getBusinessLevel();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return demographyD05.getDataGrid();
    }

}

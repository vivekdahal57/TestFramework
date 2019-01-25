package com.vh.mi.automation.test.pages.analytics.hospitalProfiler;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.HospitalProfilesHP100;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;

/**
 * @author nimanandhar
 */
public class HospitalProfilesHP100BLComponentTest extends AbstractBLCompTest {
    private HospitalProfilesHP100 hospitalProfilesHP100Page;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }


    @Override
    protected void initializePage() {
        hospitalProfilesHP100Page = navigationPanel.doNavigateTo(HospitalProfilesHP100.class, context.getDefaultWaitTimeout());
    }

    @Override
    protected IBusinessLevelsComponent getBusinessLevelsComponent() {
        return hospitalProfilesHP100Page.getBusinessLevel();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return hospitalProfilesHP100Page.getDataGrid();
    }
}

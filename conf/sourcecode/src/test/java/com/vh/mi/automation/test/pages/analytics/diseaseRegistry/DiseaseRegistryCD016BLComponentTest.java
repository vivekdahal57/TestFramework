package com.vh.mi.automation.test.pages.analytics.diseaseRegistry;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.DiseaseRegistryCD016;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DiseaseRegistryCD016BLComponentTest extends AbstractBLCompTest {
    private DiseaseRegistryCD016 diseaseRegistryCD016;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        diseaseRegistryCD016 = navigationPanel.doNavigateTo(DiseaseRegistryCD016.class, defaultWaitTime);
    }

    @Override
    protected IBusinessLevelsComponent getBusinessLevelsComponent() {
        return diseaseRegistryCD016.getBusinessLevel();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return null;
    }

}

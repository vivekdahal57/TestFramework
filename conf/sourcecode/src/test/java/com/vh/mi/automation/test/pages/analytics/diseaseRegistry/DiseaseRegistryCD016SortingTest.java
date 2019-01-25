package com.vh.mi.automation.test.pages.analytics.diseaseRegistry;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.diseaseRegistry.IDiseaseRegistryCD016;
import com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.DiseaseRegistryCD016;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 3/13/18.
 */
public class DiseaseRegistryCD016SortingTest extends AbstractDataGrid_SortingTest {
    private IDiseaseRegistryCD016 diseaseRegistryCD016 ;

    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        diseaseRegistryCD016 = navigationPanel.doNavigateTo(DiseaseRegistryCD016.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle().equals("(CD016) Disease Registry"));
        return diseaseRegistryCD016.getDataGrid();
    }
}

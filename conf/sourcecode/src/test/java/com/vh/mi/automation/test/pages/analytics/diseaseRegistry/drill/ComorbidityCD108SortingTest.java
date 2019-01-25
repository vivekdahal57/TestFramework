package com.vh.mi.automation.test.pages.analytics.diseaseRegistry.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.diseaseRegistry.IDiseaseRegistryCD016;
import com.vh.mi.automation.api.pages.analytics.diseaseRegistry.drill.IComorbidityCD108DrillPage;
import com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.DiseaseRegistryCD016;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 3/14/18.
 */
public class ComorbidityCD108SortingTest extends AbstractDataGrid_SortingTest {
    private IDiseaseRegistryCD016 diseaseRegistryCD016 ;
    private IComorbidityCD108DrillPage comorbidityCD108DrillPage;


    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        diseaseRegistryCD016 = navigationPanel.doNavigateTo(DiseaseRegistryCD016.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle().equals(diseaseRegistryCD016.getPageTitle()));
        comorbidityCD108DrillPage = (IComorbidityCD108DrillPage) diseaseRegistryCD016.drillDownToPage("Comorbidity");
        return comorbidityCD108DrillPage.getDataGrid();
    }
}

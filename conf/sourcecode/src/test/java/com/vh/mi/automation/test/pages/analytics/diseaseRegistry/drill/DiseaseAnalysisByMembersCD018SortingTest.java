package com.vh.mi.automation.test.pages.analytics.diseaseRegistry.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.diseaseRegistry.IDiseaseRegistryCD016;
import com.vh.mi.automation.api.pages.analytics.diseaseRegistry.drill.IDiseaseRegistryCD018DrillPage;
import com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.DiseaseRegistryCD016;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 3/16/18.
 */
public class DiseaseAnalysisByMembersCD018SortingTest extends AbstractDataGrid_SortingTest {
    private IDiseaseRegistryCD016 diseaseRegistryCD016;
    private IDiseaseRegistryCD018DrillPage diseaseRegistryCD018DrillPage;


    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid(){
     diseaseRegistryCD016 = navigationPanel.doNavigateTo(DiseaseRegistryCD016.class, defaultWaitTime);
     assertThat(navigationPanel.getCurrentPageTitle().equals(diseaseRegistryCD016.getPageTitle()));
     diseaseRegistryCD018DrillPage  = (IDiseaseRegistryCD018DrillPage) diseaseRegistryCD016.drillDownToDifferentWindow("Newly Identified Members");
     return diseaseRegistryCD018DrillPage.getDataGrid();
    }
}

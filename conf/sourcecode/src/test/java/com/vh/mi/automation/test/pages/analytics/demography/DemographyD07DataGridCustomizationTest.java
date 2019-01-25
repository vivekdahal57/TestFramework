package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.api.pages.analytics.demography.drill.ID07AgeGroupDrillPage;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.comp.dataGrid.DataGridCustomizationTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.DEMOGRAPHY;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 3/2/18.
 */
public class DemographyD07DataGridCustomizationTest  extends DataGridCustomizationTest{
    private IDemographyD05 demoD05;
    private ID07AgeGroupDrillPage d07AgeGroupDrillPage;

    @BeforeClass
    public void setUp(){
       super.setUp();
      demoD05 =  navigationPanel.doNavigateTo(DemographyD05.class, defaultWaitTime);
      assertThat(navigationPanel.getCurrentPageTitle().equalsIgnoreCase(DEMOGRAPHY.getPageTitle()));
      d07AgeGroupDrillPage = (ID07AgeGroupDrillPage) demoD05.drillDownToPage("Age Group");

    }

    @Override
    public IDataGridCustomizer getDataGridCustomizer() {
        return d07AgeGroupDrillPage.getDataGridCustomizer();
    }

    @Override
    public boolean isDataGridCustomizable() {
        return d07AgeGroupDrillPage.isDataGridCustomizable();
    }

    @Test
    public void test_demoD07_instance() {
        assertThat(d07AgeGroupDrillPage).isNotNull();
    }

    @Test
    public void test_demoD07_navigation() {
        String currentPageTitle = navigationPanel.getCurrentPageTitle();
        assertThat(currentPageTitle).isEqualTo(d07AgeGroupDrillPage.getPageTitle());
    }
}

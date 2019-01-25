package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.api.pages.analytics.demography.drill.ID07AgeGroupDrillPage;
import com.vh.mi.automation.api.pages.analytics.demography.drill.ID09AgeGroupDrillPage;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.comp.dataGrid.DataGridCustomizationTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.DEMOGRAPHY;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 3/5/18.
 */
public class DemographyD09DataGridCustomizationTest extends DataGridCustomizationTest {
    private IDemographyD05 demoD05;
    private ID07AgeGroupDrillPage d07AgeGroupDrillPage;
    private ID09AgeGroupDrillPage d09AgeGroupDrillPage;

    @BeforeClass
    public void setUp(){
        super.setUp();
        demoD05 = navigationPanel.doNavigateTo(DemographyD05.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle().equalsIgnoreCase(DEMOGRAPHY.getPageTitle()));
        d07AgeGroupDrillPage = (ID07AgeGroupDrillPage) demoD05.drillDownToPage("Age Group");
        assertThat(d07AgeGroupDrillPage.getPageTitle().equals("(D07) Total Paid by Age Group"));
        d09AgeGroupDrillPage = (ID09AgeGroupDrillPage) d07AgeGroupDrillPage.drillDownToPage("Age Sub-group");
        assertThat(d09AgeGroupDrillPage.getPageTitle().equals("(D09) Total Paid by Age Sub-Groups"));
    }

    @Override
    public IDataGridCustomizer getDataGridCustomizer() {
     return d09AgeGroupDrillPage.getDataGridCustomizer();
    }

    @Override
    public boolean isDataGridCustomizable() {
        return d09AgeGroupDrillPage.isDataGridCustomizable();
    }


    @Test
    public void test_demoD09_instance() {
        assertThat(d09AgeGroupDrillPage).isNotNull();
    }

    @Test
    public void test_demoD07_navigation() {
        String currentPageTitle = navigationPanel.getCurrentPageTitle();
        assertThat(currentPageTitle).isEqualTo(d09AgeGroupDrillPage.getPageTitle());
    }
}

package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.comp.dataGrid.DataGridCustomizationTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.DEMOGRAPHY;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by nimanandhar on 9/10/2014.
 */
public class DemographyD05DataGridCustomizationTest extends DataGridCustomizationTest {
    private IDemographyD05 demoD05;

    @BeforeClass
    public void setUp() {
        super.setUp();
        demoD05 = navigationPanel.doNavigateTo(DemographyD05.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(DEMOGRAPHY.getPageTitle());
    }

    public IDataGridCustomizer getDataGridCustomizer() {
        return demoD05.getDataGridCustomizer();
    }

    @Override
    public boolean isDataGridCustomizable() {
        return demoD05.isDataGridCustomizable();
    }

    @Test
    public void test_demoD05_instance() {
        assertThat(demoD05).isNotNull();
    }

    @Test
    public void test_demoD05_navigation() {
        String currentPageTitle = navigationPanel.getCurrentPageTitle();
        assertThat(currentPageTitle).isEqualTo(demoD05.getPageTitle());
    }
}

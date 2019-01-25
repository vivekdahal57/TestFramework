package com.vh.mi.automation.test.pages.analytics.hospitalProfiler;

import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.pages.analytics.hospitalProfiler.IHospitalProfilesHP100;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.HospitalProfilesHP100;
import com.vh.mi.automation.test.comp.dataGrid.DataGridCustomizationTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.HOSPITAL_PROFILER;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80448 on 11/20/2014.
 */
public class HospitalProfilesHP100DataGridCustomizationTest extends DataGridCustomizationTest {
    private IHospitalProfilesHP100 hp100;

    @BeforeClass
    public void setUp() {
        super.setUp();
        hp100 = navigationPanel.doNavigateTo(HospitalProfilesHP100.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(HOSPITAL_PROFILER.getPageTitle());
    }


    public IDataGridCustomizer getDataGridCustomizer() {
        return hp100.getDataGridCustomizer();
    }

    @Override
    public boolean isDataGridCustomizable() {
        return hp100.isDataGridCustomizable();
    }

    @Test
    public void test_hp100_instance() {
        assertThat(hp100).isNotNull();
    }

    @Test
    public void test_indv301_navigation() {
        String currentPageTitle = navigationPanel.getCurrentPageTitle();
        assertThat(currentPageTitle).isEqualTo(hp100.getPageTitle());
    }
}

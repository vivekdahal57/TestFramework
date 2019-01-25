package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.test.comp.dataGrid.DataGridCustomizationTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.INDIVIDUALS_301;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80448 on 11/20/2014.
 */
public class Indv301DataGridCustomizationTest extends DataGridCustomizationTest {
    private Indv301 indv301;

    @BeforeClass
    public void setUp() {
        super.setUp();
        indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(INDIVIDUALS_301.getPageTitle());
        indv301.popupExists();
    }


    public IDataGridCustomizer getDataGridCustomizer() {
        return indv301.getDataGridCustomizer();
    }

    @Override
    public boolean isDataGridCustomizable() {
        return indv301.isDataGridCustomizable();
    }

    @Test
    public void test_indv301_instance() {
        assertThat(indv301).isNotNull();
    }

    @Test
    public void test_indv301_navigation() {
        String currentPageTitle = navigationPanel.getCurrentPageTitle();
        assertThat(currentPageTitle).isEqualTo(indv301.getPageTitle());
    }
}

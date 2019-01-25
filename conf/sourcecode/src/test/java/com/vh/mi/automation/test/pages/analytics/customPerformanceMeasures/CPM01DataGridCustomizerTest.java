package com.vh.mi.automation.test.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.comp.dataGrid.customizer.ICPM01DataGridCustomizer;
import com.vh.mi.automation.api.comp.navigation.INavigationPanel;
import com.vh.mi.automation.api.constants.MILandingPages;
import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01;
import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01DataGridColumn;
import com.vh.mi.automation.api.pages.common.ILoginPage;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.comp.favqm.ListScope;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01DataGridColumn;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 Created by i82298 on 2/27/2017. */
public class CPM01DataGridCustomizerTest extends BaseTest {
    ICPM01 cpm01;
    ILoginPage loginPage;

    @Override
    public boolean skipBrowserCreation() {
        return true;
    }

    @BeforeClass()
    public void setUp() {
        super.setUp();

    }

    @BeforeMethod
    public void beforeTestMethod() {
        createNewBrowserInstance();
        loginPage = mi.open(context.getApplication());
        loginPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        IWelcomePage welcomePage = loginPage
                .loginWith(getUser(), defaultWaitTime);
        INavigationPanel navigationPanel = welcomePage
                .selectFront(getUser().getAssignedApplications().get(0));
        navigationPanel.doWaitTillFullyLoaded(defaultWaitTime);
        cpm01 = navigationPanel.doNavigateTo(CPM01.class, defaultWaitTime);
    }

    @AfterMethod
    public void afterTest() {

        closeBrowserInstance();
    }

    @Test
    public void testApplyCustomizationEventWorks() {
        ICPM01DataGridCustomizer customizer = cpm01.getDataGridCustomizer();
        customizer.doSelect(CPM01DataGridColumn.ADMITS_PER_1K);
        customizer.doApplySelection();
        customizer = cpm01.getDataGridCustomizer();
        assertThat(customizer.isSelected(CPM01DataGridColumn.ADMITS_PER_1K))
                .isTrue();
    }

    @Test()
    public void testThatSelectAllEventWorks() {
        ICPM01DataGridCustomizer customizer = cpm01.getDataGridCustomizer();
        customizer.doSelectMainCatagory(CPM01DataGridColumn.COST_GROUP);
        customizer.doSelectSubCatagory(CPM01DataGridColumn.PMPM_GROUP);
        customizer.doSelectAll();
        customizer.doApplySelection();
         customizer = cpm01.getDataGridCustomizer();
        for (ICPM01DataGridColumn col:CPM01DataGridColumn.getColumnsUnderGroup(CPM01DataGridColumn.PMPM_GROUP) ) {
        assertThat(customizer.isSelected(col)).isTrue();
        }

    }


    @Test()
    public void testThatUnSelectAllEventWorks() {
        ICPM01DataGridCustomizer customizer = cpm01.getDataGridCustomizer();
        customizer.doSelectMainCatagory(CPM01DataGridColumn.COST_GROUP);
        customizer.doSelectSubCatagory(CPM01DataGridColumn.PMPM_GROUP);
        customizer.doUnselectAll();
        customizer.doApplySelection();
        customizer = cpm01.getDataGridCustomizer();
        for (ICPM01DataGridColumn col:CPM01DataGridColumn.getColumnsUnderGroup(CPM01DataGridColumn.PMPM_GROUP) ) {
            assertThat(customizer.isSelected(col)).isFalse();
        }

    }

}

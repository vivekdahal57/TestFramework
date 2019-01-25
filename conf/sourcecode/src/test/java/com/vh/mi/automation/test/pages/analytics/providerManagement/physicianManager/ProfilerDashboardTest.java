package com.vh.mi.automation.test.pages.analytics.providerManagement.physicianManager;

import com.vh.mi.automation.api.pages.analytics.providerManagement.PhysicianManager.Drill.IProfilerDashboardDrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.physicianManager.CP110;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

@Test (groups = {"Product-Critical" })
public class ProfilerDashboardTest extends BaseTest {
    private CP110 cp110;
    private IProfilerDashboardDrillPage cp150;


    @BeforeClass
    public void setUp() {
        super.setUp();
        cp110 = navigationPanel.doNavigateTo(CP110.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(cp110.getPageTitle());
    }

    @Test
    public void CP150LoadTest() {
        cp150  = cp110.drillDownToProfilerDashboard();
        assertThat(cp150.getPageTitle()).isEqualToIgnoringCase("(CP150) Profiler Dashboard");
    }
}

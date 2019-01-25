package com.vh.mi.apiAutomation.util;

import com.vh.mi.apiAutomation.AbstractDeveloperPageAction;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.analytics.myDashboard.MyDashboard;
import org.fest.assertions.Assertions;
import org.testng.annotations.Test;

public class ApplyCohort extends AbstractDeveloperPageAction {
    MyDashboard userDashboard;

    public static String getRandomCohortName() {
        return Random.getRandomStringOfLength(10);
    }

    @Test
    public void addDeleteDashboardAddDeleteWidgetWithApplySFWCohortTest() {
        super.setUp("miautomation_group_level_sfw_user");
        userDashboard = navigationPanel.doNavigateTo(MyDashboard.class, 60);
        String[] cohortId = {SFW_DYNAMIC_COHORTID, SFW_STATIC_COHORTID};
        //Verify New Dashboard can be added
        String dashboardName = getRandomCohortName();
        String name = userDashboard.addNewDashboard(dashboardName);
        Assertions.assertThat(dashboardName.equalsIgnoreCase(name)).isTrue();
        //Verify widgets can be added
        userDashboard.addNewWidget();
        try {
            Thread.sleep(5000);
            for (String cid : cohortId) {
                userDashboard.addExistingCohort(cid);
            }
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Verify Dashboard can be deleted
        userDashboard.deleteDashboard();
    }

    @Test
    public void addDeleteDashboardAddDeleteWidgetWithApplyCohortTest() {
        super.setUp("miautomation_super_user");
        userDashboard = navigationPanel.doNavigateTo(MyDashboard.class, 60);
        String[] cohortId = {DYNAMIC_COHORTID, STATIC_COHORTID};
        //Verify New Dashboard can be added
        String dashboardName = getRandomCohortName();
        String name = userDashboard.addNewDashboard(dashboardName);
        Assertions.assertThat(dashboardName.equalsIgnoreCase(name)).isTrue();
        //Verify widgets can be added
        userDashboard.addNewWidget();
        try {
            Thread.sleep(3000);
            for (String cid : cohortId) {
                userDashboard.addExistingCohort(cid);
            }
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Verify Dashboard can be deleted
        userDashboard.deleteDashboard();
    }
}

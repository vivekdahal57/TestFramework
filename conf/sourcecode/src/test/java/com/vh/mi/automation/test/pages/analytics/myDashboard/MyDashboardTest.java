package com.vh.mi.automation.test.pages.analytics.myDashboard;

import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.analytics.myDashboard.MyDashboard;
import com.vh.mi.automation.test.base.BaseTest;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by i20345 on 11/13/2017.
 */
public class MyDashboardTest extends BaseTest {
    MyDashboard userDashboard;

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    public static String getRandomCohortName() {
        return Random.getRandomStringOfLength(10);
    }

    @BeforeMethod
    public void beforeEach() {
        userDashboard = navigationPanel.doNavigateTo(MyDashboard.class, 60);
    }

    @Test
    public void addDeleteDashboardaddDeleteWidgetTest() {
        //Verify New Dashboard can be added
        String dashboardName = getRandomCohortName();
        String Name = userDashboard.addNewDashboard(dashboardName);
        Assertions.assertThat(dashboardName.equalsIgnoreCase(Name)).isTrue();
        //Verify widgets can be added
        userDashboard.addNewWidget();
        //Verify Dashboard can be deleted
        userDashboard.deleteDashboard();
    }
}

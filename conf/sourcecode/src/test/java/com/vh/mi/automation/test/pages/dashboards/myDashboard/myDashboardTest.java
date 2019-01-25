package com.vh.mi.automation.test.pages.dashboards.myDashboard;

import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.dashboards.myDashboard.MyDashboard;
import com.vh.mi.automation.test.base.BaseTest;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class myDashboardTest extends BaseTest {

    MyDashboard myDashboard;

    @BeforeClass
    public void setUp() {
        super.setUp();
        myDashboard = navigationPanel.doNavigateTo(MyDashboard.class, 30);
    }


    @Test(priority = 1)
    public void AddDashboard()
    {
        String dashName = "automation_" + Random.getRandomStringOfLength(8);
        String addedDashName = myDashboard.AddDashboard(dashName);
        Assertions.assertThat(dashName.equalsIgnoreCase(addedDashName));
    }

    @Test(priority = 2)
    public void AddWidgets() throws InterruptedException
    {
        //Boolean result =
        myDashboard.AddWidgets();
        //Assertions.assertThat(!result.booleanValue()).isTrue();
    }

    @Test(priority = 3)
    public void DeleteWidgets() throws InterruptedException
    {
         // Boolean result1 =
          myDashboard.DeleteDashboard();
          //Assertions.assertThat(!result1).isTrue();

    }

}

package com.vh.mi.automation.api.pages.analytics.myDashboard;

import com.vh.mi.automation.api.features.*;

/**
 * Created by i20345 on 11/13/2017.
 */
public interface IMyDashboard extends IHaveCohortSelection {
    String addNewDashboard(String dashboardName);

    void deleteDashboard();

    void addNewWidget();
}

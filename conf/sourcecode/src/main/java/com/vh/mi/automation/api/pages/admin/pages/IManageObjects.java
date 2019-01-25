package com.vh.mi.automation.api.pages.admin.pages;

import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IApplications;
import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IUsers;

/**
 * Created by i81306 on 5/11/2017.
 */
public interface IManageObjects extends IAmWebComponent {

    public IApplications goToApplicationsPage();
    public IUsers goToUsersPage();
}

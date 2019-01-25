package com.vh.mi.automation.api.pages.admin.pages.manageObjects;

import com.vh.mi.automation.api.features.IAmWebComponent;

/**
 * Created by i81306 on 5/11/2017.
 */
public interface IChangeDatabase extends IAmWebComponent {
    public void changeDatabaseTo(String databaseType, String databaseName);

    public IApplications switchDatabase(String databaseType, String databaseOne, String databaseTwo);
}

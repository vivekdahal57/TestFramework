package com.vh.mi.automation.api.pages.admin.pages.manageObjects;

import com.vh.mi.automation.api.features.IAmWebComponent;

/**
 * Created by i81306 on 5/15/2017.
 */
public interface IAddNewApplication extends IAmWebComponent {
    public IRegisterApplication connectToApplications(String databaseType, String schemaName, String processingType);
}

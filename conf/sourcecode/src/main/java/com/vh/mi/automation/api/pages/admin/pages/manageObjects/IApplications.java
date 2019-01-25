package com.vh.mi.automation.api.pages.admin.pages.manageObjects;

import com.vh.mi.automation.api.features.IAmWebComponent;

/**
 * Created by i81306 on 5/11/2017.
 */
public interface IApplications extends IAmWebComponent {
    public IApplicationStatus viewDetailsForApp(String appId);
    public void deleteApplication(String appId);
    public IAddNewApplication goToAddNewApplicationPage();
    public boolean isApplicationPostingSuccessful(String appId, int timeouts);
    public boolean hasPostingStarted(String appId);
}

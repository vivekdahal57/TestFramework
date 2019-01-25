package com.vh.mi.automation.api.pages.admin.pages.manageObjects;

import com.vh.mi.automation.api.features.IAmWebComponent;

/**
 * Created by i82716 on 5/25/2017.
 */
public interface IUsersRegistration extends IAmWebComponent {
    public IUsers registerUserToGroup(String groupName);
    public boolean isUserInSelectedGroup(String groupName);
    public boolean removeUserFromGroup(String groupName);
}

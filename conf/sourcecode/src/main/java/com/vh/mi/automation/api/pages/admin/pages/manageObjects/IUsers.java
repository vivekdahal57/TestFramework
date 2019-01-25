package com.vh.mi.automation.api.pages.admin.pages.manageObjects;

import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.impl.pages.admin.pages.manageObjects.UsersRegistration;

/**
 * Created by i81306 on 5/11/2017.
 */
public interface IUsers extends IAmWebComponent{
    public UsersRegistration goToUserRegistrationPage(String loginName, boolean isFilteringRequired);
}

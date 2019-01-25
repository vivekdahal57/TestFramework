package com.vh.mi.automation.api.pages.admin;

import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.api.pages.admin.pages.IManageObjects;
import com.vh.mi.automation.api.pages.admin.pages.ISecurity;

/**
 * @author nimanandhar
 */
public interface IAdminPage extends IAmWebComponent {

    String getPageTitle();

    IManageObjects goToManageObjectsPage();
    ISecurity goToSecurityPage();





}

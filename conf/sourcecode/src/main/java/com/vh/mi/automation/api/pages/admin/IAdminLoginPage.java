package com.vh.mi.automation.api.pages.admin;

import com.vh.mi.automation.api.features.IAmWebComponent;

/**
 * @author nimanandhar
 */
public interface IAdminLoginPage extends IAmWebComponent {

    IAdminPage loginWith(String userName, String password);
}

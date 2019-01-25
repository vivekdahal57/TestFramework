package com.vh.mi.automation.api.reportManager;

import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.api.pages.admin.IAdminPage;

/**
 * Created by i81306 on 8/9/2017.
 */
public interface IRMLoginPage extends IAmWebComponent {
    IReportManagerPage loginWith(String userName, String password);
}

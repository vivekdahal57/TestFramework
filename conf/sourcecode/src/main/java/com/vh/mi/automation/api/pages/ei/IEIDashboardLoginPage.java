package com.vh.mi.automation.api.pages.ei;

import com.vh.mi.automation.api.features.IAmWebComponent;

/**
 * Created by i81306 on 5/30/2017.
 */
public interface IEIDashboardLoginPage extends IAmWebComponent {
    IEIDashboard loginWith(String userName, String password);
}

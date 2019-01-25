package com.vh.mi.automation.api.reportManager.reportWizard;

import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.impl.reportManager.ReportAdministrator;

public interface IUserAccessTab extends IAmWebComponent {
    ReportAdministrator saveAndPublish();
}

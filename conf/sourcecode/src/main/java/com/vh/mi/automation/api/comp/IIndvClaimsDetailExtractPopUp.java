package com.vh.mi.automation.api.comp;

import com.vh.mi.automation.api.features.IAmWebComponent;

public interface IIndvClaimsDetailExtractPopUp extends IAmWebComponent {
    void exportToCsv(String members, String jobName);
    void exportToExcel(String members, String jobName);
}

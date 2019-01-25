package com.vh.mi.automation.api.reportManager.reportWizard;


import com.vh.mi.automation.api.comp.bl.BL;
import com.vh.mi.automation.api.reportManager.IReportManagerPage;
import com.vh.mi.automation.impl.reportManager.reportWizard.DataSelectionTab;
import com.vh.mi.automation.impl.reportManager.reportWizard.GenerationTab;
import com.vh.mi.automation.impl.reportManager.reportWizard.ReportsTab;
import com.vh.mi.automation.impl.reportManager.reportWizard.UserAccessTab;

import java.util.List;
import java.util.Map;

/**
 * Created by bibdahal on 1/28/2016.
 */
public interface IReportWizard extends IReportManagerPage {

    void goBack();

    List<String> getDisplayedTabRows();

    DataSelectionTab getDataSelectionTab();

    GenerationTab getGenerationTab();

    ReportsTab getReportsTab();

    UserAccessTab getUserAccessTab();

}

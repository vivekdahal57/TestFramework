package com.vh.mi.automation.api.reportManager;

import com.vh.mi.automation.impl.reportManager.reportWizard.ReportWizard;

import java.util.List;

/**
 * @author nimanandhar
 */
public interface IReportAdministrator extends IReportManagerPage {

    /**
     * Get applications listed in the dropdown
     *
     * @return
     */
    public List<String> getApplications();

    /**
     * Get the application that is currently selected
     *
     * @return
     */
    public String getSelectedApplication();

    public void goBack();

    public ReportWizard goToReportWizard(int waitTime);

    public <T> T logoutExpectingPage(Class<T> pageObjectClass, Integer waitTime);
}

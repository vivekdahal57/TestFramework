package com.vh.mi.automation.api.reportManager;

import com.vh.mi.automation.api.features.IAmWebComponent;

import java.util.List;

/**
 * @author nimanandhar
 */
public interface IReportManagerPage extends IAmWebComponent {


    /**
     * Unlike MI Page ReportManager Pages contains frames
     * and we need to switch the web driver to switch to
     * frame before we can find elements
     */
    public void doSwitchToMainFrame();


    public String getDisplayedTitle();


    public List<String> getDisplayedTabs();
}

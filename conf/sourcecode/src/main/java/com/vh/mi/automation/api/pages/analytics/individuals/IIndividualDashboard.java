package com.vh.mi.automation.api.pages.analytics.individuals;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.features.IAmTabbedPage;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.pages.common.IMIPage;

import java.util.List;

/**
 * Page Object API for Individual Dashboard: (320) Page
 *
 * @author nimanandhar
 */
public interface IIndividualDashboard extends IMIPage, IAmTabbedPage, IDrillPage {

    /**
     * Query to determine if the Next button (to navigate to
     * next member) is displayed. This will be important to
     * SAML login, which requires that the Next button is
     * unavailable.
     *
     * @return true if Next button is dsiplayed, false otherwise
     */
    public boolean isNextButtonDisplayed();


    /**
     * Returns the name of the current tab which is currently
     * selected
     *
     * @return the name of the currently selected tab
     */
    public String getSelectedTab();

    /**
     * @return A list of String with tabnames available in Individual Dashboard Pages
     */
    List<String> getAllTabs();


    /**
     * @return True if the tab is visible
     */
    boolean isTabAvailable(String tabName);


    /**
     * Returns a collection of all tabs that are visible in the
     * Individual Dashboard Page
     *
     * @return a list of available tabs
     */
    public List<String> getAvailableToolBarItems();

    ImmutableList<String> getDrillOptionsFor(String id);

    void clickDrillOptionForComponent(String componentId, String linkName);
}

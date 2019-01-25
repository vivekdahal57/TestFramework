package com.vh.mi.automation.api.features;

import com.vh.mi.automation.api.comp.dataGrid.customizer.ICustomizer;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizerEvents;
import com.vh.mi.automation.api.comp.navigation.INavigationPanel;
import com.vh.mi.automation.api.constants.SwitchBoard;
import com.vh.mi.automation.api.pages.common.IMIPage;

/**
 * Represents a MI landing page.
 * <p/>
 * Landing page :
 * <li>directly navigable from left navigation panel,</li>
 * <li>can be set as home page, and </li>
 * <li>add/remove to user's favorite pages list </li>
 *
 * @author i80448
 */
public interface IAmLandingPage extends IMIPage {

    /**
     * Gets switch board to which this landing page belongs to.
     *
     */
    public SwitchBoard getSwitchBoard();

    /**
     * Sets this page as user's home page, if not currently a home page.
     */
    public void doSetAsHomePage();

    /**
     * Adds this page to user's favorites list if not already in the list.
     */
    public void doAddToFavoritesList();

    /**
     * Removes this page from user's favorites list if is currently in the list.
     */
    public void doRemoveFromFavoriteList();

    /**
     * Checks if this page is currently in user's favorites list.
     *
     */
    public boolean isInFavoriteList();


    public INavigationPanel getNavigationPanel();

    void doCustomizeAllColumns();
}

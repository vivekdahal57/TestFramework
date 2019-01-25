package com.vh.mi.automation.api.comp.navigation;

import com.vh.mi.automation.api.constants.MILandingPages;
import com.vh.mi.automation.api.constants.SwitchBoard;
import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.api.pages.common.IWelcomePage;

import java.util.Collection;

/**
 * @author i80448
 */
public interface INavigationPanel extends IAmWebComponent {

    public Collection<SwitchBoard> getSwitchBoards();

    /**
     * Gets currently displayed page (form) title.
     *
     * @return
     */
    public String getCurrentPageTitle();

    public String getCurrentPageId();

    public void doExpandSwitchBoard(SwitchBoard switchBoard);

    public void doCollapseSwitchBoard(SwitchBoard switchBoard);

    /**
     * Gets a switch board's status. <br>
     * NOTE: Switch board may or may not be available so always use it with
     * {@link #isSwitchBoardAvailable(SwitchBoard)} method.
     *
     * @param switchBoard - {@link SwitchBoard}
     * @return {@link com.vh.mi.automation.api.constants.SwitchBoard.SwitchBoardStatus}
     */
    public SwitchBoard.SwitchBoardStatus getSwitchBoardStatus(SwitchBoard switchBoard);

    /**
     * Checks if a switch board is available.
     *
     * @param switchBoard - {@link SwitchBoard}
     * @return boolean
     */
    public boolean isSwitchBoardAvailable(SwitchBoard switchBoard);

    public <T> T doNavigateTo(Class<T> landPageImplClass, Integer waitTimeInSeconds);

    public <T> T doNavigateToReportManagerPage(Class<T> landPageImplClass, Integer waitTimeInSeconds);

    /**
     * Navigates to given landing page from the favorites switchboard.
     * Before calling this method make sure that the page is actually in favorites switchboard.
     *
     * @param landPageImplClass
     * @param <T>
     * @return
     */
    public <T> T doNavigateFromFavoritesSwitchBoardTo(MILandingPages landPage, Class<T> landPageImplClass);

    public Collection<MILandingPages> getUserFavorites();

    public MILandingPages getCurrentHomepage();

    public void doRemoveFromFavoritesList(MILandingPages page);

    public boolean isPageAvailable(MILandingPages landingPage);

    public IWelcomePage chooseAnotherApplication(Integer waitTimeSeconds);

    public boolean isChooseAnotherApplicationLinkNameAvailable();

}

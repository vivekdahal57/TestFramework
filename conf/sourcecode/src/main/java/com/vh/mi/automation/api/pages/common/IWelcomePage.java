package com.vh.mi.automation.api.pages.common;

import com.vh.mi.automation.api.comp.navigation.INavigationPanel;
import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.impl.pages.common.OAMPage;
import com.vh.mi.automation.impl.pages.common.ProductUsageDashboard.ProductUsageDashboardPage;
import com.vh.mi.automation.impl.pages.common.myAccountProfile.MyAccountProfile;

import java.util.Collection;

/**
 * @author i80448
 */
public interface IWelcomePage extends IAmWebComponent {

    /**
     * @return
     */
    public ILoginPage logOut();


    /**
     * same as above but waits for a specified time for the Login Page to appear
     * Clients don't have to call wait on the loginPage instance
     */
    public ILoginPage logOut(Integer waitTimeSeconds);

    /**
     * Gets all front appIds available in application drop-down list.
     *
     * @return
     */
    public Collection<String> getAvailableFronts();

    /**
     * Checks if a front is available in the drop-down list.
     *
     * @param front
     * @return
     */
    public boolean isFrontAvailable(String front);

    /**
     * Selects given front from the drop-down list.
     *
     * @param appId
     * @return
     */
    public INavigationPanel selectFront(String appId);


    /**
     * Selects given front from the drop-down list.
     *
     * @param appId
     * @return
     */
    public INavigationPanel selectFront(String appId,Integer waitTimeSeconds);


    /**
     * Check whether the link is seen below the app drop down
     * @param linkText the linkText to check
     * @return true if a link with the name is available
     */
    public boolean containsLink(String linkText);

    public void clickOnLink(String link);

    public MyAccountProfile openAccountProfilePage(Integer waitTime);

    public ProductUsageDashboardPage openProductUsageDashBoardPage(Integer waitTime);

    public OAMPage openOAM(Integer waitTime);

    public <T> T selectFontExpectingPage(Class<T> expectedPageClass, String appId, Integer waitTimeSeconds);
}

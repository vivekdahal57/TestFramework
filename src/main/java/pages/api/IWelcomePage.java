package pages.api;

import framework.api.features.IAmWebComponent;

/**
 * @author bibdahal
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
     * Check whether the link is seen
     * @param linkText the linkText to check
     * @return true if a link with the name is available
     */
    public boolean containsLink(String linkText);

    public void clickOnLink(String link);

    public <T> T selectFontExpectingPage(Class<T> expectedPageClass, Integer waitTimeSeconds);
}

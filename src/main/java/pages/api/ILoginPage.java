package pages.api;

import framework.api.features.IAmWebComponent;
import pages.config.User;

/**
 * @author bibdahal
 */
public interface ILoginPage extends IAmWebComponent {

    /**
     * Gets login status message. Message will be available only if the login is
     * failed.
     *
     * @return
     */
    IWelcomePage searchTextInGoogle(String textToSearch);
}


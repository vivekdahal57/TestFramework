package com.vh.mi.automation.impl.pages.saml;

import com.vh.mi.automation.api.pages.saml.IHighMarkMISSO;
import org.openqa.selenium.WebDriver;

/**
 * @author nimanandhar
 */
public class HighMarkMISSO extends AbstractSSOPages implements IHighMarkMISSO {
    private static final String URL_SUFFIX = "/SAML/Response/Sample/HighMarkClientSamplePageAUP.jsp";

    /**
     * Load the Page for entering SAML SSO Information. This does not
     * return until the page has been loaded, so no wait is necessary
     * Note the application should not end in /
     */
    public static IHighMarkMISSO loadPage(WebDriver driver, String application) {
        validateApplication(application);

        driver.get(application + URL_SUFFIX);
        return new HighMarkMISSO(driver);
    }


    private HighMarkMISSO(WebDriver driver) {
        super(driver);
    }

}

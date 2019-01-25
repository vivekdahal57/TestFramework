package com.vh.mi.automation.impl.pages.saml;

import com.vh.mi.automation.api.pages.saml.IHighMarkPISSO;
import org.openqa.selenium.WebDriver;

/**
 * @author nimanandhar
 */
public class HighMarkPISSO extends AbstractSSOPages implements IHighMarkPISSO {
    private static final String URL_SUFFIX = "/SAML/Response/Sample/SampleClientPageAUP.jsp";

    /**
     * Load the Page for entering SAML SSO Information. This does not
     * return until the page has been loaded, so no wait is necessary
     * Note the application should not end in /
     */
    public static IHighMarkPISSO loadPage(WebDriver driver, String application) {
        validateApplication(application);

        driver.get(application + URL_SUFFIX);
        return new HighMarkPISSO(driver);
    }


    private HighMarkPISSO(WebDriver driver) {
        super(driver);
    }
}

package com.vh.mi.automation.impl.pages.saml;

import com.vh.mi.automation.api.pages.saml.IPremierSSO;
import org.openqa.selenium.WebDriver;

/**
 * @author nimanandhar
 */
public class PremierSSO extends AbstractSSOPages implements IPremierSSO {
    private static final String URL_SUFFIX = "/SAML/Response/Sample/SampleClientPage.jsp";


    /**
     * Load the Page for entering SAML SSO Information. This does not
     * return until the page has been loaded, so no wait is necessary
     * Note the application should not end in /
     */
    public static IPremierSSO loadPage(WebDriver driver, String application) {
        validateApplication(application);

        driver.get(application + URL_SUFFIX);
        return new PremierSSO(driver);
    }


    private PremierSSO(WebDriver driver) {
        super(driver);
    }
}

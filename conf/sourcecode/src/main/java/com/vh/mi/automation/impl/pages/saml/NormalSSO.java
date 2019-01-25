package com.vh.mi.automation.impl.pages.saml;

import com.vh.mi.automation.api.pages.saml.INormalSSO;
import org.openqa.selenium.WebDriver;

/**
 * @author nimanandhar
 */
public class NormalSSO extends AbstractSSOPages implements INormalSSO {
    private static final String URL_SUFFIX = "/SAML/Response/Sample/SampleClientPage.jsp";

    /**
     * Load the Page for entering SAML SSO Information. This does not
     * return until the page has been loaded, so no wait is necessary
     * Note the application should not end in /
     */
    public static INormalSSO loadPage(WebDriver driver, String application) {
        validateApplication(application);

        driver.get(application + URL_SUFFIX);
        return new NormalSSO(driver);
    }


    private NormalSSO(WebDriver driver) {
        super(driver);
    }

}

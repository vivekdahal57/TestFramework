package com.vh.mi.automation.impl.pages.saml;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.pages.saml.IRMSSO;
import com.vh.mi.automation.api.reportManager.IReportManagerPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * @author nimanandhar
 */
public class RMSSO extends AbstractSSOPages implements IRMSSO {
    private static final String URL_SUFFIX = "/SAML/Response/Sample/D2RMClientPage.jsp";

    /**
     * Load the Page for entering SAML SSO Information. This does not
     * return until the page has been loaded, so no wait is necessary
     * Note the application should not end in /
     */
    public static IRMSSO loadPage(WebDriver driver, String application) {
        validateApplication(application);

        driver.get(application + URL_SUFFIX);
        return new RMSSO(driver);
    }

    private RMSSO(WebDriver driver) {
        super(driver);
    }


    /**
     * Clicking on reportManager button opens up report manager in a different
     * window.This is a problem because the current window is still the old'
     * window for RMSSO and all selenium commands are forwarded to this window.
     * This method will first close the RMSSO window and then wait for the
     * pageObjectClass to load
     */
    @Override
    public <T extends AbstractWebComponent> T doSubmit(Class<T> pageObjectClass, int waitTimeSeconds) {
        clickReportManagerButton();
        SeleniumUtils.closeCurrentWindowAndSwitchToNewWindow(getDriver());

        T t = PageFactory.initElements(getDriver(), pageObjectClass);
        Preconditions.checkArgument(t instanceof IReportManagerPage);

        ((IReportManagerPage) t).doSwitchToMainFrame();

        t.doWaitTillFullyLoaded(waitTimeSeconds);
        return t;
    }
}

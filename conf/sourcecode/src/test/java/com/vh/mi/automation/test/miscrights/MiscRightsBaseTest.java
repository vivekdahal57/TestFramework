package com.vh.mi.automation.test.miscrights;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.common.ILoginPage;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.pages.common.LoginPage;
import com.vh.mi.automation.test.base.BaseTest;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base Test for all Misc Rights Creation.
 * All Misc Rights test need to login and logout multiple times hence using a separate instance
 * of browser for each test is expedient. Handles creating browser,loggin in,and closing of
 * browser instance after each test.
 * Note:
 * the webDriver and navigationPanel fields of the BaseTest
 * are hidden by this class for all its child classes
 *
 * @author nimanandhar
 */
public class MiscRightsBaseTest extends BaseTest {
    protected final Logger logger = LoggerFactory.getLogger(MiscRightsBaseTest.class);


    @Override
    public boolean skipBrowserCreation() {
        return true;
    }

    @BeforeMethod (alwaysRun = true)
    public void beforeTestMethod() {
        createNewBrowserInstance();
    }


    @AfterMethod (alwaysRun = true)
    public void afterTestMethod() throws Exception {
        closeBrowserInstance();
    }

    protected IWelcomePage login(User user, long waitTimeOut) {
        webDriver.get(context.getApplication().getUrl());
        ILoginPage loginPage = PageFactory.initElements(webDriver, LoginPage.class);
        loginPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        return loginPage.loginWith(user, waitTimeOut);
    }
}

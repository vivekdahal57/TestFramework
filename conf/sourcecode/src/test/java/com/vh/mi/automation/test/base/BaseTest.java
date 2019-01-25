package com.vh.mi.automation.test.base;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.annotations.documentation.PackagePrivate;
import com.vh.mi.automation.api.annotations.logging.LogMethodEnter;
import com.vh.mi.automation.api.comp.navigation.INavigationPanel;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.config.IDriverConfiguration;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.admin.IAdminLoginPage;
import com.vh.mi.automation.api.pages.admin.IAdminPage;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.api.pages.ei.IEIDashboard;
import com.vh.mi.automation.api.pages.ei.IEIDashboardLoginPage;
import com.vh.mi.automation.api.reportManager.IRMLoginPage;
import com.vh.mi.automation.api.reportManager.IReportManagerPage;
import com.vh.mi.automation.impl.pages.admin.MIAdmin;
import com.vh.mi.automation.impl.reportManager.ReportManager;
import com.vh.mi.automation.impl.pages.common.MedicalIntelligence;
import com.vh.mi.automation.impl.pages.ei.EI;
import com.vh.mi.automation.impl.selenium.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;

import java.io.UnsupportedEncodingException;
import java.util.Date;


/**
 * The Base Class for all UI Tests
 * Handles configuration management,login etc
 *
 * @author nimanandhar
 */
public abstract class BaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected final ExecutionContext context;
    protected final Integer defaultWaitTime;
    protected DriverFactory driverFactory;
    protected INavigationPanel navigationPanel;
    protected WebDriver webDriver;
    protected MedicalIntelligence mi;
    protected MIAdmin miAdmin;

    public BaseTest() {
        context = ExecutionContext.forEnvironment(System.getProperty("environment"));
        defaultWaitTime = context.getDefaultWaitTimeout();
    }

    @LogMethodEnter
    public void setUp() {
        driverFactory = getDriverFactory();
        if (!skipBrowserCreation()) {
            createNewBrowserInstance();
            if (!skipLogin()) {
                loginAndSelectFront(getUser(), context.getDefaultWaitTimeout());
            }
        }
    }

    @LogMethodEnter
    public void setUp(String automationUserId) {
        driverFactory = getDriverFactory();
        if (!skipBrowserCreation()) {
            createNewBrowserInstance();
            if (!skipLogin()) {
                loginAndSelectFront(getUser(automationUserId), context.getDefaultWaitTimeout());
            }
        }
    }

    @LogMethodEnter
    public void setUpAdmin(String automationUserId) {
        driverFactory = getDriverFactory();
        if (!skipBrowserCreation()) {
            createNewMIAdminBrowserInstance();
            if (!skipLogin()) {
                loginToProxyGrantingAdminPage(getUser(automationUserId), context.getDefaultWaitTimeout());
            }
        }
    }

    protected final void createNewBrowserInstance() {
        Preconditions.checkState(webDriver == null);
        webDriver = driverFactory.createDriver();
        webDriver.manage().window().maximize();
        mi = new MedicalIntelligence(webDriver);
    }

    protected final void createNewMIAdminBrowserInstance() {
        Preconditions.checkState(webDriver == null);
        webDriver = driverFactory.createDriver();
        webDriver.manage().window().maximize();
        miAdmin = new MIAdmin(webDriver);
    }

    protected final void loginAndSelectFront(User user, Integer waitTime) {
        Preconditions.checkState(webDriver != null);
        Preconditions.checkState(user != null);
        IWelcomePage welcomePage = mi.open(context.getApplication()).loginWith(user, waitTime);

        navigationPanel = welcomePage.selectFront(context.getApplication().getAppId());
        navigationPanel.doWaitTillFullyLoaded(waitTime);
    }

    protected IAdminPage loginToProxyGrantingAdminPage(User user, Integer waitTime){
        Preconditions.checkState(webDriver != null);
        Preconditions.checkState(user != null);
        IAdminLoginPage adminLoginPage = new MIAdmin(webDriver).open(context.getProxyTicketUrl());
        return adminLoginPage.loginWith(user.getUserId(), user.getPassword());
    }

    protected IAdminPage loginToAdminApplication(User user, Integer waitTime){
        Preconditions.checkState(webDriver != null);
        Preconditions.checkState(user != null);
        IAdminLoginPage adminLoginPage = new MIAdmin(webDriver).open(context.getAdminUrl());
        return adminLoginPage.loginWith(user.getUserId(), user.getPassword());
    }

    protected IReportManagerPage loginToRMApplication(User user, Integer waitTime){
        Preconditions.checkState(webDriver != null);
        Preconditions.checkState(user != null);
        IRMLoginPage rmLoginPage = new ReportManager(webDriver).open(context.getRmUrl());
        return rmLoginPage.loginWith(user.getUserId(), user.getPassword());
    }

    protected IEIDashboard loginToEIDashboard(User user, Integer waitTime){
        Preconditions.checkState(webDriver != null);
        Preconditions.checkState(user != null);
        IEIDashboardLoginPage eiDashboardLoginPage = new EI(webDriver).open(context.getEiDashboardUrl());
        return eiDashboardLoginPage.loginWith(user.getUserId(), user.getPassword());
    }

    @AfterClass(alwaysRun = true)
    public final void tearDownTestClass() {
        closeBrowserInstance();
    }

    protected final void closeBrowserInstance() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
            mi = null;
            miAdmin = null;
            navigationPanel = null;
        }
    }

    /*
    * This method should be called form Test class constructor when we need to
    * choose particular browser for a test class from xml suite.
    *
    * @Parameters("browser")
    * public ClassConstructor(@Optional("") String browser){
    *    super.setBrowser(browser);
    * }
    * */
    public void setBrowser(String browser){
        IDriverConfiguration driverConfiguration = context.getDriverConfiguration();
        driverConfiguration.setBrowser(browser);
        logger.info("Setting Browser from xml");
        driverFactory = new DriverFactory(driverConfiguration);
    }

    private DriverFactory getDriverFactory(){
        if(null != driverFactory){
            return driverFactory;
        }
        return new DriverFactory(context.getDriverConfiguration());
    }

    /**
     * Override this method to return true if you want to avoid
     * creating web-driver instance automatically by BaseTest
     * Used by Tests that creates new browser instance for each
     * test
     */
    public boolean skipBrowserCreation() {
        return false;
    }


    /**
     * Override this method to return true if you want
     * the web-driver instance to be created but do not
     * want to automatically login to MI
     *
     * @return true if you want to skip login,false otherwise
     */
    protected boolean skipLogin() {
        return false;
    }


    protected final User getUser() {
        return UserManager.INSTANCE.getUser();
    }

    protected final User getUser(String automationId) {
        return UserManager.INSTANCE.getUser(automationId);
    }

    /**
     * Tests do not need to (and cannot) release Users.
     * Releasing users are done through  {@link UserManagementAspect}
     */
    @PackagePrivate
    final void releaseUser(User user) {
        UserManager.INSTANCE.releaseUser(user);
    }

    public final WebDriver getWebDriver() {
        return webDriver;
    }

}

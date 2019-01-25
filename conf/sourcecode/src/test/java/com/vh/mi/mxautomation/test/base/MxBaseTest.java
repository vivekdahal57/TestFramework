package com.vh.mi.mxautomation.test.base;

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
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.admin.MIAdmin;
import com.vh.mi.automation.impl.pages.common.LoginPage;
import com.vh.mi.automation.impl.reportManager.ReportManager;
import com.vh.mi.automation.impl.pages.common.MedicalIntelligence;
import com.vh.mi.automation.impl.pages.ei.EI;
import com.vh.mi.automation.impl.selenium.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;


/**
 * The Base Class for all UI Tests
 * Handles configuration management,login etc
 *
 * @author nimanandhar
 */
public abstract class MxBaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(MxBaseTest.class);
    protected final ExecutionContext context;
    protected final Integer defaultWaitTime;
    protected DriverFactory driverFactory;
    protected WebDriver webDriver;

    public MxBaseTest() {
        context = ExecutionContext.forEnvironment(System.getProperty("environment"));
        defaultWaitTime = context.getDefaultWaitTimeout();
    }

    @LogMethodEnter
    public void setUp() {
        driverFactory = getDriverFactory();
        if (!skipBrowserCreation()) {
            createNewBrowserInstance();
            if (!skipLogin()) {
                openMxApplication();
            }
        }
    }



    protected final void createNewBrowserInstance() {
        Preconditions.checkState(webDriver == null);
        webDriver = driverFactory.createDriver();
        webDriver.manage().window().maximize();
    }

    protected final void openMxApplication() {
        Preconditions.checkState(webDriver != null);
        String url = context.getMxAppUrl();
        checkArgument(!isNullOrEmpty(url));
        logger.info("Opening {}", url);
        webDriver.get(url);
    }



    @AfterClass(alwaysRun = true)
    public final void tearDownTestClass() {
        closeBrowserInstance();
    }

    protected final void closeBrowserInstance() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
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

    public final WebDriver getWebDriver() {
        return webDriver;
    }

}

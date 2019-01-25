import framework.api.config.ExecutionContext;
import com.google.common.base.Preconditions;
import com.paulhammant.ngwebdriver.NgWebDriver;
import framework.api.annotations.logging.LogMethodEnter;
import framework.selenium.DriverFactory;
import framework.selenium.SeleniumUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import pages.api.IWelcomePage;
import pages.config.User;
import pages.impl.TestApplication;
import pages.impl.WelcomePage;

import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;


/**
 * The Base Class for all UI Tests
 * Handles configuration management,login etc
 *
 * @author bibdahal
 */
public abstract class BaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected final ExecutionContext context;
    protected final Integer defaultWaitTime;
    protected DriverFactory driverFactory;
    protected WebDriver webDriver;
    protected NgWebDriver ngWebDriver;
    protected TestApplication testApplication;

    public BaseTest() {
        context = ExecutionContext.forEnvironment(System.getProperty("environment"));
        defaultWaitTime = context.getDefaultWaitTimeout();
        ngWebDriver = new NgWebDriver((JavascriptExecutor) getWebDriver());
    }

    @LogMethodEnter
    public void setUp()  {
        driverFactory = getDriverFactory();
        if (!skipBrowserCreation()) {
            createNewBrowserInstance();
        }
    }

    protected final void createNewBrowserInstance() {
        Preconditions.checkState(webDriver == null);
        webDriver = driverFactory.createDriver();
        webDriver.manage().window().maximize();
        testApplication=new TestApplication(webDriver);
    }

    protected final void openTestApplication(User user, Integer waitTime)  {
        Preconditions.checkState(webDriver != null);
        String appUrl = context.getAppUrl();
        checkArgument(!isNullOrEmpty(appUrl));
        logger.info("Opening {}", appUrl);
        webDriver.get(appUrl);
        testApplication.open(context.getApplication());
        SeleniumUtils.refreshPage(webDriver);
        webDriver.manage().timeouts().setScriptTimeout(200, TimeUnit.SECONDS);
        new NgWebDriver((JavascriptExecutor) webDriver).waitForAngularRequestsToFinish();
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
     * want to automatically login to
     *
     * @return true if you want to skip login,false otherwise
     */
    protected boolean skipLogin() {
        return false;
    }


    protected final User getUser() {
         return new User(context.getAppUsername(),context.getAppPassword());
    }

    protected final User getUser(String username,String password) {
        return new User(username,password);
    }

    public final WebDriver getWebDriver() {
        return webDriver;
    }

}

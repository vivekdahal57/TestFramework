package framework.selenium;

import framework.api.config.IDriverConfiguration;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author bibdahal
 */
public class DriverFactory {
    public static final String IE_DRIVER_PATH = "src/test/resources/drivers/IEDriverServer.exe";
    public static final String CHROME_DRIVER_PATH = "src/test/resources/drivers/chromedriver.exe";
    public static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
    public static final String IE_DRIVER_KEY = "webdriver.ie.driver";
    private final IDriverConfiguration driverConfiguration;

    public DriverFactory(IDriverConfiguration config) {
        checkArgument(config != null);
        driverConfiguration = config;
    }

    public final WebDriver createDriver() {
        WebDriver webDriver = newDriver();
        webDriver.manage().timeouts().pageLoadTimeout(driverConfiguration.getDefaultWaitTimeout(), TimeUnit.SECONDS);
        if (driverConfiguration.getImplicitWaitTime() > 0) {
            webDriver.manage().timeouts().implicitlyWait(driverConfiguration.getImplicitWaitTime(), TimeUnit.SECONDS);
        }
        //ensure that web-driver is only accessed from the thread on which it was constructed
        //commented out since it creates a proxy and we cannot get browser name and other capabilities from the proxy
        //return ThreadGuard.protect(webDriver);
        return webDriver;
    }

    private WebDriver newDriver() {
        if (driverConfiguration.runOnGrid()) {
            return new RemoteWebDriver(getHubUrl(), getCapabilities());
        } else {
            switch (driverConfiguration.getBrowser()) {
                case FIREFOX:
                    FirefoxProfile fprofile= new FirefoxProfile();
                    fprofile.setPreference("xpinstall.signatures.required", false);
                    fprofile.setPreference("browser.download.folderList",2);
                    fprofile.setPreference("browser.download.manager.showWhenStarting", false);
                    fprofile.setPreference("browser.download.dir",driverConfiguration.getFileDownloadLocation());
                    fprofile.setPreference("browser.download.manager.focusWhenStarting", false);
                    fprofile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/vnd.ms-excel");
                    fprofile.setPreference("browser.download.manager.alertOnExeOpen", false);
                    return new FirefoxDriver(fprofile);
                case CHROME:
                    System.setProperty(CHROME_DRIVER_KEY, CHROME_DRIVER_PATH);
                    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                    chromePrefs.put("profile.default_content_settings.popups", 0);
                    chromePrefs.put("download.prompt_for_download", "false");
                    chromePrefs.put("download.directory_upgrade", "true");
                    chromePrefs.put("download.default_directory", driverConfiguration.getFileDownloadLocation());
                    ChromeOptions options = new ChromeOptions();
                    options.setExperimentalOption("prefs", chromePrefs);
                    options.addArguments("start-maximized");
                    DesiredCapabilities cap = DesiredCapabilities.chrome();
                    cap.setBrowserName(driverConfiguration.getBrowser().getName());
                    cap.setCapability(ChromeOptions.CAPABILITY, options);
                    WebDriver driver = new ChromeDriver(cap);
                    return driver;
                case IE:
                    System.setProperty(IE_DRIVER_KEY, IE_DRIVER_PATH);
                    InternetExplorerDriverService service = new InternetExplorerDriverService.Builder().build();
                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                    capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP,true);
                    capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                    capabilities.setCapability("enablePersistentHover", false);
                    capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");
                    return new InternetExplorerDriver(service, capabilities);
                default:
                    throw new RuntimeException("Unknown Browser " + driverConfiguration.getBrowser());
            }
        }
    }

    private URL getHubUrl() {
        try {
            return new URL(driverConfiguration.getHubUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed hubUrl " + driverConfiguration.getHubUrl());
        }
    }

    private Capabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(driverConfiguration.getBrowser().getName());
        if (driverConfiguration.getBrowser() == IDriverConfiguration.Browser.IE) {
            capabilities.setCapability("enablePersistentHover", false);
            capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP,true);
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
            capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");
        }
        if (driverConfiguration.getBrowser() == IDriverConfiguration.Browser.CHROME) {
            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.prompt_for_download", "false");
            chromePrefs.put("download.directory_upgrade", "true");
            chromePrefs.put("download.default_directory", driverConfiguration.getFileDownloadLocation());
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", chromePrefs);
            options.addArguments("start-maximized");
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        }
        if(driverConfiguration.getBrowser() == IDriverConfiguration.Browser.FIREFOX){
            FirefoxProfile fprofile= new FirefoxProfile();
            fprofile.setPreference("browser.download.folderList",2);
            fprofile.setPreference("browser.download.manager.showWhenStarting", false);
            fprofile.setPreference("browser.download.dir",driverConfiguration.getFileDownloadLocation());
            fprofile.setPreference("browser.download.manager.focusWhenStarting", false);
            fprofile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/vnd.ms-excel");
            fprofile.setPreference("browser.download.manager.alertOnExeOpen", false);
            capabilities.setCapability(FirefoxDriver.PROFILE, fprofile);
        }
        return capabilities;
    }
}

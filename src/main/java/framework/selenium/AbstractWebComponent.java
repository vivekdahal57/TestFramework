package framework.selenium;

import com.google.common.base.Function;

import framework.api.annotations.logging.LogMethodExecutionTime;
import framework.api.features.IAmWebComponent;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Represents any page/form e.g. Login page, Welcome page
 * etc.</br> NOTE - Creating an instance of it's implementations does not mean
 * that web driver is actually in that page.
 *
 * @author bibdahal
 */
public abstract class AbstractWebComponent implements IAmWebComponent {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebDriver driver;

    public AbstractWebComponent(WebDriver driver) {
        checkArgument(driver != null);
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Set the current driver.
     *
     * @param driver
     */
    public void setDriver(WebDriver driver) {
        checkArgument(driver != null);
        this.driver = driver;
    }

    @LogMethodExecutionTime
    @Override
    public IAmWebComponent doWaitTillFullyLoaded(long waitTimeInSecs) {

       logger.info("Waiting for __{}__ loading ...", this.getClass()
                 .getSimpleName());

        new WebDriverWait(getDriver(), waitTimeInSecs).ignoring(StaleElementReferenceException.class).ignoring(WebDriverException.class)
                .until(new Function<WebDriver,Boolean>() {
                    @Override
                    public Boolean apply(WebDriver arg0) {
                        return isFullyLoaded();
                    }
                });

        return this;
    }

    public void waitTillDocumentReady(long waitTimeInSecs){
        new WebDriverWait(getDriver(), waitTimeInSecs).until(new Function<WebDriver,Boolean>() {
                                                                 public Boolean apply(WebDriver driver) {
                                                                     return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                                                                 }
                                                             }
        );
    }
}

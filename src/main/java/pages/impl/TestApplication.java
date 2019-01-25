package pages.impl;

import framework.api.dto.Application;
import framework.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import pages.api.ILoginPage;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author i80448
 */
public class TestApplication extends AbstractWebComponent {

    public TestApplication(WebDriver driver) {
        super(driver);
    }

    public ILoginPage open(Application application) {
        String url = application.getUrl();
        checkArgument(!isNullOrEmpty(url));
        logger.info("Opening {}", url);
        getDriver().get(url);
        return PageFactory.initElements(getDriver(), LoginPage.class);
    }

    @Override
    public boolean isFullyLoaded() {
        return true;
    }

    public void quit() {
        getDriver().quit();
    }
}

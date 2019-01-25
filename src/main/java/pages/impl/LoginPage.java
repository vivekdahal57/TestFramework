package pages.impl;

import framework.api.features.IAmWebComponent;
import framework.selenium.AbstractWebComponent;
import framework.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.api.ILoginPage;
import pages.api.IWelcomePage;
import pages.config.User;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by bibdahal
 */
public class LoginPage extends AbstractWebComponent implements ILoginPage {
    private final WebElements webElements;

    public LoginPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.searchTextbox.isDisplayed();
    }

    private void doWaitTillErrorMessageAppears(long timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.visibilityOf(webElements.searchTextbox));
    }

    @Override
    public IWelcomePage searchTextInGoogle(String searchText) {
        WelcomePage welcomePage = PageFactory
                .initElements(getDriver(), WelcomePage.class);
        enterSearchText(searchText).submit();
        return welcomePage;
    }

    /**
     * Click Submit button.
     */
    private LoginPage submit() {
        SeleniumUtils.click(getDriver(), webElements.submitButton);
        return this;
    }

    private LoginPage enterSearchText(String text) {
        checkArgument(!isNullOrEmpty(text));

        webElements.searchTextbox.clear();
        webElements.searchTextbox.sendKeys(text);
        return this;
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        //
        @FindBy(name = "q")
        private WebElement searchTextbox;

        @FindBy(name = "btnK")
        private WebElement submitButton;
    }

}

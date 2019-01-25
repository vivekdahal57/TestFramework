package com.vh.mi.automation.impl.pages.common;

import com.vh.mi.automation.api.comp.IPreferencesBar;
import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.api.pages.common.ILoginPage;
import com.vh.mi.automation.api.pages.common.IMIPage;
import com.vh.mi.automation.impl.comp.PreferencesBar;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Base class for MI pages which provides default implementations of
 * {@link IMIPage#getDisplayedPageTitle()} and {@link IMIPage#isFullyLoaded()}
 * methods. If specific pages needs different implementations then override
 * methods. Specially note the implementation of
 * {@link AbstractLandingPage#isFullyLoaded()} method which checks if the page
 * title is displayed to determine if the page is completely loaded, in some
 * pages this logic may not be sufficient.
 *
 * @author i80448
 */
public abstract class AbstractMIPage extends AbstractWebComponent implements IMIPage {

    private final WebElements webElements;

    public AbstractMIPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }


    @Override
    public String getDisplayedPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public boolean isFullyLoaded() {
        return getPageTitle().equalsIgnoreCase(getDisplayedPageTitle());
    }

    /*
    The current page is dead after calling logout
    It is the responsibility of the calling method to wait
    till loginPage is fully loaded, if required
     */
    @Override
    public ILoginPage doLogOut() {
        webElements.logout.click();
        return PageFactory.initElements(getDriver(), LoginPage.class);
    }

    @Override
    public IPreferencesBar getPreferencesBar(){
        return new PreferencesBar(getDriver());
    }

    @Override
    public <T> T logoutExpectingPage(Class<T> expectedClass, Integer waitTime) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(webElements.logout).click().perform();
        T pageInstance = PageFactory.initElements(getDriver(), expectedClass);
        IAmWebComponent webComponentInstance = (IAmWebComponent) pageInstance;
        webComponentInstance.doWaitTillFullyLoaded(waitTime);
        return pageInstance;
    }

    protected static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle = null;

        @FindBy(linkText = "Log out")
        private WebElement logout = null;

    }

}

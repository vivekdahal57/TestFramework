package com.vh.mi.automation.impl.pages.common;

import com.vh.mi.automation.api.comp.IPreferencesBar;
import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.common.ILoginPage;
import com.vh.mi.automation.api.pages.common.IMIPage;
import com.vh.mi.automation.impl.comp.PreferencesBar;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * A Generic MI Page to be used when we don't know which page will be loaded
 * When asked to wait, it will wait until the title element is visislbe
 *
 * @author nimanandhar
 */
public class GenericMIPage extends AbstractWebComponent implements IMIPage {
    private final WebElements webElements;

    public GenericMIPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public String getPageTitle() {
        return getDisplayedPageTitle();
    }

    @Override
    public String getPageId() {
        throw new RuntimeException("Not Supported");
    }

    @Override
    public String getDisplayedPageTitle() {
            return webElements.pageTitleElm.getText();

    }

    @Override
    public ILoginPage doLogOut() {
        throw new RuntimeException("Not Supported");
    }

    @Override
    public <T> T logoutExpectingPage(Class<T> pageObjectClass, Integer waitTime) {
        throw new NotImplementedException("not implemented");
    }

    @Override
    public IPreferencesBar getPreferencesBar(){
        return new PreferencesBar(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitleElm.isDisplayed() || webElements.primaryLogo.isDisplayed();
    }

    private static class WebElements {
        private final WebDriver webDriver;

        private WebElements(WebDriver webDriver) {
            this.webDriver = webDriver;
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id='pageTitle']")
        private WebElement pageTitleElm;

        @FindBy(xpath = "//*[@class='primaryLogo']//img")
        private WebElement primaryLogo;

        @FindBy(linkText = "Log out")
        private WebElement logout = null;

    }
}

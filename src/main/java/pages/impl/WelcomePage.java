package pages.impl;

import framework.api.exceptions.AmbiguousLinkTextException;
import framework.api.exceptions.LinkTextNotAvailableException;
import framework.api.features.IAmWebComponent;
import framework.selenium.AbstractWebComponent;
import framework.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import pages.api.ILoginPage;
import pages.api.IWelcomePage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public class WelcomePage extends AbstractWebComponent implements IWelcomePage {
    public static final String ACCOUNT_PROFILE_LINK = "My Account Profile";

    public static final String PRODUCT_USAGE_DASHBOARD_LINK = "Product Usage Dashboard";
    public static final String OAM_LINK = "Verscend OAM";

    @FindBy(linkText = "Log out")
    private WebElement logout;

    @FindBy(id = "d2Form:FrontList")
    private WebElement appDropDownList;

    @FindBy(id = "boxbrdr1")
    private WebElement linksBox;

    @FindBy(id = "d2Form:noticeBoardContainer")
    private WebElement noticeBoardContainer;

    @FindBy(xpath = "//*[@id='d2Form:noticeBoardContentTable']//input[@value='Close']")
    private WebElement noticeCloseButton;

    public WelcomePage(WebDriver driver) {
        super(driver);
    }

    private boolean isAppDropDownListDisplayed() {
        return appDropDownList.isDisplayed();
    }

    @Override
    public boolean isFullyLoaded() {
        return isAppDropDownListDisplayed();
    }

    public boolean isNoticeBoardVisible() {
        try {
            return noticeBoardContainer.isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    public void closeNoticeBoard() {
        noticeCloseButton.click();
    }

    @Override
    public ILoginPage logOut() {
        //		SeleniumUtils.waitForVisibility(getDriver(), logout, 20);
        logout.click();
        return PageFactory.initElements(getDriver(), LoginPage.class);

    }

    @Override
    public ILoginPage logOut(Integer waitTimeSeconds) {
        logOut();
        LoginPage loginPage = PageFactory
                .initElements(getDriver(), LoginPage.class);
        loginPage.doWaitTillFullyLoaded(waitTimeSeconds);
        return loginPage;
    }

    @Override
    public boolean containsLink(String linkText) {
        return linksBox.findElements(By.linkText(linkText)).size() > 0;
    }

    /**
     * Click on Links displayed below the Select Application drop down
     * Callers should figure out the page it should land to
     * and call wait accordingly. Another possibility to think about
     * is to pass the expected page object to this method
     *
     * @param linkText
     */
    @Override
    public void clickOnLink(String linkText) {
        List<WebElement> links = linksBox.findElements(By.linkText(linkText));
        if (links.isEmpty())
            throw new LinkTextNotAvailableException(linkText);
        else if (links.size() > 1)
            throw new AmbiguousLinkTextException(linkText);
        else
            SeleniumUtils.click(getDriver(), links.get(0));
    }


    @Override
    public <T> T selectFontExpectingPage(Class<T> expectedPageClass, Integer waitTimeSeconds) {
        T pageInstance = PageFactory
                .initElements(getDriver(), expectedPageClass);
        IAmWebComponent webComponentInstance = (IAmWebComponent) pageInstance;
        webComponentInstance.doWaitTillFullyLoaded(waitTimeSeconds);
        return pageInstance;
    }
}

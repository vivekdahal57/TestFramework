package com.vh.mi.automation.impl.pages.common;

import com.vh.mi.automation.api.comp.navigation.INavigationPanel;
import com.vh.mi.automation.api.exceptions.AmbiguousLinkTextException;
import com.vh.mi.automation.api.exceptions.LinkTextNotAvailableException;
import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.api.pages.common.ILoginPage;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.comp.navPanel.NavigationPanel;
import com.vh.mi.automation.impl.pages.common.ProductUsageDashboard.ProductUsageDashboardPage;
import com.vh.mi.automation.impl.pages.common.myAccountProfile.MyAccountProfile;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

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
        LoginPage loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
        loginPage.doWaitTillFullyLoaded(waitTimeSeconds);
        return loginPage;
    }

    @Override
    public Collection<String> getAvailableFronts() {
        waitTillDocumentReady(30);
        Select appSelect = new Select(appDropDownList);
        List<String> fronts = new ArrayList<>();
        for (WebElement option : appSelect.getOptions()) {
            String value = option.getAttribute("value");
            if (!value.equals("0")) {
                fronts.add(value);
            }
        }
        return fronts;
    }

    @Override
    public boolean isFrontAvailable(String front) {
        Collection<String> applicationList = getAvailableFronts();
        return applicationList.contains(front);
    }

    @Deprecated
    @Override
    public INavigationPanel selectFront(String appId) {
        checkArgument(!isNullOrEmpty(appId));
        new Select(appDropDownList).selectByValue(appId);

        return PageFactory.initElements(getDriver(), NavigationPanel.class);
    }

    @Override
    public INavigationPanel selectFront(String appId,Integer waitTimeSeconds) {
        checkArgument(!isNullOrEmpty(appId));
        new Select(appDropDownList).selectByValue(appId);

        NavigationPanel navigationPanel = PageFactory.initElements(getDriver(), NavigationPanel.class);
        navigationPanel.doWaitTillFullyLoaded(waitTimeSeconds);
        return navigationPanel;
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
//            links.get(0).click();
            SeleniumUtils.click(links.get(0), getDriver());
    }

    @Override
    public MyAccountProfile openAccountProfilePage(Integer waitTime) {
        clickOnLink(ACCOUNT_PROFILE_LINK);
        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(getDriver());
        SeleniumUtils.switchToNewWindow(getDriver());
        MyAccountProfile accountProfilePage = new MyAccountProfile(getDriver());
//        accountProfilePage.waitTillFullyLoaded(waitTime);
        return accountProfilePage;
    }

    @Override
    public ProductUsageDashboardPage openProductUsageDashBoardPage(Integer waitTime){
        clickOnLink(PRODUCT_USAGE_DASHBOARD_LINK);
        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(getDriver());
        SeleniumUtils.switchToNewWindow(getDriver());
        ProductUsageDashboardPage productUsageDashboardPage = new ProductUsageDashboardPage(getDriver());
        return productUsageDashboardPage;
    }

    @Override
    public OAMPage openOAM(Integer waitTime){
        clickOnLink(OAM_LINK);
        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(getDriver());
        SeleniumUtils.switchToNewWindow(getDriver());
        OAMPage oamPage = new OAMPage(getDriver());
        return oamPage;
    }

    @Override
    public <T> T selectFontExpectingPage(Class<T> expectedPageClass, String appId, Integer waitTimeSeconds) {
        selectFront(appId, waitTimeSeconds);
        T pageInstance = PageFactory.initElements(getDriver(), expectedPageClass);
        IAmWebComponent webComponentInstance = (IAmWebComponent) pageInstance;
        webComponentInstance.doWaitTillFullyLoaded(waitTimeSeconds);
        return pageInstance;
    }
}

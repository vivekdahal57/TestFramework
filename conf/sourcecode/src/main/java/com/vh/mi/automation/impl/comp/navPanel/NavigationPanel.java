package com.vh.mi.automation.impl.comp.navPanel;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.annotations.logging.LogMethodExecutionTime;
import com.vh.mi.automation.api.comp.navigation.INavigationPanel;
import com.vh.mi.automation.api.constants.MILandingPages;
import com.vh.mi.automation.api.constants.SwitchBoard;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.pages.common.IMIPage;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.api.reportManager.IReportManagerPage;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.groovy.Module;
import com.vh.mi.automation.groovy.ModuleInfo;
import com.vh.mi.automation.impl.pages.common.WelcomePage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.vh.mi.automation.api.constants.SwitchBoard.FAVORITES;
import static com.vh.mi.automation.api.constants.SwitchBoard.SwitchBoardStatus;
import static com.vh.mi.automation.api.constants.SwitchBoard.SwitchBoardStatus.COLLAPSED;
import static com.vh.mi.automation.api.constants.SwitchBoard.SwitchBoardStatus.EXPANDED;
import static com.vh.mi.automation.impl.comp.navPanel.NavigationPanelTestHelper.CLOSE_PARENT_LEVEL;

/**
 * @author i80448
 * @author nimanandhar
 */
public class NavigationPanel extends AbstractWebComponent implements
        INavigationPanel {

    private final FavoritesSwitchBoard favoritesSwitchBoard;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebElements webElements;

    public NavigationPanel(WebDriver driver) {
        super(driver);
        this.webElements = new WebElements(driver);
        favoritesSwitchBoard = new FavoritesSwitchBoard(getDriver());
    }


    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitleElm.isDisplayed();
    }


    @Override
    public Collection<SwitchBoard> getSwitchBoards() {
        List<SwitchBoard> switchBoards = new ArrayList<>();
        for (SwitchBoard switchBoard : SwitchBoard.values()) {
            if (isSwitchBoardAvailable(switchBoard))
                switchBoards.add(switchBoard);
        }
        return switchBoards;
    }

    @Override
    public String getCurrentPageTitle() {
        return webElements.pageTitleElm.getText();
    }

    @Override
    public void doExpandSwitchBoard(SwitchBoard sw) {
        if (getSwitchBoardStatus(sw) == SwitchBoardStatus.COLLAPSED) {
            toggleSwitchBoard(sw);
        } else {
            logger.info("{} already in expanded status.", sw);
        }
    }

    @Override
    public void doCollapseSwitchBoard(SwitchBoard switchBoard) {
        if (getSwitchBoardStatus(switchBoard) == SwitchBoardStatus.EXPANDED) {
            toggleSwitchBoard(switchBoard);
        } else {
            logger.info("{} already in collapsed status.", switchBoard);
        }
    }

    private void toggleSwitchBoard(SwitchBoard switchBoard) {
        try {
            WebElement switchboardElement = webElements.getSwitchboardElement(switchBoard);
            SeleniumUtils.hoverAndClick(getDriver(), switchboardElement);
        } catch (StaleElementReferenceException e) {
            logger.info("Stale element exception Handled");
            SeleniumUtils.refreshPage(getDriver());
            doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);

            WebElement switchboardElement = webElements.getSwitchboardElement(switchBoard);
            SeleniumUtils.hoverAndClick(getDriver(), switchboardElement);
        }
        WaitUtils.waitForSeconds(2);
    }

    @Override
    public boolean isSwitchBoardAvailable(SwitchBoard switchBoard) {
        switch (switchBoard) {
            case FAVORITES:
                return isSwitchboardVisible(IDs.SWB_FAVORITES_HEADER);
            case ANALYTICS:
                return isSwitchboardVisible(IDs.SWB_ANALYTICS_HEADER);
            case DASHBOARDS:
                return isSwitchboardVisible(IDs.SWB_DASHBOARDS_HEADER);
            case QUERY_BUILDER:
                return isSwitchboardVisible(IDs.SWB_QUERY_BUILDER_HEADER);
            case REPORTS:
                return isSwitchboardVisible(IDs.SWB_REPORTS_HEADER);
            case DOCUMENTATION:
                return isSwitchboardVisible(IDs.SWB_DOCUMENTATION_HEADER);
            case OUTREACH:
                return isSwitchboardVisible(IDs.SWB_OUTREACH_HEADER);
            default:
                throw new RuntimeException("Unknown switchboard " + switchBoard);
        }
    }

    private boolean isSwitchboardVisible(String id) {
        return idExist(id) && !isTextEmpty(id);
    }

    private boolean idExist(String id) {
        try {
            return SeleniumUtils.elementExists(webElements.leftNavigationElm, ".//*[@id='" + id + "']");
        } catch (StaleElementReferenceException e) {
            logger.info("Stale element exception Handled");
            SeleniumUtils.refreshPage(getDriver());
            doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
            return SeleniumUtils.elementExists(webElements.leftNavigationElm, ".//*[@id='" + id + "']");
        }
    }

    private boolean isTextEmpty(String id) {
        try {
            return webElements.leftNavigationElm.findElement(By.id(id)).getText().isEmpty();
        } catch (StaleElementReferenceException e) {
            logger.info("Stale element exception Handled");
            SeleniumUtils.refreshPage(getDriver());
            doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
            return webElements.leftNavigationElm.findElement(By.id(id)).getText().isEmpty();
        }
    }

    @Override
    public SwitchBoardStatus getSwitchBoardStatus(SwitchBoard switchBoard) {
        String style;
        try {
            style = webElements.getContentElement(switchBoard).getAttribute("style");
        } catch (StaleElementReferenceException e) {
            SeleniumUtils.refreshPage(getDriver());
            doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
            style = webElements.getContentElement(switchBoard).getAttribute("style");
        }
        style = style.replace(" ", "").toLowerCase();
        return style.contains("display:none") ? COLLAPSED : EXPANDED;
    }

    @Override
    public String getCurrentPageId() {
        throw new AutomationException("Not Implemented.Consider removing this feature");
    }

    /**
     * Used to determine if the navigation panel is available in the page. This
     * will only be used by SAML SSO Tests, in all other cases, navigation panel
     * is assumed to be present. Because of the specific nature  of the method,
     * it is not placed in the interface. Another option that can be considered
     * is to create a new helper class for SAML tests in which all such misc
     * methods are placed.
     *
     * @return true if navigation panel is available, false otherwise
     */
    public static boolean isAvailable(WebDriver driver) {
        return !driver.findElements(By.id("left")).isEmpty();
    }


    @Override
    public <T> T doNavigateFromFavoritesSwitchBoardTo(MILandingPages landPage, Class<T> landPageClass) {
        logger.info("Navigating to __{}__ page from favorites switch board.", landPageClass.getSimpleName());

        T pageInstance = PageFactory.initElements(getDriver(), landPageClass);

        checkArgument(pageInstance instanceof IAmLandingPage, landPageClass.getSimpleName() + " is not a landing page. Make sure that it implements IAmLandingPage interface.");

        IAmLandingPage landPageInstance = (IAmLandingPage) pageInstance;
        if (isCurrentPage(landPageInstance)) {
            return pageInstance;
        }

        if (getSwitchBoardStatus(FAVORITES) == COLLAPSED) {
            doExpandSwitchBoard(FAVORITES);
        }
        new FavoritesSwitchBoard(getDriver()).doNavigateTo(landPage);
        return pageInstance;
    }

    @Override
    public <T> T doNavigateTo(Class<T> landPageClass, Integer waitTimeInSeconds) {
        T pageInstance = PageFactory.initElements(getDriver(), landPageClass);

        checkState(pageInstance instanceof IAmLandingPage, landPageClass.getSimpleName() + " is not a landing page. Make sure that it implements IAmLandingPage interface.");
        IAmLandingPage landPageInstance = (IAmLandingPage) pageInstance;
        if (isCurrentPage(landPageInstance)) {
            return pageInstance;
        }

        clickOnPageLinkAndWaitTillFullyLoaded(landPageInstance, waitTimeInSeconds);
        return pageInstance;
    }

    @Override
    public <T> T doNavigateToReportManagerPage(Class<T> reportPageClass,Integer waitTimeInSeconds){
        goToReportManagerApplication();
        T pageInstance = PageFactory.initElements(getDriver(),reportPageClass);
        checkState(pageInstance instanceof IReportManagerPage, reportPageClass.getSimpleName() + " is not a Report Manager Application page. Make sure that it implements IReportManagerPage interface.");
        IReportManagerPage reportPageInstance = (IReportManagerPage) pageInstance;
        reportPageInstance.doSwitchToMainFrame();
        reportPageInstance.doWaitTillFullyLoaded(waitTimeInSeconds);
        return pageInstance;
    }

    public void goToReportManagerApplication() {
        NavigationPanelTestHelper navigationPanelTestHelper = new NavigationPanelTestHelper(getDriver());
        if (!getSwitchBoards().contains(SwitchBoard.REPORTS)) {
            throw new SkipException("REPORTS Switchboard not available");
        }
        doExpandSwitchBoard(SwitchBoard.REPORTS);
        List<Module> modules = navigationPanelTestHelper.getModules(SwitchBoard.REPORTS);
        Module reportManagerModule = null;
        for (Module module : modules) {
            if (module.getLinkName().equals("Report Manager")) {
                reportManagerModule = module;
                break;
            }
        }
        if (reportManagerModule == null) {
            throw new SkipException("Report Manager Module not available");
        }
        navigationPanelTestHelper.clickOnLink(reportManagerModule);
    }

    private void clickOnPageLinkAndWaitTillFullyLoaded(IAmLandingPage landPageInstance, Integer waitTime) {
        expandSwitchboard(landPageInstance);
        Module module = ModuleInfo.INSTANCE.getModuleByPageId(landPageInstance.getPageId());
        if (module.hasParent())
            expandParentSections(module.getParent());

        String moduleId = module.getId();
        clickAndWaitTillFullyLoaded(landPageInstance, moduleId, waitTime);
        logger.info("Navigated to Page {}", landPageInstance.getDisplayedPageTitle());
    }

    @LogMethodExecutionTime
    private void clickAndWaitTillFullyLoaded(IAmLandingPage landingPage, String moduleId, Integer waitTime) {
        //try catch block added in order to fix stale element exception. Remove this implementation if issue is fixed
        try {
            WebElement moduleElement = getDriver().findElement(By.xpath("//*[@id='" + moduleId + "']/a"));
            SeleniumUtils.hoverAndClick(getDriver(), moduleElement);
        } catch (StaleElementReferenceException e) {
            logger.info("Stale element exception Handled");
            WebElement moduleElement = getDriver().findElement(By.xpath("//*[@id='" + moduleId + "']/a"));
            SeleniumUtils.hoverAndClick(getDriver(), moduleElement);
        }
        landingPage.doWaitTillFullyLoaded(waitTime);
    }

    private void expandSwitchboard(IAmLandingPage landPageInstance) {
        SwitchBoard switchBoard = landPageInstance.getSwitchBoard();
        Preconditions.checkState(isSwitchBoardAvailable(switchBoard));

        if (getSwitchBoardStatus(switchBoard) == COLLAPSED) {
            doExpandSwitchBoard(switchBoard);
        }
    }


    private void expandParentSections(Module section) {
        if (section.hasParent()) {
            expandParentSections(section.getParent());
        }
        expandSection(section);
    }

    private void expandSection(Module section) {
        try {
            WebElement sectionElement = getDriver().findElement(By.id(section.getId()));
            boolean isCollapsed = sectionElement.getAttribute("class").endsWith(CLOSE_PARENT_LEVEL);
            if (isCollapsed) {
                sectionElement.findElement(By.linkText(section.getLinkName())).click();
                WaitUtils.waitForSeconds(1);
            }
        } catch (StaleElementReferenceException e) {
            logger.info("Stale Element Exception but Handled");
            expandSection(section);
        }
    }


    private boolean isCurrentPage(IMIPage page) {
        return page.getPageTitle().equalsIgnoreCase(getCurrentPageTitle());
    }

    @Override
    public Collection<MILandingPages> getUserFavorites() {
        if (getSwitchBoardStatus(FAVORITES) == COLLAPSED) {
            doExpandSwitchBoard(FAVORITES);
        }
        return favoritesSwitchBoard.getFavoritePages();
    }

    @Override
    public void doRemoveFromFavoritesList(MILandingPages page) {
        if (getSwitchBoardStatus(FAVORITES) == COLLAPSED) {
            doExpandSwitchBoard(FAVORITES);
        }
        favoritesSwitchBoard.doRemoveFromFavoriteList(page);
    }

    @Override
    public MILandingPages getCurrentHomepage() {
        if (getSwitchBoardStatus(FAVORITES) == COLLAPSED) {
            doExpandSwitchBoard(FAVORITES);
        }
        return favoritesSwitchBoard.getCurrentHomePage();
    }


    @Override
    public boolean isPageAvailable(MILandingPages miLandingPage) {
        try {
            SwitchBoard switchBoard = miLandingPage.getSwitchBoard();
            if (!isSwitchBoardAvailable(switchBoard)) {
                return false;
            }
            WebElement switchboardContentElement = webElements.getContentElement(switchBoard);
            String xpath = ".//div[@id='" + miLandingPage.getDbId() + "']";
            return SeleniumUtils.elementExists(switchboardContentElement, xpath);
        } catch (StaleElementReferenceException e) {
            SeleniumUtils.refreshPage(getDriver());
            doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
            return isPageAvailable(miLandingPage);
        }
    }

    @Override
    public IWelcomePage chooseAnotherApplication(Integer waitTimeSeconds) {
        webElements.chooseAnotherApplicationLink.click();
        IWelcomePage welcomePage = PageFactory.initElements(getDriver(), WelcomePage.class);
        welcomePage.doWaitTillFullyLoaded(waitTimeSeconds);
        return welcomePage;
    }

    @Override
    public boolean isChooseAnotherApplicationLinkNameAvailable(){
        return getDriver().findElements(By.linkText("Choose another application")).size() != 0;
    }

    public static class IDs {
        public static final String SWB_FAVORITES_HEADER = "swb_My Favorites";
        public static final String SWB_ANALYTICS_HEADER = "swb_Analytics";
        public static final String SWB_DASHBOARDS_HEADER = "swb_Dashboards";
        public static final String SWB_QUERY_BUILDER_HEADER = "swb_Query Builder";
        public static final String SWB_REPORTS_HEADER = "swb_Reports";
        public static final String SWB_OUTREACH_HEADER = "swb_Outreach";
        public static final String SWB_DOCUMENTATION_HEADER = "swb_Documentation";
    }

    static class WebElements {
        WebDriver driver;

        WebElements(WebDriver driver) {
            this.driver = driver;
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitleElm = null;

        @FindBy(id = "left")
        private WebElement leftNavigationElm = null;

        @FindBy(id = "swb_My Favorites")
        private WebElement swbFavoritesElm = null;

        @FindBy(id = "My Favorites_content")
        private WebElement swbFavoritesContentElm = null;

        @FindBy(id = "swb_Analytics")
        private WebElement swbAnalyticsElm = null;

        @FindBy(id = "Analytics_content")
        private WebElement swbAnalyticsContentElm = null;

        @FindBy(id = "swb_Dashboards")
        private WebElement swbDashboardsElm = null;

        @FindBy(id = "Dashboards_content")
        private WebElement swbDashboardsContentElm = null;

        @FindBy(id = "swb_Query Builder")
        private WebElement swbQueryBuilderElm = null;


        @FindBy(id = "Query Builder_content")
        private WebElement swbQueryBuilderContentElm = null;

        @FindBy(id = "swb_Reports")
        private WebElement swbReportsElm = null;

        @FindBy(id = "Reports_content")
        private WebElement swbReportsContentElm = null;

        @FindBy(id = "swb_Outreach")
        private WebElement swbOutreachElm = null;

        @FindBy(id = "Outreach_content")
        private WebElement swbOutreachContentElm = null;

        @FindBy(id = "swb_Documentation")
        private WebElement swbDocumentationElm = null;

        @FindBy(id = "Documentation_content")
        private WebElement swbDocumentationContentElm = null;

        @FindBy(linkText = "Choose another application")
        private WebElement chooseAnotherApplicationLink;

        private WebElement getSwitchboardElement(SwitchBoard switchBoard) {
            switch (switchBoard) {
                case FAVORITES:
                    return swbFavoritesElm;
                case ANALYTICS:
                    return swbAnalyticsElm;
                case DASHBOARDS:
                    return swbDashboardsElm;
                case QUERY_BUILDER:
                    return swbQueryBuilderElm;
                case REPORTS:
                    return swbReportsElm;
                case DOCUMENTATION:
                    return swbDocumentationElm;
                case OUTREACH:
                    return swbOutreachElm;
                default:
                    throw new RuntimeException("Unknown switchboard " + switchBoard);
            }
        }

        public WebElement getContentElement(SwitchBoard switchBoard) {
            switch (switchBoard) {
                case FAVORITES:
                    return swbFavoritesContentElm;
                case ANALYTICS:
                    return swbAnalyticsContentElm;
                case DASHBOARDS:
                    return swbDashboardsContentElm;
                case QUERY_BUILDER:
                    return swbQueryBuilderContentElm;
                case REPORTS:
                    return swbReportsContentElm;
                case DOCUMENTATION:
                    return swbDocumentationContentElm;
                case OUTREACH:
                    return swbOutreachContentElm;
                default:
                    throw new RuntimeException("getSwitchboard Status is not implemented for " + switchBoard);
            }
        }
    }
}
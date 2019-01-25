package com.vh.mi.automation.impl.comp.navPanel;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.vh.mi.automation.api.constants.MILandingPages;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.impl.comp.LoadingPanelComp;
import com.vh.mi.automation.impl.comp.LoadingPopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by i80448 on 10/7/2014.
 */
class FavoritesSwitchBoard extends AbstractWebComponent {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean isFullyLoaded() {
        return true;
    }

    private static abstract class IDs {
        public static final String SWB_FAVORITES_HEADER = "swb_My Favorites";
        public static final String SWB_FAVORITES_CONTENT = "My Favorites_content";
    }

    private abstract class XPath {
        public static final String PAGE_TITLE = "//*[@id='pageTitle']";
    }

    @FindBy(xpath = XPath.PAGE_TITLE)
    private WebElement pageTitleElm;

    @FindBy(id = IDs.SWB_FAVORITES_CONTENT)
    private WebElement contentElm;

    public FavoritesSwitchBoard(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Gets all pages available in Favorites switch board.
     *
     * @return
     */
    public List<MILandingPages> getAllPages() {
        List<MILandingPages> pages = new ArrayList<>();

        for (WebElement elm : getRowElements()) {
            String navLinkLabel = elm.getText();

            // filter out dummy rows - current implementation has dummy  <li> elements.
            if (!isNullOrEmpty(navLinkLabel)) {
                Optional<MILandingPages> landPage = MILandingPages.getByNavLinkLabel(navLinkLabel.trim());
                if (landPage.isPresent()) {
                    pages.add(landPage.get());
                } else {
                    logger.info("Mapping not available for _{}_ in LandingPages enum.", navLinkLabel.trim());
                }
            }
        }
        return pages;
    }

    /**
     * Gets user's current favorite pages.
     *
     * @return
     */

    public Collection<MILandingPages> getFavoritePages() {
        List<MILandingPages> allPages = getAllPages();

        return Collections2.filter(allPages, new Predicate<MILandingPages>() {
            @Override
            public boolean apply(MILandingPages input) {

                return (input != MILandingPages.OPTIONS && input != MILandingPages.MY_JOBS)
                        && (!isCurrentHomePage(input) || isHomePageAndFavorite(input));
            }
        });
    }

    /**
     * Checks if given page is currently in user's favorite list.
     *
     * @param landPage
     * @return
     */
    public boolean isFavorite(MILandingPages landPage) {
        Collection<MILandingPages> currentFavorites = getFavoritePages();
        if (currentFavorites != null && !currentFavorites.isEmpty()) {
            return currentFavorites.contains(landPage);
        }
        return false;
    }

    public boolean isInFavoriteSwitchBoard(MILandingPages landPage) {
        List<MILandingPages> allPages = getAllPages();
        if (allPages != null && !allPages.isEmpty()) {
            return allPages.contains(landPage);
        }
        return false;
    }

    private boolean isHomePageAndFavorite(MILandingPages landPage) {
        if (isCurrentHomePage(landPage)) {
            // current home page may or may not be in favorite list.
            // All MI pages available in favorites switchboard are user's favorites except the current home page.
            // So explicitly check if current home page is also in user's favorites list.

            WebElement rowElm = getRowElementFor(landPage);

            // check if 'Remove from favorites' button exists
            String xPathQry = ".//input[@type='button' and @title='Remove from Favorites']";
            boolean removeBtnAvail = SeleniumUtils.elementExists(rowElm, xPathQry);
            return removeBtnAvail;
        }

        return false;
    }

    private String getCurrentPageTitle() {
        return pageTitleElm.getText();
    }

    private boolean isCurrentlyNavigatedPage(MILandingPages page) {
        String title = page.getPageTitle();
        return title.equalsIgnoreCase(getCurrentPageTitle());
    }

    private List<WebElement> getRowElements() {
        WebElement container = contentElm.findElement(By.tagName("ul"));
        List<WebElement> rows = container.findElements(By.tagName("li"));

        return rows;
    }

    /**
     * Check if given landPage is in favorite list before calling this method.
     *
     * @param landPage
     * @return
     */
    private WebElement getRowElementFor(MILandingPages landPage) {
        // Assumption : landPage is in favorite switch board.
        List<WebElement> rows = getRowElements();
        for (WebElement r : rows) {
            if (landPage.getNavLinkLabel().equals(r.getText())) {
                return r;
            }
        }
        // ----
        throw new AutomationException(landPage + " exists in user's favorite list but could not get a row element for this page, there is something wrong.");
    }

    /**
     * Checks if given page is the user's current home page.
     *
     * @param page
     * @return
     */
    public boolean isCurrentHomePage(MILandingPages page) {
        WebElement rowElm = getRowElementFor(page);

        if (rowElm != null) {
            // check if the home button exists

            String xPathQry = ".//input[@type='submit' and @title='Home Page' and @disabled='disabled']";
            boolean homepageBtnAvailable = SeleniumUtils.elementExists(rowElm, xPathQry);

            return homepageBtnAvailable;
        }
        return false;
    }

    /**
     * Gets user's current home page.
     *
     * @return {@link com.vh.mi.automation.api.constants.MILandingPages}
     */
    public MILandingPages getCurrentHomePage() {
        for (MILandingPages p : getAllPages()) {
            if (isCurrentHomePage(p)) return p;
        }

        throw new AutomationException("There must be a home page set for the user. Something wrong .....");
    }

    // Current implementation, (why there is inconsistency?????)
    // Cases for a nav link -

    // current, homepage, fav => 3 cols, link is disabled, fav link is <input ...>
    // !current, homepage, fav => 3 cols, link is available <a ...>, fav link is <input ...>

    // current, !homepage, fav => 2 cols, link is disabled, fav link is <a ...>
    // !current, !homepage, fav => 2 cols, link is available <a ...>, fav link is <a ...>

    private WebElement getNavLinkElementFor(MILandingPages page) {
        if (isInFavoriteSwitchBoard(page)) {
            if (!isCurrentlyNavigatedPage(page)) {
                WebElement rowElm = getRowElementFor(page);
                By byNavLinkText = By.linkText(page.getNavLinkLabel());
                return rowElm.findElement(byNavLinkText);
            } else {
                logger.info("__{}__ is currently navigated page, so nav link is disabled.", page);
                return null;
            }
        }
        logger.info("__{}__ is not in user's favorites switch board.", page);
        return null;
    }

    private WebElement getRemoveFromFavLinkElementFor(MILandingPages page) {
        if (isInFavoriteSwitchBoard(page)) {
            String xPathQry;
            if (isHomePageAndFavorite(page)) {
                xPathQry = ".//input[@type='button' and @title='Remove from Favorites']";
                WebElement rowElm = getRowElementFor(page);
                return rowElm.findElement(By.xpath(xPathQry));
            } else if (!isCurrentHomePage(page)) {
                xPathQry = ".//a[@title='Remove from Favorites']";
                WebElement rowElm = getRowElementFor(page);
                return rowElm.findElement(By.xpath(xPathQry));
            } else {
                logger.info("_{}_ is 'homepage && NOT in user's favorite list'.");
                return null;
            }
        }
        logger.info("__{}__ is not in user's favorite switch board.", page);
        return null;
    }

    public void doRemoveFromFavoriteList(MILandingPages page) {
        checkState(isInFavoriteSwitchBoard(page), page + " is not in favorite switch board.");
        checkState(isFavorite(page), page + " is not a favorite page.");

        WebElement removeBtn = getRemoveFromFavLinkElementFor(page);
        removeBtn.click();

        new LoadingPopup(getDriver()).waitTillDisappears();
    }

    public void doNavigateTo(MILandingPages page) {
        checkState(isInFavoriteSwitchBoard(page), page + " is not in favorite switch board.");

        if (!isCurrentlyNavigatedPage(page)) {
            WebElement navLinkElm = getNavLinkElementFor(page);
            navLinkElm.click();

            new LoadingPanelComp(getDriver()).waitTillDisappears();
        } else {
            logger.info(page + " is the currently navigated page.");
        }
    }

}

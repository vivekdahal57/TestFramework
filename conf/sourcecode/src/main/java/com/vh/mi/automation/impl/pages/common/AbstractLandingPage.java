package com.vh.mi.automation.impl.pages.common;

import com.google.common.base.Optional;
import com.vh.mi.automation.api.comp.dataGrid.customizer.ICustomizer;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizerEvents;
import com.vh.mi.automation.api.comp.navigation.INavigationPanel;
import com.vh.mi.automation.api.constants.SwitchBoard;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.groovy.Module;
import com.vh.mi.automation.groovy.ModuleInfo;
import com.vh.mi.automation.impl.comp.loading.LoadingCustomization;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.comp.loading.MessagePopup;
import com.vh.mi.automation.impl.comp.navPanel.NavigationPanel;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * @author i80448
 * @author nimanandhar
 */
public abstract class AbstractLandingPage extends AbstractMIPage implements IAmLandingPage {
    private Module module;
    private final LoadingPopup loadingPopup;
    private final MessagePopup messagePopup;


    public AbstractLandingPage(WebDriver driver, String moduleId) {
        super(driver);
        this.module = ModuleInfo.INSTANCE.getModule(moduleId);
        loadingPopup = new LoadingPopup(driver);
        messagePopup = new MessagePopup(driver);
    }

    @Override
    public SwitchBoard getSwitchBoard() {
        return module.getSwitchBoard();
    }

    @Override
    public String getPageId() {
        return module.getPageId();
    }

    @Override
    public String getPageTitle() {
        return module.getPageTitle();
    }

    @Override
    public void doSetAsHomePage() {
        WebElement swElm = getSwitchboardContentElement(getSwitchBoard());

        if (!isCurrentHomepage()) {
            String homePageBtnEnabledXpath = ".//input[@type='button' and @title='Set as home page']";
            WebElement homePageButton = swElm.findElement(By.xpath(homePageBtnEnabledXpath));
            SeleniumUtils.click(homePageButton, getDriver());

            loadingPopup.waitTillDisappears();
            WaitUtils.waitForMilliSeconds(200);
            messagePopup.waitTillDisappears();

            logger.info("__{}__ set as user's home page.", getClass().getSimpleName());
        } else {
            // it's because this page is currently the user's home page.
            logger.info("__{}__ is the current home page.", getClass().getSimpleName());
        }
    }


    private WebElement getSwitchboardContentElement(SwitchBoard switchBoard) {
        switch (switchBoard) {
            case ANALYTICS:
                return getDriver().findElement(By.id(IDs.SWB_ANALYTICS_CONTENT));
            case DOCUMENTATION:
                return getDriver().findElement(By.id(IDs.SWB_DOCUMENTATION_CONTENT));
            case FAVORITES:
                return getDriver().findElement(By.id(IDs.SWB_FAVORITES_CONTENT));
            case QUERY_BUILDER:
                return getDriver().findElement(By.id(IDs.SWB_QUERY_BUILDER_CONTENT));
            case REPORTS:
                return getDriver().findElement(By.id(IDs.SWB_REPORTS_CONTENT));
            case OUTREACH:
                return getDriver().findElement(By.id(IDs.SWB_OUTREACH_CONTENT));
            default:
                throw new AutomationException("Unknown Switchboard " + switchBoard);
        }
    }

    @Override
    public void doAddToFavoritesList() {
        Optional<WebElement> addToFavButton = getAddToFavButton();
        if (addToFavButton.isPresent()) {
            addToFavButton.get().click();

            loadingPopup.waitTillDisappears();
            logger.info("__{}__ added to user's favorites list.", getClass().getSimpleName());
        } else {
            logger.info("__{}__ is already in user's favorites list.", getClass().getSimpleName());
        }
    }

    @Override
    public INavigationPanel getNavigationPanel(){
        return PageFactory.initElements(getDriver(), NavigationPanel.class);
    }

    @Override
    public void doCustomizeAllColumns(){
        IDataGridCustomizer dataGridCustomizer = (IDataGridCustomizer) getDataGridCustomizer();
        if(null != dataGridCustomizer){
            dataGridCustomizer.doSelectAll();
            dataGridCustomizer.doApplySelection();
            new LoadingCustomization(getDriver()).waitTillDisappears();
        }
    }

    public IDataGridCustomizerEvents getDataGridCustomizer() {
        return null;
    }

    @Override
    public void doRemoveFromFavoriteList() {
        Optional<WebElement> removeButton = getRemoveFromFavButton();
        if (removeButton.isPresent()) {
            removeButton.get().click();
            loadingPopup.waitTillDisappears();
            logger.info("__{}__ removed from user's favorites list.", getClass().getSimpleName());
        } else {
            logger.info("__{}__ is not in user's favorites list.", getClass().getSimpleName());
        }
    }

    private Optional<WebElement> getRemoveFromFavButton() {
        WebElement swElm = getSwitchboardContentElement(getSwitchBoard());
        String removeBtnXpath = ".//input[@type='button' and @class='d2-ico-fav' and @title='Remove from Favorites']";
        boolean removeBtnExists = SeleniumUtils.elementExists(swElm, removeBtnXpath);
        if (removeBtnExists) {
            WebElement removeBtn = swElm.findElement(By.xpath(removeBtnXpath));
            return Optional.of(removeBtn);
        }
        return Optional.absent();
    }

    /**
     * Web element for add to favorites button link.
     *
     */
    private Optional<WebElement> getAddToFavButton() {
        WebElement swElm = getSwitchboardContentElement(getSwitchBoard());
        String addBtnXpath = ".//input[@type='button' and @class='d2-ico-notfav' and @title='Add to Favorites']";
        boolean addBtnExists = SeleniumUtils.elementExists(swElm, addBtnXpath);
        if (addBtnExists) {
            WebElement addBtn = swElm.findElement(By.xpath(addBtnXpath));
            return Optional.of(addBtn);
        }
        return Optional.absent();
    }

    @Override
    public boolean isInFavoriteList() {
        return getRemoveFromFavButton().isPresent();
    }

    private boolean isCurrentHomepage() {
        WebElement swElm = getSwitchboardContentElement(getSwitchBoard());

        // set home page button is disabled?
        String homeBtnDisabledXpath = ".//input[@type='button' and @title='Set as home page' and @disabled='disabled']";

        return SeleniumUtils.elementExists(swElm, homeBtnDisabledXpath);
    }


    private static final class IDs {
        public static final String SWB_FAVORITES_CONTENT = "My Favorites_content";
        public static final String SWB_ANALYTICS_CONTENT = "Analytics_content";
        public static final String SWB_QUERY_BUILDER_CONTENT = "Query Builder_content";
        public static final String SWB_OUTREACH_CONTENT = "Outreach_content";
        public static final String SWB_REPORTS_CONTENT = "Reports_content";
        public static final String SWB_DOCUMENTATION_CONTENT = "Documentation_content";
    }
}

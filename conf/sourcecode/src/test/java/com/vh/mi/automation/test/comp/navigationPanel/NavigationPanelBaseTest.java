package com.vh.mi.automation.test.comp.navigationPanel;

import com.vh.mi.automation.api.constants.MILandingPages;
import com.vh.mi.automation.api.constants.SwitchBoard;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.groovy.Module;
import com.vh.mi.automation.impl.comp.navPanel.NavigationPanelTestHelper;
import com.vh.mi.automation.impl.configuration.FrontConfiguration;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import org.fest.assertions.Assertions;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.vh.mi.automation.api.constants.SwitchBoard.SwitchBoardStatus.COLLAPSED;
import static com.vh.mi.automation.api.constants.SwitchBoard.SwitchBoardStatus.EXPANDED;

/**
 * Base Class for all NavigationPanelTest that provides utility methods and
 * data providers common to all NavigationPanel tests
 * <p/>
 * Created by nimanandhar on 9/12/2014.
 */
public abstract class NavigationPanelBaseTest extends BaseTest {
    public static final Random RANDOM = new Random();
    public static final int NUMBER_OF_SWITCHBOARDS_TO_TEST = 2;
    public static final List<SwitchBoard> NON_LANDING_PAGE_SWITCHBOARDS = Arrays.asList(SwitchBoard.DOCUMENTATION, SwitchBoard.REPORTS);
    public static final List<MILandingPages> REMOVE_LANDING_PAGE = Arrays.asList(MILandingPages.MY_JOBS, MILandingPages.OPTIONS,MILandingPages.MY_DASHBOARD);
    public static final List<String> NON_LANDING_PAGE = Arrays.asList(MILandingPages.MY_DASHBOARD.getDbId(), MILandingPages.MY_JOBS.getDbId());

    protected LandingPageClasses landingPageClasses;
    protected NavigationPanelTestHelper navigationPanelTestHelper;
    protected FrontConfiguration frontConfiguration;

    public void setUp() {
        super.setUp();
        navigationPanelTestHelper = new NavigationPanelTestHelper(webDriver);
        frontConfiguration = new FrontConfiguration(context.getApplication().getAppId());
        landingPageClasses = new LandingPageClasses(navigationPanelTestHelper.getDriver());
    }

    protected IAmLandingPage doNavigateTo(MILandingPages landingPages, Integer waitTimeInSeconds) {
        Class<IAmLandingPage> implClass = landingPageClasses.getImplClass(landingPages);
        return navigationPanel.doNavigateTo(implClass, waitTimeInSeconds);
    }

    @DataProvider(name = "landingPagesProvider")
    protected Object[][] landingPagesProvider() {
        return getTestLandingPagesOfSize(100);
    }

    @DataProvider(name = "singleLandingPageProvider")
    protected Object[][] singleLandingPageProvider() {
        return getTestLandingPagesOfSize(1);
    }

    private Object[][] getTestLandingPagesOfSize(int numberOfPages) {
        List<MILandingPages> landingPagesToTest = RANDOM.randomSubList(getAvailableLandingPages(), numberOfPages);
        return DataProviderUtils.getObjects(landingPagesToTest);
    }

    @DataProvider(name = "allAvailableLandingPagesProvider")
    protected Object[][] allAvailableLandingPagesProvider() {
        return DataProviderUtils.getObjects(getAvailableLandingPages());
    }

    private List<MILandingPages> getAvailableLandingPages() {
        List<MILandingPages> landingPages = new ArrayList();
        for (MILandingPages page : MILandingPages.values()) {
            if (REMOVE_LANDING_PAGE.contains(page))
                continue;
            if (isImplemented(page) && navigationPanel.isPageAvailable(page)) {
                landingPages.add(page);
            }
        }
        return landingPages;
    }

    private boolean isImplemented(MILandingPages landingPage) {
        return landingPageClasses.hasMappingFor(landingPage);
    }

    @DataProvider(name = "allSwitchboardsProvider")
    protected Object[][] allSwitchboardsProvider() {
        return DataProviderUtils.getObjects(Arrays.asList(SwitchBoard.values()));
    }

    /*
    Provides switchboard and the modules in the switchboard
    The firstParameter is the switchboard
    The secondParameter is the list of top-level modules in that switchboard
     */
    @DataProvider(name = "switchboardLandingModulesProvider")
    protected Object[][] switchboardLandingModulesProvider() {
        List<SwitchBoard> availableSwitchboards = getAvailableSwitchboards();
        availableSwitchboards.remove(SwitchBoard.FAVORITES); //Modules in switchboard are not arranged in order
        availableSwitchboards.remove(SwitchBoard.DOCUMENTATION); //Documentations are not landing pages
        availableSwitchboards.remove(SwitchBoard.REPORTS); //Reports are not landing Pages since they navigate to different application

        Object[][] switchboardModules = new Object[availableSwitchboards.size()][2];
        int row = 0;
        for (SwitchBoard switchboard : availableSwitchboards) {
            List<Module> modules = navigationPanelTestHelper.getModules(switchboard);
            switchboardModules[row][0] = switchboard;
            switchboardModules[row][1] = modules;
            row++;
        }
        return switchboardModules;
    }


    private List<SwitchBoard> getAvailableSwitchboards() {
        List<SwitchBoard> availableSwitchboards = new ArrayList<>();
        for (SwitchBoard switchBoard : SwitchBoard.values()) {
            if (navigationPanel.isSwitchBoardAvailable(switchBoard)) {
                availableSwitchboards.add(switchBoard);
            }
        }
        return availableSwitchboards;
    }

    @DataProvider(name = "switchboardModulesCollectionProvider")
    protected Object[][] switchboardModulesCollectionProvider() {
        List<SwitchBoard> availableSwitchboards = getAvailableSwitchboards();
        Object[][] switchboardSections = new Object[availableSwitchboards.size()][2];

        int row = 0;
        for (SwitchBoard switchboard : availableSwitchboards) {
            switchboardSections[row][0] = switchboard;
            switchboardSections[row][1] = navigationPanelTestHelper.getModules(switchboard);
            row++;
        }
        return switchboardSections;
    }

    @DataProvider(name = "landingPageModulesProvider")
    protected Object[][] landingPageModulesProvider() {
        List<Module> allModules = new ArrayList<>();
        for (SwitchBoard switchBoard : getAvailableSwitchboards()) {
            if (NON_LANDING_PAGE_SWITCHBOARDS.contains(switchBoard))
                continue;
            for (Module module : navigationPanelTestHelper.getModules(switchBoard)) {
                if (NON_LANDING_PAGE.contains(module.getId()))
                    continue;
                allModules.addAll(module.getLeafModules());
            }
        }
        return DataProviderUtils.getObjects(allModules);
    }

    @DataProvider(name = "leafModulesProvider")
    protected Object[][] landingModulesProvider() {
        List<Module> allModules = new ArrayList<>();
        for (SwitchBoard switchBoard : getAvailableSwitchboards()) {
            for (Module module : navigationPanelTestHelper.getModules(switchBoard)) {
                allModules.addAll(module.getLeafModules());
            }
        }
        return DataProviderUtils.getObjects(allModules);
    }


    @DataProvider(name = "switchboardSectionsProvider")
    protected Object[][] switchboardSectionsProvider() {
        List<SwitchBoard> availableSwitchboards = getAvailableSwitchboards();
        Object[][] switchboardSections = new Object[availableSwitchboards.size()][2];

        int row = 0;
        for (SwitchBoard switchboard : availableSwitchboards) {
            switchboardSections[row][0] = switchboard;
            switchboardSections[row][1] = getSections(switchboard);
            row++;
        }
        return switchboardSections;

    }

    @DataProvider(name = "sectionsProvider")
    protected Object[][] sectionsProvider() {
        List<SwitchBoard> availableSwitchboards = getAvailableSwitchboards();

        List<Module> sections = new ArrayList<>();
        for (SwitchBoard switchboard : availableSwitchboards) {
            sections.addAll(getSections(switchboard));
        }

        return DataProviderUtils.getObjects(sections);
    }

    /**
     * Sections is defined to be those modules that have sub-modules, and
     * are displayed in the navigation panel with a plus/minus sign
     *
     * @param switchBoard
     * @return the sections in the switchboard
     */
    private List<Module> getSections(SwitchBoard switchBoard) {
        List<Module> modules = navigationPanelTestHelper.getModules(switchBoard);
        List<Module> sections = new ArrayList<>();
        for (Module module : modules) {
            if (module.hasChildren()) {
                sections.add(module);
                sections.addAll(getSectionsFromSubModules(module));
            }
        }
        return sections;
    }

    private List<Module> getSectionsFromSubModules(Module section) {
        List<Module> sections = new ArrayList<>();
        for (Module subModule : section.getChildren()) {
            if (subModule.hasChildren()) {
                sections.add(subModule);
                sections.addAll(getSectionsFromSubModules(subModule));
            }
        }
        return sections;
    }

    protected void expandSwitchboardIfCollapsed(SwitchBoard switchBoard) {
        if (navigationPanel.getSwitchBoardStatus(switchBoard) == COLLAPSED) {
            navigationPanel.doExpandSwitchBoard(switchBoard);
        }
        Assertions.assertThat(navigationPanel.getSwitchBoardStatus(switchBoard))
                .as("Switchboard " + switchBoard + " must be expanded but was not")
                .isEqualTo(EXPANDED);
    }

    protected boolean isErrorPage() {
        try {
            webDriver.findElement(By.id("leftContent"));
            return false;
        } catch (Exception ex) {
            return true;
        }
    }

    protected void navigateBack() {
        webDriver.navigate().back();
    }
}

package com.vh.mi.automation.test.comp.navigationPanel;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.vh.mi.automation.api.config.IDriverConfiguration;
import com.vh.mi.automation.api.constants.MILandingPages;
import com.vh.mi.automation.api.constants.SwitchBoard;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.groovy.Module;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.fest.assertions.Assertions.assertThat;

/**
 * UserStory: US23221
 * Name: STA 8: Click each and every links of all sections of navigation panel
 * 1) Respective page should open after clicking links of navigation panel.
 * 2) Set as home page and Add to favorite icon must be present at the end of links that is just clicked.
 * <p/>
 * Created by nimanandhar on 10/13/2014.
 */
public class NavigationPanel_Links_Test_US23221 extends NavigationPanelBaseTest {
    private boolean skipTests = false;

    @BeforeClass
    public void setUp() {
        super.setUp();
    }


    @Test(dataProvider = "landingPageModulesProvider", description = "Test that respective page should open" +
            "after clicking on links of Navigation Panel.")
    public void correctNavigationFromLinksOfNavigationPanelTest(Module link) {


        Preconditions.checkArgument(link.isLeaf(), "Precondition failed for module " + link + " Module is not leaf module");


        if (skipTests) {
            logger.warn("Skipped homePage tests for " + link + " because previous homePage was in an inconsistent state");
            return;
        }

        if (link.getSwitchBoard()!=SwitchBoard.DASHBOARDS) {

            expandSwitchboardIfCollapsed(link.getSwitchBoard());

            navigationPanelTestHelper.clickOnLink(link);

             Optional<MILandingPages> miLandingPage = MILandingPages.byId(link.getId());

            if (!miLandingPage.isPresent()) {
                throw new Error("No MILandingPage defined for moduleId " + link.getId() + "Please define the module in MILandingPage");
            }
            final String expectedPageTitle = miLandingPage.get().getPageTitle();

            if (context.getDriverConfiguration().getBrowser() != IDriverConfiguration.Browser.IE) {
                navigationPanelTestHelper.waitTillHomePageLinksAppear(link);
                assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(expectedPageTitle);
                assertThat(navigationPanelTestHelper.hasHomePageIcon(link)).isTrue();
                assertThat(navigationPanelTestHelper.hasFavoriteIcon(link)).isTrue();
            } else {
                /**
                 * Test For IE
                 * IE does not work reliably find Home Page Link
                 */

                WaitUtils.waitUntil(getWebDriver(), defaultWaitTime, new Function() {
                    @Override
                    public Boolean apply(Object input) {
                        return navigationPanel.getCurrentPageTitle().equals(expectedPageTitle);
                    }
                });
                assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(expectedPageTitle);
             }
            if (link.getId().equalsIgnoreCase("4")) {
                landingPageClasses.popupExists();

            }
        }
    }


    @AfterMethod
    public void navigateBackIfOnErrorPage(ITestResult testResult) {
        if (!testResult.isSuccess() && isErrorPage()) {
            skipTests = true;
        }
    }
}

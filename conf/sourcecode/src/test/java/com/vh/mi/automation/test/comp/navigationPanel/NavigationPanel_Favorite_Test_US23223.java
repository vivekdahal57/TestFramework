package com.vh.mi.automation.test.comp.navigationPanel;

import com.vh.mi.automation.api.constants.MILandingPages;
import com.vh.mi.automation.api.features.IAmLandingPage;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * StoryName: STA 10: Click "Add to favorite" icons.
 * Id: US23223
 * Description:
 * 1) Page should be added as favorite and link of page should be added in My SMI section.
 * 2) Clicking Remove From Favorite star icon in My Favorites section should remove link from Favorite.
 * Rally URL: https://rally1.rallydev.com/#/20126998288d/detail/userstory/23062382679
 * <p/>
 * Created by nimanandhar on 10/13/2014.
 */
public class NavigationPanel_Favorite_Test_US23223 extends NavigationPanelBaseTest {

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Test(dataProvider = "landingPagesProvider")
    public void testThatLandingPageCanBeAddedAndRemovedFromFavorites(MILandingPages miLandingPage) {
        IAmLandingPage page = doNavigateTo(miLandingPage, defaultWaitTime);

        String landingPage = String.valueOf(miLandingPage);
        if (landingPage == "INDIVIDUALS_301") {
            landingPageClasses.popupExists();
        }

        page.doAddToFavoritesList();

        assertThat(page.isInFavoriteList())
                .as("Expecting " + miLandingPage + " to be in favorite list but was not")
                .isTrue();
        assertThat(navigationPanel.getUserFavorites()).contains(miLandingPage);

        page.doRemoveFromFavoriteList();
        assertThat(page.isInFavoriteList())
                .as(miLandingPage + " is in favorite list even after removing it")
                .isFalse();
        assertThat(navigationPanel.getUserFavorites()).excludes(miLandingPage);
    }

    @AfterMethod
    public void navigateBackIfOnErrorPage(ITestResult testResult) throws Exception {
        if (isErrorPage()) {
            navigateBack();
        }
    }
}

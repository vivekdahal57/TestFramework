package com.vh.mi.automation.test.comp.navigationPanel;

import com.vh.mi.automation.api.constants.MILandingPages;
import com.vh.mi.automation.api.constants.SwitchBoard;
import com.vh.mi.automation.api.features.IAmLandingPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collection;

import static com.vh.mi.automation.api.constants.SwitchBoard.SwitchBoardStatus.COLLAPSED;
import static com.vh.mi.automation.api.constants.SwitchBoard.SwitchBoardStatus.EXPANDED;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests related to NavigationPanel Component itself and not any specific user stories
 * Created by nimanandhar on 10/13/2014.
 */
public class NavigationPanelTest extends NavigationPanelBaseTest {

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldBeAbleToExpandAndCollapseSwitchboards() {
        Collection<SwitchBoard> switchBoards = RANDOM.randomSubList(navigationPanel.getSwitchBoards(), NUMBER_OF_SWITCHBOARDS_TO_TEST);
        assertThat(switchBoards.size()).isEqualTo(NUMBER_OF_SWITCHBOARDS_TO_TEST);

        for (SwitchBoard switchBoard : switchBoards) {
            navigationPanel.doExpandSwitchBoard(switchBoard);
            assertThat(navigationPanel.getSwitchBoardStatus(switchBoard)).isEqualTo(EXPANDED);

            navigationPanel.doCollapseSwitchBoard(switchBoard);
            assertThat(navigationPanel.getSwitchBoardStatus(switchBoard)).isEqualTo(COLLAPSED);
        }
    }

    @Test
    public void testThatGetCurrentHomePageWorksEvenWhenAllSwitchboardsAreCollapsed() {
        for (SwitchBoard switchBoard : SwitchBoard.values()) {
            navigationPanel.doCollapseSwitchBoard(switchBoard);
        }
        MILandingPages currentHomepage = navigationPanel.getCurrentHomepage();
        assertThat(currentHomepage).isNotNull();
    }


    /**
     * Test that the operation of adding and removing favorites is idempotent
     * i.e adding a page to a favorites once, is the same as adding a page to favorite
     * multiple times. This behavior is important in the test because,  we do not have to
     * check whether the page is already a favorite when adding a random page to favorite
     */
    @Test(dataProvider = "singleLandingPageProvider")
    public void testThatAddRemoveFavoritesIsIdempotent(MILandingPages landingPage) {
        IAmLandingPage page = doNavigateTo(landingPage, defaultWaitTime);

        page.doAddToFavoritesList();
        page.doAddToFavoritesList();
        assertThat(page.isInFavoriteList()).isTrue();
        assertThat(navigationPanel.getUserFavorites()).contains(landingPage);

        page.doRemoveFromFavoriteList();
        page.doRemoveFromFavoriteList();
        assertThat(page.isInFavoriteList()).isFalse();
        assertThat(navigationPanel.getUserFavorites()).excludes(landingPage);
    }


    @Test(dataProvider = "singleLandingPageProvider")
    public void testThatSetHomePageIsIdempotent(MILandingPages landingPage) {
        IAmLandingPage page = doNavigateTo(landingPage, defaultWaitTime);
        page.doSetAsHomePage();
        page.doSetAsHomePage();
        assertThat(navigationPanel.getCurrentHomepage()).isEqualTo(landingPage);
    }

    @Test(dataProvider = "singleLandingPageProvider")
    public void testThatHomePageCanBeAddedAndRemovedFromFavorites(MILandingPages landingPage) {
        IAmLandingPage page = doNavigateTo(landingPage, defaultWaitTime);
        page.doSetAsHomePage();
        assertThat(navigationPanel.getCurrentHomepage()).isEqualTo(landingPage);

        //check that homepage can be added to favorite
        page.doAddToFavoritesList();
        assertThat(page.isInFavoriteList()).isTrue();
        assertThat(navigationPanel.getUserFavorites()).contains(landingPage);

        //remove from favorites
        page.doRemoveFromFavoriteList();
        assertThat(page.isInFavoriteList()).isFalse();
        assertThat(navigationPanel.getUserFavorites()).excludes(page);
    }
}

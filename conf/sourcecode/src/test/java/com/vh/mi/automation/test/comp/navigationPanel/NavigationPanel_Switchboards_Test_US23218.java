package com.vh.mi.automation.test.comp.navigationPanel;

import com.vh.mi.automation.api.constants.SwitchBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/*
 * UserStory: US23218
 * Name: STA 5: Check the sections (switch boards) of Navigation Panel
 * Navigation panel should contain following sections :
 * My Favorites
 * Analytics
 * Query Builder
 * Outreach
 * Reports
 * Documentation
 *
 * Created by nimanandhar on 10/13/2014.
 */
public class NavigationPanel_Switchboards_Test_US23218 extends NavigationPanelBaseTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final List<String> EXPECTED_SWITCHBOARD_TEXTS = Arrays.asList("My Favorites", "Analytics", "Dashboards", "Query Builder", "Outreach", "Reports", "Documentation");

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testThatAllSwitchboardsArePresent() {
        Collection<SwitchBoard> actualSwitchboards = navigationPanel.getSwitchBoards();
        List<SwitchBoard> expectedSwitchboards = frontConfiguration.getSwitchboards();
        assertThat(actualSwitchboards)
                .as("Expected available switchboards to be " + expectedSwitchboards + " but was " + actualSwitchboards)
                .containsOnly(expectedSwitchboards.toArray());
    }

    @Test
    public void testThatTheTextsOfTheSwitchboardIsCorrect() {
        List<String> switchboardHeaderTexts = navigationPanelTestHelper.getSwitchboardHeaderTexts();
        logger.info("Found the following switchboard texts " + switchboardHeaderTexts);

        assertThat(switchboardHeaderTexts).isNotEmpty();
        for (String switchboardHeaderText : switchboardHeaderTexts) {
            assertThat(switchboardHeaderText).isIn(EXPECTED_SWITCHBOARD_TEXTS);
        }


    }
}

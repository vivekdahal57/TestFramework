package com.vh.mi.automation.test.comp.navigationPanel;

import com.vh.mi.automation.groovy.Module;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/*
 * UserStory: US23220
 * Name: STA 7: Expand and collapse sections of navigation panel and the tree links in them if any
 * Description:
 * 1) Clicking the section name shoud expand or collapse that particular section.
 * 2) Clicking plus icon in link should expand it and sub links should be seen and clicking minus icon in link should collapse sub links
 *
 * Created by nimanandhar on 10/13/2014.
 */
public class NavigationPanel_ExpandCollapse_Section_Test_US23220 extends NavigationPanelBaseTest {

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Test(dataProvider = "sectionsProvider", description = "Test that clicking the Section Name should" +
            "expand or collapse that particular section.")
    public void sectionNameExpandOrCollapseTest(Module section) {
        expandSwitchboardIfCollapsed(section.getSwitchBoard());

        navigationPanelTestHelper.doExpandSection(section);
        assertThatSectionIsExpanded(section);
        assertThatSubModulesAreDisplayed(section);

        navigationPanelTestHelper.doCollapseSection(section);
        assertThatSectionIsCollapsed(section);
        assertThatSubModulesAreNotDisplayed(section);
    }

    private void assertThatSectionIsExpanded(Module section) {
        Assertions.assertThat(navigationPanelTestHelper.isExpanded(section)).isTrue();
    }

    private void assertThatSubModulesAreDisplayed(Module section) {
        for (Module subModule : section.getChildren()) {
            String message = subModule + " of " + section + " should be visible.";
            Assertions.assertThat(navigationPanelTestHelper.isVisible(subModule)).as(message).isTrue();
        }
    }

    private void assertThatSectionIsCollapsed(Module section) {
        Assertions.assertThat(navigationPanelTestHelper.isCollapsed(section)).isTrue();
    }

    private void assertThatSubModulesAreNotDisplayed(Module section) {
        for (Module subModule : section.getChildren()) {
            String message = subModule + " of " + section + " should not be visible";
            Assertions.assertThat(navigationPanelTestHelper.isVisible(subModule)).as(message).isFalse();
        }
    }
}

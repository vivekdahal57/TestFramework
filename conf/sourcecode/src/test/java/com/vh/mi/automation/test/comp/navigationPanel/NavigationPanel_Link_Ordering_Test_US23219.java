package com.vh.mi.automation.test.comp.navigationPanel;

import com.vh.mi.automation.api.constants.SwitchBoard;
import com.vh.mi.automation.groovy.Module;
import com.vh.mi.automation.test.utils.TestUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * UserStory: US23219
 * Name: STA 6: Check ordering and grouping of Navigation Panel
 * Description
 * 1) Links of Navigation Panel should be listed in Alphabetic order in each section.
 * 2) Links should be arranged in its associated section (i.e links are not misplaced in other sections of navigation panel).
 * <p/>
 * Created by nimanandhar on 10/17/2014.
 */
public class NavigationPanel_Link_Ordering_Test_US23219 extends NavigationPanelBaseTest {
    @BeforeClass
    public void setUp() {
        super.setUp();
    }


    @Test(dataProvider = "switchboardModulesCollectionProvider", description = "Test that the links of " +
            "Navigation Panel should be listed in alphabetic order in each section.")

    public void alphabeticalOrderOfNavigationPannelLinksTest(SwitchBoard switchBoard, List<Module> modules) {
        if (switchBoard != SwitchBoard.DASHBOARDS) {
            if (frontConfiguration.getSwitchboardsToBeExcludedFromOrderingTest().contains(switchBoard)) {
                return;
            }

            assertThat(modules).as("The modules are empty for switchboard " + switchBoard).isNotEmpty();
            assertThat(TestUtils.isSorted(modules)).as("The modules \n" + modules + "\n are not sorted").isTrue();

            for (Module module : modules) {
                assertThatSubModulesAreSorted(module);
            }
        }
    }
    private void assertThatSubModulesAreSorted(Module module) {
        if (!module.hasChildren())
            return;

        assertThat(TestUtils.isSorted(module.getChildren()))
                .as("The sub-modules of module " + module + " are not sorted")
                .isTrue();

        for (Module subModule : module.getChildren()) {
            assertThatSubModulesAreSorted(subModule);
        }
    }
}

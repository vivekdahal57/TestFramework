package com.vh.mi.automation.test.comp.navigationPanel;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.constants.SwitchBoard;
import com.vh.mi.automation.groovy.Module;
import com.vh.mi.automation.groovy.ModuleInfo;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
public class NavigationPanel_Link_AssociatedSection_Test_US23219 extends NavigationPanelBaseTest {
    private final ModuleInfo moduleInfo = ModuleInfo.INSTANCE;

    @BeforeClass
    public void setUp() {
        super.setUp();
    }


    @Test(dataProvider = "leafModulesProvider")
    public void linksShouldBeArrangedInAssociatedSection(Module module) {
        if (module.getSwitchBoard() != SwitchBoard.DASHBOARDS) {
            Preconditions.checkArgument(module.isLeaf(), "Precondition violated. Link module must be leaf module. Module = " + module);

            Module expectedModule = moduleInfo.getModule(module.getId());


            assertModulesAreEqual(module, expectedModule);
            if (module.hasParent()) {
                assertParentsAreSame(module.getParent(), expectedModule.getParent());
            }
        }
    }

        private void assertParentsAreSame (Module parent, Module expectedParent){
            assertModulesAreEqual(parent, expectedParent);
            if (parent.hasParent() || expectedParent.hasParent()) {
                assertParentsAreSame(parent.getParent(), expectedParent.getParent());
            }
        }

        private void assertModulesAreEqual (Module module1, Module module2){
            assertThat(module1.hasParent()).isEqualTo(module2.hasParent()); //either both should have parents or both should not have parents
            assertThat(module1.getId()).isEqualTo(module2.getId());
            assertThat(module1.getLinkName()).isEqualTo(module2.getLinkName());
            assertThat(module1.getSwitchBoard()).isEqualTo(module2.getSwitchBoard());
        }
    }
//}

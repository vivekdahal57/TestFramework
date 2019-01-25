package com.vh.mi.automation.test.pages.reportManager;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.pages.common.GenericMIPage;
import com.vh.mi.automation.impl.reportManager.ReportAdministrator;
import com.vh.mi.automation.test.miscrights.MiscRightsBaseTest;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author nimanandhar
 */
@Test (groups = {"Component-Interaction"})
public class ReportManagerTest extends MiscRightsBaseTest {

    @BeforeClass
    public void setUp() {
        super.setUp();
    }


    @Test
    public void testThatWeCanJumpToReportManagerAndComeBackToMI() throws Exception {
        User user = getUser("miautomation_report_manager_allowed_user");

        loginAndSelectFront(user, context.getDefaultWaitTimeout());

        String pageTitleBeforeJumping = navigationPanel.getCurrentPageTitle();
        ReportAdministrator administrator = navigationPanel.doNavigateToReportManagerPage(ReportAdministrator.class,defaultWaitTime);
        Assertions.assertThat(administrator.getDisplayedTitle()).isEqualTo("Report Administration");
        administrator.goBack();
        GenericMIPage miPage = new GenericMIPage(webDriver);
        miPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());

        Assertions.assertThat(miPage.getDisplayedPageTitle()).isEqualTo(pageTitleBeforeJumping);
    }
}

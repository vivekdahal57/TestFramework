package com.vh.mi.automation.test.miscrights;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndividualDashboard;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301DataGrid;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
/**
 * @author nimanandhar
 */
public class MiscRightShowMemberScoreCardTest extends MiscRightsBaseTest {
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
    }

    @Test(groups = "Security", description = "Test that Scorecard Tab appears in Individual Dashboard page for users with Member " +
            "Scorecard Right allowed.")
    public void scorecardTabAppearanceForRightfulUsersTest() {
        User user = getUser("miautomation_show_member_scorecard_allowed_user");
        IIndividualDashboard indvDashboardPage = loginAndGoToIndividualDashboardPage(user);
        Assertions.assertThat(indvDashboardPage.getAvailableToolBarItems()).contains("Scorecard");
    }


    @Test(groups = "Security", description = "Test that Scorecard Tab does not appear in Individual Dashboard page for users with Member " +
            "Scorecard Right denied.")
    public void scorecardTabDoesNotAppearForUsersWithNoRightTest() {
        User user = getUser("miautomation_show_member_scorecard_denied_user");
        IIndividualDashboard indvDashboardPage = loginAndGoToIndividualDashboardPage(user);
        Assertions.assertThat(indvDashboardPage.getAvailableToolBarItems()).excludes("Scorecard");
    }


    private IIndividualDashboard loginAndGoToIndividualDashboardPage(User user) {
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        IIndv301 indvPage = navigationPanel.doNavigateTo(Indv301.class, context.getDefaultWaitTimeout());

        indvPage.popupExists();

        Indv301DataGrid dataGrid = (Indv301DataGrid) indvPage.getDataGrid();
        IDataGridRow dataGridRow = dataGrid.getRowWithDrillOption();
        String anyAvailableDrillOption = dataGridRow.getDrillOptions().get(0);

        IIndividualDashboard indvDashboardPage = (IIndividualDashboard) dataGridRow.doDrillBy(anyAvailableDrillOption);
        indvDashboardPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        return indvDashboardPage;
    }
}

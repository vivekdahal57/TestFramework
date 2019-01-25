package com.vh.mi.automation.test.miscrights;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301Columns;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301DataGrid;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author nimanandhar
 */
public class MiscRightPHITest extends MiscRightsBaseTest {

    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
    }

    /**
     * Rights for testUser miautomation_phi_all_allowed_user
     * allow 'D2Explorer'
     * allow 'Real Member ID'
     * allow 'Demographic'
     * allow 'Show Member Name'
     */
    @Test (groups = "Security")
    public void phiInformationNotBlindedForUserWithAllPhiAllowedTest() {
        Indv301DataGrid dataGrid = loginAndGetIndv301DataGrid("miautomation_phi_all_allowed_user");
        assertThat(dataGrid.getColumns()).contains(Indv301Columns.MEMBER_ID, Indv301Columns.DOB, Indv301Columns.MEMNAME);

        for (String dob : dataGrid.getData(Indv301Columns.DOB)) {
            assertThat(dob).isNotEqualTo("Blinded");
        }
        assertThat(dataGrid.isSortable(Indv301Columns.MEMBER_ID)).isTrue();
        assertThat(dataGrid.isFilterable(Indv301Columns.MEMBER_ID)).isTrue();

        assertThat(dataGrid.isSortable(Indv301Columns.DOB)).isTrue();
        assertThat(dataGrid.isFilterable(Indv301Columns.DOB)).isTrue();

        assertThat(dataGrid.isSortable(Indv301Columns.MEMNAME)).isTrue();
        assertThat(dataGrid.isFilterable(Indv301Columns.MEMNAME)).isTrue();
    }


    /**
     * Rights for testUser miautomation_phi_all_denied_user
     * allow 'D2Explorer'
     * deny 'Real Member ID'
     * deny 'Demographic'
     * deny 'Show Member Name'
     */
    @Test (groups = "Security")
    public void phiInformationBlindedForUserWithAllPhiDeniedTest() {
        Indv301DataGrid dataGrid = loginAndGetIndv301DataGrid("miautomation_phi_all_denied_user");
        List<IDataGridColumn> columns = dataGrid.getColumns();

        assertThat(columns).contains(Indv301Columns.MEMBER_ID, Indv301Columns.DOB);
        assertThat(columns).excludes(Indv301Columns.MEMNAME);

        for (String dob : dataGrid.getData(Indv301Columns.DOB)) {
            assertThat(dob).isEqualTo("Blinded");
        }

        assertThat(dataGrid.isSortable(Indv301Columns.MEMBER_ID)).isFalse();
        assertThat(dataGrid.isFilterable(Indv301Columns.MEMBER_ID)).isTrue();

        assertThat(dataGrid.isSortable(Indv301Columns.DOB)).isFalse();
        assertThat(dataGrid.isFilterable(Indv301Columns.DOB)).isFalse();
    }


    /**
     * allow 'D2Explorer'
     * deny 'Real Member ID'
     * allow 'Demographic'
     * allow 'Show Member Name'
     */
    @Test(groups = "Security", description = "Test that user with Real Member Id is denied and Demographic, Show Member Name are" +
            "allowed.")
    public void realMemberIdDeniedAndDemographicAndShowMemberNameAllowed() {
        Indv301DataGrid dataGrid = loginAndGetIndv301DataGrid("miautomation_phi_real_memberId_denied_user");
        List<IDataGridColumn> columns = dataGrid.getColumns();

        assertThat(columns).contains(Indv301Columns.MEMBER_ID, Indv301Columns.DOB);
        assertThat(columns).excludes(Indv301Columns.MEMNAME);

        for (String dob : dataGrid.getData(Indv301Columns.DOB)) {
            assertThat(dob).isEqualTo("Blinded");
        }

        assertThat(dataGrid.isSortable(Indv301Columns.MEMBER_ID)).isFalse();
        assertThat(dataGrid.isFilterable(Indv301Columns.MEMBER_ID)).isTrue();
        assertThat(dataGrid.isSortable(Indv301Columns.DOB)).isFalse();
        assertThat(dataGrid.isFilterable(Indv301Columns.DOB)).isFalse();
    }


    private Indv301DataGrid loginAndGetIndv301DataGrid(String automationUserId) {
        loginAndSelectFront(getUser(automationUserId), context.getDefaultWaitTimeout());
        Indv301 individualPage = navigationPanel.doNavigateTo(Indv301.class, context.getDefaultWaitTimeout());
        individualPage.popupExists();
        assertThat(individualPage.getDisplayedPageTitle()).isEqualTo(individualPage.getPageTitle());

        if (individualPage.isDataGridCustomizable()) {
            IDataGridCustomizer dataGridCustomizer = individualPage.getDataGridCustomizer();
            dataGridCustomizer.doSelectAll();
            dataGridCustomizer.doApplySelection();
            new AbstractLoadingComp(webDriver, "_panelHeader").waitTillDisappears();

        }
        return individualPage.getDataGrid();
    }
}

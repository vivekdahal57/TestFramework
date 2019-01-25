package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301DataGrid;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardIndividualClaimDetails;
import com.vh.mi.automation.test.comp.dataGrid.DataGridCustomizationTest;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static com.vh.mi.automation.api.constants.MILandingPages.INDIVIDUALS_301;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80690 on 12/5/2016.
 */
public class IndvClaimDetail949DataGridCustomizationTest extends DataGridCustomizationTest {

    private Indv301 indv301;
    private IndividualDashboardIndividualClaimDetails IndividualDashboardIndividualClaimDetails;

    @BeforeClass
    public void setUp() {
        super.setUp();
        indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(INDIVIDUALS_301.getPageTitle());
        indv301.popupExists();
        IndividualDashboardIndividualClaimDetails = indv301.doDrillFromIndv301();
    }


    private Indv301DataGrid getIndvDataGrid() {
        return indv301.getDataGrid();
    }

    public IDataGridCustomizer getDataGridCustomizer() {
        return IndividualDashboardIndividualClaimDetails.getDataGridCustomizer();
    }

    @Override
    public boolean isDataGridCustomizable() {
        return IndividualDashboardIndividualClaimDetails.isDataGridCustomizable();
    }


    @Test
    public void test_indv949_instance() {
        assertThat(IndividualDashboardIndividualClaimDetails).isNotNull();
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo("Individual Dashboard: (949) Individual Claim Details");
    }

    @Test
    public void test_renameColumnHeadinglabel() {
        // Columnn Diagnosis 1 is renamed to Primary Diagnosis/Diagnosis 1
        String actualDiagnosislabel = IndividualDashboardIndividualClaimDetails.getRenameDiagnosistext();
        String expectedDiagnosislabel = "Primary Diagnosis/Diagnosis 1";
        Assertions.assertThat(actualDiagnosislabel).isEqualTo(expectedDiagnosislabel);

        //Column Service/Rx is renamed to POS/FDB Grouper Code Description
        String actualPOSFDBgrouperlabel = IndividualDashboardIndividualClaimDetails.getRenamePOSFDBGrouperLabel();
        String expectedPOSFDBgrouperlabel = "POS/FDB Grouper Code Description";
        Assertions.assertThat(actualPOSFDBgrouperlabel).isEqualTo(expectedPOSFDBgrouperlabel);
    }

    @Test
    public void test_nextMemberRecord_Feature() {
        int Currentrecordno = IndividualDashboardIndividualClaimDetails.getRecordno();
        IndividualDashboardIndividualClaimDetails.clickonNext();
        int nextrecord = IndividualDashboardIndividualClaimDetails.getRecordno();
        Assertions.assertThat(Currentrecordno).isEqualTo(nextrecord - 1);
    }


}

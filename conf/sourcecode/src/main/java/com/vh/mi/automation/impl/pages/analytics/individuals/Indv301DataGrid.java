package com.vh.mi.automation.impl.pages.analytics.individuals;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.constants.SortOrder;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.*;
import org.fest.assertions.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * @author nimanandhar
 */
public class Indv301DataGrid extends AbstractDataGrid {

    public Indv301DataGrid(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return Indv301Columns.fromId(id);
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch (drillOption) {
            case "Summary":
                return IndividualDashboardMemberSummary.class;
            case "Quality Measures":
                return IndividualDashboardQualityMeasures.class;
            case "Clinical Event Chart":
                return IndividualDashboardClinicalEventChart.class;
            case "Individual Claim Details":
                return IndividualDashboardIndividualClaimDetails.class;
            case "Biometrics/Labs":
                return IndividualDashboardBiometricsLabs.class;
            case "HRA":
                return IndividualDashBoardHRA.class;
            case "Lab Results":
                return IndividualDashboardLabResults.class;
            case "Trend":
                return IndividualDashboardTrendAnalysis.class;
            case "HAS":
                return IndividualDashBoradHAS.class;
            case "True Performance Measure":
                return IndividualDashboardTruePerformance.class;
            case "DxCG Risk Solutions":
                return IndividualDashboardMemberDetailReport.class;
            case "Point of Care":
                return IndividualDashboardPointOfCare.class;

        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }


    public IDataGridRow getRowWithDrillOption() {
        IDataGridRow firstRowWithDrillOption = getFirstRowWithDrillOptions();

        if (firstRowWithDrillOption == null) {
            //try sorting by memberId in ascending order
            doSort(Indv301Columns.MEMBER_ID, SortOrder.ASC);
            firstRowWithDrillOption = getFirstRowWithDrillOptions();

            if (firstRowWithDrillOption == null) {
                //try again sorting by descending order
                doSort(Indv301Columns.MEMBER_ID, SortOrder.DESC);
                firstRowWithDrillOption = getFirstRowWithDrillOptions();
            }
        }

        if (firstRowWithDrillOption == null) {
            throw new AutomationException("No rows contains drill options");
        }
        return firstRowWithDrillOption;
    }


    private IDataGridRow getFirstRowWithDrillOptions() {
        ImmutableList<IDataGridRow> rows = getRows();
        Integer memberIdColumnPosition = getColumnIndex(Indv301Columns.MEMBER_ID);
        IDataGridRow firstRowWithDrillOption = null;
        for (IDataGridRow row : rows) {

            if (row.hasDrillOptions()) {
                firstRowWithDrillOption = row;
                break;
            } else {
                String memberId = row.getValue(memberIdColumnPosition);
                Assertions.assertThat(memberId)
                        .as("A row with no drill option should only occur for memberId starting with DD")
                        .startsWith("DD");
            }
        }
        return firstRowWithDrillOption;
    }

}
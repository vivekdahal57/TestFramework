package com.vh.mi.automation.test.pages.analytics.individuals;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301DataGrid;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardMemberSummary;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author nimanandhar
 */
public class Indv301DataGridDrillTest extends AbstractDataGridDrillTest {

   @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
        User user = getUser("miautomation_indv_user");
        loginAndSelectFront(user,context.getDefaultWaitTimeout());
        dataGrid = getDataGrid();
    }

    @Override
    protected IDataGrid getDataGrid() {
        IIndv301 individualPage = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        individualPage.popupExists();
        IAnalysisPeriod analysisPeriod = individualPage.getAnalysisPeriod();
        if (analysisPeriod.getSelectedOption() != IAnalysisPeriod.APOption.FULL_CYCLE) {
            analysisPeriod.doSelect(IAnalysisPeriod.APOption.FULL_CYCLE);
        }

        IReportingBy reportingBy = individualPage.getReportingBy();
        if (reportingBy.getSelectedOption() != IReportingBy.RBOption.PAID) {
            reportingBy.select(IReportingBy.RBOption.PAID);
        }

        return individualPage.getDataGrid();
    }


    /**
     * For Individual Page there are cases in which some rows don't have drill options
     * In these cases we want to sort by memberId to ensure that we get a row that
     * has the drill option. This cannot be made into a generic algorithm because
     * we don't know on what field to sort. For eg for individual page we know to sort
     * on memberId field, but other data grid may not have that column
     *
     * @return row with drill options
     */
    @DataProvider
    @Override public Object[][] drillOptionsProvider() {
        IDataGridRow gridRow = ((Indv301DataGrid) dataGrid).getRowWithDrillOption();

        ImmutableList<String> drillOptions = gridRow.getDrillOptions();

        List<IDataGridRow> rowsList = new ArrayList<>();
        List<String> drillOptionsList = new ArrayList<>();

        for (String drillOption : drillOptions) {
            rowsList.add(gridRow);
            drillOptionsList.add(drillOption);
        }

        return DataProviderUtils.getObjects(rowsList, drillOptionsList);
    }
}

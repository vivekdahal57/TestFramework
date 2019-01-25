package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.api.pages.analytics.demography.drill.ID07AgeGroupDrillPage;
import com.vh.mi.automation.impl.comp.AbstractLoadingComp;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/26/18.
 */
public class DemographyD07AgeGroupSortingTest extends AbstractDataGrid_SortingTest {
    private IDemographyD05 demographyD05;
    private ID07AgeGroupDrillPage d07AgeGroupDrillPage;

    @BeforeClass
    public void setUp(){
        super.setUp();
    }


    @Override
    protected IDataGrid getDataGrid() {
       DemographyD05 demographyD05 = navigationPanel.doNavigateTo(DemographyD05.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(demographyD05.getPageTitle());
        d07AgeGroupDrillPage = (ID07AgeGroupDrillPage) demographyD05.drillDownToPage("Age Group");
        IAnalysisPeriod analysisPeriod = demographyD05.getAnalysisPeriod();
        if (analysisPeriod.getAvailableOptions().contains(IAnalysisPeriod.APOption.FULL_CYCLE)) {
            analysisPeriod.doSelect(IAnalysisPeriod.APOption.FULL_CYCLE);
        }

        return d07AgeGroupDrillPage.getDataGrid();
    }
}

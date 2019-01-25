package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.api.pages.analytics.demography.drill.ID07AgeGroupDrillPage;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.comp.pagination.AbstractPaginationComponentTest;
import org.testng.annotations.BeforeClass;


import static com.vh.mi.automation.api.constants.MILandingPages.DEMOGRAPHY;
import static org.fest.assertions.Assertions.assertThat;

public class D07AgeGroupPaginationTest extends AbstractPaginationComponentTest {

    IDemographyD05 demographyD05;
    ID07AgeGroupDrillPage d07AgeGroupDrillPage;

    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @Override
    protected void setupPage() {
        demographyD05 = navigationPanel.doNavigateTo(DemographyD05.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(DEMOGRAPHY.getPageTitle());
        d07AgeGroupDrillPage = (ID07AgeGroupDrillPage) demographyD05.drillDownToPage("Age Group");
        assertThat(d07AgeGroupDrillPage.getPageTitle()).isEqualToIgnoringCase("(D07) Total Paid by Age Group");
    }

    @Override
    protected IPaginationComponent getPaginationComponent() {
        return d07AgeGroupDrillPage.getPaginationComponent();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return d07AgeGroupDrillPage.getDataGrid();
    }

}

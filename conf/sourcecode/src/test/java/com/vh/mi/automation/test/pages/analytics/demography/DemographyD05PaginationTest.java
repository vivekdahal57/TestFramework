package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.comp.pagination.AbstractPaginationComponentTest;
import org.testng.annotations.BeforeClass;

/**
 * @author nimanandhar
 */
public class DemographyD05PaginationTest extends AbstractPaginationComponentTest {

    private IDemographyD05 demographyD05Page;

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void setupPage() {
        demographyD05Page = navigationPanel.doNavigateTo(DemographyD05.class, context.getDefaultWaitTimeout());
    }

    @Override
    protected IPaginationComponent getPaginationComponent() {
        return demographyD05Page.getPaginationComponent();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return demographyD05Page.getDataGrid();
    }

}

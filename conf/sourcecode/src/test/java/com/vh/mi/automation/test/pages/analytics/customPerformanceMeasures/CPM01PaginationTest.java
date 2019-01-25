package com.vh.mi.automation.test.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01;
import com.vh.mi.automation.test.comp.pagination.AbstractPaginationComponentTest;
import org.junit.Before;
import org.testng.annotations.BeforeClass;

/**
 * Created by i10359 on 11/10/17.
 */
public class CPM01PaginationTest extends AbstractPaginationComponentTest{
    private ICPM01 icpm01Page;


    @BeforeClass
    public  void setUp(){
        super.setUp();
    }

    @Override
    protected void setupPage() {
        icpm01Page = navigationPanel.doNavigateTo(CPM01.class,context.getDefaultWaitTimeout());
    }

    @Override
    protected IPaginationComponent getPaginationComponent() {

        return icpm01Page.getPaginationComponent();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return icpm01Page.getDataGrid();
    }
}

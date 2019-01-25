package com.vh.mi.automation.test.pages.analytics.demography;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;


/**
 * Created by i10359 on 2/27/18.
 */
public class DemographyD05SortingTest extends AbstractDataGrid_SortingTest {
    IDemographyD05 demographyD05;

    @BeforeClass
    public void setUp(){
       super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
       demographyD05 = navigationPanel.doNavigateTo(DemographyD05.class, defaultWaitTime);
        IAnalysisPeriod analysisPeriod = demographyD05.getAnalysisPeriod();
        if (analysisPeriod.getAvailableOptions().contains(IAnalysisPeriod.APOption.FULL_CYCLE)) {
            analysisPeriod.doSelect(IAnalysisPeriod.APOption.FULL_CYCLE);
        }
        return demographyD05.getDataGrid();
    }
}

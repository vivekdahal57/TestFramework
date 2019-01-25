package com.vh.mi.automation.test.pages.analytics.conversionAnalyzer;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.conversionAnalyzer.IConversionAnalyzerA17;
import com.vh.mi.automation.impl.pages.analytics.conversionAnalyzer.ConversionAnalyzerA17;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

/**
 * Created by nimanandhar on 12/9/2014.
 */
public class ConversionAnalyzerA17DataGridSortingTest extends AbstractDataGrid_SortingTest {

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        IConversionAnalyzerA17 page = navigationPanel.doNavigateTo(ConversionAnalyzerA17.class, defaultWaitTime);

        IAnalysisPeriod analysisPeriod = page.getAnalysisPeriod();
        if (analysisPeriod.getAvailableOptions().contains(IAnalysisPeriod.APOption.FULL_CYCLE)) {
            analysisPeriod.doSelect(IAnalysisPeriod.APOption.FULL_CYCLE);
        }
        return page.getDataGrid();
    }
}

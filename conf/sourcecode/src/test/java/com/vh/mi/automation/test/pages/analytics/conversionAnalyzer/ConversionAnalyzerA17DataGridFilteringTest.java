package com.vh.mi.automation.test.pages.analytics.conversionAnalyzer;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.analytics.conversionAnalyzer.IConversionAnalyzerA17;
import com.vh.mi.automation.impl.pages.analytics.conversionAnalyzer.ConversionAnalyzerA17;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_FilterTest;
import org.testng.annotations.BeforeClass;

import java.util.List;

import static com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption;

/**
 * @author nimanandhar
 */
public class ConversionAnalyzerA17DataGridFilteringTest extends AbstractDataGrid_FilterTest {

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        IConversionAnalyzerA17 page = navigationPanel.doNavigateTo(ConversionAnalyzerA17.class, defaultWaitTime);

        IAnalysisPeriod analysisPeriod = page.getAnalysisPeriod();
        if (analysisPeriod.getAvailableOptions().contains(APOption.FULL_CYCLE)) {
            analysisPeriod.doSelect(APOption.FULL_CYCLE);
        }
        return page.getDataGrid();
    }

    @Override
    public List<IDataGridColumn> getColumnsToTest() {
        return dataGrid.getColumns();//test filter for all columns
    }
}

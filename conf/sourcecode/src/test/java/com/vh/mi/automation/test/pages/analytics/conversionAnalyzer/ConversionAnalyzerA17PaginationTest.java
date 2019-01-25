package com.vh.mi.automation.test.pages.analytics.conversionAnalyzer;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.pages.analytics.conversionAnalyzer.IConversionAnalyzerA17;
import com.vh.mi.automation.impl.pages.analytics.conversionAnalyzer.ConversionAnalyzerA17;
import com.vh.mi.automation.test.comp.pagination.AbstractPaginationComponentTest;
import org.testng.annotations.BeforeClass;

/**
 * @author nimanandhar
 */
public class ConversionAnalyzerA17PaginationTest extends AbstractPaginationComponentTest {
    private IConversionAnalyzerA17 conversionAnalyzerA17Page;

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void setupPage() {
        conversionAnalyzerA17Page = navigationPanel.doNavigateTo(ConversionAnalyzerA17.class, context.getDefaultWaitTimeout());
    }

    @Override
    protected IPaginationComponent getPaginationComponent() {
        return conversionAnalyzerA17Page.getPaginationComponent();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return conversionAnalyzerA17Page.getDataGrid();
    }

}

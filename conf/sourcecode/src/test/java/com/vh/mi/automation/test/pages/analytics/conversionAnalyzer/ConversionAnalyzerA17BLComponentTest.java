package com.vh.mi.automation.test.pages.analytics.conversionAnalyzer;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.impl.pages.analytics.conversionAnalyzer.ConversionAnalyzerA17;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;

/**
 * @author nimanandhar
 */
public class ConversionAnalyzerA17BLComponentTest extends AbstractBLCompTest {
    private ConversionAnalyzerA17 conversionAnalyzerA17;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        conversionAnalyzerA17 = navigationPanel.doNavigateTo(ConversionAnalyzerA17.class, context.getDefaultWaitTimeout());
    }

    @Override
    protected IBusinessLevelsComponent getBusinessLevelsComponent() {
        return conversionAnalyzerA17.getBusinessLevel();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return conversionAnalyzerA17.getDataGrid();
    }

}

package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;

/**
 * @author nimanandhar
 */
public class Indv301BLComponentTest extends AbstractBLCompTest {
    private Indv301 indvPage;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        indvPage = navigationPanel.doNavigateTo(Indv301.class, context.getDefaultWaitTimeout());
        indvPage.popupExists();
    }

    @Override
    protected IBusinessLevelsComponent getBusinessLevelsComponent() {
        return indvPage.getBusinessLevel();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return indvPage.getDataGrid();
    }
}

package com.vh.mi.automation.test.pages.analytics.executiveSummary;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.impl.pages.analytics.executiveSummary.ExecutiveSummary;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ExecutiveSummaryBLComponentTest extends AbstractBLCompTest {
    private ExecutiveSummary executiveSummaryPage;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        executiveSummaryPage = navigationPanel.doNavigateTo(ExecutiveSummary.class, defaultWaitTime);
    }

    @Override
    protected IBusinessLevelsComponent getBusinessLevelsComponent() {
        return executiveSummaryPage.getBusinessLevel();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return null;
    }
}

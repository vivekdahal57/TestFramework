package com.vh.mi.automation.test.pages.analytics.qualityAndRisk;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.impl.pages.analytics.qualityMeasures.qualityAndRisk.QualityAndRisk670;
import com.vh.mi.automation.test.comp.dataGrid.AbstractDataGrid_SortingTest;
import org.testng.annotations.BeforeClass;

/**
 * @author nimanandhar
 */
public class QualityAndRiskDataGridSortingTest extends AbstractDataGrid_SortingTest {

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected IDataGrid getDataGrid() {
        QualityAndRisk670 qualityAndRisk670 = navigationPanel.doNavigateTo(QualityAndRisk670.class, defaultWaitTime);
        return qualityAndRisk670.getDataGrid();

    }
}

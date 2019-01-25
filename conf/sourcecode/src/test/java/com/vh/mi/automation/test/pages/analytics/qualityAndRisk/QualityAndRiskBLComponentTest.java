package com.vh.mi.automation.test.pages.analytics.qualityAndRisk;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.impl.pages.analytics.qualityMeasures.qualityAndRisk.QualityAndRisk670;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;

/**
 * @author nimanandhar
 */
public class QualityAndRiskBLComponentTest extends AbstractBLCompTest {
    private QualityAndRisk670 qualityAndRisk670Page;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        qualityAndRisk670Page = navigationPanel.doNavigateTo(QualityAndRisk670.class, context.getDefaultWaitTimeout());
    }

    @Override
    protected IBusinessLevelsComponent getBusinessLevelsComponent() {
        return qualityAndRisk670Page.getBusinessLevel();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return qualityAndRisk670Page.getDataGrid();
    }
}

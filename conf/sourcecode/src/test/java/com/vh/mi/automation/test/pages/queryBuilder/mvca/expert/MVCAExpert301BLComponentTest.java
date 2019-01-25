package com.vh.mi.automation.test.pages.queryBuilder.mvca.expert;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.impl.pages.queryBuilder.mvca.basic.MVCABasic301B;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;

/**
 * @author nimanandhar
 */
public class MVCAExpert301BLComponentTest extends AbstractBLCompTest {

    private MVCABasic301B mvcaBasic301BPage;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        mvcaBasic301BPage = navigationPanel.doNavigateTo(MVCABasic301B.class, context.getDefaultWaitTimeout());
    }

    @Override
    protected IBusinessLevelsComponent getBusinessLevelsComponent() {
        return mvcaBasic301BPage.getBusinessLevel();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return mvcaBasic301BPage.getDataGrid();
    }
}

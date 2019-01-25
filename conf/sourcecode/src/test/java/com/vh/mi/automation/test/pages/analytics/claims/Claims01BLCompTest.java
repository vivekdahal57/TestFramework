
package com.vh.mi.automation.test.pages.analytics.claims;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.impl.pages.analytics.claims.Claims01;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;


/**
 * Created by bchataut on 10/5/2017.
 */
public class Claims01BLCompTest extends AbstractBLCompTest {
    private Claims01 claims01;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        claims01 = navigationPanel.doNavigateTo(Claims01.class, defaultWaitTime);
    }

    @Override
    protected IBusinessLevelsComponent getBusinessLevelsComponent() {

        return claims01.getBusinessLevel();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return claims01.getDataGrid();
    }
}


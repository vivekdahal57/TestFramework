
package com.vh.mi.automation.test.pages.analytics.hcciClaims;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;


/**
 * Created by bchataut on 10/5/2017.
 */
public class HcciClaims01BLCompTest extends AbstractBLCompTest {
    private HcciClaims01 claims01;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        claims01 = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
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


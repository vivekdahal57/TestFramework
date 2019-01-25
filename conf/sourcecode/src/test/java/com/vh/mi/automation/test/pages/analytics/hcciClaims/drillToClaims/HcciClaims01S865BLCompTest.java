
package com.vh.mi.automation.test.pages.analytics.hcciClaims.drillToClaims;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciS865DrillPage;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;


/**
 * Created by i10105 on 05/10/2018.
 */
public class HcciClaims01S865BLCompTest extends AbstractBLCompTest {
    private HcciClaims01 claims01;
    HcciS865DrillPage hcciS865DrillPage;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        claims01 = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
        hcciS865DrillPage = (HcciS865DrillPage) claims01.getDataGrid().getRows().get(0).doDrillBy("Claims");
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


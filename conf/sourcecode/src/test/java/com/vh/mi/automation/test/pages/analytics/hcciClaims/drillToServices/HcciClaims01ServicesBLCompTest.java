
package com.vh.mi.automation.test.pages.analytics.hcciClaims.drillToServices;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01DrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciServicesDrillPage;
import com.vh.mi.automation.test.comp.AbstractBLCompTest;
import org.testng.annotations.BeforeClass;


/**
 * Created by i10105 on 05/10/2018.
 */
public class HcciClaims01ServicesBLCompTest extends AbstractBLCompTest {
    private HcciClaims01 claims01;
    HcciServicesDrillPage hcciServicesDrillPage;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void initializePage() {
        claims01 = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
        hcciServicesDrillPage = (HcciServicesDrillPage) claims01.getDataGrid().getRows().get(0).doDrillBy("Services");

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


package com.vh.mi.automation.test.pages.analytics.hcciClaims.drillToServices;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciServicesDrillPage;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

@Test(groups = "HcciClaims101", sequential = true)
public class DrillDownFromServicesToClaims extends BaseTest {

    private HcciClaims01 hcciClaims01;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        hcciClaims01 = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
        drillFromServicesToClaims();
    }

    @Test(description = "Drill to Claim Lines(S965) from Claims(S865)")
    private void drillFromServicesToClaims() {
        HcciServicesDrillPage HcciClaims01DrillPage;
        HcciClaims01DrillPage = (HcciServicesDrillPage) hcciClaims01.getDataGrid().getRows().get(0).doDrillBy("Services");
        HcciClaims01DrillPage.getDataGrid().getRows().get(0).doDrillBy("Claims");
        assertThat(navigationPanel.getCurrentPageTitle().equals("(S865) Claims"));
    }
}

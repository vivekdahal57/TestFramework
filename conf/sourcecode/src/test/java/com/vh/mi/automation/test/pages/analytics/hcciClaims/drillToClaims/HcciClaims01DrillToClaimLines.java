package com.vh.mi.automation.test.pages.analytics.hcciClaims.drillToClaims;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciS865DrillPage;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Created by i10105 on 05/10/2018.
 */

@Test(groups = "claims101", sequential = true)
public class HcciClaims01DrillToClaimLines extends BaseTest {
    private HcciS865DrillPage claims01DrillPage;
    private HcciClaims01 claims101;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        claims101 = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
        drillToClaimLines();
    }

    @Test(description = "Drill to Claim Lines(S965) from Claims(S865)")
    private void drillToClaimLines() {
        claims01DrillPage = (HcciS865DrillPage) claims101.getDataGrid().getRows().get(0).doDrillBy("Claims");
        claims01DrillPage.getDataGrid().getRows().get(0).doDrillBy("Claim Lines");
        assertThat(navigationPanel.getCurrentPageTitle().equals("(S965) Medical Claim Detail"));
    }
}
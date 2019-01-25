package com.vh.mi.automation.test.pages.analytics.hcciClaims.drillToClaims;

import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciS865DrillPage;
import com.vh.mi.automation.test.comp.AbstractReportingByTest;
import org.testng.annotations.BeforeClass;


/**
 * Created by i10105 on 05/10/2018.
 */

public class HcciClaims01S865RBTest extends AbstractReportingByTest {
    HcciS865DrillPage hcciS865DrillPage;
    private IReportingBy rb;

    @BeforeClass
    public void setUp() {
        super.setUp();
        HcciClaims01 claims101 = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
        claims101.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        hcciS865DrillPage = (HcciS865DrillPage) claims101.getDataGrid().getRows().get(0).doDrillBy("Claims");
        rb = hcciS865DrillPage.getReportingBy();
    }

    @Override
    public IReportingBy getReportingBy() {
        return rb;
    }
}


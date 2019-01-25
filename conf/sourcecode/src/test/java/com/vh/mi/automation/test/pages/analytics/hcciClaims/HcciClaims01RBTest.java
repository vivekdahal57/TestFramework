package com.vh.mi.automation.test.pages.analytics.hcciClaims;

import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.test.comp.AbstractReportingByTest;
import org.testng.annotations.BeforeClass;


/**
 * Created by bchataut on 10/5/2017.
 */

public class HcciClaims01RBTest extends AbstractReportingByTest {
    private HcciIClaims01 hcciClaims101;
    private IReportingBy rb;

    @BeforeClass
    public void setUp() {
        super.setUp();
        hcciClaims101 = navigationPanel.doNavigateTo(HcciClaims01.class, defaultWaitTime);
        hcciClaims101.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        rb = hcciClaims101.getReportingBy();
    }

    @Override
    public IReportingBy getReportingBy() {
        return rb;
    }
}


package com.vh.mi.automation.test.pages.analytics.claims;

import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.pages.analytics.claims.IClaims01;
import com.vh.mi.automation.impl.pages.analytics.claims.Claims01;
import com.vh.mi.automation.test.comp.AbstractReportingByTest;
import org.testng.annotations.BeforeClass;


/**
 * Created by bchataut on 10/5/2017.
 */

public class Claims01RBTest extends AbstractReportingByTest {
    private IClaims01 claims101;
    private IReportingBy rb;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        claims101 = navigationPanel.doNavigateTo(Claims01.class, defaultWaitTime);

        claims101.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        rb = claims101.getReportingBy();
    }

    @Override
    public IReportingBy getReportingBy() {
        return rb;
    }
}


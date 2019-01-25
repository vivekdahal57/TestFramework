package com.vh.mi.automation.test.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01;
import com.vh.mi.automation.test.comp.AbstractReportingByTest;
import org.testng.annotations.BeforeClass;

/**
 * Created by i10359 on 1/30/18.
 */
public class CPM01RBTest extends AbstractReportingByTest{
    private ICPM01 cpm01;
    private IReportingBy rb;

    @BeforeClass
    public void setUp(){
        super.setUp();
         cpm01 = navigationPanel.doNavigateTo(CPM01.class , defaultWaitTime);
         rb = cpm01.getReportingBy();
    }

    @Override
    protected IReportingBy getReportingBy() {
        return rb;
    }
}

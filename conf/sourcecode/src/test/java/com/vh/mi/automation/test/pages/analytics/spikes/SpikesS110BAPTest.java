package com.vh.mi.automation.test.pages.analytics.spikes;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.pages.analytics.spikes.ISpikes;
import com.vh.mi.automation.impl.pages.analytics.spikes.SpikesS110B;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;

/**
 * Created by nimanandhar on 1/2/2015.
 */
public class SpikesS110BAPTest extends AbstractAnalysisPeriodTest {
    ISpikes spikesPage;
    IAnalysisPeriod ap;
    IPeriod appCyclePeriod;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        spikesPage = navigationPanel.doNavigateTo(SpikesS110B.class, defaultWaitTime);
        ap = spikesPage.getAnalysisPeriod();
        appCyclePeriod = ap.getFullCyclePeriod();
    }


    @Override
    protected IAnalysisPeriod getAnalysisPeriod() {
        return ap;
    }

    @Override
    protected IPeriod getAppCyclePeriod() {
        return appCyclePeriod;
    }

}

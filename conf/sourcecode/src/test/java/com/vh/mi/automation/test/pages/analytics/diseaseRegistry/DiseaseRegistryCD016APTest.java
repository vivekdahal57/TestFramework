package com.vh.mi.automation.test.pages.analytics.diseaseRegistry;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.pages.analytics.diseaseRegistry.IDiseaseRegistryCD016;
import com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.DiseaseRegistryCD016;
import com.vh.mi.automation.test.comp.AbstractAnalysisPeriodTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.DISEASE_REGISTRY;
import static org.fest.assertions.Assertions.assertThat;

@Test(groups = "drCD016")
public class DiseaseRegistryCD016APTest extends AbstractAnalysisPeriodTest {
    private IDiseaseRegistryCD016 drCD016;
    private IAnalysisPeriod ap;
    private IPeriod appCyclePeriod;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        drCD016 = navigationPanel.doNavigateTo(DiseaseRegistryCD016.class, defaultWaitTime);

        long waitTime = context.getDefaultWaitTimeout();

        drCD016.doWaitTillFullyLoaded(waitTime);

        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(DISEASE_REGISTRY.getPageTitle());

        ap = drCD016.getAnalysisPeriod();
        appCyclePeriod = ap.getFullCyclePeriod();
    }

    @Override
    public IAnalysisPeriod getAnalysisPeriod() {
        return ap;
    }

    @Override
    protected IPeriod getAppCyclePeriod() {
        return appCyclePeriod;
    }
}

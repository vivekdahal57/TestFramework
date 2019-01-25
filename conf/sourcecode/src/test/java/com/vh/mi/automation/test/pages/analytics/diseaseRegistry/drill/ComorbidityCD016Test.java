package com.vh.mi.automation.test.pages.analytics.diseaseRegistry.drill;

import com.vh.mi.automation.api.pages.analytics.diseaseRegistry.IDiseaseRegistryCD016;
import com.vh.mi.automation.api.pages.analytics.diseaseRegistry.drill.IComorbidityCD108DrillPage;
import com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.DiseaseRegistryCD016;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ComorbidityCD016Test extends BaseTest {

    private IDiseaseRegistryCD016 cd016;
    private IComorbidityCD108DrillPage cd108DrillPage;

    @BeforeClass
    public void setUp(){
        super.setUp();
        cd016 = navigationPanel.doNavigateTo(DiseaseRegistryCD016.class, defaultWaitTime);
    }

    @Test
    public void checkPageLoad() {
        cd108DrillPage = (IComorbidityCD108DrillPage) cd016.drillDownToPage("Comorbidity");
        assertThat(cd108DrillPage.getPageTitle()).isEqualToIgnoringCase("(CD108) Comorbidity Analysis by Diseases");
    }
}

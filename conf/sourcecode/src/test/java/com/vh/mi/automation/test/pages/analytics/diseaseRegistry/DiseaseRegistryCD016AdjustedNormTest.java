package com.vh.mi.automation.test.pages.analytics.diseaseRegistry;

import com.vh.mi.automation.api.comp.adjustedNorm.IAdjustedNorm;
import com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.DiseaseRegistryCD016;
import com.vh.mi.automation.test.comp.adjustedNorm.AbstractAdjustedNormTest;
import org.testng.annotations.BeforeClass;

/**
 * @author pakshrestha
 */
public class DiseaseRegistryCD016AdjustedNormTest extends AbstractAdjustedNormTest {
    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected IAdjustedNorm getAdjustedNormComponent() {
        DiseaseRegistryCD016 diseaseRegistryPage = navigationPanel.doNavigateTo(DiseaseRegistryCD016.class, defaultWaitTime);
        return diseaseRegistryPage.getAdjustedNorm();
    }
}

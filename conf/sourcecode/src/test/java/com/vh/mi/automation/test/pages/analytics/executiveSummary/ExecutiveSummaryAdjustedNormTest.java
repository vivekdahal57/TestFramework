package com.vh.mi.automation.test.pages.analytics.executiveSummary;

import com.vh.mi.automation.api.comp.adjustedNorm.IAdjustedNorm;
import com.vh.mi.automation.impl.pages.analytics.executiveSummary.ExecutiveSummary;
import com.vh.mi.automation.test.comp.adjustedNorm.AbstractAdjustedNormTest;
import org.testng.annotations.BeforeClass;

/**
 * @author pakshrestha
 */
public class ExecutiveSummaryAdjustedNormTest extends AbstractAdjustedNormTest {
    @BeforeClass
    public void setUp() {
        super.setUp();
    }


    @Override
    protected IAdjustedNorm getAdjustedNormComponent() {
        ExecutiveSummary executiveSummaryPage = navigationPanel.doNavigateTo(ExecutiveSummary.class, defaultWaitTime);
        return executiveSummaryPage.getAdjustedNorm();
    }
}

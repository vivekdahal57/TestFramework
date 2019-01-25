package com.vh.mi.automation.test.pages.queryBuilder.mvca.expert;

import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.impl.pages.queryBuilder.mvca.expert.MVCAExpert301E;
import com.vh.mi.automation.test.comp.AbstractReportingByTest;
import org.testng.annotations.BeforeClass;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by nimanandhar on 1/2/2015.
 */
public class MVCAExpert301E_RBTest extends AbstractReportingByTest {
    private MVCAExpert301E mvcaExpertPage;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        mvcaExpertPage = navigationPanel.doNavigateTo(MVCAExpert301E.class, defaultWaitTime);

        assertThat(mvcaExpertPage.getDisplayedPageTitle()).isEqualTo(mvcaExpertPage.getPageTitle());
    }

    @Override
    protected IReportingBy getReportingBy() {
        return mvcaExpertPage.getReportingBy();
    }
}

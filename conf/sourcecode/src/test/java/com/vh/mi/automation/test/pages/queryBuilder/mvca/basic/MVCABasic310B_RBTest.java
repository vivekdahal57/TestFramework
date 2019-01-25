package com.vh.mi.automation.test.pages.queryBuilder.mvca.basic;

import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.pages.queryBuilder.mvca.basic.IMVCABasic301B;
import com.vh.mi.automation.impl.pages.queryBuilder.mvca.basic.MVCABasic301B;
import com.vh.mi.automation.test.comp.AbstractReportingByTest;
import org.testng.annotations.BeforeClass;

import static com.vh.mi.automation.api.constants.MILandingPages.MVCA_BASIC;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by nimanandhar on 1/2/2015.
 */
public class MVCABasic310B_RBTest extends AbstractReportingByTest {
    private IMVCABasic301B mvcaBasicPage;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        mvcaBasicPage = navigationPanel.doNavigateTo(MVCABasic301B.class, defaultWaitTime);
        mvcaBasicPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(MVCA_BASIC.getPageTitle());
    }

    @Override
    protected IReportingBy getReportingBy() {
        return mvcaBasicPage.getReportingBy();
    }
}

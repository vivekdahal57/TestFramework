package com.vh.mi.automation.test.pages.queryBuilder.mvca.basic;

import com.vh.mi.automation.api.comp.IAnalysisCondition;
import com.vh.mi.automation.api.pages.queryBuilder.mvca.basic.IMVCABasic301B;
import com.vh.mi.automation.impl.pages.queryBuilder.mvca.basic.MVCABasic301B;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vh.mi.automation.api.constants.MILandingPages.MVCA_BASIC;
import static org.fest.assertions.Assertions.assertThat;

public class MVCABasic301AnalysisConditionTest extends BaseTest {

    private IMVCABasic301B mvcaBasic;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        mvcaBasic = navigationPanel.doNavigateTo(MVCABasic301B.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(MVCA_BASIC.getPageTitle());
    }

    @Test
    public void applyAnalysisConditionAndCheckDataGridIsNotEmpty() {
        mvcaBasic.getAnalysisCondition().selectAnalysisCondition(IAnalysisCondition.Type.DiagGroup, IAnalysisCondition.Level.First, "Osteoarthritis");
        mvcaBasic.getAnalysisCondition().selectAnalysisCondition(IAnalysisCondition.Type.ProcGroup, IAnalysisCondition.Level.First, "CT Scan");
        mvcaBasic.getAnalysisCondition().selectAnalysisCondition(IAnalysisCondition.Type.RX_CLASS, IAnalysisCondition.Level.First, "Calcium and Bone Metabolism Regulators");
        mvcaBasic.getAnalysisCondition().clickRun();
        assertThat(mvcaBasic.getDataGrid().getData().size()).isGreaterThan(0);

    }
}

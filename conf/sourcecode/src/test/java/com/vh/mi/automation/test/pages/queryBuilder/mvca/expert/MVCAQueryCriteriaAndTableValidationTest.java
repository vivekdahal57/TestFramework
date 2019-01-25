package com.vh.mi.automation.test.pages.queryBuilder.mvca.expert;


import com.vh.mi.automation.api.pages.queryBuilder.mvca.expert.IMVCAExpert301E;
import com.vh.mi.automation.impl.pages.queryBuilder.mvca.expert.MVCAExpert301E;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class MVCAQueryCriteriaAndTableValidationTest extends BaseTest {
    private IMVCAExpert301E mvcaExpert;

    @BeforeClass
    public void setUp() {
        super.setUp();
        mvcaExpert = navigationPanel.doNavigateTo(MVCAExpert301E.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(mvcaExpert.getPageTitle());
    }

    @Test
    public void dataGridValidation() {
        mvcaExpert.applyMinimumQueryCriteriaRequired(IMVCAExpert301E.Group.DIAGNOSIS_GROUP);
        assertThat(mvcaExpert.getDataGrid().getData().size()).isGreaterThan(0);
    }


}

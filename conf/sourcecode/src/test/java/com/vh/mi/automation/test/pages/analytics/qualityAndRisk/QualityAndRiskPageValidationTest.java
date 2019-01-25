package com.vh.mi.automation.test.pages.analytics.qualityAndRisk;

import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.pages.analytics.qualityMeasures.qualityAndRisk.IQualityAndRisk;
import com.vh.mi.automation.impl.pages.analytics.qualityMeasures.qualityAndRisk.QualityAndRisk670;
import com.vh.mi.automation.impl.pages.analytics.qualityMeasures.qualityAndRisk.drill.QRMDetail690;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static org.fest.assertions.Assertions.assertThat;

public class QualityAndRiskPageValidationTest extends BaseTest {
    IQualityAndRisk qualityAndRisk;
    QRMDetail690 qrmDetail690;

    @BeforeClass
    public void setUp() {
        super.setUp();
        qualityAndRisk = navigationPanel.doNavigateTo(QualityAndRisk670.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualToIgnoringCase(qualityAndRisk.getPageTitle());
    }

    @Test
    public void pageValidationTest() {
        if (qualityAndRisk.isDataGridCustomizable()) {
            IDataGridCustomizer dgc1 = qualityAndRisk.getDataGridCustomizer();
            dgc1.doResetSelections();
        }
        qrmDetail690 = (QRMDetail690) qualityAndRisk.drillDownToPage("Detail");
        assertThat(qrmDetail690.isDrillPageValid()).isTrue();
    }
}

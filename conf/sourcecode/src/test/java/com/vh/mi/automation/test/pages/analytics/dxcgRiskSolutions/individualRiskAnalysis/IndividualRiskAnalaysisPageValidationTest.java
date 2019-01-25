package com.vh.mi.automation.test.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.IIndividualRiskAnalysis;
import com.vh.mi.automation.api.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.drill.IRP150MemberDetailReportDrillPage;
import com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.IndividualRiskAnalysis;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class IndividualRiskAnalaysisPageValidationTest extends BaseTest {

    IIndividualRiskAnalysis individualRiskAnalysis;
    IRP150MemberDetailReportDrillPage rp150;

    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp(){
        super.setUp();
        User user = getUser("miautomation_reports_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        individualRiskAnalysis = navigationPanel.doNavigateTo(IndividualRiskAnalysis.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(individualRiskAnalysis.getPageTitle());
    }

    @Test
    public void memberDetailReportPageValidation() {
        rp150 = (IRP150MemberDetailReportDrillPage) individualRiskAnalysis.drillDownToPage("DxCG Risk Solutions Member Detail");
        assertThat(rp150.getPageTitle()).isEqualToIgnoringCase("Individual Dashboard: (RP150) Member Detail Report");
    }
}

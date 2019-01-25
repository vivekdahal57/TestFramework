package com.vh.mi.automation.test.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.claims.IClaims01;
import com.vh.mi.automation.api.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.IIndividualRiskAnalysis;
import com.vh.mi.automation.impl.pages.analytics.claims.Claims01;
import com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.IndividualRiskAnalysis;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 10/26/17.
 */
@Test(groups = {"Report-Download" })
public class IndividualRiskAnalysisExcelTest extends BaseTest {
    IIndividualRiskAnalysis individualRiskAnalysis;
    private static final String FILENAME="RP115.xlsx";


    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
        User user = getUser("miautomation_reports_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        individualRiskAnalysis = navigationPanel.doNavigateTo(IndividualRiskAnalysis.class, defaultWaitTime);
    }



    @Test(description="RP115 => click send to excel => validate the downloaded file")
    public void sendToExcel() throws  IOException{
        individualRiskAnalysis.getPreferencesBar().sendToExcel();
        assertThat(individualRiskAnalysis.sendToExcelAndValidate(FILENAME, context)).isTrue();

    }


}

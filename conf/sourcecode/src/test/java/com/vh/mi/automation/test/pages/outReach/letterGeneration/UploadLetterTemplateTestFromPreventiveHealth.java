package com.vh.mi.automation.test.pages.outReach.letterGeneration;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.PreventiveHealth;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10314 on 8/28/2017.
 */
@Test(groups = {"Report-Download", "Product-Critical"})
public class UploadLetterTemplateTestFromPreventiveHealth extends BaseTest {
    private static final String TEMPLATE_TO_UPLOAD = "Preventive Health(L02)*.doc";
    private static final Integer TOP_N = 2;
    private PreventiveHealth preventiveHealth;
    private User user;

    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setup() {
        super.setUp();
        user = getUser("miautomation_letterModules_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
    }

    @Test(description = "Navigate to Outreach => Letter Generation => Preventive Health then, Upload Letter Template and assert if it " +
            "is uploaded successfully in the Letter Template modification Form => Generate Letter for any selected individuals and assert" +
            "if the letter is generated successfully.")
    public void verifyPreventiveLetterGeneration() throws Exception {
        preventiveHealth = navigationPanel.doNavigateTo(PreventiveHealth.class, defaultWaitTime);
        preventiveHealth.updateLetterTemplate(TEMPLATE_TO_UPLOAD, context);
        assertThat(preventiveHealth.letterTemplateDownloadValidation(TEMPLATE_TO_UPLOAD, context, TimeOuts.FIVE_SECONDS)).isTrue();
        assertThat(preventiveHealth.letterTemplateUpdatedSuccessfully(TEMPLATE_TO_UPLOAD)).isTrue();

        preventiveHealth.selectIndividualsAndGenerateLetterForThem(TOP_N);
        assertThat(preventiveHealth.letterTemplateDownloadValidation(TEMPLATE_TO_UPLOAD, context, TimeOuts.FIVE_SECONDS));
    }
}

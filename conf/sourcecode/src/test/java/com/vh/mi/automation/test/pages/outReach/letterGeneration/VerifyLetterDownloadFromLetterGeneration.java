package com.vh.mi.automation.test.pages.outReach.letterGeneration;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.LetterOutbox;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.MemberListLG;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.SelectTemplatePage;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10314 on 8/9/2017.
 */
@Test(groups = {"Report-Download", "Product-Critical"})
public class VerifyLetterDownloadFromLetterGeneration extends BaseTest {
    MemberListLG memberListLG;
    Indv301 indv301;
    SelectTemplatePage selectTemplatePage;
    LetterOutbox letterOutbox;
    private final static int TOP_N = 3;
    private final static String DYNAMIC_MEMBER_LIST_NAME = "AUTO_INDV_DYNAMIC_MEM_LIST_" + Random.getRandomStringOfLength(4);
    private static final Integer TOP_N_CHKBOXES = 4;

    Integer TEMPLATE_TYPE = Integer.parseInt(SelectTemplatePage.TemplateType.OUTREACH.getValue());
    private final static String TEMPLATE_NAME_HOLDER = "AUTOMATION_LETTER_TEMPLATE_";
    private final static String LETTER_CAMPAIGN_NAME_HOLDER = "AUTOMATION_LETTER_CAMPAIGN_";
    private final static String CREATED_TEMPLATE_FILE_NAME = "AUTOMATION_LETTER_CAMPAIGN_*.doc";
    private static final String TEMPLATE_TO_UPLOAD = "OutreachLetter*.doc";
    private static final String SAMPLE_TEMPLATE_FORMAT = "AUTOMATION_LETTER_TEMPLATE_*.doc";
    private static final int RANDOM_STRING_LENGTH = 4;

    private static final String RANDOM_TEMPLATE_NAME = TEMPLATE_NAME_HOLDER + Random.getRandomStringOfLength(RANDOM_STRING_LENGTH);
    private static final String RANDOM_LETTER_CAMPAIGN_NAME = LETTER_CAMPAIGN_NAME_HOLDER + Random.getRandomStringOfLength(RANDOM_STRING_LENGTH);


    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setup() {
        super.setUp();
        User user = getUser("miautomation_letterModules_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
    }

    @Test
    public void verifyLetterGenerationFromLM001() throws Exception {
        indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        indv301.createDymanicMemberList(TOP_N, DYNAMIC_MEMBER_LIST_NAME);

        assertThat(indv301.getMemberList().checkIfMemberListExists(DYNAMIC_MEMBER_LIST_NAME)).isTrue();
        assertThat(indv301.checkIfMemberListIsNotEmpty()).isTrue();

        memberListLG = navigationPanel.doNavigateTo(MemberListLG.class, defaultWaitTime);
        memberListLG.selectCreatedDynamicMemberList(DYNAMIC_MEMBER_LIST_NAME);
        memberListLG.selectCheckBoxesInFirstPage(TOP_N_CHKBOXES);

        selectTemplatePage = memberListLG.goToSelectTemplatePage();
        selectTemplatePage.uploadLetterTemplate(RANDOM_TEMPLATE_NAME, TEMPLATE_TYPE, TEMPLATE_TO_UPLOAD, context);

        assertThat(selectTemplatePage.isLetterTemplateUploadedSuccessfully(RANDOM_TEMPLATE_NAME)).isTrue();
        assertThat(selectTemplatePage.sampleTemplateDownloadValidation(SAMPLE_TEMPLATE_FORMAT, context, TimeOuts.TWO_SECOND));

        selectTemplatePage.chooseCreatedTemplateAndProvideNameLetterCampaignName(RANDOM_TEMPLATE_NAME, RANDOM_LETTER_CAMPAIGN_NAME);

        letterOutbox = selectTemplatePage.sendToOutbox();
        assertThat(letterOutbox.checkIfStatusOfCreatedTemplateIsComplete(RANDOM_LETTER_CAMPAIGN_NAME)).isTrue();
        letterOutbox.downloadCreatedTemplate(RANDOM_LETTER_CAMPAIGN_NAME);
        assertThat(letterOutbox.downloadLetterTemplateAndValidate(CREATED_TEMPLATE_FILE_NAME, context, TimeOuts.TWO_SECOND)).isTrue();
    }

    @AfterClass
    public void tearDown() {
        super.tearDownTestClass();
    }
}

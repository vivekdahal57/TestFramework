package com.vh.mi.automation.test.pages.outReach.letterGeneration.memberListSummary;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.outReach.MemberListSummary;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.LetterOutbox;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.SelectTemplatePage;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10314 on 9/5/2017.
 */
@Test (groups = { "Report-Download", "Products-Critical" })
public class MemberListSummaryTest extends BaseTest {
    private static final int TOP_N = 5;
    private static final String DYNAMIC_MEMBER_LIST_NAME = "AUTO_INDV_DYNAMIC_MEM_LIST_" + Random.getRandomStringOfLength(4);
    Integer TEMPLATE_TYPE = Integer.parseInt(SelectTemplatePage.TemplateType.OUTREACH.getValue());
    private static final String RANDOM_TEMPLATE_NAME = "AUTOMATION_LETTER_TEMPLATE_" + Random.getRandomStringOfLength(4);
    private static final String RANDOM_CAMPAIGN_NAME = "AUTOMATION_LETTER_CAMPAIGN_" + Random.getRandomStringOfLength(4);

    private static final String TEMPLATE_TO_UPLOAD = "OutreachLetter*.doc";
    private static final String SAMPLE_TEMPLATE_FORMAT = "AUTOMATION_LETTER_TEMPLATE_*.doc";
    private final static String CREATED_TEMPLATE_FILE_NAME = "AUTOMATION_LETTER_CAMPAIGN_*.doc";


    private User user;
    private MemberListSummary memberListSummary;
    private Indv301 indv301;
    private SelectTemplatePage selectTemplatePage;
    private LetterOutbox letterOutbox;

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

    @Test
    public void verifyMemberListSummaryTest() throws Exception {
        indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        indv301.createDymanicMemberList(TOP_N, DYNAMIC_MEMBER_LIST_NAME);
        assertThat(indv301.getMemberList().checkIfMemberListExists(DYNAMIC_MEMBER_LIST_NAME)).isTrue();
        assertThat(indv301.checkIfMemberListIsNotEmpty()).describedAs("No members in the MemberList").isTrue();

        memberListSummary = navigationPanel.doNavigateTo(MemberListSummary.class,defaultWaitTime);
        assertThat(memberListSummary.isMemberListPresent(DYNAMIC_MEMBER_LIST_NAME)).isTrue();

        indv301 = memberListSummary.goToIndv301PageFor(DYNAMIC_MEMBER_LIST_NAME);
        selectTemplatePage = indv301.selectTopIDsAndSendToOutBox(TOP_N);

        selectTemplatePage.uploadLetterTemplate(RANDOM_TEMPLATE_NAME,TEMPLATE_TYPE,TEMPLATE_TO_UPLOAD,context);
        assertThat(selectTemplatePage.isLetterTemplateUploadedSuccessfully(RANDOM_TEMPLATE_NAME)).isTrue();
        assertThat(selectTemplatePage.sampleTemplateDownloadValidation(SAMPLE_TEMPLATE_FORMAT, context, TimeOuts.THIRTY_SECONDS));

        selectTemplatePage.chooseCreatedTemplateAndProvideNameLetterCampaignName(RANDOM_TEMPLATE_NAME,RANDOM_CAMPAIGN_NAME);
        letterOutbox = selectTemplatePage.sendToOutbox();
        letterOutbox.checkIfStatusOfCreatedTemplateIsComplete(RANDOM_CAMPAIGN_NAME);
        letterOutbox.downloadCreatedTemplate(RANDOM_CAMPAIGN_NAME);
        assertThat(letterOutbox.downloadLetterTemplateAndValidate(CREATED_TEMPLATE_FILE_NAME,context,TimeOuts.THIRTY_SECONDS)).isTrue();
    }
}

package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardOutreachHistory;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.LetterOutbox;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.SelectTemplatePage;
import com.vh.mi.automation.impl.pages.queryBuilder.Individual301;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10314 on 8/17/2017.
 */
@Test(groups = {"Product-Critical", "Report-Download"})
public class Indv301LetterGenerationTests extends BaseTest {
    Individual301 individual301;
    Indv301 indv301;
    IndividualDashboardOutreachHistory indvOutreachHistory;
    SelectTemplatePage selectTemplatePage;
    LetterOutbox letterOutbox;
    private final static int TOP_N = 5;

    Integer TEMPLATE_TYPE = Integer.parseInt(SelectTemplatePage.TemplateType.OUTREACH.getValue());
    private final static String CREATED_TEMPLATE_FILE_NAME = "AUTOMATION_LETTER_CAMPAIGN_*.doc";
    private static final String TEMPLATE_TO_UPLOAD = "OutreachLetter*.doc";
    private static final String SAMPLE_TEMPLATE_FORMAT = "AUTOMATION_LETTER_TEMPLATE_*.doc";

    private final static String GENERATED_LETTER_FILE_NAME = "com.d2.d2e.letterModule.letterLog.Controller.LML090*.doc";


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

    @Test(priority = 0, description = "Navigate to Indv301 => create a dynamic member list with some criteria => assert" +
            "that if the list is created successfully => select it and go to Select Template page => upload a letter" +
            "template with outreach template type => assert if the template is uploaded successfully => go to letter Outbox page" +
            "choosing the created template and providing a letter-campaign-name => assert if the status is complete and after" +
            "that => download the template and assert if downloaded successfully.")
    public void verifySendToOutBoxFeature() throws Exception {
        String dynamic_member_list_name = "AUTO_INDV_DYNAMIC_MEM_LIST_" + Random.getRandomStringOfLength(4);
        String random_template_name = "AUTOMATION_LETTER_TEMPLATE_" + Random.getRandomStringOfLength(4);
        String random_letter_campaign_name = "AUTOMATION_LETTER_CAMPAIGN_" + Random.getRandomStringOfLength(4);

        indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        indv301.popupExists();
        indv301.createDymanicMemberList(TOP_N, dynamic_member_list_name);

        assertThat(indv301.getMemberList().checkIfMemberListExists(dynamic_member_list_name)).isTrue();
        assertThat(indv301.checkIfMemberListIsNotEmpty()).describedAs("No members in the MemberList").isTrue();

        selectTemplatePage = indv301.getPreferencesBar().sendToOutBox();
        selectTemplatePage.uploadLetterTemplate(random_template_name, TEMPLATE_TYPE, TEMPLATE_TO_UPLOAD, context);
        assertThat(selectTemplatePage.isLetterTemplateUploadedSuccessfully(random_template_name)).describedAs("Could Not Upload Template").isTrue();
        assertThat(selectTemplatePage.sampleTemplateDownloadValidation(SAMPLE_TEMPLATE_FORMAT, context, TimeOuts.TWO_SECOND));
        selectTemplatePage.chooseCreatedTemplateAndProvideNameLetterCampaignName(random_template_name, random_letter_campaign_name);

        letterOutbox = selectTemplatePage.sendToOutbox();

        assertThat(letterOutbox.checkIfStatusOfCreatedTemplateIsComplete(random_letter_campaign_name)).isTrue();

        letterOutbox.downloadCreatedTemplate(random_letter_campaign_name);
        assertThat(letterOutbox.downloadLetterTemplateAndValidate(CREATED_TEMPLATE_FILE_NAME, context, TimeOuts.TEN_SECONDS)).isTrue();
    }

    @Test(priority = 1, description = "Navigate to Indv301 => create a dynamic member list with some criteria => assert" +
            "that if the list is created successfully => select it and go to Select Template page => upload a letter" +
            "template with outreach template type => assert if the template is uploaded successfully => go to letter Outbox page" +
            "choosing the created template and providing a letter-campaign-name => assert if the status is complete and then " +
            "mark it as sent => go to Individual301 page and go to Outreach History page => click generate letter and assert" +
            "if the letter is downloaded successfully.")
    public void verifyGenerateLetterAndOutreachHistoryFeature() throws Exception {
        String dynamic_member_list_name = "AUTO_INDV_DYNAMIC_MEM_LIST_" + Random.getRandomStringOfLength(4);
        String random_template_name = "AUTOMATION_LETTER_TEMPLATE_" + Random.getRandomStringOfLength(4);
        String random_letter_campaign_name = "AUTOMATION_LETTER_CAMPAIGN_" + Random.getRandomStringOfLength(4);

        indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        indv301.popupExists();
        indv301.createDymanicMemberList(TOP_N, dynamic_member_list_name);

        assertThat(indv301.getMemberList().checkIfMemberListExists(dynamic_member_list_name)).isTrue();
        assertThat(indv301.checkIfMemberListIsNotEmpty()).describedAs("No Members in the MemberList").isTrue();

        selectTemplatePage = indv301.getPreferencesBar().sendToOutBox();
        selectTemplatePage.uploadLetterTemplate(random_template_name, TEMPLATE_TYPE, TEMPLATE_TO_UPLOAD, context);
        assertThat(selectTemplatePage.isLetterTemplateUploadedSuccessfully(random_template_name)).describedAs("Could not Upload Template").isTrue();
        assertThat(selectTemplatePage.sampleTemplateDownloadValidation(SAMPLE_TEMPLATE_FORMAT, context, TimeOuts.TWO_SECOND));
        selectTemplatePage.chooseCreatedTemplateAndProvideNameLetterCampaignName(random_template_name, random_letter_campaign_name);

        letterOutbox = selectTemplatePage.sendToOutbox();
        assertThat(letterOutbox.checkIfStatusOfCreatedTemplateIsComplete(random_letter_campaign_name)).isTrue();
        letterOutbox.markTheTemplateAsSent(random_letter_campaign_name);

        individual301 = letterOutbox.goToIndividual301page(random_letter_campaign_name);
        indvOutreachHistory = individual301.goToOutreachHistoryPage();
        indvOutreachHistory.generateLetter();
        assertThat(indvOutreachHistory.downloadLetterTemplateAndValidate(GENERATED_LETTER_FILE_NAME, context, TimeOuts.FIVE_SECONDS)).isTrue();

        indvOutreachHistory.doClose();
    }


    @AfterClass
    public void tearDown() {
        super.tearDownTestClass();
    }
}

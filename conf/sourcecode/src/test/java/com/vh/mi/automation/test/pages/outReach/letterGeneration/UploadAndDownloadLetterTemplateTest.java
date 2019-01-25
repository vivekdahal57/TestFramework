package com.vh.mi.automation.test.pages.outReach.letterGeneration;


import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.LetterOutbox;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.ManageTemplates;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82716 on 5/18/2017.
 */
@Test(groups = {"Report-Download", "Product-Critical", "Component-Interaction"})
public class UploadAndDownloadLetterTemplateTest extends BaseTest {
    private LetterOutbox letterOutbox;
    private ManageTemplates manageTemplates;
    private final static String TEMPLATE_NAME_HOLDER = "AUTOMATION_LETTER_TEMPLATE";
    private static final String TEMPLATE_TO_UPLOAD = "WelcomeLetter*.doc";
    private static final String SAMPLE_TEMPLATE_FORMAT = "AUTOMATION_LETTER_TEMPLATE*.doc";
    private static final int RANDOM_STRING_LENGTH = 4;
    private static final int WELCOME_TEMPLATE_TYPE = 1;
    private static final String RANDOM_TEMPLATE_NAME = TEMPLATE_NAME_HOLDER + Random.getRandomStringOfLength(RANDOM_STRING_LENGTH);


    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp(){
        super.setUp();
        User user = getUser("miautomation_letterModules_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        letterOutbox = navigationPanel.doNavigateTo(LetterOutbox.class,defaultWaitTime);
    }

    @Test (priority = 0)
    public void uploadLetterTemplate() throws Exception {
        manageTemplates = letterOutbox.goToManageTemplate();
        manageTemplates.uploadLetterTemplate(RANDOM_TEMPLATE_NAME,WELCOME_TEMPLATE_TYPE,TEMPLATE_TO_UPLOAD,context);
        //uploadLetterTemplage method uploads templates and closes tab so that it lands in letterOutbox page again
        manageTemplates = letterOutbox.goToManageTemplate();
        assertThat(manageTemplates.isLetterTemplateUploadedSuccessfully(RANDOM_TEMPLATE_NAME)).isTrue();
        assertThat(manageTemplates.sampleTemplateDownloadValidation(SAMPLE_TEMPLATE_FORMAT,context));
    }

    @Test (priority = 1)
    public void downloadAndValidateLetterTemplate() throws Exception {
        manageTemplates.extractLetterTemplate(RANDOM_TEMPLATE_NAME);
        assertThat(manageTemplates.downloadLetterTemplateAndValidate(TEMPLATE_TO_UPLOAD,context)).isTrue();
    }

    @AfterClass
    public void tearDown(){
        super.tearDownTestClass();

    }
}

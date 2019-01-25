package com.vh.mi.automation.test.security;

import com.vh.mi.automation.api.pages.common.ILoginPage;
import com.vh.mi.automation.impl.pages.common.forgotPassword.ForgotPasswordPage;
import com.vh.mi.automation.impl.pages.common.forgotPassword.SecurityQuestionPage;
import com.vh.mi.automation.test.base.BaseTest;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by i82298 on 10/30/2015.
 * US37929
 * TC1572
 */
@Test (groups = "Security")
public class ForgotPasswordTest extends BaseTest {

    private static final String ERROR_MESSAGE = "Sorry, the security question information entered does not match the information on record.Please try again.";
    public static final String WRONG_ANSWER = "wrong answer";
    public static final String SSN = "1111";
    public static final String MONTH = "Jan";
    public static final String DAY_OF_MONTH = "14";
    ILoginPage loginPage;

    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
        loginPage = mi.open(context.getApplication());
        loginPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
    }


    @Test
    public void testThatWrongAnswerToSecurityQuestionDisplayErrorMessage() {
        ForgotPasswordPage forgotPasswordPage = loginPage.doOpenForgetPasswordPage();
        forgotPasswordPage.enterUserName(getUser().getUserId());
        SecurityQuestionPage securityQuestionPage = forgotPasswordPage.clickToContinue();
        securityQuestionPage = securityQuestionPage.enterDate(MONTH, DAY_OF_MONTH).enterSSN(SSN).enterAnswer(WRONG_ANSWER);
        securityQuestionPage = securityQuestionPage.submitExpectingErrorMessage();
        String message = securityQuestionPage.getErrorMessage();
        Assertions.assertThat(message).isEqualTo(ERROR_MESSAGE);


    }
}

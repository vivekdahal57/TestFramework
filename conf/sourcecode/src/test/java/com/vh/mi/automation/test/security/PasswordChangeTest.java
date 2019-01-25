package com.vh.mi.automation.test.security;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.exceptions.accountprofile.InvalidCredentialsException;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.pages.common.myAccountProfile.ChangePasswordPage;
import com.vh.mi.automation.impl.pages.common.myAccountProfile.MyAccountProfile;
import com.vh.mi.automation.test.base.BaseTest;
import org.fest.assertions.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by i82298 on 10/27/2015.
 * US37929
 * TC1574
 */
@Test(groups = {"Component-Interaction"})
public class PasswordChangeTest extends BaseTest {
    private static final List<String> PASSWORD_LIST = Arrays.asList(new String[]{"Password4", "Password3", "Password2"});
    private static final List<String> PASSWORD_LIST_RESET = Arrays.asList(new String[]{"Password7", "Password8", "Password9"});
    public static final String INVALID_CREDENTIAL_MESSAGE = "Your password must be different from the last three passwords";
    public static final int FIRST = -2;
    public static final int SECOND = -1;
    public static final int THIRD = 0;

    private ChangePasswordPage changePasswordPage;
    private MyAccountProfile accountProfilePage;
    private String initialPassword;
    private String currentPassword;

    /**
     * Skip automatic login done by Base Test
     */
    @Override
    protected boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
        User user = getUser("miautomation_password_change_test_user");
        initialPassword = user.getPassword();
        currentPassword = initialPassword;

        IWelcomePage welcomePage = mi.open(context.getApplication()).loginWith(user, defaultWaitTime);
        accountProfilePage = welcomePage.openAccountProfilePage(TimeOuts.SIXTY_SECONDS);

        for (String newPassword : PASSWORD_LIST) {
            Assertions.assertThat(currentPassword).isNotEqualTo(newPassword);
            changePasswordPage = accountProfilePage.openChangePasswordPage();
            accountProfilePage = changePasswordPage.changePassword(currentPassword, newPassword);
            currentPassword = newPassword;
        }

        accountProfilePage.openChangePasswordPage();
    }

    @Test(priority = FIRST)
    public void userCannotChangePasswordWithLastThreePasswordAttempt_1() {
        String password_1_of_last_3 = PASSWORD_LIST.get(0);
        try {

            changePasswordPage.changePassword(currentPassword, password_1_of_last_3);
            fail("Should not be able to change password");
        } catch (InvalidCredentialsException ex) {
            Assertions.assertThat(ex.getMessage()).isEqualTo(INVALID_CREDENTIAL_MESSAGE);
        }

    }

    @Test(priority = SECOND)
    public void userCannotChangePasswordWithLAstThreePasswordAttempt_2() {
        String password_2_of_last_3 = PASSWORD_LIST.get(1);
        try {
            changePasswordPage.changePassword(currentPassword, password_2_of_last_3);
            fail("Should not be able to change password");
        } catch (InvalidCredentialsException ex) {
            Assertions.assertThat(ex.getMessage()).isEqualTo(INVALID_CREDENTIAL_MESSAGE);
        }
    }

    @Test(priority = THIRD)
    public void userCanChangePasswordOtherThenLastThree() {
        try {
            changePasswordPage.changePassword(currentPassword, initialPassword);
            currentPassword = initialPassword;
        } catch (InvalidCredentialsException ex) {
            fail(ex.getMessage());
        }
    }

    @AfterClass
    public void changePasswordsSoThatPASSWORD_LISTIsNotTheLastThreePasswords(){
        for (String newPassword : PASSWORD_LIST_RESET) {
            Assertions.assertThat(currentPassword).isNotEqualTo(newPassword);
            changePasswordPage = accountProfilePage.openChangePasswordPage();
            accountProfilePage = changePasswordPage.changePassword(currentPassword, newPassword);
            currentPassword = newPassword;
        }

        accountProfilePage.openChangePasswordPage();
        changePasswordPage.changePassword(currentPassword, initialPassword);
    }

}

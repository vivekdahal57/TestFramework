package com.vh.mi.automation.test.pages.common;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.common.ILoginPage;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author i80448
 * @author nimanandhar
 */
@Test (groups = {"Component-Interaction", "Security"})
public class LoginPageTest extends BaseTest {
    public static final String INVALID_USER_ID = "invalidUserId";
    public static final String INVALID_PASSWORD = "invalidPassword";
    private ILoginPage loginPage;

    @Override
    public boolean skipBrowserCreation() {
        return true;
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @BeforeMethod
    public void beforeTestMethod() {
        createNewBrowserInstance();
        loginPage = mi.open(context.getApplication());
        loginPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
    }

    @AfterMethod
    public void afterTestMethod() {
        closeBrowserInstance();
    }

    @Test(description = "This is the login success test.")
    public void loginSuccessTest() {
        User user = loginPage.getCurrentUser();
        assertThat(user.getPassword()).isEmpty();
        assertThat(user.getUserId()).isEmpty();
        IWelcomePage welcomePage = loginPage.loginWith(getUser(), context.getDefaultWaitTimeout());
        welcomePage.logOut();
    }

    @Test(description = "This is the login failure test.")
    public void loginFailureTest() {
        ILoginPage loginPage1 = loginPage.invalidLogin(new User(INVALID_USER_ID, INVALID_PASSWORD), context.getDefaultWaitTimeout());
        assertThat(loginPage1.getLoginStatusMessage()).isEqualTo("You have entered an invalid Username or Password, please re-enter.");
    }

    @Test(description = "This test is to assert that, user should reach login page after clicking logout")
    public void userShouldReachLoginPageAfterLogout() {
        IWelcomePage welcomePage = loginPage.loginWith(getUser(), defaultWaitTime);
        assertThat(welcomePage.isFullyLoaded()).isTrue();

        ILoginPage loginPage = welcomePage.logOut();
        loginPage.doWaitTillFullyLoaded(defaultWaitTime);
        User currentUser = loginPage.getCurrentUser();
        assertThat(currentUser.getUserId()).isEmpty();
        assertThat(currentUser.getPassword()).isEmpty();
    }

    @Test(description = "This is the login failure test.")
    public void loginFailureTestWithRightUsernameAndWrongPassword() {
        String validUsername = getUser().getUserId();
        ILoginPage loginPage1 = loginPage.invalidLogin(new User(validUsername, INVALID_PASSWORD), context.getDefaultWaitTimeout());
        assertThat(loginPage1.getLoginStatusMessage()).isEqualTo("You have entered an invalid Username or Password, please re-enter.");
    }

    @Test(description = "This is the login failure test.")
    public void loginFailureTestWithInvalidUsername() {
        User user = getUser();
        ILoginPage loginPage1 = loginPage.invalidLogin(new User("invalidUsername_TC1577", user.getPassword()), defaultWaitTime);
        assertThat(loginPage1.getLoginStatusMessage()).isEqualTo("You have entered an invalid Username or Password, please re-enter.");
    }
}

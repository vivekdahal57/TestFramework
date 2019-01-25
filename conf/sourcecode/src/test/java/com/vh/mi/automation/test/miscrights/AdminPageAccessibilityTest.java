package com.vh.mi.automation.test.miscrights;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.pages.admin.AdminLoginPage;
import com.vh.mi.automation.impl.pages.admin.AdminPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.fest.assertions.Assertions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * See scripts\groupsAndUsers.groovy for definition of groups and testUser
 * used in this test
 *
 * @author nimanandhar
 */
public class AdminPageAccessibilityTest extends MiscRightsBaseTest {

    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
    }

    @Test (groups = "Security")
    public void testThatAdminLinkDoesNotAppearForUserWithNoRoleAssigned() {
        User user = getUser();
        IWelcomePage welcomePage = login(user, context.getDefaultWaitTimeout());
        assertThat(welcomePage.containsLink("Administration")).isFalse();
    }


    @Test (groups = "Security")
    public void testThatAdminLinkAppearsForUsersWithSuperAdminRoleAssigned() {
        User user = getUser("miautomation_super_user");
        IWelcomePage welcomePage = login(user, context.getDefaultWaitTimeout());

        assertThat(welcomePage.containsLink("Administration")).isTrue();
        welcomePage.clickOnLink("Administration");

        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(webDriver);
        Assertions.assertThat(SeleniumUtils.getNumberOfOpenWindows(webDriver)).as("Admin should open in new window").isEqualTo(2);
        switchToOtherWindow();
        AdminPage adminPage = new AdminPage(webDriver);
        adminPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
        String pageTitle = adminPage.getPageTitle();

        //testUser has logged out, now we can perform any assertion without affecting other tests
        assertThat(pageTitle)
                .as("Expected testUser to go to Admin SuperUser Page but was navigated to " + pageTitle + " Page")
                .isEqualTo("D2HawkeyeExplorer Superuser Menu");
    }


    @Test(groups = "Security", description = "Assert if AdminLink appears for developer, but clicking on AdminLink doesnot open" +
            "Authorized Page.")
    public void adminLinkAppearsForDeveloperButNoAccessToAdminPageTest() {
        User user = getUser("miautomation_developer_user");
        IWelcomePage welcomePage = login(user, context.getDefaultWaitTimeout());
        assertThat(welcomePage.containsLink("Administration")).isTrue();
        welcomePage.clickOnLink("Administration");
        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(webDriver);
        Assertions.assertThat(SeleniumUtils.getNumberOfOpenWindows(webDriver)).as("Admin should open in new window").isEqualTo(2);
        switchToOtherWindow();
        assertThat(getWindowTitle())
                .as("Expected window title to be Not Authorized but was " + getWindowTitle())
                .isEqualTo("Not Authorized");
    }


    @Test (groups = "Security")
    public void nonAdminUserCannotLoginToAdminDirectly() {
        User user = getUser("miautomation_developer_user");
        AdminLoginPage adminLoginPage = AdminLoginPage.loadPage(webDriver, context.getAdminUrl());
        adminLoginPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());

        adminLoginPage.loginWith(user.getUserId(), user.getPassword());
        WebDriverWait wait = new WebDriverWait(getWebDriver(), TimeOuts.SIXTY_SECONDS);
        wait.until(ExpectedConditions.titleContains("Not Authorized"));
    }


    private void switchToOtherWindow() {
        SeleniumUtils.switchToNewWindow(webDriver);
    }


    private String getWindowTitle() {
        return webDriver.getTitle();
    }

}

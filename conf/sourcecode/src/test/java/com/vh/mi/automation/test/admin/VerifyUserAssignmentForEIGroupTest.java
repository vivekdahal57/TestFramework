package com.vh.mi.automation.test.admin;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.admin.IAdminPage;
import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IUsers;
import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IUsersRegistration;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82716 on 5/25/2017.
 */
@Test(groups = "No-Run")
public class VerifyUserAssignmentForEIGroupTest extends BaseTest {

    IAdminPage adminPage;
    IUsers usersPage;
    IUsersRegistration usersRegistrationPage;
    private static final String GROUP_NAME_TO_ASSIGN_USER = "EI";

    @Override
    public boolean skipBrowserCreation() {
        return true;
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
        createNewBrowserInstance();
        User user = getUser("miautomation_super_user");
        adminPage = loginToAdminApplication(user, defaultWaitTime);
    }

   // @Test
    public void validateUserAssignmentForEIGroup(){
        String userLoginName = getUser("miautomation_EI_user").getUserId();
        usersRegistrationPage = adminPage.goToManageObjectsPage().goToUsersPage().goToUserRegistrationPage(userLoginName,true);
        usersPage = usersRegistrationPage.registerUserToGroup(GROUP_NAME_TO_ASSIGN_USER);
        usersRegistrationPage = usersPage.goToUserRegistrationPage(userLoginName,false);
        assertThat(usersRegistrationPage.isUserInSelectedGroup(GROUP_NAME_TO_ASSIGN_USER)).isTrue();
    }

    @AfterClass
    public void bringUserToOriginalState(){
        usersRegistrationPage.removeUserFromGroup(GROUP_NAME_TO_ASSIGN_USER);
    }

}

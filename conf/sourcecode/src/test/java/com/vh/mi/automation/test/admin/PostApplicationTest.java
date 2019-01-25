package com.vh.mi.automation.test.admin;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.admin.IAdminPage;
import com.vh.mi.automation.api.pages.admin.pages.manageObjects.*;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i81306 on 5/11/2017.
 */
public class PostApplicationTest extends BaseTest {
    private static final String DEFAULT_DATABASE_TYPE = "vertica";
    private static final String DEFAULT_PROCESSING_TYPE = "Normal";

    IAdminPage adminPage;
    IApplications applicationsPage;
    IAddNewApplication addNewApplicationPage;
    IRegisterApplication registerApplicationPage;
    IApplicationStatus applicationStatusPage;
    IChangeDatabase changeDatabasePage;

    @Override
    public boolean skipBrowserCreation() {
        return true;
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        super.setUp();
        createNewBrowserInstance();
        User user = getUser("miautomation_super_user");
        adminPage = loginToAdminApplication(user, defaultWaitTime);
    }

    @Test(groups = {"Posting"})
    public void postApplicationTest() {
        String appId = context.getAppId();
        String schemaName = context.getPostingSchema();
        applicationsPage = adminPage.goToManageObjectsPage().goToApplicationsPage();
        applicationsPage.deleteApplication(appId);
        addNewApplicationPage = applicationsPage.goToAddNewApplicationPage();
        registerApplicationPage = addNewApplicationPage.connectToApplications(DEFAULT_DATABASE_TYPE, schemaName, DEFAULT_PROCESSING_TYPE);
        applicationsPage = registerApplicationPage.registerApplication(appId);
        assertThat(applicationsPage.isApplicationPostingSuccessful(context.getAppId(), TimeOuts.FIFTEEN_MINUTES) == true);
    }

    @Test(groups = "Change-Database", description = "Admin => Change Database of application")
    public void changeDatabaseTest() {
        String appToChange = context.getAppId2();
        applicationsPage = adminPage.goToManageObjectsPage().goToApplicationsPage();
        applicationStatusPage = applicationsPage.viewDetailsForApp(appToChange);
        changeDatabasePage = applicationStatusPage.changeDatabase();
        applicationsPage = changeDatabasePage.switchDatabase("Vertica",context.getSwitchDatabase1(), context.getSwitchDatabase2() );

        assertThat(applicationsPage.isApplicationPostingSuccessful(context.getAppId(), TimeOuts.FIFTEEN_MINUTES) == true);
    }
}

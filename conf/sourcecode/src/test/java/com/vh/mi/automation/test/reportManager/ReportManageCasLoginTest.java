package com.vh.mi.automation.test.reportManager;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.reportManager.IReportManagerPage;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i81306 on 8/9/2017.
 */
@Test(groups = "Security")
public class ReportManageCasLoginTest extends BaseTest {
    IReportManagerPage reportManagerPage;
    @Override
    public boolean skipBrowserCreation() {
        return true;
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
        createNewBrowserInstance();
        User user = getUser("mta_RMSAML");
        reportManagerPage = loginToRMApplication(user, defaultWaitTime);
    }

    @Test(description = "Verify user can login to RM application from CAS login page")
    public void rmCasLoginTest() {
        assertThat(reportManagerPage.getDisplayedTitle()).isEqualTo("Report Administration");
    }
}

package com.vh.mi.automation.test.security;

import com.vh.mi.automation.api.common.TestGroups;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.dataSources.DataSourceFactory;
import com.vh.mi.automation.impl.pages.common.myAccountProfile.ChangePasswordPage;
import com.vh.mi.automation.test.base.BaseTest;
import org.fest.assertions.Assertions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.sql.DataSource;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author nimanandhar
 */
@Test (groups = "Database")
public class SixtyDaysPasswordRuleTest extends BaseTest {

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected boolean skipLogin() {
        return true;
    }

    @Test(groups = {TestGroups.REQUIRES_DATABASE}, description = "User should be shown password change page " +
            "when he has not changed password for last 60 days")
    public void passwordChangePageShownIfPasswordNotChangedForLast60Days() throws Exception {
        DataSource heuserDataSource = DataSourceFactory.getInstance(context).getNewHeuserDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(heuserDataSource);
        User user = getUser("miautomation_security_test_TC1573_user");

        String userId = jdbcTemplate.queryForObject("SELECT USERID FROM USR_USERS WHERE LOGINNAME=?", String.class, user.getUserId());
        assertThat(userId).matches("^\\d{5,6}$");
        jdbcTemplate.update("DELETE FROM USR_LGPWD WHERE userid=?", userId);

        //First check that the user can login normally
        IWelcomePage welcomePage = mi.open(context.getApplication()).loginWith(user, defaultWaitTime);
        Assertions.assertThat(welcomePage.getAvailableFronts()).hasSize(user.getAssignedApplications().size());
        welcomePage.logOut(defaultWaitTime);

        jdbcTemplate.update("INSERT INTO USR_LGPWD (userid,datestamp) VALUES(?,SYSDATE-61)", userId);

        //This time the user should be shown password change page
        ChangePasswordPage changePasswordPage = mi.open(context.getApplication()).loginExpectingChangePasswordScreen(user, defaultWaitTime);
        assertThat(webDriver.getTitle()).isEqualTo("Medical Intelligence : Change your Password");
    }
}

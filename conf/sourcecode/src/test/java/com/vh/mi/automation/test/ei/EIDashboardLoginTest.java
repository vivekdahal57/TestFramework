package com.vh.mi.automation.test.ei;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.ei.IEIDashboard;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i81306 on 5/30/2017.
 */
@Test(groups = {"Component-Interaction"})
public class EIDashboardLoginTest extends BaseTest {
    private static String EI_DASHBOARD_TITLE = "Enterprise Intelligence";
    IEIDashboard eiDashboard;

    @Parameters("browser")
    public EIDashboardLoginTest(@Optional("IE") String browser){
        super.setBrowser(browser);
    }

    @Override
    public boolean skipBrowserCreation() {
        return true;
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
        createNewBrowserInstance();
        User user = getUser("miautomation_EI_user");
        eiDashboard = loginToEIDashboard(user, defaultWaitTime);
    }

    @Test(description = "TC36249: Go to EI application => login with user having EI access => Assert that User lands on EI dashboard page")
    public void eiLoginTest() {
        eiDashboard.waitTillDocumentReady(defaultWaitTime);
        assertThat(eiDashboard.getPageTitle()).isEqualTo(EI_DASHBOARD_TITLE);
    }
}

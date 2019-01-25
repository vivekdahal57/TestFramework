package com.vh.mi.automation.test.miscrights;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author nimanandhar
 */
@Test (groups = "Security")
public class MiscRightFullFinancialAccessTest extends MiscRightsBaseTest {
    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testFullFinancialAccessAllowed() {
        User user = getUser("miautomation_full_financial_access_allowed_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        navigationPanel.doNavigateTo(Indv301.class, context.getDefaultWaitTimeout());
    }

    @Test
    public void testFullFinancialAccessDenied() {
        User user = getUser("miautomation_full_financial_access_denied_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        navigationPanel.doNavigateTo(Indv301.class, context.getDefaultWaitTimeout());
    }
}

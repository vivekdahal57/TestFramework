package com.vh.mi.automation.test.pages.common;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.pages.common.ProductUsageDashboard.ProductUsageDashboardPage;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10314 on 7/26/2017.
 */
public class ProductUsageDashboardTest extends BaseTest {
    private ProductUsageDashboardPage productUsageDashboardPage;
    private IWelcomePage welcomePage;

    @Override
    protected boolean skipLogin(){
        return true;
    }

    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @BeforeMethod
    public void beforeEach(){
        User user = getUser("miautomation_super_user");
        welcomePage = mi.open(context.getApplication()).loginWith(user, defaultWaitTime);
    }

    @Test(groups = "No-Run")
    public void ProductUsageDashboardLoadingTest(){
        productUsageDashboardPage = welcomePage.openProductUsageDashBoardPage(TimeOuts.SIXTY_SECONDS);
        assertThat(productUsageDashboardPage.isFullyLoaded()).isTrue();
    }
}

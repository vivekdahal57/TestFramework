package com.vh.mi.automation.test.pages.common;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.common.IOAMPage;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.pages.common.OAMPage;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i81306 on 8/28/2017.
 */
@Test (groups = {"Component-Interaction"})
public class OAMPageTest extends BaseTest {

    private IWelcomePage welcomePage;
    private OAMPage oamPage;

    @Override
    protected boolean skipLogin(){
        return true;
    }

    @BeforeClass
    public void setUp(){
        super.setUp();
        User user = getUser("miautomation_super_user");
        welcomePage = mi.open(context.getApplication()).loginWith(user, defaultWaitTime);
    }

    @Test(groups = "No-Run")
    public void oamPageLoadTest(){
        oamPage = welcomePage.openOAM(TimeOuts.SIXTY_SECONDS);
        assertThat(oamPage.isFullyLoaded()).isTrue();
    }
}

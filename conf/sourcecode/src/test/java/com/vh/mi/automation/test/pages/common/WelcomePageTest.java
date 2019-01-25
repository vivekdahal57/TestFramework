package com.vh.mi.automation.test.pages.common;

import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.test.base.BaseTest;
import org.fest.assertions.Assertions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collection;

public class WelcomePageTest extends BaseTest {
    private IWelcomePage welcomePage;

    @Override
    protected boolean skipLogin() {
        return true;
    }

    @BeforeClass()
    public void setUp() {
        super.setUp();
        welcomePage = mi.open(context.getApplication()).loginWith(getUser(), context.getDefaultWaitTimeout());
    }


    @Test(description = "This is the welcom page test")
    public void test_application_list() {
        Collection<String> applicationIds = welcomePage.getAvailableFronts();

        Assert.assertNotNull(applicationIds);
        Assert.assertTrue(applicationIds.size() > 0);

        String appId = context.getApplication().getAppId();
        Assertions.assertThat(applicationIds).contains(appId);
    }
}

package com.vh.mi.automation.test.saml;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.groovy.dsl.groupsandusers.Issuer;
import com.vh.mi.automation.impl.pages.saml.*;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.base.TestEnvironment;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Created by i81247 on 11/15/2016.
 */
@Test (groups = "Component-Interaction")
public class DemoerEISSOTests extends BaseTest {
    private String demoerUrl = context.getDemoerUrl();
    private String eiDashboardUrl = context.getEiDashboardUrl();

    @Parameters("browser")
    public DemoerEISSOTests(@Optional("IE") String browser){
        super.setBrowser(browser);
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected boolean skipLogin() {
        return true;
    }

   @Test
   public void testLandingOnEIDashboard() {
        User demoerSvcAcccount = getUser();
        DemoerLoginPage loginPage = DemoerLoginPage .loadPage(getWebDriver(), demoerUrl);
        DemoerAssistedInputPage assistedInputPage = loginPage.doLogin(demoerSvcAcccount.getUserId(), demoerSvcAcccount.getPassword());
        DemoerQAPage demoerQAPage = assistedInputPage.loadQAPage();
        demoerQAPage.selectAppType(DemoerQAPage.AppType.ENTERPRISE_INTELLIGENCE);
        demoerQAPage.enterIssuer(TestEnvironment.find.issuerById("www.webautomationtestissuer_ei.com").getName());
        User eiUser = getUser("mta_EIUser");
        demoerQAPage.enterLoginName(eiUser.getUserId());
        demoerQAPage.submitForm();
        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(webDriver);
        SeleniumUtils.switchToNewWindow(webDriver);
        if (webDriver.getTitle().contains("End User License Agreement (EULA)")) {
            EULAPage eulaPage = new EULAPage(webDriver);
            eulaPage.agree(EIDashboardDummyPage.class, 30);
        }
        String currentURL = webDriver.getCurrentUrl();
        Assert.assertTrue(currentURL.startsWith(eiDashboardUrl));
    }
}

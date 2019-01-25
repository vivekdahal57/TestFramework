package com.vh.mi.automation.test.saml;

import com.vh.mi.automation.groovy.dsl.groupsandusers.Issuer;
import com.vh.mi.automation.impl.comp.navPanel.NavigationPanel;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.base.TestEnvironment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author nimanandhar
 */
public class SSOBaseTest extends BaseTest {

    public static final int WAIT_TIME_SECONDS = 180;

    @Override
    protected boolean skipLogin() {
        return true; //skip login
    }


    protected boolean isNavigationPanelAvailable() {
        return NavigationPanel.isAvailable(webDriver);
    }


    public String randomValue() {
        return String.valueOf(System.currentTimeMillis());
    }

    public String ssoDateFrom() {
        //Todo for dynamic value
        return "2016-01-01T00:00:01Z";
    }

    public String ssoDateTo() {
        //Todo for dynamic value
        return "2020-12-31T23:59:59Z";
    }

    protected Issuer getIssuer(String automationIssuerId) {
        return TestEnvironment.find.issuerById(automationIssuerId);
    }
}

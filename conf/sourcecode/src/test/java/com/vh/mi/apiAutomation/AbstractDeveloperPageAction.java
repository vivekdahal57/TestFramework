package com.vh.mi.apiAutomation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.vh.mi.automation.api.data.DataSourceConfiguration;
import com.vh.mi.automation.groovy.dsl.groupsandusers.sql.SqlExecutor;
import com.vh.mi.automation.test.base.BaseTest;
import org.openqa.selenium.By;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by i82325 on 11/8/2017.
 */
public class AbstractDeveloperPageAction extends BaseTest {
    public final String APIGATEWAY_URL = context.getApiGatewayUrl();
    public final String API_WIDGET_SERVICE_ENDPOINT_URL = context
            .getApiWidgetServiceEndpointUrl();
    public final String API_ER_SERVICE_ENDPOINT_URL = context
            .getApiERServiceEndpointUrl();
    public static final int FULL_CYCLE = 1;
    public static final int CONTRACT_YEAR = 2;
    public static final int ROLLING_YEAR = 3;
    public static final int INVALID_BUT_EXISTS = 4;
    public static final int WRONG_AP = 9;
    public static final String PEPM = "PEPM";
    public static final String PMPM = "PMPM";
    public static final String PMPY = "PMPY";
    public static final String PEPY = "PEPY";
    public static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
    public static final String DRGS_MEMPER1000 = "MEMPER1000";
    public static final String MEMPER_1000 = "MEMPER_1000";
    public static final String ERPER_1000 = "ERPER_1000";
    public static final String TOTAL_PAID = "TOTAL_PAID";
    public static final String AVG_RISK_SCORE = "AVG_RISK_SCORE";
    public static final String PREDICTED_COST_PMPY = "PREDICTED_COST_PMPY";
    public static final String TOTAL_ER = "TOTAL_ER";
    public static final String INPATIENT_ER = "INPATIENT_ER";
    public static final String OUTPATIENT_ER = "OUTPATIENT_ER";
    public static final String AVERAGE_RRS = "AVERAGE_RRS";
    public static final String PREDICTED_COST_ANALYSIS = "PREDICTED_COST_ANALYSIS";
    public static final String ALL = "ALL";
    public static final int INCURRED_DATE = 1;
    public static final int PAID_DATE = 2;
    //    Analyze by
    public static final String ALLOWED_AMOUNT = "ALLOWED_AMOUNT";
    public static final String PAID_AMOUNT = "PAID_AMOUNT";
    //    ClaimType
    public static final String MEDICAL = "MED";
    public static final String PHARMACY = "RX";
    public static final String VISION = "VIS";
    public static final String DENTAL = "DEN";

    public static final String TOKEN_JWT_SIGNKEY = "hB.O#Z4)jEWlDD~DYLbD>I;^8ZD36cR?";
    public static final String TOKEN_AUDIENCE = "widgetservice.verscend.com";
    public static final String TOKEN_EMAIL = "amit.pokhrel@verscend.com";
    public static final String TOKEN_ISSUER = "API Gateway";
    public static final String USER_ID_KEY = "userId";
    public static final String EMAIL_ID_KEY = "email";
    public static final String APP_ID_KEY = "appIdList";

    //ER Top 10 diagnosis Grouper
    public static final String NO_OF_MEMBERS = "NO_OF_MEMBERS";
    public static final String NO_OF_VISITS = "NO_OF_VISITS";
    public static final String INPATIENT = "IP";
    public static final String OUTPATIENT = "OP";

    //ER Visit by Frequency
    public static final String AVGCOSTPEREVENT = "AVGCOSTPEREVENT";

    //cohort Ids
    public final String SFW_DYNAMIC_COHORTID = context.getSfwDynamicCohortid();
    public final String SFW_STATIC_COHORTID = context.getSfwStaticCohortid();
    public final String DYNAMIC_COHORTID =context.getDynamicCohortid();
    public final String STATIC_COHORTID = context.getStaticCohortid();
    public static final String INVALID_COHORTID = "11111";

    //Er Vs Urgent care
    public static final String UTILIZATION = "UTILIZATION";
    public static final String COST = "COST";

    //Top non pbm pos
    public static final String All = "ALL";
    public static final String HCPCS = "HCPCS";
    public static final String REVENUE_CODE = "REVENUECODE";

    protected final String getTokenForRequestedURL(String url) {
        getWebDriver().findElement(By.name("serviceURL")).clear();
        getWebDriver().findElement(By.name("serviceURL")).sendKeys(url);
        getWebDriver().findElement(By.id("submitButton")).click();
        return getWebDriver().findElement(By.xpath(
                "html/body/table/tbody/tr[3]/td/table/tbody/tr[1]/td[2]"))
                .getText();
    }

    public String getToken(String automationId) {
        String[] appArrayList = new String[] { context.getAppId(),
                context.getAppId2() };
        Algorithm signAlgo = null;
        try {
            signAlgo = Algorithm.HMAC256(TOKEN_JWT_SIGNKEY);
        } catch (UnsupportedEncodingException e) {
            logger.info(e.getMessage());
        }
        DataSourceConfiguration dsc = context
                .getHeuserDataSourceConfiguration();
        SqlExecutor sqlExecutor = new SqlExecutor(dsc, dsc, dsc);
        String userId = sqlExecutor
                .getUserId(getUser(automationId).getUserId());
        String userName = getUser(automationId).getUserId();
        return JWT.create()
                .withIssuer(TOKEN_ISSUER)
                .withIssuedAt(new Date())
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withSubject(userName)
                .withAudience(TOKEN_AUDIENCE)
                .withClaim(USER_ID_KEY, userId)
                .withClaim(EMAIL_ID_KEY, TOKEN_EMAIL)
                .withArrayClaim(APP_ID_KEY, appArrayList)
                .sign(signAlgo);
    }

    public String getToken(String automationId, String userId) {
        String[] appArrayList = { context.getAppId(), context.getAppId2() };
        Algorithm signAlgo = null;
        try {
            signAlgo = Algorithm.HMAC256(TOKEN_JWT_SIGNKEY);
        } catch (UnsupportedEncodingException e) {
            logger.info(e.getMessage());
        }

        return JWT.create().withIssuer(TOKEN_ISSUER).withIssuedAt(new Date())
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withSubject(getUser(automationId).getUserId())
                .withAudience(TOKEN_AUDIENCE)
                .withClaim(USER_ID_KEY, userId)
                .withClaim(EMAIL_ID_KEY, TOKEN_EMAIL)
                .withArrayClaim(APP_ID_KEY, appArrayList).sign(signAlgo);
    }

    public String getTokenWithOutAppId(String automationId) {
        Algorithm signAlgo = null;
        String[] appArrayList = {};
        try {
            signAlgo = Algorithm.HMAC256(TOKEN_JWT_SIGNKEY);
        } catch (UnsupportedEncodingException e) {
            logger.info(e.getMessage());
        }

        return JWT.create().withIssuer(TOKEN_ISSUER).withIssuedAt(new Date())
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withSubject(getUser(automationId).getUserId())
                .withAudience(TOKEN_AUDIENCE)
                .withClaim(USER_ID_KEY, getUser(automationId).getUserId())
                .withClaim(EMAIL_ID_KEY, TOKEN_EMAIL)
                .withArrayClaim(APP_ID_KEY, appArrayList).sign(signAlgo);
    }

    public String getTokenWithMultipleAppId(String automationId) {
        Algorithm signAlgo = null;
        String[] appArrayList = { "977-036", "739-001", "659-004", "008-101" };
        try {
            signAlgo = Algorithm.HMAC256(TOKEN_JWT_SIGNKEY);
        } catch (UnsupportedEncodingException e) {
            logger.info(e.getMessage());
        }

        return JWT.create().withIssuer(TOKEN_ISSUER).withIssuedAt(new Date())
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withSubject(getUser(automationId).getUserId())
                .withAudience(TOKEN_AUDIENCE)
                .withClaim(USER_ID_KEY, getUser(automationId).getUserId())
                .withClaim(EMAIL_ID_KEY, TOKEN_EMAIL)
                .withArrayClaim(APP_ID_KEY, appArrayList).sign(signAlgo);
    }
}

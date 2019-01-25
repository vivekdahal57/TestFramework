package com.vh.mi.apiAutomation.test.demographicsMovement;

import com.vh.mi.apiAutomation.AbstractDeveloperPageAction;
import com.vh.mi.apiAutomation.groovy.PayloadCreator;
import com.vh.mi.apiAutomation.util.Utility;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.pages.common.WelcomePage;
import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import groovy.util.Eval;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82325 on 11/8/2017.
 */
public class DemographicsWidgetSFWUserTest extends AbstractDeveloperPageAction {
    String ticket = "ST-3754-9dRelxBFtrXamx4fUgxL-cas";
    final String REQUEST_URL = APIGATEWAY_URL + "/widgets/demMovement";
    static String JSON_SCHEMA_PATH = "schema/newDemographicsMovement.json";
    static String dataURL = "/api/widgets/demMovement";
    static String memberQueryFileName = "demographicsMovementWidgetMemberQuerySFW.sql";

    @BeforeClass(alwaysRun = true)
    public void setup() {
        super.setUpAdmin("miautomation_group_level_sfw_user");
        getWebDriver().get(context.getAppUrl());
        IWelcomePage welcomePage = PageFactory
                .initElements(getWebDriver(), WelcomePage.class);
        welcomePage.selectFront(context.getAppId());
        getWebDriver().get(context.getProxyTicketUrl());
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValues() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVG_RISK_SCORE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithAllCorrectLvlValues() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVG_RISK_SCORE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithEmptyBusinessLvlValues() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVG_RISK_SCORE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithWidgetPayloadToGetMemberQuery() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, FULL_CYCLE, PAID_DATE,
                        AVG_RISK_SCORE, SFW_DYNAMIC_COHORTID);
        String memberQueryPayLoad = new PayloadCreator()
                .demographicsMovementMemberQueryPayload(payLoad, dataURL,
                        "DEMOGRAPHICS", "All", true, 9).toPrettyString();
        ticket = getTokenForRequestedURL(REQUEST_URL + "/memberQuery");
        logger.info("Request Sent: \n" + memberQueryPayLoad);
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL + "/memberQuery");
        String jsonString = res.getBody().asString();
        JsonPath jsonPath = new JsonPath(jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        String actualSql = Utility.normalizeSQL(jsonPath.get("query"));
        String expectedQuery = Utility.readFileAsString(memberQueryFileName);
        Assert.assertEquals(actualSql, expectedQuery);
        res.then().body("params.size()", is(4));
        List<String> queryParams = Utility
                .findQueryParams(jsonPath.get("query"), ":");
        Set<Object> paramsFromResponse = jsonPath.from(res.asString())
                .getMap("params").keySet();
        boolean flag = false;
        for (String param : queryParams) {
            for (int i = 0; i < paramsFromResponse.size(); i++) {
                if (param.replace(":", "").replace(")","")
                        .equals(paramsFromResponse.toArray()[i])) {
                    flag = false;
                    break;
                } else {
                    flag = true;
                }
            }
            if (flag) {
                Assert.assertEquals(flag, false,
                        param + " not found in Response param");
                flag = false;
            }
        }
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesAndRollingYearAP() {
        String[][] blParam = { {}, { "Family Practice" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, ROLLING_YEAR, INCURRED_DATE,
                        AVG_RISK_SCORE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test(enabled = false)
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButInvalidAP() {
        String[][] blParam = { {}, {}, { "BERGE, ERLAND MD" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, INVALID_BUT_EXISTS,
                        INCURRED_DATE, AVG_RISK_SCORE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 500, jsonString);
    }

    @Test(enabled = false)
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButWrongAP() {
        String[][] blParam = { {}, { "General Practice" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, WRONG_AP, INCURRED_DATE,
                        AVG_RISK_SCORE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 500, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesAndContractYearAP() {
        String[][] blParam = { { "ABC" }, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, CONTRACT_YEAR,
                        INCURRED_DATE, AVG_RISK_SCORE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithInCorrectLvlValues() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { { "NATIONAL MED" }, { "Hartsburg Physicians" },
                {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVG_RISK_SCORE, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvl1ButIncorrectLvl2Values() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { { "ABC" }, { "Capitol Group" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVG_RISK_SCORE, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithUsedToken() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVG_RISK_SCORE, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 401, jsonString);
    }

    @Test
    public void verifyWidgetCallWithIncorrectToken() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVG_RISK_SCORE, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", "skldjfklasdjflasjdfksja")
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 401, jsonString);
    }

    @Test
    public void verifyWidgetCallWithWrongLvlValues() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { { "wrong" }, { "wrong" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVG_RISK_SCORE, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithInvalidAppIdMemberQuery() {
        ticket = getTokenForRequestedURL(REQUEST_URL + "/memberQuery");
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVG_RISK_SCORE, null);
        Eval.xy(payLoad, "Doesnotexists",
                "x.content." + "domainParams.appId" + "=y");
        String memberQueryPayLoad = new PayloadCreator()
                .demographicsMovementMemberQueryPayload(payLoad, dataURL,
                        "DEMOGRAPHICS", "All", true, 9).toPrettyString();
        logger.info(payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL + "/memberQuery");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 403, jsonString);
        res.then().body("errors[0].code", is("unauthorized-appid"));
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithUnauthorizedAppId() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVG_RISK_SCORE, null);
        Eval.xy(payLoad, "982-001", "x.content." + "domainParams.appId" + "=y");
        logger.info(payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 403, jsonString);
        res.then().body("errors[0].code", is("unauthorized-appid"));
    }

    @Test
    public void verifyWidgetCallWithDyanmicCohort() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, FULL_CYCLE, PAID_DATE,
                        AVG_RISK_SCORE, SFW_DYNAMIC_COHORTID);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithStaticCohort() {
        String[][] blParam = { { "ABC" }, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, CONTRACT_YEAR, PAID_DATE,
                        AVG_RISK_SCORE, SFW_STATIC_COHORTID);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithInvalidCohort() {
        String[][] blParam = { {}, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .demographicsWigdetPayload(blParam, ROLLING_YEAR, INCURRED_DATE,
                        AVG_RISK_SCORE, INVALID_BUT_EXISTS);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 400);
        res.then().body("errors[0].code", is("invalid-cohort"));
    }

    @AfterClass
    public void quitDriver() {
        getWebDriver().quit();
    }

}

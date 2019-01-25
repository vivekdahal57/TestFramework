package com.vh.mi.apiAutomation.test.predictedCostByRiskBand;

import com.vh.mi.apiAutomation.AbstractDeveloperPageAction;
import com.vh.mi.apiAutomation.groovy.PayloadCreator;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.pages.common.WelcomePage;
import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import groovy.util.Eval;
import io.restassured.response.Response;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

/**
 * Created by i82325 on 11/8/2017.
 */
public class PredictedCostWidgetSFWUserTest
        extends AbstractDeveloperPageAction {
    String ticket = "ST-3754-9dRelxBFtrXamx4fUgxL-cas";
    final String REQUEST_URL =
            APIGATEWAY_URL + "/widgets/predictedcostriskband";
    static String JSON_SCHEMA_PATH = "schema/predictedCost.json";
    static String dataURL = "/api/widgets/riskband";

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
                .predictedCostWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVERAGE_RRS, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.size()", is(3));
        res.then().body("data.RRSBand", hasItems("Very Low", "Low", "Medium"));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithWidgetPayloadToGetMemberQuery() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, CONTRACT_YEAR,
                        INCURRED_DATE, AVERAGE_RRS, null);
        String memberQueryPayLoad = new PayloadCreator()
                .predictedCostMemberQueryPayload(payLoad, dataURL,
                        "COORDINATION_OF_CARE", "Very Low").toPrettyString();
        ticket = getTokenForRequestedURL(REQUEST_URL + "/memberQuery");
        logger.info("Request Sent: \n" + memberQueryPayLoad);
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .when()
                .post(REQUEST_URL + "/memberQuery" + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithAllCorrectLvlValues() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVERAGE_RRS, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.size()", is(3));
        res.then().body("data.RRSBand", hasItems("Very Low", "Low", "Medium"));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithEmptyBusinessLvlValues() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVERAGE_RRS, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.size()", is(3));
        res.then().body("data.RRSBand", hasItems("Very Low", "Low", "Medium"));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesAndRollingYearAP() {
        String[][] blParam = { {}, { "Family Practice" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, ROLLING_YEAR,
                        INCURRED_DATE, AVERAGE_RRS, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.size()", is(3));
        res.then().body("data.RRSBand", hasItems("Very Low", "Low", "Medium"));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test(enabled = false)
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButInvalidAP() {
        String[][] blParam = { {}, {}, { "BERGE, ERLAND MD" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, INVALID_BUT_EXISTS,
                        INCURRED_DATE, PREDICTED_COST_ANALYSIS, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 500, jsonString);
    }

    @Test(enabled = false)
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButWrongAP() {
        String[][] blParam = { {}, { "General Practice" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, WRONG_AP, INCURRED_DATE,
                        PREDICTED_COST_ANALYSIS, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 500, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesAndContractYearAP() {
        String[][] blParam = { { "ABC" }, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, CONTRACT_YEAR,
                        INCURRED_DATE, AVERAGE_RRS, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.size()", is(3));
        res.then().body("data.RRSBand", hasItems("Very Low", "Low", "Medium"));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithInCorrectLvlValues() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { { "NATIONAL MED" }, { "Hartsburg Physicians" },
                {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVERAGE_RRS, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.size()", is(0));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvl1ButIncorrectLvl2Values() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { { "ABC" }, { "Capitol Group" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVERAGE_RRS, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.size()", is(0));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithUsedToken() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        PREDICTED_COST_ANALYSIS, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 401, jsonString);
    }

    @Test
    public void verifyWidgetCallWithIncorrectToken() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        PREDICTED_COST_ANALYSIS, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + "skldjfklasdjflasjdfksja");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 401, jsonString);
    }

    @Test
    public void verifyWidgetCallWithWrongLvlValues() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { { "wrong" }, { "wrong" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVERAGE_RRS, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.size()", is(0));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithInvalidAppIdMemberQuery() {
        ticket = getTokenForRequestedURL(REQUEST_URL + "/memberQuery");
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVERAGE_RRS, null);
        Eval.xy(payLoad, "Doesnotexists",
                "x.content." + "domainParams.appId" + "=y");
        String memberQueryPayLoad = new PayloadCreator()
                .predictedCostMemberQueryPayload(payLoad, dataURL,
                        "COORDINATION_OF_CARE", "Very Low").toPrettyString();
        logger.info(payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .when()
                .post(REQUEST_URL + "/memberQuery" + "?ticket=" + ticket);
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
                .predictedCostWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVERAGE_RRS, null);
        Eval.xy(payLoad, "982-001", "x.content." + "domainParams.appId" + "=y");
        logger.info(payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 403, jsonString);
        res.then().body("errors[0].code", is("unauthorized-appid"));
    }

    @Test
    public void verifyWidgetCallWithDynamicCohort() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVERAGE_RRS, SFW_DYNAMIC_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithStaticCohort() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVERAGE_RRS, SFW_STATIC_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithInvalidCohort() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { { "wrong" }, { "wrong" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .predictedCostWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        AVERAGE_RRS, INVALID_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
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

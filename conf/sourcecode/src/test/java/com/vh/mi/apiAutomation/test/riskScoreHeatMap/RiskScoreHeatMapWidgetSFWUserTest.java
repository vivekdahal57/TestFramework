package com.vh.mi.apiAutomation.test.riskScoreHeatMap;

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
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82325 on 11/8/2017.
 */
public class RiskScoreHeatMapWidgetSFWUserTest
        extends AbstractDeveloperPageAction {
    String ticket = "ST-3754-9dRelxBFtrXamx4fUgxL-cas";
    final String REQUEST_URL = APIGATEWAY_URL + "/widgets/riskscore";
    final String REQUEST_URL1 = APIGATEWAY_URL + "/widgets/riskscore/model";
    static String JSON_SCHEMA_PATH = "schema/riskScoreHeatMapModels.json";
    static String JSON_SCHEMA_PATH1 = "schema/riskScoreHeatMapModelNo.json";
    static String dataURL = "/api/widgets/riskscore";

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
    public void verifyWidgetCallToGetListOfDxcgModels() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, "", FULL_CYCLE,
                        INCURRED_DATE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL + "/models");
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "/models" + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.size()", is(3));
        res.then().body("data", hasItems(18, 55, 56));
    }

    @Test
    public void verifyWidgetCallWithWidgetPayloadToGetMemberQuery() {
        int modelNo = 56;
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, null);
        String memberQueryPayLoad = new PayloadCreator()
                .riskScoreHeatMapMemberQueryPayload(payLoad, dataURL,
                        "RiskScore", 04013, modelNo).toPrettyString();
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
    public void verifyWidgetCallWithCorrectLvlValues() {
        int modelNo = 56;
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
        res.then().body("data.size()", is(1));
    }

    @Test
    public void verifyWidgetCallWithAllCorrectLvlValues() {
        int modelNo = 56;
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
        res.then().body("data.size()", is(1));
    }

    @Test
    public void verifyWidgetCallWithEmptyBusinessLvlValues() {
        int modelNo = 18;
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
        res.then().body("data.size()", is(1));
    }

    @Test
    public void verifyWidgetCallWithEmptyBusinessLvlValuesStateLevel() {
        int modelNo = 18;
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, null);
        ticket = getTokenForRequestedURL(
                REQUEST_URL1 + "/" + modelNo + "/state/KY");
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "/state/KY" + "?ticket="
                        + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
        res.then().body("data.size()", is(1));
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesAndRollingYearAP() {
        int modelNo = 55;
        String[][] blParam = { {}, { "Family Practice" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, ROLLING_YEAR,
                        INCURRED_DATE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
        res.then().body("data.size()", is(1));
    }

    @Test(enabled = false)
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButInvalidAP() {
        int modelNo = 56;
        String[][] blParam = { {}, {}, { "BERGE, ERLAND MD" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo,
                        INVALID_BUT_EXISTS, INCURRED_DATE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 500, jsonString);
    }

    @Test(enabled = false)
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButWrongAP() {
        int modelNo = 18;
        String[][] blParam = { {}, { "General Practice" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, WRONG_AP,
                        INCURRED_DATE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 500, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesAndContractYearAP() {
        int modelNo = 55;
        String[][] blParam = { { "ABC" }, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, CONTRACT_YEAR,
                        INCURRED_DATE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
        res.then().body("data.size()", is(1));
    }

    @Test
    public void verifyWidgetCallWithInCorrectLvlValues() {
        int modelNo = 56;
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        String[][] blParam = { { "NATIONAL MED" }, { "Hartsburg Physicians" },
                {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
        res.then().body("data.size()", is(0));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvl1ButIncorrectLvl2Values() {
        int modelNo = 18;
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        String[][] blParam = { { "ABC" }, { "Capitol Group" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
        res.then().body("data.size()", is(0));
    }

    @Test
    public void verifyWidgetCallWithUsedToken() {
        int modelNo = 55;
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 401, jsonString);
    }

    @Test
    public void verifyWidgetCallWithIncorrectToken() {
        int modelNo = 56;
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket="
                        + "skldjfklasdjflasjdfksja");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 401, jsonString);
    }

    @Test
    public void verifyWidgetCallWithWrongLvlValues() {
        int modelNo = 18;
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        String[][] blParam = { { "wrong" }, { "wrong" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        res.then().body("data.size()", is(0));
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithInvalidAppIdMemberQuery() {
        int modelNo = 18;
        ticket = getTokenForRequestedURL(REQUEST_URL + "/memberQuery");
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, null);
        Eval.xy(payLoad, "Doesnotexists1",
                "x.content." + "domainParams.appId" + "=y");
        String memberQueryPayLoad = new PayloadCreator()
                .riskScoreHeatMapMemberQueryPayload(payLoad, dataURL,
                        "RiskScore", 04013, modelNo).toPrettyString();
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
        int modelNo = 18;
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, null);
        Eval.xy(payLoad, "982-001", "x.content." + "domainParams.appId" + "=y");
        logger.info(payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 403, jsonString);
        res.then().body("errors[0].code", is("unauthorized-appid"));
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithUnauthorizedAppIdStateLevel() {
        int modelNo = 18;
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, null);
        Eval.xy(payLoad, "982-001", "x.content." + "domainParams.appId" + "=y");
        ticket = getTokenForRequestedURL(
                REQUEST_URL1 + "/" + modelNo + "/state/KY");
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "/state/KY" + "?ticket="
                        + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 403, jsonString);
        res.then().body("errors[0].code", is("unauthorized-appid"));
    }

    @Test
    public void verifyWidgetCallWithDyanmicCohort() {
        int modelNo = 56;
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, SFW_DYNAMIC_COHORTID);
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
        res.then().body("data.size()", is(1));
    }

    @Test
    public void verifyWidgetCallWithStaticCohort() {
        int modelNo = 18;
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, CONTRACT_YEAR,
                        PAID_DATE, SFW_STATIC_COHORTID);
        ticket = getTokenForRequestedURL(
                REQUEST_URL1 + "/" + modelNo + "/state/KY");
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "/state/KY" + "?ticket="
                        + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
        res.then().body("data.size()", is(1));
    }

    @Test
    public void verifyWidgetCallWithInvalidCohort() {
        int modelNo = 18;
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        String[][] blParam = { { "wrong" }, { "wrong" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, ROLLING_YEAR,
                        INCURRED_DATE, INVALID_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
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

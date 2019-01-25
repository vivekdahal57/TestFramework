package com.vh.mi.apiAutomation.test.riskScoreHeatMap;

import com.vh.mi.apiAutomation.AbstractDeveloperPageAction;
import com.vh.mi.apiAutomation.groovy.PayloadCreator;
import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82325 on 11/8/2017.
 */
public class RiskScoreHeatMapWidgetTest extends AbstractDeveloperPageAction {
    String ticket;
    final String REQUEST_URL = APIGATEWAY_URL + "/widgets/riskscore";
    final String REQUEST_URL1 = APIGATEWAY_URL + "/widgets/riskscore/model";
    static String JSON_SCHEMA_PATH = "schema/riskScoreHeatMapModels.json";
    static String JSON_SCHEMA_PATH1 = "schema/riskScoreHeatMapModelNo.json";
    static String dataURL = "/api/widgets/riskscore";

    @BeforeClass
    public void setup() {
        super.setUpAdmin("miautomation_super_user");
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
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.size()", is(3));
        res.then().body("data", hasItems(18, 55, 56));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
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
        res.then().body("data.size()", is(2));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAndIncurredDate() {
        int modelNo = 56;
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
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
        res.then().body("meta.model", is(nullValue()));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAndPaidDate() {
        int modelNo = 56;
        String[][] blParam = { { "ABC", "NATIONAL MED" }, {},
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        PAID_DATE, null);
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
        res.then().body("meta.model", is(nullValue()));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAndContractYearAP() {
        int modelNo = 56;
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice" }, { "BERGE, ERLAND MD" }, {}, {} };
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
        res.then().body("meta.model", is(nullValue()));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAndRollingYearAP() {
        int modelNo = 56;
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "General Practice" }, { "BERGE, ERLAND MD" }, {}, {} };
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
        res.then().body("data.size()", is(0));
        res.then().body("meta.model", is(nullValue()));
    }

    @Test
    public void verifyWidgetCallWithInCorrectLvlValues() {
        int modelNo = 56;
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        String[][] blParam = { { "wrong" }, {}, {}, {}, {}, {} };
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
        res.then().body("meta.model", is(nullValue()));
    }

    @Test
    public void verifyWidgetCallWithMixedLvlValues() {
        int modelNo = 56;
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        String[][] blParam = { { "ABC" }, { "wrong" }, {}, {}, {}, {} };
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
        res.then().body("meta.model", is(nullValue()));
    }

    @Test
    public void verifyWidgetCallWithWidgetPayloadToGetMemberQuery() {
        int modelNo = 56;
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
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
    public void verifyWidgetCallWithEmptyBusinessLvlValues() {
        int modelNo = 56;
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
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
        res.then().body("data.size()", is(37));
        res.then().body("meta.model", is(nullValue()));
        res.then().body("data.RRSBand",
                hasItems("Very Low", "Low", "Medium", "High"));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesModel18() {
        int modelNo = 18;
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice" }, {}, {}, {} };
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
        res.then().body("data.size()", is(23));
        res.then().body("meta.model", is(nullValue()));
        res.then().body("data.RRSBand", hasItems("Very Low", "Low", "Medium"));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesModel56() {
        int modelNo = 56;
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
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
        res.then().body("data.size()", is(27));
        res.then().body("meta.model", is(nullValue()));
        res.then().body("data.RRSBand", hasItems("Very Low", "Low", "Medium"));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesModel55() {
        int modelNo = 55;
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        PAID_DATE, null);
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL1 + "/" + modelNo + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
        res.then().body("data.size()", is(23));
        res.then().body("meta.model", is(nullValue()));
        res.then().body("data.RRSBand", hasItems("Very Low", "Low", "Medium"));
    }

    @Test
    public void verifyWidgetCallWithDyanmicCohort() {
        int modelNo = 56;
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, FULL_CYCLE,
                        INCURRED_DATE, DYNAMIC_COHORTID);
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
        res.then().assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
    }

    @Test
    public void verifyWidgetCallWithStaticCohort() {
        int modelNo = 18;
        String[][] blParam = { { "ABC", "NATIONAL MED" }, {},
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, CONTRACT_YEAR,
                        PAID_DATE, STATIC_COHORTID);
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
        res.then().assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH1));
    }

    @Test
    public void verifyWidgetCallWithInvalidCohort() {
        int modelNo = 55;
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice" }, { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .riskScoreHeatMapWidgetPayload(blParam, modelNo, ROLLING_YEAR,
                        INCURRED_DATE, INVALID_COHORTID);
        ticket = getTokenForRequestedURL(REQUEST_URL1 + "/" + modelNo);
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

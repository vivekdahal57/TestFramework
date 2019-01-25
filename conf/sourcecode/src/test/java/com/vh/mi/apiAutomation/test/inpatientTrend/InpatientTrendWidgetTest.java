package com.vh.mi.apiAutomation.test.inpatientTrend;

import com.vh.mi.apiAutomation.AbstractDeveloperPageAction;
import com.vh.mi.apiAutomation.groovy.PayloadCreator;
import com.vh.mi.apiAutomation.util.Utility;
import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;

public class InpatientTrendWidgetTest extends AbstractDeveloperPageAction {

    String ticket;
    final String REQUEST_URL =
            API_WIDGET_SERVICE_ENDPOINT_URL + "/widgets/inpatientTrend";
    static String JSON_SCHEMA_PATH = "schema/inpatientTrend.json";
    static String token = "";
    static String memberQueryFileName = "inpatientTrendMemberQuery.sql";

    @BeforeClass
    public void setup() {
        token = getToken("miautomation_super_user");
    }

    private enum AnalyticOptions {
        ADMPERK, READMPERK, ACSCADMPERK
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValues() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .inpatientTrendPayload(blParam, FULL_CYCLE,
                        AnalyticOptions.ADMPERK);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("meta.norm.size()", is(7));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAndContractYearAP() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice" }, { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .inpatientTrendPayload(blParam, CONTRACT_YEAR,
                        AnalyticOptions.ADMPERK);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("meta.norm.size()", is(7));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAndRollingYearAP() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "General Practice" }, { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .inpatientTrendPayload(blParam, ROLLING_YEAR,
                        AnalyticOptions.ACSCADMPERK);

        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("meta.norm.size()", is(7));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithMixedLvlValues() {
        String[][] blParam = { { "ABC" }, { "wrong" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .inpatientTrendPayload(blParam, FULL_CYCLE,
                        AnalyticOptions.ACSCADMPERK);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
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
    public void verifyWidgetCallWithEmptyBusinessLvlValues() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .inpatientTrendPayload(blParam, FULL_CYCLE,
                        AnalyticOptions.READMPERK);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
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

    @Test
    public void verifyWidgetCallWithWidgetPayloadToGetMemberQuery() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .inpatientTrendPayload(blParam, FULL_CYCLE,
                        AnalyticOptions.READMPERK);
        String memberQueryPayLoad = new PayloadCreator()
                .inpatientTrendMemberQueryPayload(payLoad, "2/2016",
                        "readmission")
                .toPrettyString();
        logger.info("Request Sent: \n" + memberQueryPayLoad);
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/memberQuery");
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        JsonPath jsonPath = new JsonPath(jsonString);
        String actualSql = Utility.normalizeSQL(jsonPath.get("query"));
        String expectedQuery = Utility.readFileAsString(memberQueryFileName);
        Assert.assertEquals(actualSql, expectedQuery);
        res.then().body("params.size()", is(3));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuess() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .inpatientTrendPayload(blParam, FULL_CYCLE,
                        AnalyticOptions.ADMPERK);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
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

}

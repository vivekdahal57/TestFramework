package com.vh.mi.apiAutomation.test.erVisitUtilizationWidgetTests;

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
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82325 on 11/8/2017.
 */
public class ErVisitUtilizationWidgetTest extends AbstractDeveloperPageAction {
    String ticket;
    final String REQUEST_URL = APIGATEWAY_URL + "/widgets/erVisitUtilization";
    static String dataURL = "/api/widgets/erVisitUtilization";
    static String JSON_SCHEMA_PATH = "schema/erVisitUtilization.json";

    @BeforeClass
    public void setup() {
        super.setUpAdmin("miautomation_super_user");
    }

    /**
     * Incurred date
     */
    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAndIncurredDate() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        ticket = getTokenForRequestedURL(REQUEST_URL);
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, TOTAL_ER, PMPM, null);
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
    public void verifyWidgetCallWithCorrectLvlValuesAndPaidDate() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, {},
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE, PAID_DATE,
                        TOTAL_ER, PMPM, null);
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
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAndContractYearAP() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice" }, { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, CONTRACT_YEAR,
                        INCURRED_DATE, TOTAL_ER, PMPM, null);
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
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAndRollingYearAP() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "General Practice" }, { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, ROLLING_YEAR,
                        INCURRED_DATE, TOTAL_ER, PMPM, null);
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
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithInCorrectLvlValues() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { { "wrong" }, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, TOTAL_ER, PMPM, null);
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
    public void verifyWidgetCallWithMixedLvlValues() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { { "ABC" }, { "wrong" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, TOTAL_ER, PMPM, null);
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
    public void verifyWidgetCallWithEmptyBusinessLvlValues() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, TOTAL_ER, PMPM, null);
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
    public void verifyWidgetCallWithEmptyBusinessLvlValuesStateLevel() {
        ticket = getTokenForRequestedURL(REQUEST_URL + "/state/KY");
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, TOTAL_ER, PMPM, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "/state/KY" + "?ticket=" + ticket);
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
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, TOTAL_ER, PMPM, null);
        String memberQueryPayLoad = new PayloadCreator()
                .erVisitUtilizationMemberQueryPayload(payLoad, dataURL,
                        "ER_VISIT_UTILIZATION", "06037").toPrettyString();
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
    public void verifyWidgetCallWithCorrectLvlValuesTotalERPEPM() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, TOTAL_ER, PEPM, null);
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
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesInPatientERPEPM() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, INPATIENT_ER, PEPM, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesOutPatientERPEPM() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, OUTPATIENT_ER, PEPM, null);
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
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesTotalERPMPM() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, TOTAL_ER, PMPM, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesInPatientERPMPM() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, INPATIENT_ER, PMPM, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesOutPatientERPMPM() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, OUTPATIENT_ER, PMPM, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        ticket = getTokenForRequestedURL(REQUEST_URL);
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
    public void verifyWidgetCallWithCorrectLvlValuesTotalERPMPY() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, TOTAL_ER, PMPY, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesInPatientERPMPY() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, INPATIENT_ER, PMPY, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesOutPatientERPMPY() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, OUTPATIENT_ER, PMPY, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesTotalERPEPY() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, TOTAL_ER, PEPY, null);
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
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesInPatientERPEPY() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, INPATIENT_ER, PEPY, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesOutPatientERPEPY() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, OUTPATIENT_ER, PEPY, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesTotalERERPERK() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, TOTAL_ER, ERPER_1000, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesInPatientERERPERK() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, INPATIENT_ER, ERPER_1000, null);
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
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesOutPatientERERPERK() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, OUTPATIENT_ER, ERPER_1000, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithDyanmicCohort() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitUtilizationWidgetPayload(blParam, CONTRACT_YEAR,
                        PAID_DATE, OUTPATIENT_ER, ERPER_1000, DYNAMIC_COHORTID);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
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
                .erVisitUtilizationWidgetPayload(blParam, CONTRACT_YEAR,
                        PAID_DATE, INPATIENT_ER, PEPY, STATIC_COHORTID);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
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
                .erVisitUtilizationWidgetPayload(blParam, CONTRACT_YEAR,
                        PAID_DATE, TOTAL_ER, PMPY, INVALID_COHORTID);
        ticket = getTokenForRequestedURL(REQUEST_URL);
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

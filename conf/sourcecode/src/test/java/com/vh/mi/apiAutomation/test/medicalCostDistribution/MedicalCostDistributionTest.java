package com.vh.mi.apiAutomation.test.medicalCostDistribution;

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

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;

public class MedicalCostDistributionTest extends AbstractDeveloperPageAction {

    final String REQUEST_URL = API_WIDGET_SERVICE_ENDPOINT_URL
            + "/widgets/medicalCostDistribution";

    static String JSON_SCHEMA_PATH = "schema/medicalCostDistribution.json";
    String token = "";

    static String memberQueryFileName = "medicalCostMemberQuery.sql";

    List<String> analyzeByOptions = Arrays.asList(PEPM, PMPM);

    List<String> amountOptions = Arrays.asList(ALLOWED_AMOUNT, PAID_AMOUNT);

    List<Integer> analysisPeriod = Arrays
            .asList(FULL_CYCLE, CONTRACT_YEAR, ROLLING_YEAR);

    List<Integer> reportingBy = Arrays.asList(INCURRED_DATE, PAID_DATE);

    public List<String> getAmountOptions() {
        return amountOptions;
    }

    public List<String> getAnalyzeByOptions() {
        return analyzeByOptions;
    }

    public List<Integer> getAnalysisPeriod() {
        return analysisPeriod;
    }

    public List<Integer> getReportingBy() {
        return reportingBy;
    }

    @BeforeClass
    public void setup() {
        token = getToken("miautomation_super_user");
    }

    @Test
    public void testAllCombinations() {
        getAmountOptions()
                .forEach(amountOptions -> combineAnalyzeBy(amountOptions));

    }

    private void combineAnalyzeBy(String amountOptions) {
        getAnalyzeByOptions().forEach(
                analyzeByOptions -> combineAnalyzeByAndAnalyticOptions(
                        amountOptions, analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptions(String amountOptions,
            String analyzeByOptions) {
        getAnalysisPeriod().forEach(
                analysisPeriod -> combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriod(
                        analysisPeriod, amountOptions,
                        analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriod(
            Integer analysisPeriod, String amountOptions,
            String analyzeByOptions) {
        getReportingBy().forEach(
                reportingBy -> combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriodAndReportingBy(
                        reportingBy, analysisPeriod,
                        amountOptions, analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriodAndReportingBy(
            Integer reportingBy, Integer analysisPeriod, String amountOptions,
            String analyzeByOptions) {
        testFilterOptionsCombinations(analysisPeriod, reportingBy,
                amountOptions, analyzeByOptions);
    }

    public void testFilterOptionsCombinations(int analysisPeriod,
            int reportingBy, String amountOptions, String analyzeBy) {
        logger.info("Test params " + analysisPeriod + " " + reportingBy + " "
                + amountOptions + " " + analyzeBy);
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .medicalCostDistributionWidgetPayLoad(blParam, analysisPeriod,
                        reportingBy, amountOptions, analyzeBy, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValues() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .medicalCostDistributionWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, ALLOWED_AMOUNT, PEPM, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void verifyWidgetCallWithInCorrectLvlValues() {
        String[][] blParam = { { "wrong" }, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .medicalCostDistributionWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PAID_AMOUNT, PEPM, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);

        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void verifyWidgetCallWithEmptyLvlValues() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .medicalCostDistributionWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PAID_AMOUNT, PEPM, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);

        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));

        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void verifyWidgetCallWithDynamicCohort() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .medicalCostDistributionWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PAID_AMOUNT, PEPM, DYNAMIC_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);

        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200);

    }

    // this test is gonna fail with message Relation \"zzz_memlst_ids_75650\" does not exist
    // this table is dynamically created once cohort is selected from UI. ( for now done manually)

    @Test
    public void verifyWidgetCallWithStaticCohort() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .medicalCostDistributionWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PAID_AMOUNT, PEPM, STATIC_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);

        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithInvalidCohort() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .medicalCostDistributionWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PEPM, PAID_AMOUNT, INVALID_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);

        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 400);
        res.then().body("errors[0].code", is("invalid-cohort"));
    }

    @Test
    public void verifyWidgetCallToVerifyMemberQuery() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .medicalCostDistributionWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PEPM, PAID_AMOUNT, null);

        String memberQueryPayload = new PayloadCreator()
                .medicalCostMemberQueryPayload(payLoad, 12345)
                .toPrettyString();
        logger.info("Request sent with \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(memberQueryPayload)
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
        res.then().body("params.size()", is(1));

    }

}

package com.vh.mi.apiAutomation.test.costTrendByClaimType;

import com.vh.mi.apiAutomation.AbstractDeveloperPageAction;
import com.vh.mi.apiAutomation.groovy.PayloadCreator;
import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82718 on 3/7/2018.
 */
public class CostTrendByClaimTypeTest extends AbstractDeveloperPageAction {

    final String REQUEST_URL =
            API_WIDGET_SERVICE_ENDPOINT_URL + "/widgets/costTrendByClmType";

    static String JSON_SCHEMA_PATH = "schema/costTrendByClmType.json";
    static String token = "";

    List<String> analyticOptions = Arrays.asList(PEPM, PMPM, TOTAL_AMOUNT);

    List<String> claimTypeOptions = Arrays
            .asList(MEDICAL, PHARMACY, VISION, DENTAL);

    List<String> analyzeByOptions = Arrays.asList(ALLOWED_AMOUNT, PAID_AMOUNT);

    List<Integer> analysisPeriod = Arrays
            .asList(FULL_CYCLE, CONTRACT_YEAR, ROLLING_YEAR);

    List<Integer> reportingBy = Arrays.asList(INCURRED_DATE, PAID_DATE);

    public List<String> getAnalyticOptions() {
        return analyticOptions;
    }

    public List<String> getClaimTypeOptions() {
        return claimTypeOptions;
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
        getAnalyticOptions()
                .forEach(analyticOptions -> combmineAnalyzeBy(analyticOptions));

    }

    private void combmineAnalyzeBy(String analyticOptions) {
        getAnalyzeByOptions().forEach(
                analyzeByOptions -> combineAnalyzeByAndAnalyticOptions(
                        analyticOptions, analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptions(String analyticOptions,
            String analyzeByOptions) {
        getClaimTypeOptions().forEach(
                claimTypeOptions -> combineAnalyzeByAndAnalyticOptionsAndClaimTypeOptions(
                        claimTypeOptions, analyticOptions, analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndClaimTypeOptions(
            String claimTypeOptions, String analyticOptions,
            String analyzeByOptions) {
        getAnalysisPeriod().forEach(
                analysisPeriod -> combineAnalyzeByAndAnalyticOptionsAndClaimTypeOptionsAndAnalysisPeriod(
                        analysisPeriod, claimTypeOptions, analyticOptions,
                        analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndClaimTypeOptionsAndAnalysisPeriod(
            Integer analysisPeriod, String claimTypeOptions,
            String analyticOptions, String analyzeByOptions) {
        getReportingBy().forEach(
                reportingBy -> combineAnalyzeByAndAnalyticOptionsAndClaimTypeOptionsAndAnalysisPeriodAndReportingBy(
                        reportingBy, analysisPeriod, claimTypeOptions,
                        analyticOptions, analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndClaimTypeOptionsAndAnalysisPeriodAndReportingBy(
            Integer reportingBy, Integer analysisPeriod,
            String claimTypeOptions, String analyticOptions,
            String analyzeByOptions) {
        testFilterOptionsCombinations(analysisPeriod, reportingBy,
                analyticOptions, claimTypeOptions, analyzeByOptions);
    }

    public void testFilterOptionsCombinations(int analysisPeriod,
            int reportingBy,
            String analyticOptions, String claimType, String analyzeBy) {
        System.out.println(
                "Test params " + analysisPeriod + " " + reportingBy + " "
                        + analyticOptions + " " + analyzeBy + " " + claimType);
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .costTrendByClaimTypeWidgetPayLoad(blParam, analysisPeriod,
                        reportingBy, analyticOptions, claimType, analyzeBy,
                        null);
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
                .costTrendByClaimTypeWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PEPM, MEDICAL, PAID_AMOUNT, null);
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
    public void vefifyWidgetCallWithInCorrectLvlValues() {
        String[][] blParam = { { "wrong" }, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .costTrendByClaimTypeWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PEPM, MEDICAL, PAID_AMOUNT, null);
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
        res.then().body("data.size()", is(0));
    }

    @Test
    public void vefifyWidgetCallWithMixLvlValues() {
        String[][] blParam = { { "ABC" }, { "wrong" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .costTrendByClaimTypeWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PEPM, MEDICAL, PAID_AMOUNT, null);
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
        res.then().body("data.size()", is(0));
    }

    @Test
    public void verifyWidgetCallWithEmptyLvlValues() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .costTrendByClaimTypeWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PEPM, MEDICAL, PAID_AMOUNT, null);
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
                .costTrendByClaimTypeWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PEPM, MEDICAL, PAID_AMOUNT,
                        DYNAMIC_COHORTID);
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
    public void verifyWidgetCallWithStaticCohort() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .costTrendByClaimTypeWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PEPM, MEDICAL, PAID_AMOUNT,
                        STATIC_COHORTID);
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
    public void verifyWidgetCallWithInvalidCohort() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .costTrendByClaimTypeWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PEPM, MEDICAL, PAID_AMOUNT,
                        INVALID_COHORTID);
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

}

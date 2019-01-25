package com.vh.mi.apiAutomation.test.erVisitByFrequency;

import com.vh.mi.apiAutomation.AbstractDeveloperPageAction;
import com.vh.mi.apiAutomation.groovy.PayloadCreator;
import com.vh.mi.apiAutomation.util.Utility;
import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import groovy.util.Eval;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;


/**
 * Created by i80690 on 4/5/2018.
 */
public class ErVisitByFrequencyWidgetTest extends AbstractDeveloperPageAction {
    final String REQUEST_URL = API_ER_SERVICE_ENDPOINT_URL + "/ervisit/erVisitDistribution";
    static String JSON_SCHEMA_PATH = "schema/erVisitByFrequency.json";
    static String token = "";
    static String dataURL = "/api/ervisit/erVisitDistribution";
    static String memberQueryFileName = "erVisitByFrequencyWidgetMemberQuery.sql";


    List<String> analyticOptions = Arrays.asList(MEMPER_1000, NO_OF_MEMBERS, ERPER_1000, NO_OF_VISITS, PEPM, PMPM, AVGCOSTPEREVENT);
    List<String> analyzeByOptions = Arrays.asList(ALLOWED_AMOUNT, PAID_AMOUNT, "NA");
    List<Integer> analysisPeriod = Arrays.asList(FULL_CYCLE, CONTRACT_YEAR, ROLLING_YEAR);
    List<Integer> reportingBy = Arrays.asList(INCURRED_DATE, PAID_DATE);


    public List<String> getAnalyticOptions() {
        return analyticOptions;
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


    @BeforeClass(alwaysRun = true)
    public void setup() {
        token = getToken("miautomation_super_user");
    }

    @Test
    public void testAllCombination() {
        getAnalyticOptions().forEach(analyticOptions -> combmineAnalyzeBy(analyticOptions));
    }

    private void combmineAnalyzeBy(String analyticOptions) {
        getAnalyzeByOptions().forEach(
                analyzeByOptions -> combineAnalyzeByAndAnalyticOptions(
                        analyticOptions, analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptions(String analyticOptions,
                                                    String analyzeByOptions) {
        getAnalysisPeriod().forEach(
                analysisPeriod -> combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriod(
                        analysisPeriod, analyticOptions, analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriod(Integer analysisPeriod, String analyticOptions,
                                                                     String analyzeByOptions) {
        getReportingBy().forEach(
                reportingBy -> combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriodAndReportingBy(
                        reportingBy, analysisPeriod, analyticOptions, analyzeByOptions
                )
        );
    }

    private void combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriodAndReportingBy(Integer reportingBy,
                                                                                   Integer analysisPeriod, String analyticOptions, String analyzeByOptions) {
        if (!(analyzeByOptions.equals("NA") && (analyticOptions.equals(PEPM) || analyticOptions.equals(PMPM) || analyticOptions.equals(AVGCOSTPEREVENT)))) {
            testFilterOptionsCombinations(analysisPeriod, reportingBy,
                    analyticOptions, analyzeByOptions);
        }

    }

    public void testFilterOptionsCombinations(int analysisPeriod,
                                              int reportingBy,
                                              String analyticOptions, String analyzeBy) {
        logger.info("Test params " + analysisPeriod + " " + reportingBy + " " + analyticOptions + " " + analyzeBy);
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitbyFrequencyWidgetPayLoad(blParam, analysisPeriod,
                        reportingBy, analyticOptions, analyzeBy, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/bucketed");
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValues() {
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {"Family Practice", "General Practice"}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().erVisitbyFrequencyWidgetPayLoad(blParam, FULL_CYCLE, INCURRED_DATE, PEPM, ALLOWED_AMOUNT, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/bucketed");

        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));

        Assert.assertEquals(res.getStatusCode(), 200);

    }

    @Test
    public void vefifyWidgetCallWithInCorrectLvlValues() {
        String[][] blParam = {{"wrong"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().erVisitbyFrequencyWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE, PEPM, ALLOWED_AMOUNT, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/bucketed");

        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));

        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body("data.size()", is(2));
    }

    @Test
    public void vefifyWidgetCallWithMixLvlValues() {
        String[][] blParam = {{"ABC"}, {"wrong"}, {"BERGE, ERLAND MD"}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().erVisitbyFrequencyWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/bucketed");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body("data.size()", is(2));
    }

    @Test
    public void verifyWidgetCallWithEmptyLvlValues() {
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().erVisitbyFrequencyWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE, PMPM, PAID_AMOUNT, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/bucketed");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButInvalidAP() {
        String[][] blParam = {{}, {}, {"BERGE, ERLAND MD"}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().erVisitbyFrequencyWidgetPayLoad(blParam, INVALID_BUT_EXISTS, PAID_DATE, PMPM, ALLOWED_AMOUNT, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/bucketed");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 400, jsonString);
        res.then().body("errors[0].title", is("Unexpected analysis periodCUSTOM"));
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButWrongAP() {
        String[][] blParam = {{}, {}, {"BERGE, ERLAND MD"}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().erVisitbyFrequencyWidgetPayLoad(blParam, WRONG_AP, PAID_DATE, PMPM, ALLOWED_AMOUNT, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/bucketed");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 500, jsonString);
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithUnauthorizedAppId() {
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().erVisitbyFrequencyWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT, null);
        Eval.xy(payLoad, "982-001", "x.content." + "domainParams.appId" + "=y");
        System.out.println(payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/bucketed");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 403, jsonString);
        res.then().body("errors[0].code", is("unauthorized-appid"));
    }

    @Test
    public void verifyAllowedAvgCostPerEventCallWithCorrectLvlValuesMemberQuery() {
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator()
                .erVisitbyFrequencyWidgetPayLoad(blParam, CONTRACT_YEAR,
                        INCURRED_DATE, AVGCOSTPEREVENT, ALLOWED_AMOUNT, null);
        String memberQueryPayLoad = new PayloadCreator()
                .erVisitByFrequencyMemberQueryPayload(payLoad, dataURL,
                        "ER_VISIT_DISTRIBUTION", "Total", "Inpatient ER").toPrettyString();
        logger.info("Request Sent: \n" + memberQueryPayLoad);
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/memberQuery");

        String jsonString = res.getBody().asString();
        JsonPath jsonPath = new JsonPath(jsonString);
        String actualSql = Utility.normalizeSQL(jsonPath.get("query"));
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        String expectedQuery = Utility.readFileAsString(memberQueryFileName);
        Assert.assertEquals(actualSql, expectedQuery);
        res.then().body("params.size()", is(4));
    }

    @Test
    public void verifyWidgetCallWithDyanmicCohort() {
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().erVisitbyFrequencyWidgetPayLoad(blParam, CONTRACT_YEAR, INCURRED_DATE, AVGCOSTPEREVENT, ALLOWED_AMOUNT, DYNAMIC_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/bucketed");
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));

    }

    @Test
    public void verifyWidgetCallWithStaticCohort() {
        String[][] blParam = {{"ABC"}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().erVisitbyFrequencyWidgetPayLoad(blParam, CONTRACT_YEAR, INCURRED_DATE, AVGCOSTPEREVENT, ALLOWED_AMOUNT, STATIC_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/bucketed");
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithInvalidCohort() {
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().erVisitbyFrequencyWidgetPayLoad(blParam, CONTRACT_YEAR, INCURRED_DATE, AVGCOSTPEREVENT, ALLOWED_AMOUNT, INVALID_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/bucketed");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 400);
        res.then().body("errors[0].code", is("invalid-cohort"));
    }
}
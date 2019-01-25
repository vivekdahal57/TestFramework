package com.vh.mi.apiAutomation.test.demographicsMovement;

import com.vh.mi.apiAutomation.AbstractDeveloperPageAction;
import com.vh.mi.apiAutomation.groovy.PayloadCreator;
import com.vh.mi.apiAutomation.util.Utility;
import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82325 on 11/8/2017.
 */
public class DemographicsWidgetTest extends AbstractDeveloperPageAction {
    String token;
    final String REQUEST_URL = API_WIDGET_SERVICE_ENDPOINT_URL + "/widgets/demMovement";
    static String JSON_SCHEMA_PATH = "schema/newDemographicsMovement.json";
    static String dataURL = "/api/widgets/demMovement";
    static String memberQueryFileName = "demographicsMovementWidgetMemberQuery.sql";

    List<String> analyticOptions = Arrays.asList(AVG_RISK_SCORE, PREDICTED_COST_PMPY);

    List<Integer> analysisPeriod = Arrays.asList(FULL_CYCLE, CONTRACT_YEAR, ROLLING_YEAR);

    List<Integer> reportingBy = Arrays.asList(INCURRED_DATE, PAID_DATE);

    public List<String> getAnalyticOptions() {
        return analyticOptions;
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
    public void testAllCombinations() {
        getAnalyticOptions()
                .forEach(analyticOptions -> combmineAnalyzeBy(analyticOptions));

    }

    private void combmineAnalyzeBy(String analyticOptions) {
        getAnalysisPeriod().forEach(
                analysisPeriod -> combineAnalyticOptionsAndAnalysisPeriod(
                        analyticOptions, analysisPeriod));

    }

    private void combineAnalyticOptionsAndAnalysisPeriod(String analyticOptions, Integer analysisPeriod) {
        getReportingBy().forEach(
                reportingBy -> combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriodAndReportingBy(
                        analyticOptions, analysisPeriod, reportingBy));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriodAndReportingBy(String analyticOptions, Integer analysisPeriod, Integer reportingBy) {
        testFilterOptionsCombinations(analyticOptions, analysisPeriod, reportingBy);
    }

    public void testFilterOptionsCombinations(String analyticOptions, int analysisPeriod, int reportingBy) {
        logger.info("Test params " + analysisPeriod + " " + reportingBy + " " + analyticOptions);
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad =  new PayloadCreator().demographicsWigdetPayload(blParam, analysisPeriod, reportingBy, analyticOptions, null);
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
    public void verifyWidgetCallWithCorrectLvlValuesAndIncurredDate() {
        String[][] blParam = {{"ABC", "NATIONAL MED"}, {"Blumfield IPA"}, {"Family Practice", "General Practice"}, {"BERGE, ERLAND MD"}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE, AVG_RISK_SCORE, null);
        
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAndPaidDate() {
        String[][] blParam = {{"ABC", "NATIONAL MED"}, {}, {"Family Practice", "General Practice"}, {"BERGE, ERLAND MD"}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, FULL_CYCLE, PAID_DATE, AVG_RISK_SCORE, null);
        
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithWidgetPayloadToGetMemberQuery() {
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, FULL_CYCLE, PAID_DATE, AVG_RISK_SCORE, DYNAMIC_COHORTID);
        String memberQueryPayLoad = new PayloadCreator().demographicsMovementMemberQueryPayload(payLoad, dataURL, "DEMOGRAPHICS", "All", true, 9).toPrettyString();
        
        logger.info("Request Sent: \n" + memberQueryPayLoad);
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/memberQuery");
        String jsonString = res.getBody().asString();
        JsonPath jsonPath = new JsonPath(jsonString);
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        String actualSql = Utility.normalizeSQL(jsonPath.get("query"));
        String expectedQuery = Utility.readFileAsString(memberQueryFileName);
        Assert.assertEquals(actualSql, expectedQuery);
        res.then().body("params.size()", is(0));
        List<String> queryParams = Utility.findQueryParams(jsonPath.get("query"), ":");
        Set<Object> paramsFromResponse = jsonPath.from(res.asString()).getMap("params").keySet();
        boolean flag = false;
        for (String param : queryParams) {
            for (int i = 0; i < paramsFromResponse.size(); i++) {
                if (param.replace(":", "").equals(paramsFromResponse.toArray()[i])) {
                    flag = false;
                    break;
                } else {
                    flag = true;
                }
            }
            if (flag) {
                Assert.assertEquals(flag, false, param + " not found in Response param");
                flag = false;
            }
        }
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAndContractYearAP() {
        String[][] blParam = {{"ABC", "NATIONAL MED"}, {"Blumfield IPA"}, {"Family Practice"}, {"BERGE, ERLAND MD"}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, CONTRACT_YEAR, INCURRED_DATE, AVG_RISK_SCORE, null);
        
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAndRollingYearAP() {
        String[][] blParam = {{"ABC", "NATIONAL MED"}, {"Blumfield IPA"}, {"General Practice"}, {"BERGE, ERLAND MD"}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, ROLLING_YEAR, INCURRED_DATE, AVG_RISK_SCORE, null);
        
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithInCorrectLvlValues() {
        
        String[][] blParam = {{"wrong"}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE, AVG_RISK_SCORE, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithMixedLvlValues() {
        
        String[][] blParam = {{"ABC"}, {"wrong"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE, AVG_RISK_SCORE, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithEmptyBusinessLvlValues() {
        
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE, AVG_RISK_SCORE, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAVG_RISK_SCORE() {
        String[][] blParam = {{"ABC", "NATIONAL MED"}, {"Blumfield IPA"}, {"Family Practice"}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE, AVG_RISK_SCORE, null);
        
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesDrugClassPREDICTEDCOSTPMPY() {
        String[][] blParam = {{"ABC", "NATIONAL MED"}, {"Blumfield IPA"}, {"Family Practice", "General Practice"}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, FULL_CYCLE, INCURRED_DATE, PREDICTED_COST_PMPY, null);
        
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAVGRISKSCOREPaid() {
        String[][] blParam = {{"ABC", "NATIONAL MED"}, {"Blumfield IPA"}, {"Family Practice"}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, FULL_CYCLE, PAID_DATE, AVG_RISK_SCORE, null);
        
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesDrugClassPREDICTEDCOSTPMPYPaid() {
        String[][] blParam = {{"ABC", "NATIONAL MED"}, {"Blumfield IPA"}, {"Family Practice", "General Practice"}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, CONTRACT_YEAR, PAID_DATE, PREDICTED_COST_PMPY, null);
        
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithDyanmicCohort() {
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, FULL_CYCLE, PAID_DATE, AVG_RISK_SCORE, DYNAMIC_COHORTID);
        
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithStaticCohort() {
        String[][] blParam = {{"ABC"}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, CONTRACT_YEAR, PAID_DATE, PREDICTED_COST_PMPY, STATIC_COHORTID);
        
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithInvalidCohort() {
        String[][] blParam = {{}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().demographicsWigdetPayload(blParam, ROLLING_YEAR, INCURRED_DATE, AVG_RISK_SCORE, INVALID_BUT_EXISTS);
        
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 400);
        res.then().body("errors[0].code", is("invalid-cohort"));
    }
}

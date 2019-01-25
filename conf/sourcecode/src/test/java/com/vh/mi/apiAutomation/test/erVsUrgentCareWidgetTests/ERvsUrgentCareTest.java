package com.vh.mi.apiAutomation.test.erVsUrgentCareWidgetTests;

import com.vh.mi.apiAutomation.AbstractDeveloperPageAction;
import com.vh.mi.apiAutomation.groovy.PayloadCreator;
import com.vh.mi.apiAutomation.util.Utility;
import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import groovy.util.Eval;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82718 on 3/7/2018.
 */
public class ERvsUrgentCareTest extends AbstractDeveloperPageAction {
    final String REQUEST_URL =
            API_ER_SERVICE_ENDPOINT_URL + "/ervisit/erVsUrgentCare/bucketed";
    static String JSON_SCHEMA_PATH = "schema/erVsUrgentCare.json";
    static String token = "";
    static String dataURL = "/api/ervisit/erVsUrgentCare";
    static String memberQueryFileName = "erVsUrgentCareQuery.sql";

    List<String> analyticOptions = Arrays.asList(UTILIZATION, COST);

    List<Integer> analysisPeriod = Arrays
            .asList(FULL_CYCLE, CONTRACT_YEAR, ROLLING_YEAR);

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

    private void combineAnalyticOptionsAndAnalysisPeriod(String analyticOptions,
            Integer analysisPeriod) {
        getReportingBy().forEach(
                reportingBy -> combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriodAndReportingBy(
                        analyticOptions, analysisPeriod, reportingBy));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriodAndReportingBy(
            String analyticOptions, Integer analysisPeriod,
            Integer reportingBy) {
        testFilterOptionsCombinations(analyticOptions, analysisPeriod,
                reportingBy);
    }

    public void testFilterOptionsCombinations(String analyticOptions,
            int analysisPeriod, int reportingBy) {
        logger.info("Test params " + analysisPeriod + " " + reportingBy + " "
                + analyticOptions);
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVsUrgentCareWidgetPayLoad(blParam, analysisPeriod,
                        reportingBy, analyticOptions, null);
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
        res.then().body("data.'OP ER'.size()", is(12));
        res.then().body("data.'IP ER'.size()", is(12));
        res.then().body("data.'Urgent Care'.size()", is(12));
        res.then().body("data.'Total ER'.size()", is(12));
        if (analyticOptions.equals(UTILIZATION)) {
            res.then().body("meta.normData.size()", is(3));
        } else {
            res.then().body("meta.normData.size()", is(0));
        }
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValues() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVsUrgentCareWidgetPayLoad(blParam, ROLLING_YEAR, PAID_DATE,
                        UTILIZATION, null);
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
        res.then().body("data.'OP ER'.size()", is(12));
        res.then().body("data.'IP ER'.size()", is(12));
        res.then().body("data.'Urgent Care'.size()", is(12));
        res.then().body("data.'Total ER'.size()", is(12));
        res.then().body("meta.normData.size()", is(3));
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void vefifyWidgetCallWithInCorrectLvlValues() {
        String[][] blParam = { { "wrong" }, { "Blumfield IPA" }, {}, {}, {},
                {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVsUrgentCareWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE,
                        COST, null);
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
        res.then().body("data.'OP ER'.size()", is(12));
        res.then().body("data.'IP ER'.size()", is(12));
        res.then().body("data.'Urgent Care'.size()", is(12));
        res.then().body("data.'Total ER'.size()", is(12));
        res.then().body("meta.normData.size()", is(0));
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void vefifyWidgetCallWithMixLvlValues() {
        String[][] blParam = { { "ABC" }, { "wrong" }, { "BERGE, ERLAND MD" },
                {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVsUrgentCareWidgetPayLoad(blParam, CONTRACT_YEAR, PAID_DATE,
                        COST, null);
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
        res.then().body("data.'OP ER'.size()", is(12));
        res.then().body("data.'IP ER'.size()", is(12));
        res.then().body("data.'Urgent Care'.size()", is(12));
        res.then().body("data.'Total ER'.size()", is(12));
        res.then().body("meta.normData.size()", is(0));
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void verifyWidgetCallWithEmptyLvlValues() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVsUrgentCareWidgetPayLoad(blParam, ROLLING_YEAR,
                        INCURRED_DATE, UTILIZATION, null);
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
        res.then().body("data.'OP ER'.size()", is(12));
        res.then().body("data.'IP ER'.size()", is(12));
        res.then().body("data.'Urgent Care'.size()", is(12));
        res.then().body("data.'Total ER'.size()", is(12));
        res.then().body("meta.normData.size()", is(3));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test(enabled = false)
    // not applicable as widget is taking Rolling year hardcoded
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButInvalidAP() {
        String[][] blParam = { {}, {}, { "BERGE, ERLAND MD" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVsUrgentCareWidgetPayLoad(blParam, INVALID_BUT_EXISTS,
                        PAID_DATE, COST, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 400, jsonString);
        res.then().body("errors[0].title", is("Invalid Analysis Period"));
    }

    @Test(enabled = false)
    // not applicable as widget is taking Rolling year hardcoded
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButWrongAP() {
        String[][] blParam = { {}, { "General Practice" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVsUrgentCareWidgetPayLoad(blParam, WRONG_AP, PAID_DATE, COST,
                        null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 400, jsonString);
        res.then().body("errors[0].title", is("Invalid Analysis Period"));
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithUnauthorizedAppId() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVsUrgentCareWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE,
                        UTILIZATION, null);
        Eval.xy(payLoad, "982-001", "x.content." + "domainParams.appId" + "=y");
        System.out.println(payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 403, jsonString);
        res.then().body("errors[0].code", is("unauthorized-appid"));
    }

    @Test
    public void verifyWidgetCallWithWidgetPayloadToGetMemberQuery() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, { "wrong" }, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVsUrgentCareWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE,
                        UTILIZATION, null);
        String memberQueryPayLoad = new PayloadCreator()
                .erVsUrgentCareWidgetMemberQueryPayload(payLoad, dataURL,
                        "ER_VS_URGENT_CARE", "Total ER", "201501")
                .toPrettyString();
        logger.info("Request Sent: \n" + memberQueryPayLoad);
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL.replace("/bucketed", "") + "/memberQuery");
        String jsonString = res.getBody().asString();
        JsonPath jsonPath = new JsonPath(jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        String actualSql = Utility.normalizeSQL(jsonPath.get("query"));
        String expectedQuery = Utility.readFileAsString(memberQueryFileName);
        Assert.assertEquals(actualSql, expectedQuery);
        res.then().body("params.size()", is(6));
        List<String> queryParams = Utility
                .findQueryParams(jsonPath.get("query"), ":");
        Set<Object> paramsFromResponse = jsonPath.from(res.asString())
                .getMap("params").keySet();
        boolean flag = false;
        for (String param : queryParams) {
            for (int i = 0; i < paramsFromResponse.size(); i++) {
                if (param.replace(":", "")
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
    public void verifyWidgetCallWithDyanmicCohort() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVsUrgentCareWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE,
                        COST, DYNAMIC_COHORTID);
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
        res.then().assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));

    }

    @Test
    public void verifyWidgetCallWithStaticCohort() {
        String[][] blParam = { { "ABC" }, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVsUrgentCareWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE,
                        UTILIZATION, STATIC_COHORTID);
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
        res.then().assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithInvalidCohort() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .erVsUrgentCareWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE,
                        COST, INVALID_COHORTID);
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

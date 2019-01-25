package com.vh.mi.apiAutomation.test.topConditionsForERVisits;

import com.google.common.collect.ImmutableList;
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

import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;

/**
 * Created by i10018 on 5/3/2018.
 */
public class TopConditionsForERVisitsTest extends AbstractDeveloperPageAction {
    private final String requestUrl =
            API_ER_SERVICE_ENDPOINT_URL + "/ervisit/topCondForER";
    private final String requestUrlMemberQuery =
            API_ER_SERVICE_ENDPOINT_URL + "/ervisit/topCondForER/memberQuery";
    private final String jsonSchemaACC = "schema/topConditionsForERVisit.json";
    private final String dataURL = "/ervisit/topCondForER";
    private final String memberQueryFileNameTopFive = "toptenConditionForERVisitTopFiveMemberQuery.sql";
    private final String memberQueryFileNameAll = "toptenConditionERVisitAllMemberQuery.sql";
    private String token = "";
    private String all = "ALL";
    private String topFivePercent = "TOP_FIVE_PERCENT";

    private final List<String> erBand = ImmutableList
            .of(all, "1", "2", "3", "4", "5");
    private final List<String> populationSelection = ImmutableList
            .of(all, topFivePercent);

    private final List<Integer> analysisPeriod = ImmutableList
            .of(FULL_CYCLE, CONTRACT_YEAR, ROLLING_YEAR);
    private final List<Integer> reportingBy = ImmutableList
            .of(INCURRED_DATE, PAID_DATE);

    @BeforeClass(alwaysRun = true)
    public void setup() {
        token = getToken("miautomation_super_user");
    }

    @Test
    public void testAllCombination() {
        erBand.forEach(band -> combinePopulationSelection(band));
    }

    private void combinePopulationSelection(String band) {
        populationSelection.forEach(
                population -> combinePopulationSelectionAndERband(
                        band, population));
    }

    private void combinePopulationSelectionAndERband(
            String band,
            String population) {
        analysisPeriod.forEach(
                analysisPeriod -> combinePopulationSelectionAndERbandAndAnalysisPeriod(
                        analysisPeriod, band, population));
    }

    private void combinePopulationSelectionAndERbandAndAnalysisPeriod(
            Integer analysisPeriod, String band,
            String population) {
        reportingBy.forEach(
                reportingBy -> combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriodAndReportingBy(
                        reportingBy, analysisPeriod, band,
                        population)
        );
    }

    private void combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriodAndReportingBy(
            Integer reportingBy,
            Integer analysisPeriod, String band,
            String population) {

        testFilterOptionsCombinations(analysisPeriod, reportingBy,
                band, population);
    }

    public void testFilterOptionsCombinations(int analysisPeriod,
            int reportingBy,
            String band, String population) {
        logger.info("Test params " + analysisPeriod + " " + reportingBy + " "
                + band + " " + population);
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topConditionForERVisitWidgetPayLoad(blParam, analysisPeriod,
                        reportingBy, band, population, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(requestUrl);
        String jsonStringAcc = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput
                        .prettyPrint(jsonStringAcc));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body("meta.size()", is(1));
        res.then().body(matchesJsonSchemaInClasspath(jsonSchemaACC));

    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValues() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topConditionForERVisitWidgetPayLoad(blParam, ROLLING_YEAR,
                        PAID_DATE, ALL, topFivePercent, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(requestUrl);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body(matchesJsonSchemaInClasspath(jsonSchemaACC));
        res.then().body("meta.size()", is(1));
    }

    @Test
    public void verifyWidgetCallWithInCorrectLvlValues() {
        String[][] blParam = { { "wrong" }, { "Blumfield IPA" }, {}, {}, {},
                {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topConditionForERVisitWidgetPayLoad(blParam, FULL_CYCLE,
                        PAID_DATE, ALL, ALL, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(requestUrl);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body(matchesJsonSchemaInClasspath(jsonSchemaACC));
        res.then().body("meta.size()", is(1));
    }

    @Test
    public void verifyWidgetCallWithMixLvlValues() {
        String[][] blParam = { { "ABC" }, { "wrong" }, { "BERGE, ERLAND MD" },
                {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topConditionForERVisitWidgetPayLoad(blParam, ROLLING_YEAR,
                        PAID_DATE, ALL, topFivePercent, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(requestUrl);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(jsonSchemaACC));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body("meta.size()", is(1));
    }

    @Test
    public void verifyWidgetCallWithEmptyLvlValues() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topConditionForERVisitWidgetPayLoad(blParam, ROLLING_YEAR,
                        INCURRED_DATE, ALL, topFivePercent, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(requestUrl);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body("meta.size()", is(1));
        res.then().body(matchesJsonSchemaInClasspath(jsonSchemaACC));
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithUnauthorizedAppId() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topConditionForERVisitWidgetPayLoad(blParam, FULL_CYCLE,
                        PAID_DATE, ALL, topFivePercent, null);
        Eval.xy(payLoad, "982-001", "x.content." + "domainParams.appId" + "=y");
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(requestUrl);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 403, jsonString);
        res.then().body("errors[0].code", is("unauthorized-appid"));
    }

    @Test
    public void verifyWidgetCallWithWidgetPayloadToGetMemberTopFiveQuery() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, { "wrong" }, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topConditionForERVisitWidgetPayLoad(blParam, FULL_CYCLE,
                        PAID_DATE, ALL, topFivePercent, null);
        String memberQueryPayLoad = new PayloadCreator()
                .topConditionForERVisitWidgetMemberQueryPayload(payLoad,
                        dataURL,
                        "TOP_CONDITION_ERVISIT", 200, 26).toPrettyString();
        logger.info("Request Sent: \n" + memberQueryPayLoad);
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .header("Authorization", "Bearer " + token)
                .when()
                .post(requestUrlMemberQuery);
        String jsonString = res.getBody().asString();
        JsonPath jsonPath = new JsonPath(jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        String actualSql = Utility.normalizeSQL(jsonPath.get("query"));
        String expectedQuery = Utility.readFileAsString(
                memberQueryFileNameTopFive);
        Assert.assertEquals(actualSql, expectedQuery);
        res.then().body("params.size()", is(9));
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
    public void verifyWidgetCallWithWidgetPayloadToGetMemberAllQuery() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, { "wrong" }, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topConditionForERVisitWidgetPayLoad(blParam, FULL_CYCLE,
                        PAID_DATE, ALL, ALL, null);
        String memberQueryPayLoad = new PayloadCreator()
                .topConditionForERVisitWidgetMemberQueryPayload(payLoad,
                        dataURL,
                        "TOP_CONDITION_ERVISIT", 200, 26).toPrettyString();
        logger.info("Request Sent: \n" + memberQueryPayLoad);
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .header("Authorization", "Bearer " + token)
                .when()
                .post(requestUrlMemberQuery);
        String jsonString = res.getBody().asString();
        JsonPath jsonPath = new JsonPath(jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        String actualSql = Utility.normalizeSQL(jsonPath.get("query"));
        String expectedQuery = Utility.readFileAsString(memberQueryFileNameAll);
        Assert.assertEquals(actualSql, expectedQuery);
        res.then().body("params.size()", is(13));
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
                .topConditionForERVisitWidgetPayLoad(blParam, FULL_CYCLE,
                        PAID_DATE, 1, ALL, DYNAMIC_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(requestUrl);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        res.then().body("meta.size()", is(1));
        res.then().assertThat()
                .body(matchesJsonSchemaInClasspath(jsonSchemaACC));

    }

    @Test
    public void verifyWidgetCallWithStaticCohort() {
        String[][] blParam = { { "ABC" }, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topConditionForERVisitWidgetPayLoad(blParam, FULL_CYCLE,
                        PAID_DATE, 1, topFivePercent, STATIC_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(requestUrl);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        res.then().body("meta.size()", is(1));
        res.then().assertThat()
                .body(matchesJsonSchemaInClasspath(jsonSchemaACC));
    }

    @Test
    public void verifyWidgetCallWithInvalidCohort() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topConditionForERVisitWidgetPayLoad(blParam, FULL_CYCLE,
                        PAID_DATE, 1, ALL, INVALID_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(requestUrl);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 400);
        res.then().body("errors[0].code", is("invalid-cohort"));
    }

}

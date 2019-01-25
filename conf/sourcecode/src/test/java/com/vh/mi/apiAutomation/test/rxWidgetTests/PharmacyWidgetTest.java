package com.vh.mi.apiAutomation.test.rxWidgetTests;

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

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82325 on 11/8/2017.
 */
public class PharmacyWidgetTest extends AbstractDeveloperPageAction {
    final String REQUEST_URL =
            API_WIDGET_SERVICE_ENDPOINT_URL + "/widgets/rxAnalysis";
    static String JSON_SCHEMA_PATH = "schema/newRxAnalysis.json";
    static String dataURL = "/api/widgets/rxAnalysis";
    static String token = "";
    static String memberQueryFileName = "pharmacyWidgetQuery.sql";

    @BeforeClass
    public void setup() {
        token = getToken("miautomation_super_user");
    }

    private List<String> analyticOption = ImmutableList
            .of("TOTAL_COST", "INCREASE_IN_AMOUNT", "INCREASE_IN_UTILIZATION");
    private List<String> analyzeBy = ImmutableList
            .of("PAID_AMOUNT", "ALLOWED_AMOUNT");

    private List<Integer> analysisPeriods = ImmutableList
            .of(FULL_CYCLE, ROLLING_YEAR, CONTRACT_YEAR);
    List<Integer> reportingBy = ImmutableList.of(INCURRED_DATE, PAID_DATE);

    @Test
    public void testAllCombinations() {
        analyticOption.forEach(
                analyticOpt -> combineAnalyticOptionAndAnalyzeBy(analyticOpt));
    }

    private void combineAnalyticOptionAndAnalyzeBy(String analyticOpt) {
        analyzeBy.forEach(
                analyzeby -> combineAnalyticOptionAndAnalyzeByAndReportingBy(
                        analyticOpt, analyzeby));
    }

    private void combineAnalyticOptionAndAnalyzeByAndReportingBy(
            String analyticOpt, String analyzeby) {
        reportingBy.forEach(
                reporting -> combineAnalyticOptionAndAnalyzeByAndReportingByAndAnalysisPeriod(
                        analyticOpt, analyzeby, reporting));
    }

    private void combineAnalyticOptionAndAnalyzeByAndReportingByAndAnalysisPeriod(
            String analyticOpt, String analyzeby, Integer reporting) {
        analysisPeriods.forEach(
                anaPeriod -> testAllFilterCombinations(analyticOpt, analyzeby,
                        reporting, anaPeriod));
    }

    private void testAllFilterCombinations(String analyticOpt,
            String analyzeby, Integer reporting, Integer anaPeriod) {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payload = new PayloadCreator()
                .rxWidgetPayload(blParam, anaPeriod, reporting, analyzeby,
                        analyticOpt, DYNAMIC_COHORTID);

        logger.info("Request sent with \n" + payload.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payload.toPrettyString())
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
    public void verifyWidgetCallToVerifyMemberQuery() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payload = new PayloadCreator()
                .rxWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        analyzeBy.get(1), analyticOption.get(1),
                        DYNAMIC_COHORTID);

        String memberQueryPayload = new PayloadCreator()
                .pharmacyAnalysisMemberQueryPayload(payload, 1234, "Generic")
                .toPrettyString();

        logger.info("Request sent with \n" + payload.toPrettyString());
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
        res.then().body("params.size()", is(3));

    }

}

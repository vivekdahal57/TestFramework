package com.vh.mi.apiAutomation.test.erTopTenDiagnosisGrouper;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82718 on 3/7/2018.
 */
public class ERTop10DiagnosisGrouperTest extends AbstractDeveloperPageAction {
    final String REQUEST_URL = API_ER_SERVICE_ENDPOINT_URL + "/ervisit/topTenDiagGroup";
    String JSON_SCHEMA_PATH = "schema/top10DiagGrouper.json";
    String token = "";
    String dataURL = "/api/ervisit/topTenDiagGroup";
    String memberQueryFileName = "top10DiagGroperERWidgetMemberQuery.sql";

    List<String> analyticOptions = Arrays.asList(MEMPER_1000, NO_OF_MEMBERS, ERPER_1000, NO_OF_VISITS, PMPM, PEPM);

    List<String> erVisitTypeOptions = Arrays.asList(ALL, INPATIENT, OUTPATIENT);

    List<String> analyzeByOptions = Arrays.asList(ALLOWED_AMOUNT, PAID_AMOUNT);

    List<String> erBandOptions = Arrays.asList(ALL, "1", "2", "3", "4", "5+");

    List<Integer> analysisPeriod = Arrays.asList(FULL_CYCLE, CONTRACT_YEAR, ROLLING_YEAR);

    List<Integer> reportingBy = Arrays.asList(INCURRED_DATE, PAID_DATE);

    public List<String> getAnalyticOptions() {
        return analyticOptions;
    }

    public List<String> getErVisitTypeOptions() {
        return erVisitTypeOptions;
    }

    public List<String> getErBandOptions() {
        return erBandOptions;
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
        logger.info(token);
    }

    @Test(enabled = false)
    public void testAllCombinations() {
        getAnalyticOptions()
                .forEach(analyticOptions -> combmineAnalyzeBy(analyticOptions));

    }

    private void combmineAnalyzeBy(String analyticOptions) {
        getAnalyzeByOptions().forEach(
                analyzeByOptions -> combineAnalyzeByAndAnalyticOptions(
                        analyticOptions, analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptions(String analyticOptions, String analyzeByOptions) {
        getErVisitTypeOptions().forEach(
                erVisitTypeOptions -> combineAnalyzeByAndAnalyticOptionsAndErVisitTypeOptions(
                        erVisitTypeOptions, analyticOptions, analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndErVisitTypeOptions(String erVisitTypeOptions, String analyticOptions, String analyzeByOptions) {
        getAnalysisPeriod().forEach(
                analysisPeriod -> combineAnalyzeByAndAnalyticOptionsAndErVisitTypeOptionsAndAnalysisPeriod(
                        analysisPeriod, erVisitTypeOptions, analyticOptions,
                        analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndErVisitTypeOptionsAndAnalysisPeriod(
            Integer analysisPeriod, String erVisitTypeOptions,
            String analyticOptions, String analyzeByOptions) {
        getReportingBy().forEach(
                reportingBy -> combineAnalyzeByAndAnalyticOptionsAndErVisitTypeOptionsAndAnalysisPeriodAndReportingBy(
                        reportingBy, analysisPeriod, erVisitTypeOptions, analyticOptions, analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndErVisitTypeOptionsAndAnalysisPeriodAndReportingBy(
            Integer reportingBy, Integer analysisPeriod,
            String erVisitTypeOptions, String analyticOptions,
            String analyzeByOptions) {
        getErBandOptions().forEach(
                erBandOptions -> combineAnalyzeByAndAnalyticOptionsAndErVisitTypeOptionsAndAnalysisPeriodAndReportingByAndErBand(
                        erBandOptions, reportingBy, analysisPeriod, erVisitTypeOptions, analyticOptions, analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndErVisitTypeOptionsAndAnalysisPeriodAndReportingByAndErBand(
            String erBandOptions, Integer reportingBy, Integer analysisPeriod,
            String erVisitTypeOptions, String analyticOptions,
            String analyzeByOptions) {
        testFilterOptionsCombinations(analysisPeriod, reportingBy,
                analyticOptions, erVisitTypeOptions, analyzeByOptions, erBandOptions);
    }

    public void testFilterOptionsCombinations(int analysisPeriod, int reportingBy, String analyticOptions, String erVisitTypeOptions, String analyzeBy, String erBandOptions) {
        logger.info("Test params " + analysisPeriod + " " + erBandOptions + " " + reportingBy + " " + analyticOptions + " " + analyzeBy + " " + erVisitTypeOptions);
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam, analysisPeriod,
                        reportingBy, analyticOptions, analyzeBy, erVisitTypeOptions, erBandOptions, null);
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
    public void verifyWidgetCallWithCorrectLvlValues() {
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {"Family Practice", "General Practice"}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT, INPATIENT, "1", null);
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
        String[][] blParam = {{"wrong"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT, OUTPATIENT, "2", null);
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
    public void vefifyWidgetCallWithMixLvlValues() {
        String[][] blParam = {{"ABC"}, {"wrong"}, {"BERGE, ERLAND MD"}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT, INPATIENT, "1", null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void verifyWidgetCallWithEmptyLvlValues() {
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE, MEMPER_1000, PAID_AMOUNT, ALL, "1", null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButInvalidAP() {
        String[][] blParam = {{}, {}, {"BERGE, ERLAND MD"}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam, INVALID_BUT_EXISTS, PAID_DATE, PMPM, ALLOWED_AMOUNT, INPATIENT, "1", null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 400, jsonString);
        res.then().body("errors[0].title", is("Invalid Reporting By"));
    }

    @Test
    public void verifyWidgetCallToGetAllSourceDiagnosisWithEmptyLvlValues() {
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT, INPATIENT, "1", null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        ArrayList<String> diagCodes = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL).then().statusCode(200).extract().path("data.diagcode");
        payLoad = new PayloadCreator().top10DiagnosisGrouperByErVisitWidgetWithDiagListPayLoad(blParam, FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT, INPATIENT, "1", diagCodes);
        logger.info("Request Sent With Diagnosis list: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/topFiveSrcDiagCodes");
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButWrongAP() {
        String[][] blParam = {{}, {"General Practice"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam, WRONG_AP, PAID_DATE, PMPM, ALLOWED_AMOUNT, INPATIENT, ALL, null);
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
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT, OUTPATIENT, "3", null);
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
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {"Family Practice", "General Practice"}, {"BERGE, ERLAND MD"}, {"wrong"}, {}};
        JsonBuilder payLoad = new PayloadCreator().top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE, MEMPER_1000, ALLOWED_AMOUNT, ALL, ALL, null);
        String memberQueryPayLoad = new PayloadCreator().top10DiagnosisGrouperByErVisitMemberQueryPayload(payLoad, dataURL, "TOP_DIAG_FOR_ER", "DD0022", ALL, ALL).toPrettyString();
        logger.info("Request Sent: \n" + memberQueryPayLoad);
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/memberQuery");
        String jsonString = res.getBody().asString();
        JsonPath jsonPath = new JsonPath(jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        String actualSql = Utility.normalizeSQL(jsonPath.get("query"));
        String expectedQuery = Utility.readFileAsString(memberQueryFileName);
        Assert.assertEquals(actualSql, expectedQuery);
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
    public void verifyWidgetCallWithDyanmicCohort() {
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE, MEMPER_1000, ALLOWED_AMOUNT, ALL, ALL, DYNAMIC_COHORTID);
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
        JsonBuilder payLoad = new PayloadCreator().top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE, MEMPER_1000, ALLOWED_AMOUNT, ALL, ALL, STATIC_COHORTID);
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
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam, FULL_CYCLE, PAID_DATE, MEMPER_1000, ALLOWED_AMOUNT, ALL, ALL, INVALID_COHORTID);
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

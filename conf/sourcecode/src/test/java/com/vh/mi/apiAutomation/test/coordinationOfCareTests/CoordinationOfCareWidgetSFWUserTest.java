package com.vh.mi.apiAutomation.test.coordinationOfCareTests;

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
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82325 on 11/8/2017.
 */
public class CoordinationOfCareWidgetSFWUserTest extends AbstractDeveloperPageAction {
    String ticket = "ST-3754-9dRelxBFtrXamx4fUgxL-cas";
    final String REQUEST_URL = APIGATEWAY_URL + "/widgets/coordinationOfCare";
    static String JSON_SCHEMA_PATH = "schema/coOrdinationOfCare.json";
    static String dataURL = "/api/widgets/demMovement";
    static String memberQueryFileName = "coordinationOfCareWidgetSFWUserTest.sql";

    static  String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJvYXV0aC52ZXJzY2VuZC5jb20iLCJpYXQiOjE1MzE3MzU5MzQsImV4cCI6MTUzMzIwNzE2MiwiYXVkIjoibXgtbWVtYmVyc2VydmljZS52ZXJzY2VuZC5jb20iLCJzdWIiOiJiaWtyYW0gcGFuZGV5IiwianRpIjoiZWExYzNkZjYtNGZlZS00NjBmLWIyYjMtMDBmNTM5MGRlZmRhIiwiY2xpZW50X2lkIjoibXgtbWVtYmVyc2VydmljZSIsInVzZXJfbmFtZSI6ImJpa3JhbSBwYW5kZXkiLCJ1c2VySWQiOiIxMDc1NjUiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiYXR0cmlidXRlcyI6eyJzQU1BY2NvdW50TmFtZSI6IiIsImxvZ2luTmFtZSI6ImJpcGFuZGV5IiwidXNlck5hbWUiOiJiaWtyYW0gcGFuZGV5IiwidXNlcklkIjoiMTA3NTY1IiwiZW1haWwiOiJiaWtyYW0ucGFuZGV5QHZlcnNjZW5kLmNvbSIsImFwcElkTGlzdCI6WyIwMDgtMTAxIiwiMDEzLTAwMSIsIjEyMy0xMjMiLCI5ODItMDA3Il19LCJhdXRob3JpdGllcyI6WyJGT1JVTV9BRE1JTklTVFJBVElPTiJdfQ.rUPT-oSYAa9vkvBPmqTSrDE45Si6pX0PkdujyfotbeE";

    private List<String> providerType = ImmutableList
            .of("ALL", "ALL_OTHERS", "FACILITY","PROFESSIONAL");

    private List<Integer> analysisPeriods = ImmutableList
            .of(FULL_CYCLE, ROLLING_YEAR);
    private List<Integer> reportingBy = ImmutableList.of(INCURRED_DATE, PAID_DATE);

    @Test
    public void getProviderTypeFilter() {
        providerType.forEach( providerType -> combineProviderTypeAndAnalysisPeriod(providerType) );
    }

    private void combineProviderTypeAndAnalysisPeriod(String providerType) {
        analysisPeriods.forEach(analysisPeriod -> combineProviderTypeAndAnalysisPeriodAndReportingType(providerType, analysisPeriod) );
    }

    private void combineProviderTypeAndAnalysisPeriodAndReportingType(
            String providerType, Integer analysisPeriod) {
        reportingBy.forEach(reportingBy -> testMainAndBobEndPoint(providerType, analysisPeriod, reportingBy));
    }

    private void testMainAndBobEndPoint(
            String providerType, Integer analysisPeriod, Integer reportingBy) {
        testAllCombinations(providerType, analysisPeriod, reportingBy);
        testAllCombinationsForBOB(providerType, analysisPeriod, reportingBy);
    }
    private void testAllCombinations(String providerType,
            Integer analysisPeriod, Integer reportingBy) {
        String[][] blParam = { {}, {},{},{}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .coordinationOfCareWidgetPayloadSFW(blParam, analysisPeriod,
                        reportingBy, null, providerType);
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

    private void testAllCombinationsForBOB(String providerType,
            Integer analysisPeriod, Integer reportingBy) {
        String[][] blParam = { {}, {},{},{}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .coordinationOfCareWidgetPayloadSFW(blParam, analysisPeriod,
                        reportingBy, null, providerType);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL+"/bobANP");
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);

    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValues() {
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().coordinationOfCareWidgetPayloadSFW(blParam, FULL_CYCLE, INCURRED_DATE,null, "ALL" );
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithWidgetPayloadToGetMemberQuery() {
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().coordinationOfCareWidgetPayloadSFW(blParam, CONTRACT_YEAR, INCURRED_DATE,null ,"ALL");
        String memberQueryPayLoad = new PayloadCreator().coordinationOfCareMemberQueryPayload(payLoad, dataURL, "COORDINATION_OF_CARE", 04013).toPrettyString();
        logger.info("Request Sent: \n" + memberQueryPayLoad);
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/memberQuery");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);

        JsonPath jsonPath = new JsonPath(jsonString);
        String actualSql = Utility.normalizeSQL(jsonPath.get("query"));
        String expectedQuery = Utility.readFileAsString(memberQueryFileName);
        Assert.assertEquals(actualSql, expectedQuery);
        res.then().body("params.size()", is(1));
    }

    @Test
    public void verifyWidgetCallWithEmptyBusinessLvlValuesStateLevel() {
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().coordinationOfCareWidgetPayloadSFW(blParam, FULL_CYCLE, INCURRED_DATE,null, "ALL" );
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/state/KY");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButInvalidAP() {
        String[][] blParam = {{}, {}, {"BERGE, ERLAND MD"}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().coordinationOfCareWidgetPayloadSFW(blParam, INVALID_BUT_EXISTS, INCURRED_DATE,null,"ALL" );
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 500, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButWrongAP() {
        String[][] blParam = {{}, {"General Practice"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().coordinationOfCareWidgetPayloadSFW(blParam, WRONG_AP, INCURRED_DATE,null,"ALL" );
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 500, jsonString);
    }


    @Test
    public void verifyWidgetCallWithInCorrectLvlValues() {
        String[][] blParam = {{"NATIONAL MED"}, {"Hartsburg Physicians"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().coordinationOfCareWidgetPayloadSFW(blParam, FULL_CYCLE, INCURRED_DATE,null, "ALL" );
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }


    @Test
    public void verifyWidgetCallWithWrongLvlValues() {
        String[][] blParam = {{"wrong"}, {"wrong"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().coordinationOfCareWidgetPayloadSFW(blParam, FULL_CYCLE, INCURRED_DATE,null, "ALL" );
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithInvalidAppIdMemberQuery() {
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().coordinationOfCareWidgetPayloadSFW(blParam, FULL_CYCLE, INCURRED_DATE,null, "ALL" );
        Eval.xy(payLoad, "Doesnotexists", "x.content." + "domainParams.appId" + "=y");
        String memberQueryPayLoad = new PayloadCreator().coordinationOfCareMemberQueryPayload(payLoad, dataURL, "COORDINATION_OF_CARE", 04013).toPrettyString();
        logger.info(payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/memberQuery");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 403, jsonString);
        res.then().body("errors[0].title", is("Not authorized to access this appid"));
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithUnauthorizedAppIdStateLevel() {
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().coordinationOfCareWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,null, "ALL" );
        Eval.xy(payLoad, "982-001", "x.content." + "domainParams.appId" + "=y");
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization","Bearer"+token)
                .when()
                .post(REQUEST_URL + "/state/KY");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 403, jsonString);
        res.then().body("errors[0].title", is("Not authorized to access this appid"));
    }

    @Test
    public void verifyWidgetCallWithDyanmicCohort() {
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().coordinationOfCareWidgetPayloadSFW(blParam, FULL_CYCLE, INCURRED_DATE, "53553","FACILITY");
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithStaticCohort() {
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().coordinationOfCareWidgetPayloadSFW(blParam, ROLLING_YEAR, INCURRED_DATE, "53554","ALL");
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/bobANP");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithInvalidCohort() {
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().coordinationOfCareWidgetPayloadSFW(blParam, CONTRACT_YEAR, INCURRED_DATE, INVALID_COHORTID, "ALL");
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer " + token)
                .when()
                .post(REQUEST_URL + "/state/KY");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 400);
        res.then().body("errors[0].title", is("Invalid cohort"));
    }

}

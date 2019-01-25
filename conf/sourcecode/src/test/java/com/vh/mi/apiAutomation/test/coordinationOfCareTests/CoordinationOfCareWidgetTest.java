package com.vh.mi.apiAutomation.test.coordinationOfCareTests;

import com.google.common.collect.ImmutableList;
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

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82325 on 11/8/2017.
 */
public class CoordinationOfCareWidgetTest extends AbstractDeveloperPageAction {
    String ticket;
    final String REQUEST_URL = APIGATEWAY_URL + "/widgets/coordinationOfCare";
    static String JSON_SCHEMA_PATH = "schema/coOrdinationOfCare.json";
    static String dataURL = "/api/widgets/demMovement";
    static String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJvYXV0aC52ZXJzY2VuZC5jb20iLCJpYXQiOjE1MzEzODE5NzYsImV4cCI6MTUzMjg1MzIwNSwiYXVkIjoibXgtbWVtYmVyc2VydmljZS52ZXJzY2VuZC5jb20iLCJzdWIiOiJhbXBva2hyZWwiLCJqdGkiOiJlYTFjM2RmNi00ZmVlLTQ2MGYtYjJiMy0wMGY1MzkwZGVmZGEiLCJjbGllbnRfaWQiOiJteC1tZW1iZXJzZXJ2aWNlIiwidXNlcl9uYW1lIjoiYW1wb2tocmVsIiwidXNlcklkIjoiMTI0MzE1Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0dHJpYnV0ZXMiOnsic0FNQWNjb3VudE5hbWUiOiIiLCJsb2dpbk5hbWUiOiJtdGEgc3VwZXJfdXNlcl9sb2NhbCIsInVzZXJOYW1lIjoibXRhIHN1cGVyX3VzZXJfbG9jYWwiLCJ1c2VySWQiOiIxMjQzMTUiLCJlbWFpbCI6InN5c3RlbUBkMmhhd2tleWUuY29tIiwiYXBwSWRMaXN0IjpbIjAwOC0xMDEiLCIwMTMtMDAxIiwiMTIzLTEyMyJdfSwiYXV0aG9yaXRpZXMiOlsiRk9SVU1fQURNSU5JU1RSQVRJT04iXX0.Frwkf3oEI39GetHZ6qFxODHSViNLF-H1jYCIe8TFRJQ";
    static Integer count = 0;
    static String memberQueryFileName = "coordinationOfCareMemberQuery.sql";

    private List<String> providerType = ImmutableList
            .of("ALL", "ALL_OTHERS", "FACILITY","PROFESSIONAL");

    private List<Integer> analysisPeriods = ImmutableList
            .of(FULL_CYCLE, ROLLING_YEAR);
    private List<Integer> reportingBy = ImmutableList.of(INCURRED_DATE, PAID_DATE);


    /**
     * Incurred date
     */

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

    private void testAllCombinationsForBOB(String providerType,
            Integer analysisPeriod, Integer reportingBy) {
        count++;
        String[][] blParam = { {}, {},{},{}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .coordinationOfCareWidgetPayload(blParam, analysisPeriod,
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
        System.out.println("Count "+count);

    }

    private void testAllCombinations(String providerType,
            Integer analysisPeriod, Integer reportingBy) {
        String[][] blParam = { {}, {},{},{}, {}, {} };
        count++;
        JsonBuilder payLoad = new PayloadCreator()
                .coordinationOfCareWidgetPayload(blParam, analysisPeriod,
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
        System.out.println("Count "+count);

    }

    @Test
    public void verifyWidgetCallWithWidgetPayloadToGetMemberQuery() {
        String[][] blParam = { { }, {},{},{}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .coordinationOfCareWidgetPayload(blParam, CONTRACT_YEAR,
                        INCURRED_DATE, null,"ALL");
        String memberQueryPayLoad = new PayloadCreator()
                .coordinationOfCareMemberQueryPayload(payLoad, dataURL,
                        "COORDINATION_OF_CARE", 04013).toPrettyString();
        logger.info("Request Sent: \n" + memberQueryPayLoad);
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .header("Authorization", "Bearer"+token)
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


    @Test
    public void verifyWidgetCallWithInCorrectLvlValues() {
        String[][] blParam = { { "wrong" }, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .coordinationOfCareWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, null,"ALL");
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization","Bearer"+token)
                .when()
                .post(REQUEST_URL);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.size()", is(0));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithMixedLvlValues() {
        String[][] blParam = { { "ABC" }, { "wrong" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .coordinationOfCareWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, null,"ALL");
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization","Bearer"+token)
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
    public void verifyWidgetCallWithEmptyBusinessLvlValuesStateLevel() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .coordinationOfCareWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, null, "ALL");
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization", "Bearer"+token)
                .when()
                .post(REQUEST_URL + "/state/KY");
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.size()", is(2));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithDyanmicCohort() {
        String[][] blParam = { { "ABC", "NATIONAL MED" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .coordinationOfCareWidgetPayload(blParam, FULL_CYCLE,
                        INCURRED_DATE, DYNAMIC_COHORTID,"ALL");
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization","Bearer"+token)
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
    public void verifyWidgetCallWithStaticCohort() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .coordinationOfCareWidgetPayload(blParam, ROLLING_YEAR,
                        INCURRED_DATE, STATIC_COHORTID,"ALL");
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization","Bearer"+token)
                .when()
                .post(REQUEST_URL + "/bobANP");
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithInvalidCohort() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .coordinationOfCareWidgetPayload(blParam, CONTRACT_YEAR,
                        INCURRED_DATE, INVALID_COHORTID,"FACILITY");
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .header("Authorization","Bearer"+token)
                .when()
                .post(REQUEST_URL + "/state/KY");
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 400);
        res.then().body("errors[0].title", is("Invalid cohort"));
    }

}

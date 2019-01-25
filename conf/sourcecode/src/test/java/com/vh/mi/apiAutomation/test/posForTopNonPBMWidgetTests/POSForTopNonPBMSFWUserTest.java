package com.vh.mi.apiAutomation.test.posForTopNonPBMWidgetTests;

import com.google.common.collect.ImmutableList;
import com.vh.mi.apiAutomation.AbstractDeveloperPageAction;
import com.vh.mi.apiAutomation.groovy.PayloadCreator;
import com.vh.mi.apiAutomation.util.Utility;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.pages.common.WelcomePage;
import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import groovy.util.Eval;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82325 on 5/23/2018.
 */
public class POSForTopNonPBMSFWUserTest extends
        AbstractDeveloperPageAction {

    private final String REQUEST_URL_DRUGS =
            APIGATEWAY_URL + "/claim/posDistOfNonPBMDrugs/topTenDrugs";
    private final String REQUEST_URL =
            APIGATEWAY_URL + "/claim/posDistOfNonPBMDrugs/topTenPos";
    private final String JSON_SCHEMA_PATH_DRUG = "schema/topNonPBMDrug_GetDrug.json";
    private final String JSON_SCHEMA_PATH_POS = "schema/topNonPBMDrug_GetPos.json";
    private final String dataURL = "/api/claim/posDistOfNonPBMDrugs";
    private final String memberQueryFileName = "posForTopNonPBMMemberQuerySFW.sql";

    private String ticket = "";

    private final List<String> drugType = ImmutableList
            .of(ALL, HCPCS, REVENUE_CODE);

    private final List<Integer> analysisPeriod = ImmutableList
            .of(FULL_CYCLE, CONTRACT_YEAR, ROLLING_YEAR);

    private final List<Integer> reportingBy = ImmutableList
            .of(INCURRED_DATE, PAID_DATE);

    @BeforeClass(alwaysRun = true)
    public void setup() {
        super.setUpAdmin("miautomation_group_level_sfw_user");
        getWebDriver().get(context.getAppUrl());
        IWelcomePage welcomePage = PageFactory
                .initElements(getWebDriver(), WelcomePage.class);
        welcomePage.selectFront(context.getAppId());
        getWebDriver().get(context.getProxyTicketUrl());
    }

    @Test
    public void testAllCombination() {
        drugType.forEach(
                drugType -> combineDrugType(drugType));
    }

    private void combineDrugType(String drugType) {
        analysisPeriod.forEach(
                analysisPeriod -> combmineDrugTypeAndAnalysisPeriod(
                        drugType, analysisPeriod));
    }

    private void combmineDrugTypeAndAnalysisPeriod(
            String drugType, Integer analysisPeriod) {
        reportingBy.forEach(
                reportingBy -> combmineDrugTypeAndAnalysisPeriodAndReportingBy(
                        drugType, analysisPeriod, reportingBy)
        );
    }

    private void combmineDrugTypeAndAnalysisPeriodAndReportingBy(
            String drugType, Integer analysisPeriod,
            Integer reportingBy) {

        testFilterOptionsCombinations(drugType, analysisPeriod, reportingBy);
    }

    public void testFilterOptionsCombinations(String drugType,
            Integer analysisPeriod, Integer reportingBy) {
        ticket = getTokenForRequestedURL(REQUEST_URL_DRUGS);
        logger.info("Test params " + drugType + " " + analysisPeriod + " "
                + reportingBy);
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .posForTopNonPBMWidgetPayLoad(blParam, analysisPeriod,
                        reportingBy, drugType, null, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL_DRUGS);
        String jsonStringForDrugs = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput
                        .prettyPrint(jsonStringForDrugs));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH_DRUG));
        Assert.assertEquals(res.getStatusCode(), 200);
        if (res.getBody().path("data[0].drugCode") != null) {
            int drugCode = res.getBody().path("data[0].drugCode");
            payLoad = new PayloadCreator()
                    .posForTopNonPBMWidgetPayLoad(blParam, analysisPeriod,
                            reportingBy, drugType, drugCode, null);
            ticket = getTokenForRequestedURL(REQUEST_URL);
            Response resPos = given().contentType("application/json")
                    .body(payLoad.toPrettyString())
                    .queryParam("ticket", ticket)
                    .when()
                    .post(REQUEST_URL);
            String jsonStringDrugs = resPos.getBody().asString();

            logger.info(
                    "Response Received: \n" + JsonOutput
                            .prettyPrint(jsonStringDrugs));
            Assert.assertEquals(resPos.getStatusCode(), 200);
            res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH_POS));
        }
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValues() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        ticket = getTokenForRequestedURL(REQUEST_URL_DRUGS);
        JsonBuilder payLoad = new PayloadCreator()
                .posForTopNonPBMWidgetPayLoad(blParam, ROLLING_YEAR,
                        PAID_DATE, ALL, null, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL_DRUGS);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH_DRUG));
        Assert.assertEquals(res.getStatusCode(), 200);
        if (res.getBody().path("data[0].drugCode") != null) {
            int drugCode = res.getBody().path("data[0].drugCode");
            ticket = getTokenForRequestedURL(REQUEST_URL);
            payLoad = new PayloadCreator()
                    .posForTopNonPBMWidgetPayLoad(blParam, ROLLING_YEAR,
                            PAID_DATE, All, drugCode, null);
            Response resPos = given().contentType("application/json")
                    .body(payLoad.toPrettyString())
                    .queryParam("ticket", ticket)
                    .when()
                    .post(REQUEST_URL);
            String jsonStringDrugs = resPos.getBody().asString();
            Assert.assertEquals(resPos.getStatusCode(), 200);
            res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH_POS));
            logger.info(
                    "Response Received: \n" + JsonOutput
                            .prettyPrint(jsonStringDrugs));
        }
    }

    @Test
    public void verifyWidgetCallWithInCorrectLvlValues() {
        String[][] blParam = { { "wrong" }, { "Blumfield IPA" }, {}, {}, {},
                {} };
        ticket = getTokenForRequestedURL(REQUEST_URL_DRUGS);
        JsonBuilder payLoad = new PayloadCreator()
                .posForTopNonPBMWidgetPayLoad(blParam, FULL_CYCLE,
                        PAID_DATE, ALL, null, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL_DRUGS);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body("data.size()", is(0));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH_DRUG));
    }

    @Test
    public void verifyWidgetCallWithMixLvlValues() {
        String[][] blParam = { { "ABC" }, { "wrong" }, { "BERGE, ERLAND MD" },
                {}, {}, {} };
        ticket = getTokenForRequestedURL(REQUEST_URL_DRUGS);
        JsonBuilder payLoad = new PayloadCreator()
                .posForTopNonPBMWidgetPayLoad(blParam, ROLLING_YEAR,
                        PAID_DATE, HCPCS, null, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL_DRUGS);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH_DRUG));
    }

    @Test
    public void verifyWidgetCallWithEmptyLvlValues() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        ticket = getTokenForRequestedURL(REQUEST_URL_DRUGS);
        JsonBuilder payLoad = new PayloadCreator()
                .posForTopNonPBMWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, REVENUE_CODE, null, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL_DRUGS);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH_DRUG));
        if (res.getBody().path("data[0].drugCode") != null) {
            ticket = getTokenForRequestedURL(REQUEST_URL);
            int drugCode = res.getBody().path("data[0].drugCode");
            payLoad = new PayloadCreator()
                    .posForTopNonPBMWidgetPayLoad(blParam, FULL_CYCLE,
                            INCURRED_DATE, REVENUE_CODE, drugCode, null);
            Response resPos = given().contentType("application/json")
                    .body(payLoad.toPrettyString())
                    .queryParam("ticket", ticket)
                    .when()
                    .post(REQUEST_URL);
            String jsonStringDrugs = resPos.getBody().asString();
            Assert.assertEquals(resPos.getStatusCode(), 200);
            res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH_POS));
            logger.info(
                    "Response Received: \n" + JsonOutput
                            .prettyPrint(jsonStringDrugs));
        }
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithUnauthorizedAppId() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        ticket = getTokenForRequestedURL(REQUEST_URL_DRUGS);
        JsonBuilder payLoad = new PayloadCreator()
                .posForTopNonPBMWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, All, null, null);
        Eval.xy(payLoad, "982-001", "x.content." + "domainParams.appId" + "=y");
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL_DRUGS);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 403, jsonString);
        res.then().body("errors[0].code", is("unauthorized-appid"));
    }

    @Test
    public void verifyWidgetCallWithWidgetPayloadToGetMemberAllQuery() {
        ticket = getTokenForRequestedURL(REQUEST_URL_DRUGS);
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .posForTopNonPBMWidgetPayLoad(blParam, FULL_CYCLE,
                        PAID_DATE, ALL, null, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL_DRUGS);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH_DRUG));
        if (res.getBody().path("data[0].drugCode") != null) {
            ticket = getTokenForRequestedURL(REQUEST_URL_DRUGS
                    .replace("/topTenDrugs", "/memberQuery"));
            int drugCode = res.getBody().path("data[0].drugCode");
            String[][] blParam1 = { { "ABC" }, { "Blumfield IPA" },
                    { "Family Practice", "General Practice" },
                    { "BERGE, ERLAND MD" }, { "wrong" }, {} };
            payLoad = new PayloadCreator()
                    .posForTopNonPBMWidgetPayLoad(blParam1, FULL_CYCLE,
                            PAID_DATE, ALL, drugCode, null);
            String memberQueryPayLoad = new PayloadCreator()
                    .posForTopNonPBMWidgetMemberQueryPayload(payLoad,
                            dataURL, "TOP_POS_NON_PBM_DRUGS", drugCode, 12)
                    .toPrettyString();
            logger.info("Request Sent: \n" + memberQueryPayLoad);
            Response resPos = given().contentType("application/json")
                    .body(memberQueryPayLoad)
                    .queryParam("ticket", ticket)
                    .when()
                    .post(REQUEST_URL_DRUGS
                            .replace("/topTenDrugs", "/memberQuery"));
            jsonString = resPos.getBody().asString();
            Assert.assertEquals(resPos.getStatusCode(), 200);
            JsonPath jsonPath = new JsonPath(jsonString);
            logger.info(
                    "Response Received: \n" + JsonOutput
                            .prettyPrint(jsonString));
            String actualSql = Utility.normalizeSQL(jsonPath.get("query"));
            String expectedQuery = Utility
                    .readFileAsString(memberQueryFileName);
            Assert.assertEquals(actualSql, expectedQuery);
            resPos.then().body("params.size()", is(8));
            List<String> queryParams = Utility
                    .findQueryParams(jsonPath.get("query"), ":");
            Set<Object> paramsFromResponse = jsonPath.from(resPos.asString())
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
    }

    @Test
    public void verifyWidgetCallWithDyanmicCohort() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        ticket = getTokenForRequestedURL(REQUEST_URL_DRUGS);
        JsonBuilder payLoad = new PayloadCreator()
                .posForTopNonPBMWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, HCPCS, null, SFW_DYNAMIC_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL_DRUGS);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);

        if (res.getBody().path("data[0].drugCode") != null) {
            int drugCode = res.getBody().path("data[0].drugCode");
            ticket = getTokenForRequestedURL(REQUEST_URL);
            payLoad = new PayloadCreator()
                    .posForTopNonPBMWidgetPayLoad(blParam, FULL_CYCLE,
                            INCURRED_DATE, HCPCS, drugCode,
                            SFW_DYNAMIC_COHORTID);
            logger.info("Request Sent: \n" + payLoad.toPrettyString());
            Response resPos = given().contentType("application/json")
                    .body(payLoad.toPrettyString())
                    .queryParam("ticket", ticket)
                    .when()
                    .post(REQUEST_URL);
            String jsonStringDrugs = resPos.getBody().asString();
            logger.info(
                    "Response Received: \n" + JsonOutput
                            .prettyPrint(jsonStringDrugs));
            Assert.assertEquals(resPos.getStatusCode(), 200);
            res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH_POS));
        }
    }

    @Test
    public void verifyWidgetCallWithStaticCohort() {
        String[][] blParam = { { "ABC" }, {}, {}, {}, {}, {} };
        ticket = getTokenForRequestedURL(REQUEST_URL_DRUGS);
        JsonBuilder payLoad = new PayloadCreator()
                .posForTopNonPBMWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, All, null, SFW_STATIC_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL_DRUGS);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH_DRUG));
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        if (res.getBody().path("data[0].drugCode") != null) {
            ticket = getTokenForRequestedURL(REQUEST_URL);
            int drugCode = res.getBody().path("data[0].drugCode");
            payLoad = new PayloadCreator()
                    .posForTopNonPBMWidgetPayLoad(blParam, FULL_CYCLE,
                            INCURRED_DATE, All, drugCode, SFW_STATIC_COHORTID);
            Response resPos = given().contentType("application/json")
                    .body(payLoad.toPrettyString())
                    .queryParam("ticket", ticket)
                    .when()
                    .post(REQUEST_URL);
            String jsonStringDrugs = resPos.getBody().asString();
            Assert.assertEquals(resPos.getStatusCode(), 200);
            res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH_POS));
            logger.info(
                    "Response Received: \n" + JsonOutput
                            .prettyPrint(jsonStringDrugs));
        }
    }

    @Test
    public void verifyWidgetCallWithInvalidCohort() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        ticket = getTokenForRequestedURL(REQUEST_URL_DRUGS);
        JsonBuilder payLoad = new PayloadCreator()
                .posForTopNonPBMWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, All, null, INVALID_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .queryParam("ticket", ticket)
                .when()
                .post(REQUEST_URL_DRUGS);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 400);
        res.then().body("errors[0].code", is("invalid-cohort"));
    }
}

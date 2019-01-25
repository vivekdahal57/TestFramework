package com.vh.mi.apiAutomation.test.topTenDrugs;

import com.vh.mi.apiAutomation.AbstractDeveloperPageAction;
import com.vh.mi.apiAutomation.groovy.PayloadCreator;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.pages.common.WelcomePage;
import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import groovy.util.Eval;
import io.restassured.response.Response;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82718 on 3/6/2018.
 */
public class TopTenDrugsSFWUserTest extends AbstractDeveloperPageAction {
    String ticket = "ST-3754-9dRelxBFtrXamx4fUgxL-cas";
    final String REQUEST_URL = APIGATEWAY_URL + "/widgets/topTenDrugs";
    static String JSON_SCHEMA_PATH_POS = "schema/topTenDrugsPOS.json";

    static String JSON_SCHEMA_PATH = "schema/topTenDrugs.json";
    static String dataURL = "/api/widgets/topTenDrugs";

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
    public void verifyWidgetCallWithCorrectLvlValuesAndIncurredDateAndFullCycleAP() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topTenDrugsWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        PEPM, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.topTenDrugs.size()", is(11));
        res.then().body("data.totalNonPbmDrugs.size()", is(3));
    }

    @Test
    public void verifyWidgetCallWithCorrectLvlValuesAndPaidDateAndContractYearAP() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topTenDrugsWidgetPayload(blParam, FULL_CYCLE, CONTRACT_YEAR,
                        PEPM, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.topTenDrugs.size()", is(11));
        res.then().body("data.totalNonPbmDrugs.size()", is(3));
    }

    //TODO check member service implementation for this case
    @Test
    public void verifyWidgetCallWithInCorrectLvlValues() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { { "wrong" }, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topTenDrugsWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        PEPM, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.topTenDrugs.size()", is(0));
        res.then().body("data.totalNonPbmDrugs.size()", is(0));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    //TODO check member service implementation for this case
    @Test
    public void verifyWidgetCallWithMixedLvlValues() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { { "ABC" }, { "wrong" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topTenDrugsWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        PEPM, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.topTenDrugs.size()", is(0));
        res.then().body("data.totalNonPbmDrugs.size()", is(0));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithEmptyBusinessLvlValues() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topTenDrugsWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        PMPY, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        res.then().body("data.topTenDrugs.size()", is(11));
        res.then().body("data.totalNonPbmDrugs.size()", is(3));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    //TODO validate member query
    @Test
    public void verifyWidgetCallWithWidgetPayloadToGetMemberQuery() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topTenDrugsWidgetPayload(blParam, CONTRACT_YEAR, INCURRED_DATE,
                        PEPY, null);
        String memberQueryPayLoad = new PayloadCreator()
                .topTenDrugsMemberQueryPayload(payLoad, dataURL,
                        "TOP_TEN_DRUGS", 71844).toPrettyString();
        ticket = getTokenForRequestedURL(REQUEST_URL + "/memberQuery");
        logger.info("Request Sent: \n" + memberQueryPayLoad);
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .when()
                .post(REQUEST_URL + "/memberQuery" + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallForTopPlacesOfService() {
        ticket = getTokenForRequestedURL(REQUEST_URL + "/top5pos");
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        List<String> srcProcdrugCodes = Arrays
                .asList("71844", "1746", "50354", "3012", "20559", "71896",
                        "16779",
                        "42422", "16456", "57247");
        Map<String, Object> drugMap = new HashMap();
        drugMap.put("drugList", srcProcdrugCodes);
        JsonBuilder payLoad = new PayloadCreator()
                .topTenDrugsWidgetPayloadPOS(blParam, FULL_CYCLE, INCURRED_DATE,
                        PMPY, drugMap);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "/top5pos" + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH_POS));
        res.then().body("data.size()", is(2));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButInvalidAP() {
        String[][] blParam = { {}, {}, { "BERGE, ERLAND MD" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topTenDrugsWidgetPayload(blParam, INVALID_BUT_EXISTS,
                        INCURRED_DATE, PMPM, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 400, jsonString);
        res.then().body("errors[0].title", is("Invalid Analysis Period"));
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButWrongAP() {
        String[][] blParam = { {}, { "General Practice" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topTenDrugsWidgetPayload(blParam, WRONG_AP, INCURRED_DATE,
                        PMPM, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 400, jsonString);
        res.then().body("errors[0].title", is("Invalid Analysis Period"));
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithInvalidAppId() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topTenDrugsWidgetPayload(blParam, WRONG_AP, INCURRED_DATE,
                        PMPM, null);
        Eval.xy(payLoad, "Doesnotexists",
                "x.content." + "domainParams.appId" + "=y");
        logger.info(payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 403, jsonString);
        res.then().body("errors[0].code", is("unauthorized-appid"));
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithUnauthorizedAppIdMemberQuery() {
        ticket = getTokenForRequestedURL(REQUEST_URL + "/memberQuery");
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topTenDrugsWidgetPayload(blParam, WRONG_AP, INCURRED_DATE,
                        PMPM, null);
        Eval.xy(payLoad, "982-001", "x.content." + "domainParams.appId" + "=y");
        String memberQueryPayLoad = new PayloadCreator()
                .topTenDrugsMemberQueryPayload(payLoad, dataURL,
                        "TOP_TEN_DRUGS", 71844).toPrettyString();
        logger.info(payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .when()
                .post(REQUEST_URL + "/memberQuery" + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 403, jsonString);
        res.then().body("errors[0].code", is("unauthorized-appid"));
    }

    @Test
    public void verifyWidgetCallWithDyanmicCohort() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topTenDrugsWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        PMPY, SFW_DYNAMIC_COHORTID);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));

    }

    @Test
    public void verifyWidgetCallWithStaticCohort() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topTenDrugsWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        PMPY, SFW_STATIC_COHORTID);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));

    }

    @Test
    public void verifyWidgetCallWithInvalidCohort() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .topTenDrugsWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        PMPY, INVALID_COHORTID);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));

        Assert.assertEquals(res.getStatusCode(), 400);
        res.then().body("errors[0].code", is("invalid-cohort"));

    }

    @AfterClass
    public void quitDriver() {
        getWebDriver().quit();
    }
}

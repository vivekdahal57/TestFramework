package com.vh.mi.apiAutomation.test.erTopTenDiagnosisGrouper;

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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82718 on 3/7/2018.
 */
public class ERTop10DiagnosisGrouperSFWUserTest
        extends AbstractDeveloperPageAction {
    final String REQUEST_URL = APIGATEWAY_URL + "/ervisit/topTenDiagGroup";
    static String JSON_SCHEMA_PATH = "schema/top10DiagGrouper.json";
    static String ticket = "";
    static String dataURL = "/api/ervisit/topTenDiagGroup";
    static String memberQueryFileName = "top10DiagGroperERWidgetMemberQuerySFW.sql";

    @BeforeClass
    public void setup() {
        super.setUpAdmin("miautomation_group_level_sfw_user");
        getWebDriver().get(context.getAppUrl());
        IWelcomePage welcomePage = PageFactory
                .initElements(getWebDriver(), WelcomePage.class);
        welcomePage.selectFront(context.getAppId());
        getWebDriver().get(context.getProxyTicketUrl());
    }

    @Test
    public void verifyWidgetCallWithAllCorrectLvlValues() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, INCURRED_DATE, NO_OF_MEMBERS,
                        ALLOWED_AMOUNT, ALL, ALL, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        res.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesAndRollingYearAP() {
        String[][] blParam = { {}, { "Family Practice" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        ROLLING_YEAR, INCURRED_DATE, MEMPER_1000,
                        ALLOWED_AMOUNT, ALL, ALL, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
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
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButInvalidAP() {
        String[][] blParam = { {}, {}, { "BERGE, ERLAND MD" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, INVALID_BUT_EXISTS, NO_OF_MEMBERS,
                        ALLOWED_AMOUNT, ALL, ALL, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad);
        Response res = given().contentType("application/json")
                .body(payLoad)
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 500, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButWrongAP() {
        String[][] blParam = { {}, { "General Practice" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, WRONG_AP, NO_OF_MEMBERS, ALLOWED_AMOUNT,
                        ALL, "5+", null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        Assert.assertEquals(res.getStatusCode(), 400, jsonString);
        logger.info("Response Received: \n" + jsonString);
        res.then().body("errors.title[0]",
                equalTo("Invalid ReporingBy Value provided"));
    }

    @Test
    public void verifyWidgetCallWithCorrectBusinessLvlValuesAndContractYearAP() {
        String[][] blParam = { { "ABC" }, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        CONTRACT_YEAR, PAID_DATE, NO_OF_MEMBERS, ALLOWED_AMOUNT,
                        ALL, ALL, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
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
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithCorrectLvl1ButIncorrectLvl2Values() {
        String[][] blParam = { { "ABC" }, { "Capitol Group" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, INCURRED_DATE, NO_OF_MEMBERS,
                        ALLOWED_AMOUNT, ALL, ALL, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
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
        res.then().body("data.size()", is(1));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test
    public void verifyWidgetCallWithUsedToken() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, INCURRED_DATE, NO_OF_MEMBERS,
                        ALLOWED_AMOUNT, ALL, ALL, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 401, jsonString);
    }

    @Test
    public void verifyWidgetCallWithIncorrectToken() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" }, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, INCURRED_DATE, NO_OF_MEMBERS,
                        ALLOWED_AMOUNT, ALL, ALL, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + "skldjfklasdjflasjdfksja");
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 401, jsonString);
    }

    @Test
    public void verifyWidgetCallWithWrongLvlValues() {
        String[][] blParam = { { "wrong" }, { "wrong" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, INCURRED_DATE, NO_OF_MEMBERS,
                        ALLOWED_AMOUNT, ALL, ALL, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
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
        res.then().body("data.size()", is(1));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithUnauthorizedAppId() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, INCURRED_DATE, NO_OF_MEMBERS,
                        ALLOWED_AMOUNT, ALL, ALL, null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        Eval.xy(payLoad, "982-001", "x.content." + "domainParams.appId" + "=y");
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

    @Test
    public void verifyWidgetCallWithCorrectLvlValues() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        ticket = getTokenForRequestedURL(REQUEST_URL);
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, INCURRED_DATE, NO_OF_MEMBERS,
                        ALLOWED_AMOUNT, ALL, ALL, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void verifyWidgetCallWithMixLvlValues() {
        String[][] blParam = { { "ABC" }, { "wrong" }, {}, {}, {}, {} };
        ticket = getTokenForRequestedURL(REQUEST_URL);
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT, INPATIENT,
                        "1", null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void verifyWidgetCallWithEmptyLvlValues() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        ticket = getTokenForRequestedURL(REQUEST_URL);
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, PAID_DATE, NO_OF_MEMBERS, ALLOWED_AMOUNT,
                        ALL, ALL, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200);

    }

    @Test
    public void verifyWidgetCallToGetAllSourceDiagnosisWithEmptyLvlValues() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT, INPATIENT,
                        "1", null);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        ArrayList<String> diagCodes = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" + ticket).then().statusCode(200)
                .extract().path("data.diagcode");
        payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetWithDiagListPayLoad(
                        blParam, FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT,
                        INPATIENT, "1", diagCodes);
        ticket = getTokenForRequestedURL(REQUEST_URL + "/topFiveSrcDiagCodes");
        logger.info("Request Sent With Diagnosis list: \n" + payLoad
                .toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "/topFiveSrcDiagCodes" + "?ticket="
                        + ticket);
        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void verifyWidgetCallWithWidgetPayloadToGetMemberQuery() {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" },
                { "Family Practice", "General Practice" },
                { "BERGE, ERLAND MD" }, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT, ALL, ALL,
                        null);
        String memberQueryPayLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitMemberQueryPayload(payLoad,
                        dataURL, "TOP_DIAG_FOR_ER", "DD0022", ALL, ALL)
                .toPrettyString();
        ticket = getTokenForRequestedURL(REQUEST_URL + "/memberQuery");
        logger.info("Request Sent: \n" + memberQueryPayLoad);
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .when()
                .post(REQUEST_URL + "/memberQuery" + "?ticket=" + ticket);
        String jsonString = res.getBody().asString();
        JsonPath jsonPath = new JsonPath(jsonString);
        String actualSql = Utility.normalizeSQL(jsonPath.get("query"));
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200, jsonString);
        String expectedQuery = Utility.readFileAsString(memberQueryFileName);
        Assert.assertEquals(actualSql, expectedQuery);
        res.then().body("params.size()", is(8));
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
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT, ALL, ALL,
                        SFW_DYNAMIC_COHORTID);
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
        res.then().assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithStaticCohort() {
        String[][] blParam = { { "ABC" }, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT, ALL, ALL,
                        SFW_STATIC_COHORTID);
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
        res.then().assertThat()
                .body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test
    public void verifyWidgetCallWithInvalidCohort() {
        String[][] blParam = { {}, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .top10DiagnosisGrouperByErVisitWidgetPayLoad(blParam,
                        FULL_CYCLE, PAID_DATE, PMPM, ALLOWED_AMOUNT, ALL, ALL,
                        INVALID_COHORTID);
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

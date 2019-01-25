package com.vh.mi.apiAutomation.test.costTrendByClaimType;

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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82718 on 3/7/2018.
 */
public class CostTrendByClaimTypeSFWUserTest extends AbstractDeveloperPageAction {

    String ticket = "ST-3754-9dRelxBFtrXamx4fUgxL-cas";
    final String REQUEST_URL = APIGATEWAY_URL + "/widgets/costTrendByClmType";

    static String JSON_SCHEMA_PATH = "schema/costTrendByClmType.json";
    static String token = "";

    List<String> analyticOptions = Arrays.asList(PEPM, PMPM, TOTAL_AMOUNT);

    List<String> claimTypeOptions = Arrays
            .asList(MEDICAL, PHARMACY, VISION, DENTAL);

    List<String> analyzeByOptions = Arrays.asList(ALLOWED_AMOUNT, PAID_AMOUNT);

    List<Integer> analysisPeriod = Arrays
            .asList(FULL_CYCLE, CONTRACT_YEAR, ROLLING_YEAR);

    List<Integer> reportingBy = Arrays.asList(INCURRED_DATE, PAID_DATE);

    public List<String> getAnalyticOptions() {
        return analyticOptions;
    }

    public List<String> getClaimTypeOptions() {
        return claimTypeOptions;
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
        super.setUpAdmin("miautomation_group_level_sfw_user");
        getWebDriver().get(context.getAppUrl());
        IWelcomePage welcomePage = PageFactory
                .initElements(getWebDriver(), WelcomePage.class);
        welcomePage.selectFront(context.getAppId());
        getWebDriver().get(context.getProxyTicketUrl());
    }

    @Test
    public void testAllCombinations() {
        getAnalyticOptions()
                .forEach(analyticOptions -> combineAnalyzeBy(analyticOptions));

    }

    private void combineAnalyzeBy(String analyticOptions) {
        getAnalyzeByOptions().forEach(
                analyzeByOptions -> combineAnalyzeByAndAnalyticOptions(
                        analyticOptions, analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptions(String analyticOptions,
                                                    String analyzeByOptions) {
        getClaimTypeOptions().forEach(
                claimTypeOptions -> combineAnalyzeByAndAnalyticOptionsAndClaimTypeOptions(
                        claimTypeOptions, analyticOptions, analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndClaimTypeOptions(
            String claimTypeOptions, String analyticOptions,
            String analyzeByOptions) {
        getAnalysisPeriod().forEach(
                analysisPeriod -> combineAnalyzeByAndAnalyticOptionsAndClaimTypeOptionsAndAnalysisPeriod(
                        analysisPeriod, claimTypeOptions, analyticOptions,
                        analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndClaimTypeOptionsAndAnalysisPeriod(
            Integer analysisPeriod, String claimTypeOptions,
            String analyticOptions, String analyzeByOptions) {
        getReportingBy().forEach(
                reportingBy -> combineAnalyzeByAndAnalyticOptionsAndClaimTypeOptionsAndAnalysisPeriodAndReportingBy(
                        reportingBy, analysisPeriod, claimTypeOptions,
                        analyticOptions, analyzeByOptions));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndClaimTypeOptionsAndAnalysisPeriodAndReportingBy(
            Integer reportingBy, Integer analysisPeriod,
            String claimTypeOptions, String analyticOptions,
            String analyzeByOptions) {
        testFilterOptionsCombinations(analysisPeriod, reportingBy,
                analyticOptions, claimTypeOptions, analyzeByOptions);
    }

    private void testFilterOptionsCombinations(int analysisPeriod,
                                               int reportingBy,
                                               String analyticOptions, String claimType, String analyzeBy) {
        logger.info(
                "Test params " + analysisPeriod + " " + reportingBy + " "
                        + analyticOptions + " " + analyzeBy + " " + claimType);
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator()
                .costTrendByClaimTypeWidgetPayLoad(blParam, analysisPeriod,
                        reportingBy, analyticOptions, claimType, analyzeBy, null);
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
    public void verifyWidgetCallWithCorrectLvlValues() {
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {"Family Practice", "General Practice"}, {}, {}, {}};
        ticket = getTokenForRequestedURL(REQUEST_URL);
        JsonBuilder payLoad = new PayloadCreator()
                .costTrendByClaimTypeWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PEPM, MEDICAL, PAID_AMOUNT, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" +ticket);

        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));

        Assert.assertEquals(res.getStatusCode(), 200);

    }

    @Test
    public void vefifyWidgetCallWithInCorrectLvlValues() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = {{"wrong"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator()
                .costTrendByClaimTypeWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PEPM, MEDICAL, PAID_AMOUNT, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" +ticket);

        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));

        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body("data.size()", is(0));
    }

    @Test
    public void vefifyWidgetCallWithMixLvlValues() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = {{"ABC"}, {"wrong"}, {"BERGE, ERLAND MD"}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().costTrendByClaimTypeWidgetPayLoad(blParam, FULL_CYCLE, INCURRED_DATE, PEPM, MEDICAL, PAID_AMOUNT, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" +ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body("data.size()", is(0));
    }

    @Test
    public void verifyWidgetCallWithEmptyLvlValues() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().costTrendByClaimTypeWidgetPayLoad(blParam, FULL_CYCLE, INCURRED_DATE, PEPM, MEDICAL, PAID_AMOUNT, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" +ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 200);
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));
    }

    @Test(enabled = false)
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButInvalidAP() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = {{}, {}, {"BERGE, ERLAND MD"}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().costTrendByClaimTypeWidgetPayLoad(blParam, INVALID_BUT_EXISTS, INCURRED_DATE, PMPM, MEDICAL, PAID_AMOUNT,  null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" +ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 400, jsonString);
        res.then().body("errors[0].title", is("Invalid Analysis Period"));
    }

    @Test(enabled = false)
    public void verifyWidgetCallWithCorrectBusinessLvlValuesButWrongAP() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = {{}, {"General Practice"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().costTrendByClaimTypeWidgetPayLoad(blParam, WRONG_AP, INCURRED_DATE, PMPM, MEDICAL, PAID_AMOUNT, null);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" +ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 400, jsonString);
        res.then().body("errors[0].title", is("Invalid Analysis Period"));
    }

    @Test(groups = "UnauthorizedAppId")
    public void verifyWidgetCallWithUnauthorizedAppId() {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = {{"ABC"}, {"Blumfield IPA"}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator().costTrendByClaimTypeWidgetPayLoad(blParam, WRONG_AP, INCURRED_DATE, PMPM, MEDICAL, PAID_AMOUNT, null);
        Eval.xy(payLoad, "982-001", "x.content." + "domainParams.appId" + "=y");
        System.out.println(payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" +ticket);
        String jsonString = res.getBody().asString();
        logger.info("Response Received: \n" + jsonString);
        Assert.assertEquals(res.getStatusCode(), 403, jsonString);
        res.then().body("errors[0].code", is("unauthorized-appid"));
    }


    @Test
    public void verifyWidgetCallWithDynamicCohort(){
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator()
                .costTrendByClaimTypeWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PEPM, MEDICAL, PAID_AMOUNT, SFW_DYNAMIC_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" +ticket);

        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));

        Assert.assertEquals(res.getStatusCode(), 200);

    }

    @Test
    public void verifyWidgetCallWithStaticCohort(){
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator()
                .costTrendByClaimTypeWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PEPM, MEDICAL, PAID_AMOUNT, SFW_STATIC_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" +ticket);

        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        res.then().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH));

        Assert.assertEquals(res.getStatusCode(), 200);

    }


    @Test
    public void verifyWidgetCallWithInvalidCohort(){
        ticket = getTokenForRequestedURL(REQUEST_URL);
        String[][] blParam = {{}, {}, {}, {}, {}, {}};
        JsonBuilder payLoad = new PayloadCreator()
                .costTrendByClaimTypeWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PEPM, MEDICAL, PAID_AMOUNT, INVALID_COHORTID);
        logger.info("Request Sent: \n" + payLoad.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payLoad.toPrettyString())
                .when()
                .post(REQUEST_URL + "?ticket=" +ticket);

        String jsonString = res.getBody().asString();
        logger.info(
                "Response Received: \n" + JsonOutput.prettyPrint(jsonString));
        Assert.assertEquals(res.getStatusCode(), 400);
        res.then().body("errors[0].code", is("invalid-cohort"));
    }

}

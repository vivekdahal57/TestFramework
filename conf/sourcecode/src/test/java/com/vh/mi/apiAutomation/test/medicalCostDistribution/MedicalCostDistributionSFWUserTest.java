package com.vh.mi.apiAutomation.test.medicalCostDistribution;

import com.vh.mi.apiAutomation.AbstractDeveloperPageAction;
import com.vh.mi.apiAutomation.groovy.PayloadCreator;
import com.vh.mi.apiAutomation.util.Utility;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.pages.common.WelcomePage;
import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import io.restassured.path.json.JsonPath;
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

public class MedicalCostDistributionSFWUserTest
        extends AbstractDeveloperPageAction {

    String ticket = "ST-3754-9dRelxBFtrXamx4fUgxL-cas";
    final String REQUEST_URL =
            APIGATEWAY_URL + "/widgets/medicalCostDistribution";
    static String memberQueryFileName = "medicalCostSFWMemberQuery.sql";

    static String JSON_SCHEMA_PATH = "schema/medicalCostDistribution.json";

    List<String> analyzeBy = Arrays.asList(PEPM, PMPM);

    List<String> amountOptions = Arrays.asList(ALLOWED_AMOUNT, PAID_AMOUNT);

    List<Integer> analysisPeriod = Arrays
            .asList(FULL_CYCLE, CONTRACT_YEAR, ROLLING_YEAR);

    List<Integer> reportingBy = Arrays.asList(INCURRED_DATE, PAID_DATE);

    public List<String> getAnalyzeBy() {
        return analyzeBy;
    }

    public List<String> getAmountOptions() {
        return amountOptions;
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
        getAnalyzeBy()
                .forEach(analyzeByOption -> combineAnalyzeBy(analyzeByOption));

    }

    private void combineAnalyzeBy(String analyzeByOption) {
        getAmountOptions().forEach(
                amountOption -> combineAnalyzeByAndAnalyticOptions(
                        analyzeByOption, amountOption));
    }

    private void combineAnalyzeByAndAnalyticOptions(String analyzeByOptions,
            String amountOption) {
        getAnalysisPeriod().forEach(
                analysisPeriod -> combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriod(
                        analysisPeriod, analyzeByOptions,
                        amountOption));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriod(
            Integer analysisPeriod, String analyzeByOptions,
            String amountOption) {
        getReportingBy().forEach(
                reportingBy -> combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriodAndReportingBy(
                        reportingBy, analysisPeriod,
                        analyzeByOptions, amountOption));
    }

    private void combineAnalyzeByAndAnalyticOptionsAndAnalysisPeriodAndReportingBy(
            Integer reportingBy, Integer analysisPeriod,
            String analyzeByOptions,
            String amountOption) {
        testFilterOptionsCombinations(analysisPeriod, reportingBy,
                amountOption, analyzeByOptions);
    }

    public void testFilterOptionsCombinations(int analysisPeriod,
            int reportingBy,
            String amountOption, String analyzeBy) {
        ticket = getTokenForRequestedURL(REQUEST_URL);
        System.out.println(
                "Test params " + analysisPeriod + " " + reportingBy + " "
                        + amountOption + " " + analyzeBy);
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .medicalCostDistributionWidgetPayLoad(blParam, analysisPeriod,
                        reportingBy, amountOption, analyzeBy, null);
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
    public void verifyWidgetCallToGetMemberQuery() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .medicalCostDistributionWidgetPayLoad(blParam, FULL_CYCLE,
                        INCURRED_DATE, PAID_AMOUNT, PEPM, null);
        String memberQueryPayLoad = new PayloadCreator()
                .medicalCostMemberQueryPayload(payLoad, 12345)
                .toPrettyString();
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
        JsonPath jsonPath = new JsonPath(jsonString);
        String actualSql = Utility.normalizeSQL(jsonPath.get("query"));
        String expectedQuery = Utility.readFileAsString(memberQueryFileName);
        Assert.assertEquals(actualSql, expectedQuery);
        res.then().body("params.size()", is(1));

    }

}

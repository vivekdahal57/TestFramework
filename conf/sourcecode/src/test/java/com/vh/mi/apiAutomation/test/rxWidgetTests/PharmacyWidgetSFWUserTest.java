package com.vh.mi.apiAutomation.test.rxWidgetTests;

import com.google.common.collect.ImmutableList;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.Is.is;

/**
 * Created by i82325 on 11/8/2017.
 */
public class PharmacyWidgetSFWUserTest extends AbstractDeveloperPageAction {
    String ticket = "ST-3754-9dRelxBFtrXamx4fUgxL-cas";
    final String REQUEST_URL = APIGATEWAY_URL + "/widgets/rxAnalysis";
    static String JSON_SCHEMA_PATH = "schema/newRxAnalysis.json";
    static String memberQueryFileName = "pharmacyWidgetQuerySFW.sql";

    @BeforeClass(alwaysRun = true)
    public void setup() {
        super.setUpAdmin("miautomation_group_level_sfw_user");
        getWebDriver().get(context.getAppUrl());
        IWelcomePage welcomePage = PageFactory
                .initElements(getWebDriver(), WelcomePage.class);
        welcomePage.selectFront(context.getAppId());
        getWebDriver().get(context.getProxyTicketUrl());
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
        ticket = getTokenForRequestedURL(REQUEST_URL);
        JsonBuilder payload = new PayloadCreator()
                .rxWidgetPayload(blParam, anaPeriod, reporting, analyzeby,
                        analyticOpt, DYNAMIC_COHORTID);

        logger.info("Request sent with \n" + payload.toPrettyString());
        Response res = given().contentType("application/json")
                .body(payload.toPrettyString())
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
    public void verifyWidgetCallToVerifyMemberQuery() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .rxWidgetPayload(blParam, FULL_CYCLE, INCURRED_DATE,
                        analyzeBy.get(1), analyticOption.get(1), null);
        String memberQueryPayLoad = new PayloadCreator()
                .pharmacyAnalysisMemberQueryPayload(payLoad, 12345, "Branded")
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
        res.then().body("params.size()", is(3));

    }

    @AfterClass
    public void quitDriver() {
        getWebDriver().quit();
    }

}

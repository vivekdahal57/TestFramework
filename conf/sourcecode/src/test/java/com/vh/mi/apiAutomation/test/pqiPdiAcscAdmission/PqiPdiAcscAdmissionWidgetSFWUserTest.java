package com.vh.mi.apiAutomation.test.pqiPdiAcscAdmission;

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

/**
 * Created by i82718 on 5/22/2018.
 */
public class PqiPdiAcscAdmissionWidgetSFWUserTest extends
        AbstractDeveloperPageAction {

    String ticket = "ST-3754-9dRelxBFtrXamx4fUgxL-cas";
    final String REQUEST_URL =
            APIGATEWAY_URL + "/widgets/pqipdiAcscConditionAdmission";
    static String JSON_SCHEMA_PATH = "schema/pqipdiAcscConditionAdmission.json";
    static String memberQueryFileName = "pqipdiAcscConditionAdmissionSFW.sql";

    List<String> analyzeBy = Arrays.asList("COST", "UTILIZATION");
    List<String> amount = Arrays.asList("PAID_AMOUNT", "ALLOWED_AMOUNT", "NA");
    List<String> cohortId = Arrays
            .asList(SFW_DYNAMIC_COHORTID, null);
    List<Integer> analysisPeriod = Arrays
            .asList(FULL_CYCLE, CONTRACT_YEAR, ROLLING_YEAR);

    public List<Integer> getAnalysisPeriod() {
        return analysisPeriod;
    }

    public List<String> getAmount() {
        return amount;
    }

    public List<String> getCohortId() {
        return cohortId;
    }

    public List<String> getAnalyzeBy() {
        return analyzeBy;
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
        getCohortId().forEach(cohortId -> combineCohortIdAndAmount(cohortId));
    }

    private void combineCohortIdAndAmount(String cohortId) {
        getAmount().forEach(
                amount -> combineCohortIdAndAmountAndAnalyzeBy(cohortId,
                        amount));
    }

    private void combineCohortIdAndAmountAndAnalyzeBy(String cohortId,
            String amount) {
        getAnalyzeBy().forEach(
                analyzeBy -> combineCohortIdAndAmountAndAnalyzeByAndAnalysisPeriod(
                        cohortId, amount, analyzeBy));
    }

    private void combineCohortIdAndAmountAndAnalyzeByAndAnalysisPeriod(
            String cohortId, String amount, String analyzeBy) {
        getAnalysisPeriod().forEach(
                analysisPeriod -> testAllFilterOptionsCombination(cohortId,
                        amount, analyzeBy, analysisPeriod));
    }

    private void testAllFilterOptionsCombination(String cohortId, String amount,
            String analyzeBy, Integer analysisPeriod) {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .pqiPdiAcscAdmissionWidgetPayload(blParam, analysisPeriod,
                        cohortId, amount, analyzeBy);
        logger.info("Filter Options Combinations:: cohortID=" + cohortId
                + " amount = " + amount + " analyzeBy = " + analyzeBy
                + " analysisPeriod = " + analysisPeriod);
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
    public void testWidgetCallToVerifyMemberQuery() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .pqiPdiAcscAdmissionWidgetPayload(blParam, FULL_CYCLE,
                        DYNAMIC_COHORTID, PAID_AMOUNT, COST);
        ticket = getTokenForRequestedURL(REQUEST_URL + "/memberQuery");

        String memberQueryPayLoad = new PayloadCreator()
                .pqiPdiAcscAdmissionMemberQueryPayload(payLoad, "pqi1")
                .toPrettyString();
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

    }

}

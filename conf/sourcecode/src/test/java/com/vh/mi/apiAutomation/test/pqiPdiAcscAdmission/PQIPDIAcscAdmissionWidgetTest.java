package com.vh.mi.apiAutomation.test.pqiPdiAcscAdmission;

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

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * Created by i82718 on 5/22/2018.
 */
public class PQIPDIAcscAdmissionWidgetTest extends AbstractDeveloperPageAction {

    final String REQUEST_URL = API_WIDGET_SERVICE_ENDPOINT_URL
            + "/widgets/pqipdiAcscConditionAdmission";
    static String JSON_SCHEMA_PATH = "schema/pqipdiAcscConditionAdmission.json";
    static String token = "";
    static String memberQueryFileName = "pqipdiAcscConditionAdmission.sql";

    List<String> analyzeBy = Arrays.asList("COST", "UTILIZATION");
    List<String> amount = Arrays.asList("PAID_AMOUNT", "ALLOWED_AMOUNT", "NA");
    List<String> cohortId = Arrays
            .asList(DYNAMIC_COHORTID, STATIC_COHORTID, null);
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

    @BeforeClass
    public void setup() {
        token = getToken("miautomation_super_user");
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

    int count = 1;

    private void testAllFilterOptionsCombination(String cohortId, String amount,
            String analyzeBy, Integer analysisPeriod) {
        String[][] blParam = { { "ABC" }, { "Blumfield IPA" }, {}, {}, {}, {} };

        JsonBuilder payLoad = new PayloadCreator()
                .pqiPdiAcscAdmissionWidgetPayload(blParam, analysisPeriod,
                        cohortId, amount, analyzeBy);
        logger.info("Filter Options Combinations:: cohortID=" + cohortId
                + " amount = " + amount + " analyzeBy = " + analyzeBy
                + " analysisPeriod = " + analysisPeriod + " Combination Count "
                + count++);
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

    @Test
    public void testWidgetCallToVerifyMemberQuery() {
        String[][] blParam = { {}, {}, {}, {}, {}, {} };
        JsonBuilder payLoad = new PayloadCreator()
                .pqiPdiAcscAdmissionWidgetPayload(blParam, FULL_CYCLE,
                        DYNAMIC_COHORTID, PAID_AMOUNT, COST);

        String memberQueryPayLoad = new PayloadCreator()
                .pqiPdiAcscAdmissionMemberQueryPayload(payLoad, "pqi1")
                .toPrettyString();
        logger.info("Request Sent: \n" + memberQueryPayLoad);
        Response res = given().contentType("application/json")
                .body(memberQueryPayLoad)
                .header("Authorization", "Bearer " + token)
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
    }
}

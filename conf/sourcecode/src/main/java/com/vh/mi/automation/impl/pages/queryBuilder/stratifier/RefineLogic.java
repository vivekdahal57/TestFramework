package com.vh.mi.automation.impl.pages.queryBuilder.stratifier;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.IRefineLogic;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.QueryBuilderToolBar;
import com.vh.mi.automation.impl.pages.queryBuilder.Individual301;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by i20345 on 12/27/2016.
 */
public class RefineLogic extends QueryBuilderToolBar implements IRefineLogic {

    WebElements webElements;
    LoadingPopup loading;
    Actions action;
    Individual301 individual301;

    public static final String REPORT_GENERATION_SUCCESSFUL_MESSAGE = "Report ${Cohort_name} has been saved.";
    public static final String QUERY_SUMMARY="Query Summary";
    public static final String QUERY_DETAILS="Query Details";
    public RefineLogic(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
        loading = new LoadingPopup(getDriver());
        action = new Actions(getDriver());
        individual301 = new Individual301(getDriver());
    }

    public Individual301 goToIndividualPage() {
        action = new Actions(getDriver());
        action.moveToElement(webElements.queryElementHoverButton).build().perform();
        webElements.btnIndividual.click();
        SeleniumUtils.switchToNewWindow(getDriver());
        individual301.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return individual301;
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.isFullyLoadedElemet.isDisplayed();
    }

    public String getMemberCount() {
        String text = webElements.temp.getText();
        Pattern pattern = Pattern.compile("(\\d+\\,?\\d*)\\s*Members");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public String getQuerySummary() {
        List<String> list=new ArrayList<>();
        String text = webElements.temp.getText();
        String pattern = "\\w"+QUERY_SUMMARY+"(.*)"+QUERY_DETAILS;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find( )) {
            list.add( m.group(1));
        }
        return list.toString();
    }

    public void saveCohort(String cohortName) {
        SeleniumUtils.hover(getDriver(), webElements.queryElementHoverButton);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.saveCohortLink);
        SeleniumUtils.click(webElements.saveCohortLink);
        SeleniumUtils.sendKeysToInput(cohortName, webElements.inputCohortName);
        SeleniumUtils.click(webElements.saveCohortButton);
        loading.waitTillDisappears();
        WaitUtils.waitUntilDisplayed(getDriver(),webElements.afterReportGenerationMessage,TimeOuts.FIVE_SECONDS);
    }

    public void saveStaticCohort(String cohortName) {
        SeleniumUtils.hover(getDriver(), webElements.queryElementHoverButton);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.saveCohortLink);
        SeleniumUtils.click(webElements.saveCohortLink);
        SeleniumUtils.click(webElements.staticCohortRadioButton);
        SeleniumUtils.sendKeysToInput(cohortName, webElements.inputCohortName);
        SeleniumUtils.click(webElements.saveCohortButton);
        loading.waitTillDisappears();
        WaitUtils.waitUntilDisplayed(getDriver(),webElements.afterReportGenerationMessage, TimeOuts.FIVE_SECONDS);
    }

    public PopulationAnalyser goBackToPopulationAnalyser(){
        PopulationAnalyser populationAnalyser = PageFactory.initElements(getDriver(),PopulationAnalyser.class);
        return populationAnalyser;
    }

    public static String getRandomCohortName() {
        return "Stratifier_Cohort_TEST_" + Random.getRandomStringOfLength(4);
    }

    public String getReportGenerationSuccessfulMessage() {
        String message = webElements.afterReportGenerationMessage.getText();
        return message;
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"pageTitle\"]")
        private WebElement isFullyLoadedElemet;

        @FindBy(xpath = "//*[@id=\"d2Form:queryExpressionGrid\"]")
        private WebElement temp;

        //Save Cohort Button
        @FindBy(id = "d2FormMoreMetrics:saveButton")
        private WebElement saveCohortButton;

        //Text Box to input cohort name
        @FindBy(id = "d2FormMoreMetrics:reportName")
        private WebElement inputCohortName;

        //Hover over query
        @FindBy(xpath = "//*[@id=\"d2Form:query_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement queryElementHoverButton;

        //Individual button
        @FindBy(id = "d2Form:link_13")
        private WebElement btnIndividual;

        //Save cohort hover button
        @FindBy(xpath = "//div[@id='query_menu']//a[text()='Save']")
        private WebElement saveCohortLink;

        @FindBy(xpath = "//*[@id=\"d2FormMoreMetrics:StaticDynamicRadio\"]//label[text()='Static Cohort']//preceding::input[@type='radio'][1]")
        private WebElement staticCohortRadioButton;

        @FindBy(xpath = "//*[@id=\"ani_message\"]")
        private WebElement afterReportGenerationMessage;
    }
}

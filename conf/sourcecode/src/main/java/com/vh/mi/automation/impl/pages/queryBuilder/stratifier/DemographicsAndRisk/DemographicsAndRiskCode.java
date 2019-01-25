package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.DemographicsAndRisk;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.DemographicsAndRisk.IDemographicsAndRiskCode;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.CriteriaComponent;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.QueryBuilderToolBar;
import com.vh.mi.automation.impl.pages.queryBuilder.Individual301;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82716 on 4/25/2017.
 */
public class DemographicsAndRiskCode  extends QueryBuilderToolBar implements IDemographicsAndRiskCode {

        WebElements webElements;
        CriteriaComponent criteriaComponent;
        RefineLogic refineLogic;
        Actions action;
        Individual301 individual301;

        public static final String pageTitle = "(Stratifier) Query Builder - Demographics & Risk";

        public DemographicsAndRiskCode(WebDriver driver){
            super(driver);
            webElements = new WebElements(getDriver());
            refineLogic = new RefineLogic(getDriver());
            action = new Actions(getDriver());
            individual301 = new Individual301(getDriver());
        }

        @Override
        public boolean isFullyLoaded(){
            return pageTitle.equalsIgnoreCase(webElements.pageTitle.getText());
        }

        public void enterAge(Integer lowerAge, Integer upperAge) {
            SeleniumUtils.click(webElements.btnAgeBetween);
            SeleniumUtils.sendKeysToInput("" + lowerAge, webElements.ageLower);
            SeleniumUtils.sendKeysToInput("" + upperAge, webElements.ageUpper);
        }

        public RefineLogic goToRefineLogicPage(){
            SeleniumUtils.hover(getDriver(),webElements.hoverQuery);
            WaitUtils.waitUntilEnabled(getDriver(),webElements.btnRefineLogic);
            SeleniumUtils.click(webElements.btnRefineLogic);
            refineLogic.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
            return refineLogic;
        }

        public Individual301 goToIndividualPage() {
            SeleniumUtils.hover(getDriver(),webElements.hoverQuery);
            WaitUtils.waitUntilEnabled(getDriver(),webElements.btnViewIndividuals);
            SeleniumUtils.click(webElements.btnViewIndividuals);
            SeleniumUtils.switchToNewWindow(getDriver());

            individual301.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);

            return individual301;
        }

        public String getMembersAccordingToSelectedCriteria(){
            SeleniumUtils.hover(getDriver(),webElements.btnQuickCountHover);
            WaitUtils.waitUntilEnabled(getDriver(),webElements.quickCountUpdate);
            SeleniumUtils.click(webElements.quickCountUpdate);
            WaitUtils.waitForSeconds(TimeOuts.FIVE_SECONDS);

            String memberCounts = webElements.memberCountQueryBuilder.getText();
            String arr[] = memberCounts.split(" ", 2);
            String memberCountInQueryBuilder = arr[0];

            return memberCountInQueryBuilder;

        }

        public void selectGenderAndMemberStatus(){
            SeleniumUtils.click(webElements.radioBtnMaleGender);
            SeleniumUtils.click(webElements.chkBoxMemberStatusCurrent);
        }

        public void enterRiskScores(Integer lowerValue, Integer UpperValue){
            SeleniumUtils.click(webElements.radioBtnRIBetween);
            SeleniumUtils.sendKeysToInput("" + lowerValue, webElements.riLower);
            SeleniumUtils.sendKeysToInput(""+ UpperValue, webElements.riUpper);
        }

    @Override
    public String getPageLink() {
        return "Demographics & Risk";
    }

    private static class WebElements{
            public WebElements(WebDriver webDriver){
                PageFactory.initElements(webDriver,this);};

            @FindBy(id="pageTitle")
            private WebElement pageTitle;

            @FindBy(xpath="//div[@id='query_menu']//a[text()='Refine Logic']")
            private WebElement btnRefineLogic;

            @FindBy(xpath = "//div[@class='dr-pnl-h rich-panel-header' and text()='Query']//following::div[1]")
            private WebElement hoverQuery;

            @FindBy(xpath="//*[@id=\"d2Form:demographyGrid\"]//td[text()='Age between ']//preceding::td[1]/input[@type='radio']")
            private WebElement btnAgeBetween;

            @FindBy(id="d2Form:demographyGrid:0:age:0:ageLower")
            private WebElement ageLower;

            @FindBy(id="d2Form:demographyGrid:0:age:0:ageUpper")
            private WebElement ageUpper;

            @FindBy(xpath = "//div[@id='query_menu']//a[text()='View Individuals']")
            private WebElement btnViewIndividuals;

            @FindBy(xpath = "//*[@id=\"d2Form:memcount_header\"]/table/tbody/tr/td/div/div[2]\n")
            private WebElement btnQuickCountHover;

            @FindBy(xpath = "//*[@id=\"memcount_menu\"]//a[.=\"Update\"]\n")
            private  WebElement quickCountUpdate;

            @FindBy(xpath = "//*[@id=\"d2Form:memcount_body\"]\n")
            private WebElement memberCountQueryBuilder;

            @FindBy(xpath = "//*[@id=\"d2Form:demographyGrid:0:gender:0:genderOption:0\"]")
            private WebElement radioBtnMaleGender;

            @FindBy(xpath = "//*[@id=\"d2Form:demographyGrid:0:memberStatus:0:memberStatusCurrent\"]")
            private WebElement chkBoxMemberStatusCurrent;

            @FindBy(xpath = "//*[@id=\"d2Form:riskScoresGrid:0:ri:0:riOption_0:0\"]")
            private WebElement radioBtnRIBetween;

            @FindBy(xpath = "//*[@id=\"d2Form:riskScoresGrid:0:ri:0:riLower\"]")
            private WebElement riLower;

            @FindBy(xpath = "//*[@id=\"d2Form:riskScoresGrid:0:ri:0:riUpper\"]")
            private WebElement riUpper;

        }
    }



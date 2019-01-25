package com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.queryBuilder.claimsSearch.IRefineLogic;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.QueryBuilderToolBar;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.queryManagement.QueryManagement;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by i81306 on 5/3/2017.
 */
public class RefineLogic extends QueryBuilderToolBar implements IRefineLogic{
    private static final String FIELD_NAME_PLACEHOLDER = "${fieldName}";
    private static final String DEFAULT_QUERY_NAME = "CLAIMS_SEARCH_EXTRACT_TEST";
    public static final String EXTRACT_GENERATION_POPUP_MESSAGE = "Query " + DEFAULT_QUERY_NAME + " has been saved.";

    private final WebElements webElements;
    private final LoadingPopup loadingPopup;

    public RefineLogic(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
        loadingPopup = new LoadingPopup(driver);
    }

    public void selectExtraOptions(){
       SeleniumUtils.click(webElements.extraOptionsLink);
        WaitUtils.waitUntilDisplayed(getDriver(), webElements.extraOptionsLink, TimeOuts.FIVE_SECONDS);
    }

    public void includeAllFieldsFor(String fieldName){
        expandCollapseFieldsFor(fieldName);
        waitForAjaxCallToFinish();
        for (WebElement includeCheckBox : getAllCheckboxForIncludeColumn()) {
            SeleniumUtils.click(includeCheckBox);
        }
        expandCollapseFieldsFor(fieldName);
        waitForAjaxCallToFinish();
    }

    public void changeExpressionToOR(){
        String currentExpression = webElements.expressionTextBox.getAttribute("value");
        String newExpression = currentExpression.replace("and","or");
        SeleniumUtils.sendKeysToInput(newExpression, webElements.expressionTextBox);
        SeleniumUtils.click(webElements.expressionUpdateButton);
        loadingPopup.waitTillDisappears();
    }

    public void applyExtraOptions(){
        SeleniumUtils.click(webElements.applyExtraOptionsButton);
    }

    public QueryManagement saveQueryWithDefaultOptionsWithName(String queryName){
        return getQueryComponent().saveQueryWithDefaultOptionsWithName(queryName);
    }

    public String getSuccessfulExtractGenerationMessage(){
        return EXTRACT_GENERATION_POPUP_MESSAGE;
    }

    public String getTextFromExtractGenerationPopupMessage() {
        return webElements.afterExtractGenerationMessage.getText();
    }

    private List<WebElement> getAllCheckboxForIncludeColumn(){
        String xpath = "//tbody[@id='d2FormExtractOptions:simpleGrid:tb']//tr//td[3]//input[@type='checkbox']";
        return SeleniumUtils.findElementsByXPath(getDriver(),xpath);
    }

    private void expandCollapseFieldsFor(String fieldName){
        String xpath = "//div[@id='extractOptionsPanelCDiv']//span[text()='${fieldName}']//preceding::a[1]";
        WebElement typeExpandLink = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(FIELD_NAME_PLACEHOLDER, fieldName));
        SeleniumUtils.click(typeExpandLink);
    }

    private void waitForAjaxCallToFinish() {
        WaitUtils.waitForMilliSeconds(100);
        loadingPopup.waitTillDisappears();
        checkState(!loadingPopup.isDisplayed(), "The popup should have disappeared");
    }


    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath="//a[text()='Please select extract options']")
        private WebElement extraOptionsLink;

        @FindBy(xpath="//div[@id='extractOptionsPanelCDiv']")
        private WebElement extraOptionPanelDiv;

        @FindBy(xpath="//*[@id='d2FormExtractOptions']/table/tbody/tr/td/input[1]")
        private WebElement applyExtraOptionsButton;

        @FindBy(xpath = "//*[@id='ani_message']")
        WebElement afterExtractGenerationMessage;

        @FindBy(xpath = "//input[@class='d2-stratifier-queryInput']")
        WebElement expressionTextBox;

        @FindBy(xpath = "//input[@value='Update']")
        WebElement expressionUpdateButton;

    }


}

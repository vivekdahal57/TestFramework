package com.vh.mi.automation.impl.pages.queryBuilder.mvca.expert;

import com.vh.mi.automation.api.comp.IMemberList;
import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.queryBuilder.mvca.expert.IMVCAExpert301E;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.MemberList;
import com.vh.mi.automation.impl.comp.ReportingBy;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;

/**
 * Created by i80448 on 9/17/2014.
 */
public class MVCAExpert301E extends AbstractLandingPage implements IMVCAExpert301E {
    public static final String MODULE_ID = "82";
    private IAnalysisPeriod analysisPeriod;
    private IBusinessLevelsComponent businessLevelsComponent;
    private IReportingBy reportingBy;
    private IMemberList memberList;
    private EMVCADatagrid emvcaDatagrid;
    LoadingPopup loadingPopup;
    public static final String LOADING_POPUP_ID = "_LoadingQryBuilder";
    private static final String NTH_CHECKBOX_PLACEHOLDER = "${nthCheckbox}";
    private static final String GROUP_PLACEHOLDER = "${group}";

    WebElements webElements;

    public MVCAExpert301E(WebDriver driver) {
        super(driver, MODULE_ID);
        webElements = new WebElements(driver);
        analysisPeriod = new AnalysisPeriod(getDriver());
        businessLevelsComponent = new BusinessLevelsComponent(getDriver());
        reportingBy = PageFactory.initElements(getDriver(), ReportingBy.class);
        memberList = new MemberList(getDriver());
        loadingPopup = new LoadingPopup(getDriver());
        emvcaDatagrid = new EMVCADatagrid(driver);
    }

    public void includeTopNGroups(int topN) {
        for (int i = 1; i <= topN; i++){
            selectNthCheckbox(String.valueOf(i));
        }
    }

    private void selectNthCheckbox(String nthCheckbox){
        String xpath = "(//*[@id='dataTable']//input[@type='checkbox' and @class='categoryCodeIn'])[${nthCheckbox}]";
        WebElement gruopIncludeCheckbox = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(NTH_CHECKBOX_PLACEHOLDER, nthCheckbox));
        SeleniumUtils.click(gruopIncludeCheckbox);
    }

    @Override
    public void applyMinimumQueryCriteriaRequired(IMVCAExpert301E.Group group){
        SeleniumUtils.hover(getDriver(), webElements.queryCriteriaHoverBtn);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.createNewQueryLink);
        SeleniumUtils.click(webElements.createNewQueryLink);
        new LoadingPopup(getDriver(),LOADING_POPUP_ID).waitTillDisappears();
        selectGroup(group);
        includeTopNGroups(1);
        SeleniumUtils.click(webElements.popupApplyButton);
        new LoadingPopup(getDriver(),LOADING_POPUP_ID).waitTillDisappears();
        SeleniumUtils.click(webElements.popupCloseButton);
        WaitUtils.waitForSeconds(TimeOuts.TEN_SECONDS);
    }

    private void selectGroup(IMVCAExpert301E.Group group) {
        if(!webElements.groupBodyElem.getText().equalsIgnoreCase(group.getValue())) {
            SeleniumUtils.hover(getDriver(), webElements.groupHoverBtn);
            WaitUtils.waitUntilEnabled(getDriver(), webElements.groupHoverBtn);
            String xpath = "//*[@id='groupDropDown_menu']//a[text()='${group}']";
            WebElement groupSelectionXpath = SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(GROUP_PLACEHOLDER, group.getValue()));
            SeleniumUtils.click(groupSelectionXpath);
            new LoadingPopup(getDriver(),LOADING_POPUP_ID).waitTillDisappears();
        }
    }


    @Override
    public void saveQuery(String queryName){
        SeleniumUtils.hover(getDriver(), webElements.queryMenuHoverBtn);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.saveQueryLink);
        SeleniumUtils.click(webElements.saveQueryLink);
        WaitUtils.waitUntilDisplayed(getDriver(), webElements.criteriaNameInputField, TimeOuts.TEN_SECONDS);
        SeleniumUtils.sendKeysToInput(queryName, webElements.criteriaNameInputField);
        SeleniumUtils.click(webElements.popupSaveButton);
        loadingPopup.waitTillDisappears();
        WaitUtils.waitForSeconds(1);
    }

    @Override
    public String getReportOperationSuccessfulMessage() {
        String message = webElements.afterReportGenerationMessage.getText();
        WaitUtils.waitUntilDisappear(getDriver(), webElements.afterReportGenerationMessage, TimeOuts.TEN_SECONDS);
        return message;
    }

    @Override
    public void createStaticMemberlistWithTopNMembers(String memberListName, int topN){
        emvcaDatagrid.selectTopNCheckBoxInDataGrid(topN);
        memberList.createStaticMemberList(memberListName);
    }

    @Override
    public void deleteQueryWithName(String queryName){
        selectQueryWithName(queryName);
        clickDeleteQueryLink();
        loadingPopup.waitTillDisappears();
        WaitUtils.waitForSeconds(1);
    }

    @Override
    public void selectQueryWithName(String savedQueryName){
        SeleniumUtils.hover(getDriver(), webElements.queryCriteriaHoverBtn);
        WebElement savedQueryLink = getSavedQueryLink(savedQueryName);
        WaitUtils.waitUntilDisplayed(getDriver(), savedQueryLink, TimeOuts.FIVE_SECONDS);
        SeleniumUtils.click(savedQueryLink);
        loadingPopup.waitTillDisappears();
    }

    @Override
    public String getExpectedQueryDeletionMessage(){
        return "Selected Query Deleted";
    }

    private WebElement getSavedQueryLink(String savedQueryName){
        return getDriver().findElement(By.linkText(savedQueryName));
    }

    private void clickDeleteQueryLink(){
        SeleniumUtils.hover(getDriver(), webElements.queryMenuHoverBtn);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.saveQueryLink);
        SeleniumUtils.click(webElements.deleteQueryLink);
        if(SeleniumUtils.isAlertPresent(getDriver())){
            SeleniumUtils.clickOkOnAlert(getDriver());
        }
    }

    @Override
    public void createDymanicMemberList(int topN,String memmberListName){
        getMemberList().clickCreateDynamicMemberList().selectAndCreateDynamicMemList(memmberListName,topN);
        loadingPopup.waitTillDisappears();
    }


    @Override
    public IAnalysisPeriod getAnalysisPeriod() {
        return analysisPeriod;
    }

    @Override
    public IBusinessLevelsComponent getBusinessLevel() {
        return businessLevelsComponent;
    }

    @Override
    public IReportingBy getReportingBy() {
        return reportingBy;
    }

    @Override
    public IDataGrid getDataGrid() {
        return emvcaDatagrid;
    }

    @Override
    public boolean isDataGridCustomizable() {
        return false;
    }

    @Override
    public IDataGridCustomizer getDataGridCustomizer() {
        return null;
    }

    @Override
    public IMemberList getMemberList() {
        return memberList;
    }

    private static class WebElements {
        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath="//*[@id=\"d2Form:criteriaPanel_header\"]//div[text()='Query Criteria']/following::div[1]")
        private WebElement queryCriteriaHoverBtn;

        @FindBy(id="d2Form:reset")
        private WebElement createNewQueryLink;

        @FindBy(xpath="//*[@id='d2Form1:_panelContent']//input[@type='submit' and @value='Close']")
        private WebElement popupCloseButton;

        @FindBy(xpath="//*[@id='d2Form1:_panelContent']//input[@type='button' and @value='Apply']")
        private WebElement popupApplyButton;

        @FindBy(xpath="//*[@id=\"query_menu_select_drillMenu_drillImage\"]/parent::span[1]")
        private WebElement queryMenuHoverBtn;

        @FindBy(id="d2Form:saveQuery")
        private WebElement saveQueryLink;

        @FindBy(id="d2Form:deleteQuery")
        private WebElement deleteQueryLink;

        @FindBy(id="d2Form:saveOrUpdateQueryButton")
        private WebElement popupSaveButton;

        @FindBy(id="d2Form:criteria_name")
        private WebElement criteriaNameInputField;

        @FindBy(xpath = "//*[@id='d2Form1:groupDropDown_header']//div[text()='Group']/following::div[1]")
        WebElement groupHoverBtn;

        @FindBy(id = "d2Form1:groupDropDown_body")
        WebElement groupBodyElem;

        @FindBy(id = "ani_message")
        WebElement afterReportGenerationMessage;
    }
}

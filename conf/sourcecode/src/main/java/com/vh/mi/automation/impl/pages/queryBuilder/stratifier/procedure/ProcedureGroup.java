package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.procedure;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.procedure.IProcedureGroup;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.ProcedureByComponent;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.QueryBuilderToolBar;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i20345 on 2/1/2017.
 */
public class ProcedureGroup extends QueryBuilderToolBar implements IProcedureGroup{
    ProcedureByComponent procedureByComponent;
    LoadingPopup loading;
    RefineLogic refineLogic;

    private WebElements webElements;

    private static final String ROW_HOLDER="${row}";

    public ProcedureGroup(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
        procedureByComponent = new ProcedureByComponent(getDriver());
        loading=new LoadingPopup(getDriver());
        refineLogic=new RefineLogic(getDriver());
    }

    public ProcedureByComponent getProcedureComponent(){
        return procedureByComponent;
    }

    @Override
    public void includeTopNProcedureCode(int topN){
        String xpath="//*[@id=\"d2Form:allProcGrid:tb\"]/tr[${row}]/td[4]";
        for(int i=1; i<=topN; i++){
            WebElement webElement= SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(ROW_HOLDER,String.valueOf(i)));
            SeleniumUtils.click(webElement);
        }
    }

    @Override
    public void excludeTopNProcedureCode(int topN){
        String xpath="//*[@id=\"d2Form:allProcGrid:tb\"]/tr[${row}]/td[5]";
        for(int i=6; i<=topN+5 ; i++){
            WebElement webElement= SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(ROW_HOLDER,String.valueOf(i)));
            SeleniumUtils.click(webElement);
        }
    }

    public String getQuickCountValue(){
        String count;
        SeleniumUtils.hover(getDriver(),webElements.quickCountHover);
        SeleniumUtils.click(webElements.btnQuickCount);
        loading.waitTillDisappears();
        count = webElements.txtQuickCount.getText();
        return count.split(" ")[0];
    }

    public RefineLogic goToRefineLogicPage(){
        SeleniumUtils.hover(getDriver(),webElements.hoverReineLogic);
        SeleniumUtils.click(webElements.btnReineLogic);
        refineLogic.doWaitTillFullyLoaded(TimeOuts.THIRTY_SECONDS);
        return refineLogic;
    }

    @Override
    public void ExcludeAll(){
        SeleniumUtils.hover(getDriver(),webElements.excludeAllHover);
        SeleniumUtils.click(webElements.btnExcludeAll);
        loading.waitTillDisappears();
    }

    public String getTotalRecordsFromRemainingTable(){
        return webElements.txtTotalBeforeInclude.getText();
    }

    public void clickRefreshButton(){
        SeleniumUtils.click(webElements.btnRefresh);
        loading.waitTillDisappears();
    }

    public  String getTotalRecordsFromUpperTable(){
        return webElements.txtTotalAfterInclude.getText();
    }

    public void includeAll(){
        doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        SeleniumUtils.hover(getDriver(),webElements.includeAllHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnIncludeAll);
        SeleniumUtils.click(webElements.btnIncludeAll);
        loading.waitTillDisappears();
    }

    public void excludeAllOnPage(){
        doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        SeleniumUtils.hover(getDriver(),webElements.excludeAllHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnExcludeAllOnPage);
        SeleniumUtils.click(webElements.btnExcludeAllOnPage);
        loading.waitTillDisappears();
    }

    public void removeAll(){
        SeleniumUtils.hover(getDriver(),webElements.hoverRemoveAll);
        SeleniumUtils.click(webElements.btnRemoveAll);
        loading.waitTillDisappears();
    }

    public String checkUpperTableIsEmpty(){
        return webElements.txtNoRecordFound.getText();
    }

    public void includeAllOnPage(){
        doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        SeleniumUtils.hover(getDriver(),webElements.btnHoverIncludeAll);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnIncludeAllOnPage);
        SeleniumUtils.click(webElements.btnIncludeAllOnPage);
        loading.waitTillDisappears();
    }

    public String getTotalProcedureGroupsCountOnAPage(){
        String text= webElements.totalProcedureGroups.getText();
        return text.substring(text.length()-2,text.length());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.fullyLoadedElement.isDisplayed();
    }

    @Override
    public String getPageLink() {
        return "Procedure";
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        //fully loaded element
        @FindBy(id = "d2Form:allProcGrid:column_PROCGROUP_header_sortCommandLink")
        private WebElement fullyLoadedElement;

        //quick count hover element
        @FindBy(xpath = "//*[@id=\"d2Form:memcount_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement quickCountHover;

        //quick count button
        @FindBy(xpath = "//*[@id=\"memcount_menu\"]//a[text()=\"Update\"]")
        private WebElement btnQuickCount;

        //quick count value/text
        @FindBy(id = "d2Form:memcount_body")
        private WebElement txtQuickCount;

        //refine logic hover
        @FindBy(xpath = "//*[@id=\"d2Form:query_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement hoverReineLogic;

        //refine  logic button
        @FindBy(xpath = "//*[@id=\"query_menu\"]//a[text()=\"Refine Logic\"]")
        private WebElement btnReineLogic;

        //Exclude hover
        @FindBy(xpath = "//*[@id=\"d2Form:allProcGrid:excludeID_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement excludeAllHover;

        //Exclude all button
        @FindBy(xpath = "//*[@id=\"excludeID_menu\"]//a[text()=\"Exclude All\"]")
        private WebElement btnExcludeAll;

        //Total records before include
        @FindBy(xpath = "//*[@id=\"d2Form:paginationPanel\"]//following::table/tbody/tr/td[1]/span[4]")
        private WebElement txtTotalBeforeInclude;

        //Refresh button
        @FindBy(xpath = "//*[@id=\"d2Form:remainingPanelGrid\"]//a/img")
        private WebElement btnRefresh;


        //Total records after include
        @FindBy(xpath = "//*[@id=\"d2Form:selectedPanelGrid\"]//span[4]")
        private WebElement txtTotalAfterInclude;

        @FindBy(xpath="//*[@id=\"d2Form:remainingPanelGrid\"]//following::table[1]//td/span[2]")
        private WebElement totalProcedureGroups;

        //Include all hover
        @FindBy(xpath = "//*[@id=\"d2Form:allProcGrid:includeID_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement includeAllHover;


        //Include button all
        @FindBy(xpath = "//*[@id='includeID_menu']//a[text()='Include All']")
        private WebElement btnIncludeAll;


        //Exclude all on page button
        @FindBy(xpath = "//*[@id=\"excludeID_menu\"]//a[text()=\"Exclude All on Page\"]")
        private WebElement btnExcludeAllOnPage;


        //Remove all hover
        @FindBy(xpath = "//*[@id=\"d2Form:selectedDataTable:selectedIncludeIDGroup_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement hoverRemoveAll;

        //Remove all hover
        @FindBy(xpath = "//*[@id=\"d2Form:selectedDataTable:unselAll\"]")
        private WebElement btnRemoveAll;


        //Include all on page hover
        @FindBy(xpath = "//*[@id=\"d2Form:allProcGrid:includeID_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement btnHoverIncludeAll;

        //No record found upper table
        @FindBy(xpath = "//*[@id=\"d2Form:selectedPaginationPanel\"]/tbody/tr/td/span[1]")
        private WebElement txtNoRecordFound;

        //Include all on page button
        @FindBy(xpath= "//*[@id=\"includeID_menu\"]//a[text()=\"Include All on Page\"]")
        private WebElement btnIncludeAllOnPage;

        @FindBy(xpath = "//*[@id=\"selectionLimitReached_title\"]")
        private WebElement txtSelectionLimit;
    }
}
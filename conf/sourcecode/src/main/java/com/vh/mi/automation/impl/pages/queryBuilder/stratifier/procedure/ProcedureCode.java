package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.procedure;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.procedure.IProcedureCode;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.QueryBuilderToolBar;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by i20345 on 2/1/2017.
 */
public class ProcedureCode extends QueryBuilderToolBar implements IProcedureCode {
    private WebElements webElements;
    LoadingPopup loading;
    RefineLogic refineLogic;
    private static final String ROW_HOLDER="${row}";

    public ProcedureCode(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
        loading=new LoadingPopup(getDriver());
        refineLogic=new RefineLogic(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.fullyLoadedElement.isDisplayed();
    }

    @Override
    public void clickAndOrButton()
    {
        SeleniumUtils.click(webElements.btnAndOr);
    }

    @Override
    public void includeTopNProcedureCode(int topN) {
        String xpath="//*[@id=\"d2Form:allProcGrid:tb\"]/tr[${row}]/td[6]";
        for(int i=1; i<=topN; i++){
            WebElement webElement= SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(ROW_HOLDER,String.valueOf(i)));
            SeleniumUtils.click(webElement);
        }
    }

    @Override
    public void excludeTopNProcedureCode(int topN)
    {
        String xpath="//*[@id=\"d2Form:allProcGrid:tb\"]/tr[${row}]/td[7]";
        for(int i=6; i<=topN+5 ; i++){
            WebElement webElement= SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(ROW_HOLDER,String.valueOf(i)));
            SeleniumUtils.click(webElement);
        }
    }

    @Override
    public void updateQuickCount()
    {

        SeleniumUtils.hover(getDriver(),webElements.quickCountHover);
        SeleniumUtils.click(webElements.btnUpdate);
        loading.waitTillDisappears();
    }

    @Override
    public String getQuickCountValue()
    {
        String count;
        count = webElements.txtQuickCount.getText();
        return count.split(" ")[0];
    }


    public String getSelectionLimitText()
    {
        loading.waitTillDisappears();
        return webElements.txtSelectionLimit.getText();
    }

    public void includeAll()
    {
        doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        SeleniumUtils.hover(getDriver(),webElements.includeAllHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnIncludeAll);
        SeleniumUtils.click(webElements.btnIncludeAll);
        loading.waitTillDisappears();
    }


    public void clickRefreshButton()
    {
        SeleniumUtils.click(webElements.btnRefresh);
        loading.waitTillDisappears();
    }

    public void includeAllOnPage()
    {
        doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        SeleniumUtils.hover(getDriver(),webElements.btnHoverIncludeAll);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnIncludeAllOnPage);
        SeleniumUtils.click(webElements.btnIncludeAllOnPage);
    }

    public String getTotalProcedureGroupsOnAPage(){
        String text= webElements.totalProcedureGroups.getText();
        return text.substring(text.length()-2,text.length());
    }

    public String getTotalProcedureGroupsAfterIncludingOrExcluding(){
        return webElements.totalincludedProcedureGroups.getText();
    }



    public void excludeAllOnPage()
    {
        doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        SeleniumUtils.hover(getDriver(),webElements.btnHoverExcludeAll);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnExcludeAllOnPage);
        SeleniumUtils.click(webElements.btnExcludeAllOnPage);
    }


    public void removeAll()
    {
        SeleniumUtils.hover(getDriver(),webElements.hoverRemoveAll);
        SeleniumUtils.click(webElements.btnRemoveAll);
        loading.waitTillDisappears();
    }

    public String checkUpperTableIsEmpty()
    {
        return webElements.txtNoRecordFound.getText();
    }


    public void filterDiagnosisCodeWith(String diagCode)
    {
        webElements.txtBoxProcCodeNameFilter.sendKeys(diagCode);

        SeleniumUtils.hover(getDriver(),webElements.hoverApplyFilter);
        SeleniumUtils.click(webElements.btnApplyFilter);
        loading.waitTillDisappears();
    }



    public void clickMaxLimitOkButton()
    {
        SeleniumUtils.click(webElements.btnOkForMaxLimit);

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
        @FindBy(id = "d2Form:allProcGrid:column_PROCCODE_header_sortCommandLink")
        private WebElement fullyLoadedElement;

        //quick count hover element
        @FindBy(xpath = "//*[@id=\"d2Form:memcount_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement quickCountHover;

        //quick count button
        @FindBy(xpath = "//*[@id=\"memcount_menu\"]//a[text()=\"Update\"]")
        private WebElement btnUpdate;

        //quick count value/text
        @FindBy(id = "d2Form:memcount_body")
        private WebElement txtQuickCount;

        //refine logic hover
        @FindBy(xpath = "//*[@id=\"d2Form:query_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement hoverReineLogic;

        //OrAnd Button
        @FindBy(id = "d2Form:allProcGrid:procCodebtnAndOr")
        private WebElement btnAndOr;

        //Selection limit test
        @FindBy(xpath = "//*[@id=\"selectionLimitReached_title\"]")
        private WebElement txtSelectionLimit;

        //Include all hover
        @FindBy(xpath = "//*[@id=\"d2Form:allProcGrid:includeID_1_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement includeAllHover;

        //Include all on page button
        @FindBy(xpath = "//*[@id=\"includeID_menu\"]//a[text()=\"Include All on Page\"]")
        private WebElement btnIncludeAllOnPage;

        //Include all button
        @FindBy(xpath = "//*[@id=\"includeID_menu\"]//a[text()=\"Include All\"]")
        private WebElement btnIncludeAll;


        //Total records before include
        @FindBy(xpath = "//*[@id=\"d2Form:j_id277\"]/tbody/tr/td[1]/span[4]")
        private WebElement txtTotalBeforeInclude;

        //Refresh button
        //@FindBy(xpath = "//*[@id=\"d2Form:j_id275\"]/img")
        @FindBy(xpath="//*[@id='d2Form:selectedPanelGrid']//*[text()='Selected Procedure Codes']/a[1]")
        private WebElement btnRefresh;


        //Total records after  include
        @FindBy(xpath = "//*[@id=\"d2Form:selectedPaginationPanel\"]/tbody/tr/td/span[4]")
        private WebElement txtTotalAfterInclude;


        //Exclude all hover
        @FindBy(xpath = "//*[@id=\"d2Form:allProcGrid:excludeID_1_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement excludeAllHover;

        //Exclude all button
        @FindBy(id = "d2Form:allProcGrid:selAll_7")
        private WebElement btnExcludeAll;


        //First Proc Code Include upper table
        @FindBy(xpath = "//*[@id='d2Form:selectedDataTable:tb']/input")
        private WebElement checkBox_firstUpperTableIncludeItem;


        //Include all hover
        @FindBy(xpath = "//*[@id=\"d2Form:allProcGrid:includeID_1_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement btnHoverIncludeAll;

        //Exclude all hover
        @FindBy(xpath = "//*[@id=\"d2Form:allProcGrid:excludeID_1_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement btnHoverExcludeAll;


        //Exclude all on page button
        @FindBy(xpath = "//*[@id=\"excludeID_menu\"]//a[text()=\"Exclude All on Page\"]")
        private WebElement btnExcludeAllOnPage;

        //Next page button
        @FindBy(xpath = "//*[@id=\"d2Form:j_id277\"]/tbody/tr/td[12]/a/img")
        private WebElement btnNextPage;

        //Remove all hover
        @FindBy(xpath = "//*[@id=\"d2Form:selectedDataTable:selectedIncludeIDCode_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement hoverRemoveAll;

        //Remove all button
        @FindBy(id = "d2Form:selectedDataTable:unselAll_1")
        private WebElement btnRemoveAll;

        //Upper table no record found text
        @FindBy(xpath = "//*[@id=\"d2Form:selectedPaginationPanel\"]/tbody/tr/td/span[1]")
        private WebElement txtNoRecordFound;


        //Upper table total record rext
        @FindBy(xpath = "//*[@id=\"d2Form:selectedPaginationPanel\"]//span[4]")
        private WebElement txtTotalRecordUpperTable;


        //Procedure code filter
        @FindBy(id = "d2Form:allProcGrid:column_PROCCODE_header_filterInputText")
        private WebElement txtBoxProcCodeNameFilter;

        //Hover for apply filter
        @FindBy(id = "procedureFilter_select_drillMenu_drillImage")
        private WebElement hoverApplyFilter;


        //Apply button filter
        @FindBy(id = "d2Form:allProcGrid:icons_applyFilter_1")
        private WebElement btnApplyFilter;

        //Max limit Ok button
        @FindBy(xpath = "//*[@id=\"d2Form:selectionLimitReachedContentTable\"]//input")
        private WebElement btnOkForMaxLimit;

        @FindBy(xpath="//*[@id=\"d2Form:remainingPanelGrid\"]//following::table[1]//td/span[2]")
        private WebElement totalProcedureGroups;

        @FindBy(xpath="//*[@id=\"d2Form:selectedPanelGrid\"]//following::table[1]//td/span[4]")
        private WebElement totalincludedProcedureGroups;

        @FindBy(id="d2Form:selectionLimitReachedCDiv")
        private WebElement popUpMessage;


    }
}

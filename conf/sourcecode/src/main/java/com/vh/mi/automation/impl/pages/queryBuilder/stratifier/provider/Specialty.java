package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.provider;

import com.vh.mi.automation.api.pages.queryBuilder.stratifier.provider.ISpecialty;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.QueryBuilderToolBar;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


/**
 * Created by i20345 on 1/23/2017.
 */
public class Specialty extends QueryBuilderToolBar implements ISpecialty {
    private static final String ROW_NUMBER_PLACE_HOLDER = "${row}";
    WebElements webElements;
    LoadingPopup loading;

    public Specialty(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
        loading = new LoadingPopup(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.fullyLoadedElement.isDisplayed();
    }

    @Override
    public String getPageLink() {
        return "Provider";
    }

    @Override
    public  void selectSpecialtyTab(){
        webElements.btnSpecialtyTab.click();
    }

    public void IncludeAll(){
        SeleniumUtils.hover(getDriver(),webElements.includeAllHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnIncludeAll);
        SeleniumUtils.click(webElements.btnIncludeAll);
        loading.waitTillDisappears();
    }
    public String getTotalRecordsInRemainingTable(){
        return webElements.txtTotalCountInRemainingTable.getText();
    }
    public void clicRefreshButton(){
        SeleniumUtils.click(webElements.btnReaminingRefresh);
        loading.waitTillDisappears();
    }
    public String getTotalRecordsInSelectedTable(){
        return webElements.txtTotalCountInSelectedTable.getText();
    }
    public void ExcludeAll(){
        SeleniumUtils.hover(getDriver(),webElements.excludeAllHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnExcludeAll);
        SeleniumUtils.click(webElements.btnExcludeAll);
        loading.waitTillDisappears();
    }
    public void IncludeTopNInFirstPage(Integer selectionNumber){
        for (int i =1; i <= selectionNumber; i++){
            String xpath = "//*[@id=\"d2Form:specialtyTable:tb\"]/tr[${row}]/td[4]/input[1]";
            SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(ROW_NUMBER_PLACE_HOLDER,Integer.toString(i))).click();
        }
    }
    public void RemoveAllInclude(){
        SeleniumUtils.hover(getDriver(),webElements.includeAllHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnRemoveAllInclude);
        SeleniumUtils.click(webElements.btnRemoveAllInclude);
        loading.waitTillDisappears();
    }
    public String getNoRecordsFoundTextInSelectedTable(){
        return webElements.txtNoRecordsFoundInSelectedTable.getText();
    }
    public void ExcludeTopNInFirstPage(Integer selectionNumber){
        for (int i =1; i <= selectionNumber; i++){
            String xpath = "//*[@id=\"d2Form:specialtyTable:tb\"]/tr[${row}]/td[5]/input[1]";
            SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(ROW_NUMBER_PLACE_HOLDER,Integer.toString(i))).click();
        }
    }
    public void RemoveAllExclude(){
        SeleniumUtils.hover(getDriver(),webElements.excludeAllHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnRemoveAllExclude);
        SeleniumUtils.click(webElements.btnRemoveAllExclude);
        loading.waitTillDisappears();
    }
    public  void  IncludeAllOnPage(){
        SeleniumUtils.hover(getDriver(),webElements.includeAllHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnIncludeAllOnPage);
        SeleniumUtils.click(webElements.btnIncludeAllOnPage);
        loading.waitTillDisappears();
    }
    public String getTotalONPageCountInRemainingTable(){
        String onPageCount = webElements.txtOnPageCountOfRemainingTable.getText();
        onPageCount = onPageCount.replace("1-","");
        return onPageCount;
    }
    public  void  ExcludeAllOnPage(){
        SeleniumUtils.hover(getDriver(), webElements.excludeAllHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnExcludeAllOnPage);
        SeleniumUtils.click(webElements.btnExcludeAllOnPage);
        loading.waitTillDisappears();
    }
    public void RemoveAllSelectedInclude(){
        SeleniumUtils.hover(getDriver(),webElements.selectedIncludeHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnRemoveAllSelectedInclude);
        SeleniumUtils.click(webElements.btnRemoveAllSelectedInclude);
        loading.waitTillDisappears();
    }
    public  void RemoveAllSelectedExclude(){
        SeleniumUtils.hover(getDriver(),webElements.selectedExcludeHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnRemoveAllSelectedExclude);
        SeleniumUtils.click(webElements.btnRemoveAllSelectedExclude);
        loading.waitTillDisappears();
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        //fully loaded element
        @FindBy(id = "d2Form:Speciality_lbl")
        private WebElement fullyLoadedElement;

        //Specialty Tab
        @FindBy(id = "d2Form:Speciality_lbl")
        private  WebElement btnSpecialtyTab;

        //Include All hover
        @FindBy(xpath = "//*[@id=\"d2Form:specialtyTable:includeIDSpeciality_header\"]/table/tbody/tr/td/div/div[2]")
        private  WebElement includeAllHover;

        //include all
        @FindBy(id = "d2Form:specialtyTable:selAll_6")
        private  WebElement btnIncludeAll;

        //total count in reamaining table
        @FindBy (xpath = "//*[@id=\"d2Form:specPaginationPanel\"]//tr/td/span[4]")
        private  WebElement txtTotalCountInRemainingTable;

        //total count selected table
        @FindBy (xpath = "//*[@id=\"d2Form:selectedSpecPagination\"]/tbody/tr/td/span[4]")
        private  WebElement txtTotalCountInSelectedTable;

        //Refresh Button of remaining specialty table
        @FindBy(xpath = "//*[@id=\"d2Form:Speciality\"]//label[.=\"Remaining Specialties\"]//following::a[1]" )
        private WebElement btnReaminingRefresh;

        //Exclude All hover
        @FindBy(xpath = "//*[@id=\"d2Form:specialtyTable:excludeIDSpeciality_header\"]/table/tbody/tr/td/div/div[2]")
        private  WebElement excludeAllHover;

        //exclude all
        @FindBy(id = "d2Form:specialtyTable:selAll_7")
        private  WebElement btnExcludeAll;

        //Remove all (include)
        @FindBy (id ="d2Form:specialtyTable:unselAll_6")
        private WebElement btnRemoveAllInclude;
        
        //No Records Found Text In Selected Table
        @FindBy(xpath = "//*[@id=\"d2Form:selectedSpecPagination\"]/tbody/tr/td/span[1]")
        private WebElement txtNoRecordsFoundInSelectedTable;

        //Remove all (exclude)
        @FindBy (id ="d2Form:specialtyTable:unselAll_7")
        private WebElement btnRemoveAllExclude;

        //Include all on page
        @FindBy (id ="d2Form:specialtyTable:selAllonPage_6")
        private WebElement btnIncludeAllOnPage;

        //On Page Count Of Remaining Table
        @FindBy(xpath = "//*[@id=\"d2Form:specPaginationPanel\"]//tr/td/span[2]")
        private WebElement txtOnPageCountOfRemainingTable;

        //exclude all on page
        @FindBy (id ="d2Form:specialtyTable:selAllonPage_7")
        private WebElement btnExcludeAllOnPage;

        //include hover selected specialty table
        @FindBy (xpath = "//*[@id=\"d2Form:selectedSpecialtyTable:selectedincludeIDSpeciality_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement selectedIncludeHover;

        //remove all include selected specialty table
        @FindBy(id = "d2Form:selectedSpecialtyTable:unselAll_4")
        private  WebElement btnRemoveAllSelectedInclude;

        //exclude hover selected specialty table
        @FindBy (xpath = "//*[@id=\"d2Form:selectedSpecialtyTable:selectedexcludeIDSpeciality_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement selectedExcludeHover;

        //remove all exclude selected specialty table
        @FindBy(id = "d2Form:selectedSpecialtyTable:unselAll_5")
        private  WebElement btnRemoveAllSelectedExclude;

    }
}

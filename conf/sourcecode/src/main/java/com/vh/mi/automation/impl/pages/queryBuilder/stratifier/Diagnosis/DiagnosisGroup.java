package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Diagnosis;

import com.vh.mi.automation.api.pages.queryBuilder.stratifier.Diagnosis.IDiagnosisGroup;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.QueryBuilderToolBar;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i20345 on 12/26/2016.
 */
public class DiagnosisGroup extends QueryBuilderToolBar implements IDiagnosisGroup{

    private static final String ROW_NUMBER_PLACE_HOLDER = "${row}";
    private WebElements webElements;
    LoadingPopup loading;


    public DiagnosisGroup(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
        loading = new LoadingPopup(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.fullyLoadedElement.isDisplayed();
    }

    public void IncludeAll(){
        SeleniumUtils.hover(getDriver(), webElements.includeAllHover);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnIncludeAll);
        SeleniumUtils.click(webElements.btnIncludeAll);
        loading.waitTillDisappears();
    }

    public String getTotalRecordsInRemainingTable(){
        return webElements.remainingTableTotalRecordCount.getText();
    }

    public void clickRefreshButton(){
        SeleniumUtils.click(webElements.btnRefresh);
        loading.waitTillDisappears();
    }

    public  String getTotalRecordsInSelectedTable(){
        return webElements.selectedTableTotalRecordCount.getText();
    }

    public void ExcludeAll(){
        SeleniumUtils.hover(getDriver(),webElements.excludeAllHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnExcludeAll);
        SeleniumUtils.click(webElements.btnExcludeAll);
        loading.waitTillDisappears();
    }

     public void selectTopNCheckBoxes(Integer topN){
        for(int i = 1; i<=topN; i++){
            String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr[${row}]/td[5]/input[1]";
            WebElement checkBoxes = SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(ROW_NUMBER_PLACE_HOLDER,Integer.toString(i)));
            SeleniumUtils.click(checkBoxes);
        }
    }

    @Override
    public String getPageLink() {
        return "Diagnosis";
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        //Include All hover
        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:includeID_header\"]/table/tbody/tr/td/div/div[2]")
        private  WebElement includeAllHover;

        //Include All button
        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:includeID_header\"]/table/tbody/tr/td/div/div[2]//a[.=\"Include All\"]")
        private WebElement btnIncludeAll;

        @FindBy(xpath = "//*[@id=\"d2Form:paginationPanel\"]//span[.=\"Records\"]//following::span[3]")
        private  WebElement remainingTableTotalRecordCount;

        //Refresh Button
        @FindBy(xpath = "//*[@id=\"d2Form:grid\"]/table[2]/tbody/tr/td[1]/label/a")
        private WebElement btnRefresh;

        @FindBy(xpath = "//*[@id=\"d2Form:selectedPaginationPanel\"]/tbody/tr/td[1]/span[4]")
        private WebElement selectedTableTotalRecordCount;

        //Exclude All hover
        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:excludeID_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement excludeAllHover;

        //Exclude All button
        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:excludeID_header\"]/table/tbody/tr/td/div/div[2]//a[.=\"Exclude All\"]")
        private WebElement btnExcludeAll;

        //fully loaded element
        @FindBy(id = "d2Form:simpleGrid:column_DIAGGROUP_header_sortCommandLink")
        private WebElement fullyLoadedElement;
    }
}

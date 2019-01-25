package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Diagnosis;

import com.vh.mi.automation.api.pages.queryBuilder.stratifier.Diagnosis.IDiagnosisCode;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.QueryBuilderToolBar;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by i20345 on 12/28/2016.
 */
public class DiagnosisCode extends QueryBuilderToolBar implements IDiagnosisCode {
    private static final String IPOP_OPTION_PLACE_HOLDER = "${IPOPoption}";
    private static final String YES_NO_OPTION_PLACE_HOLDER = "${option}";
    private WebElements webElements;

    LoadingPopup loading;

    public DiagnosisCode(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
        loading = new LoadingPopup(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.fullyLoadedElement.isDisplayed();
    }

    public void includeAllOnPage() {
        SeleniumUtils.hover(getDriver(), webElements.btnHoverIncludeAll);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnIncludeAllOnPage);
        SeleniumUtils.click(webElements.btnIncludeAllOnPage);
    }

    public void clickRefreshButton() {
        SeleniumUtils.click(webElements.btnRefresh);
        loading.waitTillDisappears();
    }

    public String getTotalCountOnPageRemainingTable() {
        String count = webElements.totalOnPageCountOfRemainingTable.getText();
        count = count.replace("1-", "");
        return count;
    }

    public String getTotalCountOnPageSelectedTable() {
        return webElements.totalOnPageCountOfSelectedTable.getText();
    }

    public void excludeAllOnPage() {
        SeleniumUtils.hover(getDriver(), webElements.btnHoverExcludeAll);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnExcludeAllOnPage);
        SeleniumUtils.click(webElements.btnExcludeAllOnPage);
    }

    public void applyIPOPToAll(String optionOfIPOP) {
        SeleniumUtils.click(webElements.dropDownIPOP);
        String xpath = "//*[@class=\"applyAllipop\"]/option[.=\"${IPOPoption} to All\"]";
        WebElement IPOPOption = SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(IPOP_OPTION_PLACE_HOLDER, optionOfIPOP));
        WaitUtils.waitUntilEnabled(getDriver(), IPOPOption);
        SeleniumUtils.click(IPOPOption);
    }

    public void removeAll() {
        SeleniumUtils.hover(getDriver(), webElements.hoverRemoveAll);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnRemoveAll);
        SeleniumUtils.click(webElements.btnRemoveAll);
        loading.waitTillDisappears();
    }

    public String checkSelectedTableIsEmpty() {
        return webElements.txtNoRecordFound.getText();
    }

    public void includeAll() {
        SeleniumUtils.hover(getDriver(), webElements.btnHoverIncludeAll);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnIncludeAll);
        SeleniumUtils.click(webElements.btnIncludeAll);
        loading.waitTillDisappears();
    }

    public String getSelectionLimitText() {
        String selectionLimitExceededText = webElements.txtSelectionLimit.getText();
        SeleniumUtils.click(webElements.btnSelectionLimitExceedClose);
        return selectionLimitExceededText;
    }

    public void filterDiagnosisCodeWith(String filterText) {
        SeleniumUtils.sendKeysToInput(filterText, webElements.txtBoxDiagCodeNameFilter);
        SeleniumUtils.hover(getDriver(), webElements.hoverApplyFilter);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnApplyFilter);
        SeleniumUtils.click(webElements.btnApplyFilter);

        loading.waitTillDisappears();
    }

    public String getIPOPCountInSelectedTable(String ipopOPtion){
        String nextBtn = "//*[@id=\"d2Form:selectedPaginationPanel\"]//td[position()=last()]//preceding::td[1]/a[@style=\"cursor:pointer\"]";
        int TotalIPOPOptionCount = 0;
        boolean flag = true;
            do {
                try {
                    String xpath = "//*[@class=\"parentIpopSelected\"]/option[.=\"${option}\"][@selected]";
                    List<WebElement> IPOPCount = SeleniumUtils.findElementsByXPath(getDriver(),xpath.replace(YES_NO_OPTION_PLACE_HOLDER, ipopOPtion));
                    int IPOPSElectedOptionCountOnPage = IPOPCount.size();
                    TotalIPOPOptionCount = TotalIPOPOptionCount + IPOPSElectedOptionCountOnPage;
                    SeleniumUtils.click(SeleniumUtils.findElementByXPath(getDriver(),nextBtn), getDriver());
                    loading.waitTillDisappears();
                }catch (NoSuchElementException ex){
                    flag = false;
                }
            }while (flag);

        return Integer.toString(TotalIPOPOptionCount);
    }

    public void applyIPOPYesToAll(){
        SeleniumUtils.click(webElements.dropDownIPOP);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.IPOPYesToAllOption);
        SeleniumUtils.click(webElements.IPOPYesToAllOption);
    }

    public String getTotalCountOnPageRemainingTableAfterFiltering(){
        return webElements.totalOnPageCountOfRemainingTableAfterFiltering.getText();
    }
    @Override
    public String getPageLink() {
        return "Diagnosis";
    }

    private static class WebElements {
        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        //Include all on page button
        @FindBy(id = "d2Form:simpleGrid:selAllonPage_5")
        private WebElement btnIncludeAllOnPage;

        //Include all Hover
        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:includeID_1_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement btnHoverIncludeAll;

        //fully loaded element
        @FindBy(id = "d2Form:simpleGrid:column_ICD_DIAG_header_sortCommandLink")
        private WebElement fullyLoadedElement;

        //Refresh Button
        @FindBy(xpath = "//*[@id=\"d2Form:grid\"]/table[2]/tbody/tr/td[1]/label[.=\"Remaining Diagnosis Codes\"]//following::a[1]/img")
        private  WebElement btnRefresh;

        @FindBy(xpath = "//*[@id=\"d2Form:paginationPanel\"]//td[1]/span[2]")
        private WebElement totalOnPageCountOfRemainingTable;

        @FindBy(xpath = "//*[@id=\"d2Form:selectedPaginationPanel\"]/tbody/tr/td[1]/span[4]")
        private WebElement totalOnPageCountOfSelectedTable;

        //Exclude all Hover
        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:excludeID_1_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement btnHoverExcludeAll;

        //Exclude all on page button
        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:excludeID_1_header\"]/table/tbody/tr/td/div/div[2]//a[.=\"Exclude All on Page\"]")
        private WebElement btnExcludeAllOnPage;

        //IPOP dorpdown
        @FindBy(xpath = "//*[@class=\"applyAllipop\"]")
        private WebElement dropDownIPOP;

        //Remove All Hover
        @FindBy(xpath = "//*[@id=\"d2Form:selectedDataTable:selectedIncludeID_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement hoverRemoveAll;

        //Upper Table No Record Found Element
        @FindBy(xpath = "//*[@id=\"d2Form:selectedPaginationPanel\"]/tbody/tr/td/span[1]")
        private WebElement txtNoRecordFound;

        //Include all button
        @FindBy(id = "d2Form:simpleGrid:selAll_5")
        private WebElement btnIncludeAll;

        //Selection Limit Exceed title
        @FindBy(id = "d2Form:selectionLimitReachedHeader")
        private WebElement txtSelectionLimit;

        //Selection Limit Exceed popup Close
        @FindBy(xpath = "//*[@id=\"selectionLimitReached_control\"]/img")
        private WebElement btnSelectionLimitExceedClose;

        //Diagnosis Code Filter first column(name)
        @FindBy(id = "d2Form:simpleGrid:column_DIAGGROUP_header_filterInputText")
        private WebElement txtBoxDiagCodeNameFilter;

        //Apply Filter Hover
        @FindBy(xpath = "//*[@id=\"allDiagFilter_select_drillMenu_drillImage\"]")
        private  WebElement hoverApplyFilter;

        //Apply filter
        @FindBy (id = "d2Form:simpleGrid:icons_applyFilter_1")
        private WebElement btnApplyFilter;

        //Remove All button
        @FindBy(id = "d2Form:selectedDataTable:unselAll")
        private WebElement btnRemoveAll;

        //Apply IPOP Yes to All
        @FindBy(xpath = "//*[@class=\"applyAllipop\"]/option[.=\"Yes to All\"]")
        private WebElement IPOPYesToAllOption;

        //Total Count On Remaining Table After Filtering
        @FindBy(xpath = "//label[text()='Remaining Diagnosis Codes']//following::table[@class='d2-pagination-table']//span[@class='d2-pagination-recordCount'][2]")
        private WebElement totalOnPageCountOfRemainingTableAfterFiltering;

    }
}

package com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.customperformancemeasures.ISaveFavoriteNormSelection;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.ICPM01DataGridCustomizer;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.ReportingBy;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.comp.customperformancemeasures.SaveFavoriteNormSelectionPopUp;
import com.vh.mi.automation.impl.comp.favqm.FavouiteQualityMeasures;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.comp.pagination.PaginationComponent;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82298 on 1/16/2017.
 */
public class CPM01 extends AbstractLandingPage implements ICPM01 {
    public static final String MODULE_ID = "422";
    private final WebElements webElements;
    private final CPM01DataGrid dataGrid;
    private IAnalysisPeriod analysisPeriod;
    private IBusinessLevelsComponent businessLevelsComponent;
    private IReportingBy reportingBy;
    private IPaginationComponent paginationComponent;
    private FavouiteQualityMeasures favouiteQualityMeasures;
    private LoadingPopup loadingPopup;
    private ISaveFavoriteNormSelection saveFavoriteNormSelectionPopup;

    private static final String CRITERIA_PLACE_HOLDER = "${Criteria}";

    public CPM01(WebDriver driver) {
        super(driver, MODULE_ID);
        webElements = new WebElements(driver);
        dataGrid = new CPM01DataGrid(driver);
        analysisPeriod = new AnalysisPeriod(getDriver());
        businessLevelsComponent = new BusinessLevelsComponent(getDriver());
        paginationComponent = PaginationComponent
                .newD2FormPaginationComponent(getDriver());
        reportingBy = PageFactory.initElements(getDriver(), ReportingBy.class);
        favouiteQualityMeasures = new FavouiteQualityMeasures(getDriver());
        loadingPopup = new LoadingPopup(getDriver());
        saveFavoriteNormSelectionPopup = new SaveFavoriteNormSelectionPopUp(getDriver());
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
    public IPaginationComponent getPaginationComponent() {
        return paginationComponent;
    }

    @Override
    public IReportingBy getReportingBy() {
        return reportingBy;
    }

    @Override
    public FavouiteQualityMeasures getFavouiteQualityMeasures() {
        return favouiteQualityMeasures;
    }

    public Indv301DrillPage doDrillToIndv() {
        dataGrid.getRows().get(0).doDrillBy("Individual").doWaitTillFullyLoaded(10);
        return new Indv301DrillPage(getDriver());
    }

    @Override
    public CPM01DataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public List<String> getNormSelectionList() {
       List<String> normSelectionList = new ArrayList<>();
       String xpath = "//*[@id = \"norm_type_menu\"]//a";
       List<WebElement> menus = getDriver().findElements(By.xpath(xpath));

       for(WebElement menu : menus){
           normSelectionList.add(menu.getAttribute("innerText"));
       }
       return normSelectionList;

    }


    @Override
    public void hoverAndClickNormSelection(String option) {
        SeleniumUtils.hover(getDriver(), webElements.normHoverElement);
        WaitUtils.waitForSeconds(2);
        String xpath = "//*[@id = 'norm_type_menu']//a[text()='${option}']";
        WebElement normSelectionElement = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace("${option}", option));
        SeleniumUtils.click(normSelectionElement);
    }

    @Override
    public void doWaitTillPopUpDisappears() {
        loadingPopup.waitTillDisappears();
    }

    @Override
    public void saveNormSelection(String viewName ) {
        saveFavoriteNormSelectionPopup.doSaveNormSelection(viewName);
    }

    @Override
    public void savedNormValidation(String drillOption, String savedName) {
        String savedViewName = "//*[@id = 'savedView_menu']//a[text()='${savedViewName}']";
        SeleniumUtils.hover(getDriver(),webElements.favoriteViewsHoverElement);
        WaitUtils.waitForSeconds(4);
        WebElement savedViewNameLink = SeleniumUtils.findElementByXPath(getDriver(), savedViewName.replace("${savedViewName}", savedName + " [My]"));
        SeleniumUtils.click(savedViewNameLink);
        loadingPopup.waitTillDisappears();
        String displayedNormSelection = "//*[@id=\"d2Form:norm_type_body\"]";
        WebElement normSelection = SeleniumUtils.findElementByXPath(getDriver() , displayedNormSelection);
        String selectedNorm = normSelection.getText();
        assertThat(selectedNorm.equalsIgnoreCase(savedName));

        SeleniumUtils.hover(getDriver(),webElements.favoriteViewsHoverElement);
        WaitUtils.waitForSeconds(4);

        String xpathCloseButton = "//*[contains(@id, 'd2Form:deleteSaveditemGrid')]//a[text() = '${savedView}']//following::img[1]";
        WebElement deleteSelectedNormBtn = SeleniumUtils.findElementByXPath(getDriver(), xpathCloseButton.replace("${savedView}", savedName+ " [My]"));
        SeleniumUtils.click(deleteSelectedNormBtn);
            if (SeleniumUtils.isAlertPresent(getDriver())) {
                SeleniumUtils.clickOkOnAlert(getDriver());
                loadingPopup.waitTillDisappears();
            }
    }

    @Override
    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(excelFileName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not export to excel " + excelFileName);
        }
        return true;
    }


    @Override
    public boolean isDataGridCustomizable() {
        return true;
    }

    @Override
    public ICPM01DataGridCustomizer getDataGridCustomizer() {
        if (isDataGridCustomizable()) {
            webElements.customizeLinkElement.click();
            new LoadingPopup(getDriver()).waitTillDisappears();
            return new CPM01DataGridCustomizer(getDriver(), new IColumnSpec() {
                @Override
                public IDataGridColumn getColumnByLabel(String colName) {
                    return CPM01DataGridColumn.fromLabel(colName);
                }
            });
        }

        return null;
    }

    @Override
    public void doCustomizeAllColumns(){
        ICPM01DataGridCustomizer dataGridCustomizer = getDataGridCustomizer();
        dataGridCustomizer.doSelectAllForAllCategory();
        dataGridCustomizer.doApplySelection();
        new LoadingPopup(getDriver()).waitTillDisappears();
    }

    public void selectQNRCriterias(List<String> criterias) {
        WaitUtils.waitUntilEnabled(getDriver(), webElements.hoverQualityMeasures);
        SeleniumUtils.hover(getDriver(), webElements.hoverQualityMeasures);

        WaitUtils.waitUntilEnabled(getDriver(), webElements.resetSelectionList);
        SeleniumUtils.click(webElements.resetSelectionList);

        loadingPopup.waitTillDisappears();

        WaitUtils.waitUntilEnabled(getDriver(), webElements.hoverQualityMeasures);
        SeleniumUtils.hover(getDriver(), webElements.hoverQualityMeasures);

        WaitUtils.waitUntilEnabled(getDriver(), webElements.createListLink);
        SeleniumUtils.click(webElements.createListLink);

        WaitUtils.waitUntilDisplayed(getDriver(), webElements.favQualityMeasureListHeader, TimeOuts.FIVE_SECONDS);

        WaitUtils.waitUntilEnabled(getDriver(), webElements.hoverQualityMeasureSet);
        SeleniumUtils.hover(getDriver(), webElements.hoverQualityMeasureSet);

        WaitUtils.waitUntilEnabled(getDriver(), webElements.qrmLink);
        SeleniumUtils.click(webElements.qrmLink);

        WaitUtils.waitUntilDisappear(getDriver(),webElements.loadingPopUp,TimeOuts.TEN_SECONDS);

        String xpath = "//*[@id=\"d2FormQmSelect:_qmSelectSimpleGrid:tb\"]//td[contains(text(),'${Criteria}')]/preceding::input[1]";
        for (int i = 0; i < criterias.size(); i++) {
            WebElement chkbox = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(CRITERIA_PLACE_HOLDER, criterias.get(i)));
            WaitUtils.waitUntilEnabled(getDriver(), chkbox);
            SeleniumUtils.click(chkbox);
        }
    }

    public void  selectHEDISCriterias(List<String> criterias){

        WaitUtils.waitUntilEnabled(getDriver(), webElements.hoverQualityMeasureSet);
        SeleniumUtils.hover(getDriver(), webElements.hoverQualityMeasureSet);

        WaitUtils.waitUntilEnabled(getDriver(), webElements.hedisClaimBasedLink);
        SeleniumUtils.click(webElements.hedisClaimBasedLink);

        WaitUtils.waitUntilDisappear(getDriver(), webElements.loadingPopUp, TimeOuts.TEN_SECONDS);

        String xpath = "//*[@id=\"d2FormQmSelect:_qmSelectSimpleGrid:tb\"]//td[.=\"${Criteria}\"]/preceding::input[1]";
        for (int i = 0; i < criterias.size(); i++) {
            WebElement chkbox = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(CRITERIA_PLACE_HOLDER, criterias.get(i)));
            WaitUtils.waitUntilEnabled(getDriver(), chkbox);
            SeleniumUtils.click(chkbox);
        }
    }

    public String saveCreatedList(String listName) {
        SeleniumUtils.sendKeysToInput(listName, webElements.txtBoxQRMName);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnSave);
        SeleniumUtils.click(webElements.btnSave);
        WaitUtils.waitUntilDisappear(getDriver(), webElements.loadingPopUp, TimeOuts.TEN_SECONDS);

        WaitUtils.waitUntilDisplayed(getDriver(),webElements.txtAfterListSaved,TimeOuts.FIVE_SECONDS);
        return webElements.txtAfterListSaved.getText();
    }



    public boolean checkIfTheseCriteriasAreAppliedSuccessfully(List<String> criterias) {

        List<WebElement> criteriaElements = webElements.selectedCriterias;
        if(criterias.size() == criteriaElements.size()){
            for (WebElement criteriaElement : criteriaElements) {
                String criteria = criteriaElement.getText();
                criteria = criteria.replaceAll("[A-Za-z()]+", "").replace(" ", "");
                if (!criterias.contains(criteria)) {
                    return false;
                }
            }
            return true;
        }else {
            return false;
        }
    }

    public List<String> qualityMeasuresElements(){
        List<String> qualityMeasureElements = new ArrayList<>();
        List<WebElement> criteriaElements = webElements.selectedCriterias;
        for (WebElement criteriaElement : criteriaElements) {
            qualityMeasureElements.add(criteriaElement.getText());
        }
        return qualityMeasureElements;
    }


    

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(linkText = "Customize")
        private WebElement customizeLinkElement;

        @FindBy(xpath = "//*[@id=\"d2Form:favQMList_ListPanel_header\"]//div[.=\"Quality Measures\"]/following::div[1]")
        private WebElement hoverQualityMeasures;

        @FindBy(xpath = "//*[@id=\"d2Form:favQMList_ListPanel_resetListPanel\"]")
        private WebElement resetSelectionList;

        @FindBy(xpath = "//*[@id=\"d2Form:favQMList_ListPanel_header\"]//div[.=\"Quality Measures\"]/following::a[.=\"Create List\"]")
        private WebElement createListLink;

        @FindBy(xpath = "//*[@id=\"_qmSelectPanelHeader\"]")
        private WebElement favQualityMeasureListHeader;

        @FindBy(xpath = "//*[@id=\"d2FormQmSelect:_qmSelectorMeasureType_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement hoverQualityMeasureSet;

        @FindBy(xpath = "//*[@id=\"d2FormQmSelect:_qmSelectorMeasureTypeHedis\"]")
        private WebElement hedisClaimBasedLink;

        @FindBy(xpath = "//*[@id=\"d2FormQmSelect:_qmSelectorMeasureTypeQrm\"]")
        private WebElement qrmLink;

        @FindBy(xpath = "//*[@id=\"d2FormQmSelect:savedQrmListName\"]")
        private WebElement txtBoxQRMName;

        @FindBy(xpath = "//*[@id=\"d2FormQmSelect:_qmSelectPanelReset\"]")
        private WebElement btnSave;

        @FindBy(xpath = "//*[@id=\"_qmSelectLoading\"]/img")
        private WebElement loadingPopUp;

        @FindBy(xpath = "//*[@id=\"ani_message\"]")
        private WebElement txtAfterListSaved;

        @FindBy(xpath = "//*[@class=\"rich-table-headercell d2-align-center\"]/a")
        private List<WebElement> selectedCriterias;

        @FindBy(xpath = "//*[@id=\"d2Form:norm_type_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement normHoverElement;

        @FindBy(xpath = "//*[@id=\"d2Form:anaPeriod_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement analysisPeriodHoverElement;

        @FindBy(xpath = "//*[@id=\"d2Form:savedView_panel_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement favoriteViewsHoverElement;

    }
}

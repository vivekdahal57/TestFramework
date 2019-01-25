package com.vh.mi.automation.impl.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.IMemberList;
import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDxcgDataGridCustomizer;
import com.vh.mi.automation.api.comp.extractDownloadPopup.ICSVExtractPopUp;
import com.vh.mi.automation.api.comp.extractDownloadPopup.ISendToExcelPopUp;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.MemberList;
import com.vh.mi.automation.impl.comp.ReportingBy;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.comp.dataGrid.customizer.DxcgDataGridCustomizer;
import com.vh.mi.automation.impl.comp.dataGrid.customizer.Indv301DataGridCustomizer;
import com.vh.mi.automation.impl.comp.extractDownloadPopup.CSVExtractPopUp;
import com.vh.mi.automation.impl.comp.extractDownloadPopup.SendToExcelPopUp;
import com.vh.mi.automation.impl.comp.favqm.FavouiteQualityMeasures;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.comp.pagination.PaginationComponent;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDahsboardHealthAnalysisSummary;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardHealthRiskAssessmentData;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardIndividualClaimDetails;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.SelectTemplatePage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.List;


/**
 * Created by i80448 on 9/17/2014.
 */
public class Indv301 extends AbstractLandingPage implements IIndv301 {
    public static final String MODULE_ID = "4";


    private final Indv301DataGrid dataGrid;
    private final WebElements webElements;
    private IAnalysisPeriod analysisPeriod;
    private IBusinessLevelsComponent businessLevelsComponent;
    private IReportingBy reportingBy;
    private IPaginationComponent paginationComponent;
    private FavouiteQualityMeasures favouiteQualityMeasures;
    private IMemberList memberList;
    private LoadingPopup loadingPopup;
    private int topN;

    private IndividualDahsboardHealthAnalysisSummary indvHAS;
    private IndividualDashboardHealthRiskAssessmentData indvHRA;
    private static final String FIELD_NAME_PLACEHOLDER = "${FieldName}";
    private static final String ROW_NUMBER_HOLDER = "${row}";
    private static final Integer REQUIRED_NUMBER_OF_WINDOWS = 3;

    public Indv301(WebDriver driver) {
        super(driver, MODULE_ID);
        dataGrid = new Indv301DataGrid(driver);
        analysisPeriod = new AnalysisPeriod(getDriver());
        memberList = new MemberList(getDriver());
        businessLevelsComponent = new BusinessLevelsComponent(getDriver());
        paginationComponent = PaginationComponent.newD2FormPaginationComponent(getDriver());
        reportingBy = PageFactory.initElements(getDriver(), ReportingBy.class);
        webElements = new WebElements(driver);
        favouiteQualityMeasures = new FavouiteQualityMeasures(getDriver());
        loadingPopup = new LoadingPopup(getDriver());
        indvHAS = new IndividualDahsboardHealthAnalysisSummary(getDriver());
        indvHRA = new IndividualDashboardHealthRiskAssessmentData(getDriver());


    }

    public void popupExists() {
        if(webElements.closePopup.isDisplayed()){
                SeleniumUtils.click(webElements.closePopup, getDriver());
        }
    }

    @Override
    public IAnalysisPeriod getAnalysisPeriod() {
        return analysisPeriod;
    }

    @Override
    public IMemberList getMemberList() {
        return memberList;
    }

    @Override
    public String getPageTitle() {
        return "(301) Individuals\n" +
                "Switch to New Individuals";
    }


    @Override
    public void createListWithSelectedMember(int topN, String memListName) {
        this.topN = topN;
        getDataGrid().selectTopNCheckBoxInDataGrid(this.topN);
        getMemberList().createStaticMemberList(memListName);
    }

    private List<String> getSelectedMemberIdList() {
        List<String> memberIdList = getDataGrid().getNthData(Indv301Columns.MEMBER_ID, topN);
        return memberIdList;
    }

    private List<String> getSavedMemberIdList(String memListName) {
        getMemberList().goToSavedMemberList(memListName);
        List<String> memberIdList = getDataGrid().getData(Indv301Columns.MEMBER_ID);
        return memberIdList;
    }

    @Override
    public boolean checkIfStaticMemberListIsSucessfullyCreated(String memListName) {
        List<String> selectedMemberList = getSelectedMemberIdList();
        List<String> savedMemberList = getSavedMemberIdList(memListName);
        logger.info("Selected Static Member List :" + selectedMemberList.size() + "  " + "Saved Static Member List: " + savedMemberList.size());
        return selectedMemberList.equals(savedMemberList);
    }

    @Override
    public void createDymanicMemberList(int topN, String memListName) {
        getMemberList().clickCreateDynamicMemberList().selectAndCreateDynamicMemList(memListName, topN);
        loadingPopup.waitTillDisappears();
    }

    @Override
    public boolean checkIfMemberListIsNotEmpty() {
        if (getDataGrid().getData().size() > 0) {
            return true;
        } else {
            logger.info("Member List is Empty");
            return false;
        }
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
    public IPaginationComponent getPaginationComponent() {
        return paginationComponent;
    }

    @Override
    public Indv301DataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public boolean isDataGridCustomizable() {

        return true;
    }

    @Override
    public IDataGridCustomizer getDataGridCustomizer() {
        if (isDataGridCustomizable()) {
            SeleniumUtils.click(webElements.customizeLinkElement, getDriver());
            return new Indv301DataGridCustomizer(getDriver(), new IColumnSpec() {
                @Override
                public IDataGridColumn getColumnByLabel(String colName) {
                    return Indv301Columns.fromLabel(colName);
                }
            });
        }

        return null;
    }

    @Override
    public ICSVExtractPopUp getCSVExtractPopUp() {
        if (getPageRowCount() < 1000000) {
            SeleniumUtils.click(webElements.csvLinkElement, getDriver());
            return new CSVExtractPopUp(getDriver());
        }
        // TODO: 2/10/2017 else condition when rowcount is greater than a million
        return null;
    }

    @Override
    public ISendToExcelPopUp getSendToExcelPopUp() {
        if(getPageRowCount() >15000){
            SeleniumUtils.click(webElements.excelLinkElement, getDriver());
            return new SendToExcelPopUp(getDriver());
        }
        return null;
    }

    private Double getPageRowCount() {
       return Double.parseDouble(webElements.rowCount.getText().replace(",", ""));
    }

    @Override
    public IDxcgDataGridCustomizer getDxcgDataGridCustomizer() {
        if (isDataGridCustomizable()) {
            SeleniumUtils.click(webElements.customizeLinkElement, getDriver());
            return new DxcgDataGridCustomizer(getDriver(), new IColumnSpec() {
                @Override
                public IDataGridColumn getColumnByLabel(String colName) {
                    return Indv301Columns.fromLabel(colName);
                }
            });
        }

        return null;
    }

    @Override
    public boolean isDxCGEnabled() {
        if (!isDataGridCustomizable()) {
            return false;
        }

        IDxcgDataGridCustomizer dxcgDataGridCustomizer = getDxcgDataGridCustomizer();
        boolean customizationAvailable = DxcgDataGridCustomizer.isCustomizationAvailable(getDriver());
        dxcgDataGridCustomizer.doCancelAndClose();

        return customizationAvailable;
    }
      /*
    need to uncomment for verifing dynamic member list
    @Override
    public MemberList getMemberListComponent() {
        return new MemberList(getDriver());
    }*/

    public IndividualDashboardIndividualClaimDetails doDrillFromIndv301() {
        getDataGrid().getRowWithDrillOption().doDrillBy("Individual Claim Details");
        return new IndividualDashboardIndividualClaimDetails(getDriver());
    }

    @Override
    public FavouiteQualityMeasures getFavouiteQualityMeasures() {
        return favouiteQualityMeasures;
    }

    public IndividualDahsboardHealthAnalysisSummary goToHASPageFor(String indvID) {
        dataGrid.doFilter(Indv301Columns.MEMBER_ID, indvID);
        loadingPopup.waitTillDisappears();

        SeleniumUtils.hover(getDriver(), webElements.hoverView);

        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnHAS);
        SeleniumUtils.click(webElements.btnHAS);

        WaitUtils.waitForSeconds(TimeOuts.TWO_SECOND);
        SeleniumUtils.switchToNewWindow(getDriver());
        indvHAS.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return indvHAS;
    }

    public IndividualDashboardHealthRiskAssessmentData goToHRAPageFor(String indvID){
        dataGrid.doFilter(Indv301Columns.MEMBER_ID,indvID);
        loadingPopup.waitTillDisappears();

        SeleniumUtils.hover(getDriver(), webElements.hoverView);

        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnHRA);
        SeleniumUtils.click(webElements.btnHRA);

        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(getDriver());
        SeleniumUtils.switchToNewWindow(getDriver());

        indvHRA.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return indvHRA;
    }

    @Override
    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(excelFileName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not export to excel " + excelFileName);
        }
        return true;
    }

    @Override
    public boolean sendToExcelAndValidateZipFile(String excelFileName, ExecutionContext context) throws IOException {
       if(!FileDownloadUtils.validateDownloadedZipFile(excelFileName, context, TimeOuts.THREE_MINUTES)){
           throw new AutomationException("Could not export to excel " + excelFileName);
       }
       return true;
    }

    @Override
    public boolean isExcelOffline() {
        if(getPageRowCount() > 25000)
            return true;
        else
            return false;
    }


    public boolean updatedDataSeenInIndividualPage(String fieldName, String updateText) {

        WaitUtils.waitUntilDisappear(getDriver(), webElements.applyingPopUp, TimeOuts.TEN_SECONDS);
        String xpath = "//*[@id=\"d2Form:simpleGrid:0:column_${FieldName}_body\"]";
        String updatedText = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(FIELD_NAME_PLACEHOLDER, fieldName)).getText();

        if (updatedText.equalsIgnoreCase(updateText)) {
            return true;
        } else {
            return false;
        }
    }

    public SelectTemplatePage selectTopIDsAndSendToOutBox(Integer selection){
        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr[${row}]/td[1]/input";
        for (int i = 1; i <= selection; i ++){
            WebElement chkbox = SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(ROW_NUMBER_HOLDER,Integer.toString(i)));
            SeleniumUtils.click(chkbox);
        }

       return getPreferencesBar().sendToOutBox();
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(linkText = "Customize")
         WebElement customizeLinkElement;

        @FindBy(linkText = "CSV")
         WebElement csvLinkElement;

        @FindBy(linkText = "Excel")
        WebElement excelLinkElement;

        @FindBy(xpath = "//*[@id='d2Form:paginationPanel']/table[1]/tbody/tr/td[1]/span[4]")
         WebElement rowCount;

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr/td[3]/a")
         WebElement hoverView;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]/a[.=\"HAS\"]")
         WebElement btnHAS;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]/a[.=\"HRA\"]")
         WebElement btnHRA;

        @FindBy(xpath = "//*[@id=\"_LoadingCustomization\"]")
         WebElement applyingPopUp;

        @FindBy(id = "d2Form:_tryNewIndividualModal_controls_close")
        WebElement closePopup;
    }
}

package com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.IIndividualRiskAnalysis;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

/**
 * Created by nimanandhar on 12/30/2014.
 */
public class IndividualRiskAnalysis extends AbstractLandingPage implements IIndividualRiskAnalysis {
    public static final String MODULE_ID = "51";
    private IBusinessLevelsComponent businessLevelsComponent;
    private IDataGrid dataGrid;
    private final WebElements webElements;
    private static final String FIELD_NAME_PLACEHOLDER = "${fieldName}";

    public IndividualRiskAnalysis(WebDriver driver) {
        super(driver, MODULE_ID);
        businessLevelsComponent = new BusinessLevelsComponent(getDriver());
        dataGrid = new IndividualRiskAnalysisDataGrid(getDriver());
        webElements = new WebElements(driver);
    }


    @Override
    public IBusinessLevelsComponent getBusinessLevel() {
        return businessLevelsComponent;
    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public IDrillPage drillDownToPage(String page) {
        IDrillPage drillPage = dataGrid.getRows().get(0).doDrillBy(page);
        drillPage.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return drillPage;
    }

    @Override
    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(excelFileName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not export to excel " + excelFileName);
        }
        return true;
    }

      public String getRecordsCountInPopup(){
        return webElements.popupRecordCount.getText();
    }

    public void waitTillPopupTableAppears(){
        WaitUtils.waitUntilDisplayed(getDriver(), webElements.popupDataTable, TimeOuts.THIRTY_SECONDS);
    }

    @Override
    public void closePopUp() {
        SeleniumUtils.click(webElements.popupCloseButton);
    }


    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy (id = "dataTableDiv")
        WebElement popupDataTable;

        @FindBy (xpath = "//*[@id='d2FormRPExtraPanel']//input[@value='Close']")
        WebElement popupCloseButton;

        @FindBy (xpath = "//*[contains(@id, 'd2FormRPExtraPanel')]/tbody/tr/td[1]/span[4]")
        WebElement popupRecordCount;



    }


}

package com.vh.mi.automation.impl.pages.analytics.providerProfiler;

import com.vh.mi.automation.api.comp.IMemberList;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.Drill.Provider.IIndividualDrillPage;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.Drill.Provider.IProviderDrillPage;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.IProviderProfilerV044;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.ProviderProfilerDataGridColumn;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.MemberList;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkState;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i80448 on 11/20/2014.
 */
public class ProviderProfilerV044 extends AbstractLandingPage implements IProviderProfilerV044 {
    public static final String MODULE_ID = "16";
    private IAnalysisPeriod analysisPeriod;
    private IBusinessLevelsComponent businessLevelsComponent;
    private ProviderProfilerDataGrid dataGrid;
    private IMemberList memberList;
    private LoadingPopup loadingPopup;

    public ProviderProfilerV044(WebDriver driver) {
        super(driver, MODULE_ID);
        analysisPeriod = new AnalysisPeriod(getDriver());
        businessLevelsComponent = new BusinessLevelsComponent(getDriver());
        dataGrid = new ProviderProfilerDataGrid(getDriver());
        memberList = new MemberList(getDriver());
        loadingPopup = new LoadingPopup(getDriver());
    }

    private IProviderDrillPage drillDownToProvider(String specialityName){
        doFilterSpecialty(specialityName);
        IProviderDrillPage drillPage = (IProviderDrillPage)  dataGrid.getRows().get(0).doDrillByOnSameWindow("Provider");
        drillPage.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return drillPage;
    }

    @Override
    public IIndividualDrillPage drillDownFromProviderToIndividual(String specialityName){
        IDataGridRow firstRow = drillDownToProvider(specialityName).getDataGrid().getRows().get(0);
        IIndividualDrillPage drillPage = (IIndividualDrillPage) firstRow.doDrillByOnSameWindow("Individual");
        return drillPage;
    }

    @Override
    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(excelFileName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not export to excel " + excelFileName);
        }
        return true;
    }


    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public IMemberList getMemberList() {
        return memberList;
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
    public IAnalysisPeriod getAnalysisPeriod() {
        return analysisPeriod;
    }

    @Override
    public IBusinessLevelsComponent getBusinessLevel() {
        return businessLevelsComponent;
    }

    public void doFilterSpecialty(String specialtyName){
      getDataGrid().doFilter(ProviderProfilerDataGridColumn.SPECIALTY,specialtyName);
        waitForAjaxCallToFinish();
        if(getDataGrid().getData().size() == 0){
            throw new Error("No Specialty with Name : " + specialtyName);
        }
    }

    private void waitForAjaxCallToFinish() {
        WaitUtils.waitForMilliSeconds(100);
        loadingPopup.waitTillDisappears();
        checkState(!loadingPopup.isDisplayed(), "The popup should have disappeared");
    }

}

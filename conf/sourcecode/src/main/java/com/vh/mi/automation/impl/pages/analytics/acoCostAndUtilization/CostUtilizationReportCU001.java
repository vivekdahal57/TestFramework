package com.vh.mi.automation.impl.pages.analytics.acoCostAndUtilization;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.pages.analytics.acoCostAndUtilization.ICostUtilizationReportCU001;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by i80448 on 11/20/2014.
 */
public class CostUtilizationReportCU001 extends AbstractLandingPage implements ICostUtilizationReportCU001 {
    public static final String MODULE_ID = "415";
    private IAnalysisPeriod analysisPeriod;
    private IBusinessLevelsComponent businessLevelsComponent;

    public CostUtilizationReportCU001(WebDriver driver) {
        super(driver, MODULE_ID);
        analysisPeriod = new AnalysisPeriod(getDriver());
        businessLevelsComponent = new BusinessLevelsComponent(getDriver());
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
    public IDataGrid getDataGrid() {
        return null;
    }

    @Override
    public boolean isDataGridCustomizable() {
        return false;
    }

    @Override
    public IDataGridCustomizer getDataGridCustomizer() {
        return null;
    }

}

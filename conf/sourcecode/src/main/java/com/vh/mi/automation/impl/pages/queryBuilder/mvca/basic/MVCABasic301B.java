package com.vh.mi.automation.impl.pages.queryBuilder.mvca.basic;

import com.vh.mi.automation.api.comp.IAnalysisCondition;
import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.pages.queryBuilder.mvca.basic.IMVCABasic301B;
import com.vh.mi.automation.impl.comp.AnalysisCondition;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.ReportingBy;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i80448 on 9/17/2014.
 */
public class MVCABasic301B extends AbstractLandingPage implements IMVCABasic301B {
    public static final String MODULE_ID = "81";
    private IAnalysisPeriod analysisPeriod;
    private IBusinessLevelsComponent businessLevelsComponent;
    private IReportingBy reportingBy;
    private IAnalysisCondition analysisCondition;
    private final IDataGrid dataGrid;



    public MVCABasic301B(WebDriver driver) {
        super(driver, MODULE_ID);
        analysisPeriod = new AnalysisPeriod(getDriver());
        businessLevelsComponent = new BusinessLevelsComponent(getDriver());
        reportingBy = PageFactory.initElements(getDriver(), ReportingBy.class);
        analysisCondition = new AnalysisCondition(getDriver());
        dataGrid = new MVCABasic301BDataGrid(getDriver());
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
    public IReportingBy getReportingBy() {
        return reportingBy;
    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
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
    public IAnalysisCondition getAnalysisCondition() {
        return analysisCondition;
    }

}

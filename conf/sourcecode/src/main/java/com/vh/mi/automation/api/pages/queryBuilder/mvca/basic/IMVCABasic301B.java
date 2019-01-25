package com.vh.mi.automation.api.pages.queryBuilder.mvca.basic;

import com.vh.mi.automation.api.comp.IAnalysisCondition;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;
import com.vh.mi.automation.api.features.IHaveReportingBy;
import com.vh.mi.automation.api.pages.common.IMIPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i80448 on 9/17/2014.
 */
public interface IMVCABasic301B extends IMIPage, IAmLandingPage, IHaveBusinessLevel, IHaveAnalysisPeriod, IHaveReportingBy {

    public IDataGrid getDataGrid();

    public boolean isDataGridCustomizable();

    public IDataGridCustomizer getDataGridCustomizer();

    public IAnalysisCondition getAnalysisCondition();
}

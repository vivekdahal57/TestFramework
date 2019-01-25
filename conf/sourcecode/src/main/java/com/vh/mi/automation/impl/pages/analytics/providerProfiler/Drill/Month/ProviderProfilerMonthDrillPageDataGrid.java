package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Month;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.DiagnosisCode.DiagnosisCodeDrillPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 1/11/18.
 */
public class ProviderProfilerMonthDrillPageDataGrid extends AbstractDataGrid {

    public ProviderProfilerMonthDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
        columnIdExtractor = new ProviderProfilerMonthDrillPageColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return ProviderProfilerMonthDrillPageDataGridColumns.fromId(id);
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch(drillOption){
            case "Diagnosis Code":
                return DiagnosisCodeDrillPage.class;
        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }
}

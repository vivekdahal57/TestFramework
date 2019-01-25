package com.vh.mi.automation.impl.pages.analytics.providerProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.ProviderProfilerDataGridColumn;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Claims.ClaimsDrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.DiagnosisCode.DiagnosisCodeDrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Individual.IndividualDrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Month.ProviderProfilerMonthDrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Procedure.ProcedureDrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Provider.ProviderDrillPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by i82716 on 6/27/2017.
 */
public class ProviderProfilerDataGrid extends AbstractDataGrid {

    public ProviderProfilerDataGrid(WebDriver driver) {
        super(driver);
        columnIdExtractor = new ProviderProfilerColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return ProviderProfilerDataGridColumn.fromId(id);
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch (drillOption) {
            case "Diagnosis Code":
                return DiagnosisCodeDrillPage.class;
            case "Procedure":
                return ProcedureDrillPage.class;
            case "Provider":
                return ProviderDrillPage.class;
            case "Claims":
                return ClaimsDrillPage.class;
            case "Individual":
                return IndividualDrillPage.class;
            case "Month":
                return ProviderProfilerMonthDrillPage.class;
        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }

}

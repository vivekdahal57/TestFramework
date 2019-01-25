package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.DiagnosisCode;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Procedure.ProcedureDrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Provider.ProviderDrillPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 1/12/18.
 */
public class DiagnosisCodeDrillPageDataGrid extends AbstractDataGrid {

    public DiagnosisCodeDrillPageDataGrid(WebDriver webDriver){
        super(webDriver);
        columnIdExtractor = new DiagnosisCodeDrillPageColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return DiagnosisCodeDrillPageDataGridColumns.fromId(id);
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch(drillOption){
            case "Procedure":
                return ProcedureDrillPage.class;
            case "Provider":
                return ProviderDrillPage.class;
        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }
}

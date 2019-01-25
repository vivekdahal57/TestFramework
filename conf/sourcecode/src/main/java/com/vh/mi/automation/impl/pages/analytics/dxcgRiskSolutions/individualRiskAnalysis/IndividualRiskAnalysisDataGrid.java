package com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.IndividualRiskAnalysisDataGridColumn;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.drill.RP150MemberDetailReportDrillPage;
import org.openqa.selenium.WebDriver;

public class IndividualRiskAnalysisDataGrid extends AbstractDataGrid {

    public IndividualRiskAnalysisDataGrid(WebDriver driver) {
        super(driver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return IndividualRiskAnalysisDataGridColumn.fromId(id);
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch (drillOption) {
            case "DxCG Risk Solutions Member Detail":
                return RP150MemberDetailReportDrillPage.class;

        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }
}

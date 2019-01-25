package com.vh.mi.automation.impl.pages.analytics.hcciClaims;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciClaims01DataGridColumn;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01DrillPage;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillLevels.CommonBusinessLevelDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillTrendBy.DrillMonth.MonthDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillTrendBy.Year.YearDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillTrendBy.Quarter.QuarterDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillTrendGraphFor.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by nimanandhar on 9/8/2014.
 */
public class HcciClaims01DataGrid extends AbstractDataGrid {

    public HcciClaims01DataGrid(WebDriver driver) {
        super(driver);
        columnIdExtractor = new HcciClaims01ColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return HcciClaims01DataGridColumn.fromId(id);
    }


    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch (drillOption) {
            case "Claims":
                return HcciS865DrillPage.class;
            case "Claim Lines":
                return HcciClaims01DrillPage.class;
            case "Month":
                return MonthDrillPage.class;
            case "Quarter":
                return QuarterDrillPage.class;
            case "Year":
                return YearDrillPage.class;
            case "Total Paid":
                return TotalPaidDrillPage.class;
            case "Member Months":
                return MemberMonthsDrillPage.class;
            case "$PMPM":
                return PMPMDrillPage.class;
            case "Cost per Unit":
                return CostPerUnitDrillPage.class;
            case "Units":
                return UnitsDrillPage.class;
            case "Employee Months":
                return EmployeeMonthsDrillPage.class;
            case "$PEPM":
                return PEPMDrillPage.class;
            case "Services":
                return HcciServicesDrillPage.class;
            default:
                return CommonBusinessLevelDrillPage.class;
        }
        //throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }




}

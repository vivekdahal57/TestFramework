package com.vh.mi.automation.impl.pages.analytics.claims;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.analytics.claims.Claims01DataGridColumn;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.claims.DrillLevels.CommonBusinessLevelDrillPage;
import com.vh.mi.automation.impl.pages.analytics.claims.DrillTrendBy.DrillMonth.MonthDrillPage;
import com.vh.mi.automation.impl.pages.analytics.claims.DrillTrendBy.Quarter.QuarterDrillPage;
import com.vh.mi.automation.impl.pages.analytics.claims.DrillTrendBy.Year.YearDrillPage;
import com.vh.mi.automation.impl.pages.analytics.claims.DrillTrendGraphFor.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by nimanandhar on 9/8/2014.
 */
public class Claims01DataGrid extends AbstractDataGrid {

    public Claims01DataGrid(WebDriver driver) {
        super(driver);
        columnIdExtractor = new Claims01ColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return Claims01DataGridColumn.fromId(id);
    }


    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch (drillOption) {
            case "Claims":
                return Claims01DrillPage.class;
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
            default:
                return CommonBusinessLevelDrillPage.class;
        }
        //throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }




}

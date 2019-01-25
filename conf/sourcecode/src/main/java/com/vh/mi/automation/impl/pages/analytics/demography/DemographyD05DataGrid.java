package com.vh.mi.automation.impl.pages.analytics.demography;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.demography.DemographyD05DataGridColumn;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.demography.drill.D07AgeGroupDrillPage;
import com.vh.mi.automation.impl.pages.analytics.demography.drill.D20MMDrillPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by nimanandhar on 9/10/2014.
 */
public class DemographyD05DataGrid extends AbstractDataGrid {
    public DemographyD05DataGrid(WebDriver driver) {
        super(driver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return DemographyD05DataGridColumn.fromId(id);
    }

   /* static {
        columnIdMap.put(COMPANY, "d2Form:simpleGrid:column_Level_header_sortCommandLink");
        columnIdMap.put(MALE_MEDICAL_PAID, "d2Form:simpleGrid:column_MaleMedCost_header_sortCommandLink");
        columnIdMap.put(FEMALE_MEDICAL_PAID, "d2Form:simpleGrid:column_FemMedCost_header_sortCommandLink");
        columnIdMap.put(MALE_RX_PAID, "d2Form:simpleGrid:column_MaleRxCost_header_sortCommandLink");
        columnIdMap.put(FEMALE_RX_PAID, "d2Form:simpleGrid:column_FemRxCost_header_sortCommandLink");
        columnIdMap.put(MM_TOTAL, "d2Form:simpleGrid:column_MemberMonth_header_sortCommandLink");
        columnIdMap.put(PMPM_TOTAL, "d2Form:simpleGrid:column_PMPM_header_sortCommandLink");
        columnIdMap.put(PAID_TOTAL, "d2Form:simpleGrid:column_TotalPaid_header_sortCommandLink");
    }*/

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch (drillOption) {
            case "Age Group":
                return D07AgeGroupDrillPage.class;

            case "MM":
                return D20MMDrillPage.class;

        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }
}

package com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.claims.DrillLevels.CommonBusinessLevelDrillPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by i82298 on 1/16/2017.
 */
public class CPM01DataGrid extends AbstractDataGrid{
    public CPM01DataGrid(WebDriver driver) {
        super(driver);
    }
    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(
            String drillOption) {
        switch (drillOption) {
        case "Population Risk Drivers":
            return null;

            default:
                return CommonBusinessLevelDrillPage.class;

        }
        //throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return CPM01DataGridColumn.fromId(id);
    }


}

package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.SubDetailedServiceDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i20841 on 7/13/2018.
 */
public class SubDetailedServiceDrillPageDataGrid extends AbstractDataGrid {
    public SubDetailedServiceDrillPageDataGrid(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return SubDetailedServiceDrillPageDataGridColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck() {
        return SubDetailedServiceDrillPageDataGridColumns.SUB_DETAILED_SERVICE_CATEGORY;
    }
}

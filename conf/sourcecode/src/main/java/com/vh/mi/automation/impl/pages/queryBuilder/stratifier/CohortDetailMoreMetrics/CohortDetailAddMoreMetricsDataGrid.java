package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.CohortDetailMoreMetrics;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.CohortDetailAddMoreMetrics.CohortDetailAddMoreMetricsDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10314 on 7/13/2017.
 */
public class CohortDetailAddMoreMetricsDataGrid extends AbstractDataGrid {
    public CohortDetailAddMoreMetricsDataGrid(WebDriver driver) {
        super(driver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return CohortDetailAddMoreMetricsDataGridColumn.fromId(id);
    }
}

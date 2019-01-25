package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.individuals.IndClaimDetailsColumns;
import org.openqa.selenium.WebDriver;

/**
 * Created by i80690 on 12/7/2016.
 */
public class IndvClaimDetailsDataGrid extends AbstractDataGrid {
    public IndvClaimDetailsDataGrid(WebDriver driver) {
        super(driver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return IndClaimDetailsColumns.fromId(id);
    }
}

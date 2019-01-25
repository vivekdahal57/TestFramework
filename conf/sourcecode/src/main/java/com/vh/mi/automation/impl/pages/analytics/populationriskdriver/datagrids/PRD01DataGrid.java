package com.vh.mi.automation.impl.pages.analytics.populationriskdriver.datagrids;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.Indv301DrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82298 on 5/31/2017.
 */

public class PRD01DataGrid extends AbstractDataGrid {
    public PRD01DataGrid(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override protected IDataGridColumn getColumn(String id) {
        return PRD01DataGridColumn.fromId(id);
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(
            String drillOption) {
        switch (drillOption) {
        case "Individuals":
            return Indv301DrillPage.class;
        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }

    public IDataGridRow getRowWithDrillOption() {
        IDataGridRow firstRowWithDrillOption = getFirstRowWithDrillOptions();

        if (firstRowWithDrillOption == null) {
            firstRowWithDrillOption = getFirstRowWithDrillOptions();
        }
        if (firstRowWithDrillOption == null) {
            throw new AutomationException("No rows contains drill options");
        }
        return firstRowWithDrillOption;
    }

    private IDataGridRow getFirstRowWithDrillOptions() {
        ImmutableList<IDataGridRow> rows = getRows();
        IDataGridRow firstRowWithDrillOption = null;
        for (IDataGridRow row : rows) {

            if (row.hasDrillOptions()) {
                firstRowWithDrillOption = row;
                break;
            }
        }
        return firstRowWithDrillOption;
    }
}

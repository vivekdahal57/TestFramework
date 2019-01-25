package com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 3/14/18.
 */
public class ComorbidityCD108DrillPageDataGrid extends AbstractDataGrid {


    public ComorbidityCD108DrillPageDataGrid(WebDriver driver){
        super(driver);
        columnIdExtractor = new ComorbidityCD108ColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
       return ComorbidityCD108DrillPageDataGridColumns.fromId(id);
    }
}

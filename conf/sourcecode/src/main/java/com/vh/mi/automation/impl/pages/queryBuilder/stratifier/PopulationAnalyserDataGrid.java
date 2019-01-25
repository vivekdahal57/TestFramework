package com.vh.mi.automation.impl.pages.queryBuilder.stratifier;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.PopulationAnalyserDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i82716 on 4/25/2017.
 */
public class PopulationAnalyserDataGrid extends AbstractDataGrid {

    public PopulationAnalyserDataGrid(WebDriver driver){
        super(driver);
        columnIdExtractor = new PopulationAnalyserColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
       return PopulationAnalyserDataGridColumn.fromId(id);
    }
}

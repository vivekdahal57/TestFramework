package com.vh.mi.automation.impl.pages.analytics.conversionAnalyzer;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.claims.Claims01ColumnIdExtractor;
import org.openqa.selenium.WebDriver;

/**
 * Created by nimanandhar on 12/9/2014.
 */
public class ConversionAnalyzerA17DataGrid extends AbstractDataGrid {

    public ConversionAnalyzerA17DataGrid(WebDriver driver) {
        super(driver);
        //the id pattern matches Claims01. Need to rename Claims01ColumnIdExtractor to something more generic
        columnIdExtractor = new ConversionAnalyzerColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return ConversionAnalyzerColumns.fromId(id);
    }

}

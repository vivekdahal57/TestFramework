package com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 3/14/18.
 */
public class CD018DiseaseAnalysisByMembersDrillPageDataGrid extends AbstractDataGrid {

    public CD018DiseaseAnalysisByMembersDrillPageDataGrid(WebDriver driver){
        super(driver);
       columnIdExtractor = new CD018DiseaseAnalysisByMembersDrillPageDefaultColumnExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return CD018DiseaseAnalysisByMembersDrillPageDataGridColumns.fromId(id);
    }
}

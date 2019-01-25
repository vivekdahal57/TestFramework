package com.vh.mi.automation.impl.pages.analytics.snfProfiler.Drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 11/16/17.
 */
public class SP120SNFAdmissionsDrillPageDataGrid extends AbstractDataGrid {

    public SP120SNFAdmissionsDrillPageDataGrid(WebDriver driver){
        super(driver);
        columnIdExtractor = new SP120SNFAdmissionsDrillPageColumnIdExtractor();

    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return null;
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        return null;
    }
}

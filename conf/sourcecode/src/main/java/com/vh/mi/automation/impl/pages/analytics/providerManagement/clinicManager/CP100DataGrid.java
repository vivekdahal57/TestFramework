package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.DrillPhysician.CP110PhysiciansDrillPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 11/20/17.
 */
public class CP100DataGrid extends AbstractDataGrid {

    public CP100DataGrid(WebDriver driver){
        super(driver);
        columnIdExtractor = new CP100ColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return CP100Columns.fromId(id);
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch (drillOption) {
            case "Physicians":
                return CP110PhysiciansDrillPage.class;
        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }
}

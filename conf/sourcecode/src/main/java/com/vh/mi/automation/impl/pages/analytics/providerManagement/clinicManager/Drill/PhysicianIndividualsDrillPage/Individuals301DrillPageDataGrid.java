package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianIndividualsDrillPage;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.Drill.IIndividual301DrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 11/27/17.
 */
public class Individuals301DrillPageDataGrid extends AbstractDataGrid {

    public Individuals301DrillPageDataGrid(WebDriver driver){
        super(driver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return null;
    }
}

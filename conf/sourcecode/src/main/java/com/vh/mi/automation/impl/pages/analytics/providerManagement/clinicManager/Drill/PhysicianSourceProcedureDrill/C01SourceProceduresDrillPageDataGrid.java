package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianSourceProcedureDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 11/27/17.
 */
public class C01SourceProceduresDrillPageDataGrid extends AbstractDataGrid {


    public C01SourceProceduresDrillPageDataGrid(WebDriver driver){
        super(driver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
         return C01SourceProceduresDrillPageColumns.fromId(id);
    }
}

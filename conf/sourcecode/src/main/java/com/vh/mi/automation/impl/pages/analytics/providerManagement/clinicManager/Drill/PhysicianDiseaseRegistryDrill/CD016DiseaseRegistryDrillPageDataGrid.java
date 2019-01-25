package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianDiseaseRegistryDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 11/21/17.
 */
public class CD016DiseaseRegistryDrillPageDataGrid extends AbstractDataGrid {


    public CD016DiseaseRegistryDrillPageDataGrid(WebDriver driver){
        super(driver);
        columnIdExtractor = new CD016DiseaseRegistryDrillPageColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return CD016DiseaseRegistryDrillPageColumns.fromId(id);
    }
}

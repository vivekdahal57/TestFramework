package com.vh.mi.automation.impl.pages.analytics.diseaseRegistry;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.diseaseRegistry.DiseaseRegistryDataGridColumn;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.drill.CD018DiseaseAnalysisByMembersDrillPage;
import com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.drill.ComorbidityCD108DrillPage;
import org.openqa.selenium.WebDriver;

public class DiseaseRegistryDataGrid extends AbstractDataGrid {

    public DiseaseRegistryDataGrid(WebDriver driver) {
        super(driver);
        columnIdExtractor = new DiseaseRegistryCD016ColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return DiseaseRegistryDataGridColumn.fromId(id);
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch (drillOption) {
            case "Comorbidity":
                return ComorbidityCD108DrillPage.class;

            case "Newly Identified Members":
                return CD018DiseaseAnalysisByMembersDrillPage.class;

        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }
}

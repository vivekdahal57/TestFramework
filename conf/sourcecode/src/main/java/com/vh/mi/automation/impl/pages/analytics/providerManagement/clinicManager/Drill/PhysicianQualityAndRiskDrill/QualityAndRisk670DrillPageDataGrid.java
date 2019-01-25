package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianQualityAndRiskDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianQualityAndRiskDrill.Drill.Detail690DrillPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 11/21/17.
 */
public class QualityAndRisk670DrillPageDataGrid extends AbstractDataGrid {

    public QualityAndRisk670DrillPageDataGrid(WebDriver driver){
        super(driver);

    }

    @Override
    protected IDataGridColumn getColumn(String id) {
       return QualityAndRisk670DrillPageColumns.fromId(id);
    }

   @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch(drillOption){
            case "Detail":
                return Detail690DrillPage.class;
        }
        throw new AutomationException("Unrecognized drill Menu "+drillOption);

    }
}

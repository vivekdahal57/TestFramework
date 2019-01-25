package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.DrillPhysician;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianIndividualsDrillPage.Individuals301DrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianSourceProcedureDrill.C01SourceProceduresDrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianProfilerDashBoardDrill.CP150ProfilerDashboardDrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianDiseaseRegistryDrill.CD016DiseaseRegistryDrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianQualityAndRiskDrill.QualityAndRisk670DrillPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 11/20/17.
 */
public class CP110PhysiciansDrillPageDataGrid extends AbstractDataGrid {


    public CP110PhysiciansDrillPageDataGrid(WebDriver driver){
        super(driver);
        columnIdExtractor = new CP110PhysicansDrillPageColumnIdExtractor();
    }


    @Override
    protected IDataGridColumn getColumn(String id) {
     return CP110PhysiciansDrillPageColumns.fromId(id);
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch(drillOption){
            case "Disease Registry":
                return CD016DiseaseRegistryDrillPage.class;

            case "Quality and Risk Measures":
                return QualityAndRisk670DrillPage.class;

            case "Source Procedures":
                return C01SourceProceduresDrillPage.class;

            case "Profiler Dashboard":
                return CP150ProfilerDashboardDrillPage.class;

            case "Individuals":
                return Individuals301DrillPage.class;

        }
        throw new AutomationException("Unrecognized drill Menu "+drillOption);

    }
}

package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillLevels;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.drill.drillPopulationRiskDrivers.AllPopulationDrillPage;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.drill.drillPopulationRiskDrivers.LOHPopulation;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.ACCDxCGDrill.ACCDxCGDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.CCDxCGDrill.CCDxCGDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.DetailedServiceDrill.DetailedServiceDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.DiagnosisDrill.DiagnosisDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.DiagnosisGroupDrill.DiagnosisGroupDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.DrillDrug.DrugDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.DrillDrugStrength.DrugStrengthDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.DrillPrescriber.PrescriberDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.DxGDXCGDrill.DxGDXCGDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.FundTypeDrill.FundTypeDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.ProcedureDrill.ProcedureDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.ProviderDrill.ProviderDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.RCCDxCGDrill.RCCDxCGDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.ServiceDrill.ServiceDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.Specialty.SpecialtyDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.DrillRxClass.RxClassDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.ProcedureGroupDrill.ProcedureGroupDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.SubDetailedServiceDrill.SubDetailedServiceDrillPage;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.SubServiceDrill.SubServiceDrillPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 11/30/17.
 */
public class CommonBusinessLevelDrillPageDataGrid extends AbstractDataGrid {

    public CommonBusinessLevelDrillPageDataGrid(WebDriver webdriver){
        super(webdriver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return CommonBusinessLevelDrillPageColumns.fromId(id);
    }

    @Override
    public IDataGridColumn getColumnForUnknownBlankCheck(){
        return CommonBusinessLevelDrillPageColumns.PLAN_TYPE;
    }


    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
       switch(drillOption){
           case  "Primary Diagnosis" :
            return DiagnosisDrillPage.class;

           case  "Diagnosis Group":
               return DiagnosisGroupDrillPage.class;

           case  "DxCG: ACC":
               return ACCDxCGDrillPage.class;

           case "DxCG: RCC":
               return RCCDxCGDrillPage.class;

           case "DxCG: CC":
               return CCDxCGDrillPage.class;

           case "DxCG: DxG":
               return DxGDXCGDrillPage.class;

           case "Fund Type":
               return FundTypeDrillPage.class;

           case "Primary/Highest-Paid Procedure":
               return ProcedureDrillPage.class;

           case "Procedure Group":

               return ProcedureGroupDrillPage.class;

           case "Provider":
               return ProviderDrillPage.class;

           case "Place of Service":
               return ServiceDrillPage.class;

           case "Specialty":
               return SpecialtyDrillPage.class;

           case "Service Category":
               return ServiceDrillPage.class;

           case "Sub-service Category":
               return SubServiceDrillPage.class;

           case "Detailed Service Category":
               return DetailedServiceDrillPage.class;

           case "Sub-detailed Service Category":
               return SubDetailedServiceDrillPage.class;

           case "Drug":
               return DrugDrillPage.class;

           case "Drug Strength":
               return DrugStrengthDrillPage.class;

           case "Prescriber":
               return PrescriberDrillPage.class;

           case "Rx Class":
               return RxClassDrillPage.class;

           case "All Population":
               return AllPopulationDrillPage.class;

           case "Top 1% LOH Population":
               return LOHPopulation.class;

               default:
                   return CommonBusinessLevelDrillPage.class;
       }
       // throw new AutomationException("Unrecognized drill Menu "+drillOption);
    }


}

package com.vh.mi.automation.impl.pages.analytics.hcciClaims;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;

/**
 * Created by i10359 on 2/20/18.
 */
public enum HcciClaims01DrillPageDataGridColumns implements IDataGridColumn {
    PLAN_TYPE("LVL1ID" , "Plan Type" , DataType.STRING),
    CLAIM_NUMBER("CLMNUM" , "Claim Number" , DataType.STRING),
    INDIVIDUAL_ID("MEMID" , "Individual Id" , DataType.STRING),
    SERVICE_DATE("SERVICEDATE" , "Service Date", DataType.DATE),
    UNIT("LVL4DESC" , "Unit" , DataType.STRING),
    PROVIDER("PROVNAME" , "Provider" , DataType.STRING),
    PROVIDER_ID("ProvId" , "Provider Id" , DataType.STRING),
    SPECIALTY("SPECDESC" , "Specialty" , DataType.STRING),
    PLACE_OF_SERVICE("POSRXDESC" , "Place Of Service" , DataType.STRING),
    DIAGNOSIS_CODE("SRCDIAGCODE" , "Diagnosis Code", DataType.INTEGER),
    DESCRIPTION("SRCDIAGDESC" , "Description", DataType.STRING ),
    PROCEDURE_CODE("SRCPROCNDCCODE", "Procedure Code", DataType.MONEY),
    PROCEDURE("SRCPROCNDCDESC" , "Procedure" , DataType.STRING),
    MODIFIER_CODE("MODIFIERCODE" , "Modifier Code", DataType.STRING),
    PRESCRIBING_PHYSICIAN("PROVNAME" , "Prescribing Physician", DataType.STRING),
    FDB_GROUPER_CODE_DESCRIPTION("POSRXDESC", "FDB Grouper Code Description" , DataType.STRING),
    DRUG_NAME("PROCDRUGDESC", "Drug Name", DataType.STRING),
    SERVICE_UNITS("SERVICEUNITS" , "Service Units", DataType.INTEGER),
    PAID_AMOUNT("PAIDAMT" , "Paid Amount", DataType.MONEY)
;

    private String id;
    private String label;
    private DataType dataType;

    private HcciClaims01DrillPageDataGridColumns(String id, String label, DataType dataType){
        this.id = id;
        this.label = label;
        this.dataType = dataType;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public DataType getDataType() {
        return this.dataType;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public static IDataGridColumn fromId(String id){
        for(HcciClaims01DrillPageDataGridColumns c : HcciClaims01DrillPageDataGridColumns.values()){
            if(c.getId().equals(id))
                return c;
        }
        throw new RuntimeException("No MedicalClaimDetail Column found for id " + id);
    }
}

package com.vh.mi.automation.api.pages.analytics.hcciClaims;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * Created by nimanandhar on 8/28/2014.
 */
public enum HcciClaims01S865DataGridColumn implements IDataGridColumn {

    INDIVIDUAL_ID("MEMID", "Individual ID", DataType.STRING),
    INDIVIDUAL_NAME("MEMNAME", "Individual Name", DataType.STRING),
    CLAIM_HEADER_NUMBER("CLAIM_ID", "Claim Header Number", DataType.STRING),
    PLAN_TYPE("LVL1DESC", "Plan Type", DataType.STRING),
    HMO("LVL2DESC", "HMO", DataType.STRING),
    COMPANY("LVL3DESC", "Company", DataType.STRING),
    UNIT("LVL4DESC", "Unit", DataType.STRING),
    BUSINESLEVEL5("LVL5DESC", "Business Level 5", DataType.STRING),
    BUSINESSLEVEL6("LVL6DESC", "Business Level 6", DataType.STRING),
    CLAIM_TYPE("CLMTYPEDESC", "Claim Type", DataType.STRING),
    CLAIM_CATEGORY("CLAIM_CATEGORY", "Claim Category", DataType.STRING),
    SERVICE_CATEGORY("SERVICE_CATEGORY", "Service Category", DataType.STRING),
    SUB_SERVICE_CATEGORY("SUB_SERVICE_CATEGORY", "Sub-Service Category", DataType.STRING),
    DETAILED_SERVICE_CATEGORY("DET_SERVICE_CATEGORY", "Detailed Service Category", DataType.STRING),
    SUB_DETAILED_SERVICE_CATEGORY("SUB_DET_SERVICE_CATEGORY", "Sub-Detailed Service Category", DataType.STRING),
    SERVICE_ID("SERVICE_ID", "Service ID", DataType.DOUBLE),
    PRIMARY_PROCEDURE_CODE("SRCPROCCODE", "Primary/Highest-Paid Procedure Code", DataType.STRING),
    PRIMARY_PROCEDURE("SRCPROCDESC", "Primary Procedure", DataType.STRING),
    DRGCODE("DRGCODE", "DRG", DataType.STRING),
    CALCULATED_DRG_CODE("CALCULATED_DRGCODE", "Calculate DRG Code", DataType.STRING),
    CALCULATED_DRGDESC("CALCULATED_DRGDESC", "Calculate DRG Description", DataType.STRING),
    START_DATE("START_DATE", "Start Date", DataType.DATE),
    END_DATE("END_DATE", "End Date", DataType.DATE),
    BILLEDAMT("BILLEDAMT", "Billed Amount", DataType.INTEGER),
    ALLOWEDAMT("ALLOWEDAMT", "Allowed Amount", DataType.INTEGER),
    PAIDAMT("PAIDAMT", "Paid Amount", DataType.INTEGER),
    SERVICEUNITS("SERVICEUNITS", "Service Units", DataType.INTEGER),
    PRIMARY_DIAGNOSIS_CODE("SRCDIAGCODE", "Primary Diagnosis Code", DataType.STRING),
    PRIMARY_DIAGNOSIS("SRCDIAGDESC", "Primary Diagnosis", DataType.STRING),
    PROVIDER_ID("PROVID", "Provider ID", DataType.STRING),
    PROVIDER("PROVNAME", "Provider", DataType.STRING),
    SPECIALTY("SPECDESC", "Specialty", DataType.STRING),
    PLACE_OF_SERVICE("POSDESC", "Place of Service", DataType.STRING),
    FUND_TYPE("FUNDDESC", "Fund Type", DataType.STRING),
    CLAIM_DATA_ISSUE("CLAIM_DATA_ISSUE", "Claim Data Issue", DataType.STRING);




    private static Logger logger = LoggerFactory.getLogger(HcciClaims01S865DataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private HcciClaims01S865DataGridColumn(String id, String label, DataType dataType) {
        this.id = id;
        this.label = label;
        this.dataType = dataType;
    }

    @Override
    public DataType getDataType() {
        return this.dataType;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public static IDataGridColumn fromId(String id) {
        for (HcciClaims01S865DataGridColumn c : HcciClaims01S865DataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }
        throw new RuntimeException("No HcciClaims01 Column found for id " + id);
    }

    public static IDataGridColumn fromLabel(String label) {
        for (HcciClaims01S865DataGridColumn c : HcciClaims01S865DataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label {} ", label);
        return null;
    }
}

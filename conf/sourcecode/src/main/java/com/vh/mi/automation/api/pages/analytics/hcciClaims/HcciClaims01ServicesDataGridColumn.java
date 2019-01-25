package com.vh.mi.automation.api.pages.analytics.hcciClaims;


import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by nimanandhar on 8/28/2014.
 */
public enum HcciClaims01ServicesDataGridColumn implements IDataGridColumn {
    PLAN_TYPE("LVL1DESC", "Plan Type", DataType.STRING),
    HMO("LVL2DESC", "HMO", DataType.STRING),
    COMPANY("LVL3DESC", "Company", DataType.STRING),
    UNIT("LVL4DESC", "Unit", DataType.STRING),
    BUSINESLEVEL5("LVL5DESC", "Business Level 5", DataType.STRING),
    BUSINESSLEVEL6("LVL6DESC", "Business Level 6", DataType.STRING),
    INDIVIDUAL_ID("MEMID", "Individual ID", DataType.STRING),
    INDIVIDUAL_NAME("MEMNAME", "Individual NAme", DataType.STRING),
    CLAIM_TYPE("CLMTYPEDESC", "Claim Type", DataType.STRING),
    CLAIM_CATEGORY("CLAIM_CATEGORY", "Claim Category", DataType.STRING),
    SERVICE_CATEGORY("SERVICE_CATEGORY", "Service Category", DataType.STRING),
    SUB_SERVICE_CATEGORY("SUB_SERVICE_CATEGORY", "Sub-Service Category", DataType.STRING),
    DETAILED_SERVICE_CATEGORY("DET_SERVICE_CATEGORY", "Detailed Service Category", DataType.STRING),
    SUB_DETAILED_SERVICE_CATEGORY("SUB_DET_SERVICE_CATEGORY", "Sub-Detailed Service Category", DataType.STRING),
    PRIMARY_PROCEDURE_CODE("SRCPROCCODE", "Primary/Highest-Paid Procedure Code", DataType.STRING),
    PRIMARY_PROCEDURE("SRCPROCDESC", "Primary Procedure", DataType.STRING),
    START_DATE("START_DATE", "Start Date", DataType.DATE),
    END_DATE("END_DATE", "End Date", DataType.DATE),
    BILLEDAMT("BILLEDAMT", "Billed Amount", DataType.INTEGER),
    ALLOWEDAMT("ALLOWEDAMT", "Allowed Amount", DataType.INTEGER),
    PAIDAMT("PAIDAMT", "Paid Amount", DataType.INTEGER),
    SERVICE_COUNT("SERVICE_COUNT", "Service Count", DataType.STRING),
    SERVICEUNITS("SERVICEUNITS", "Service Units", DataType.INTEGER),
    PRIMARY_DIAGNOSIS_CODE("SRCDIAGCODE", "Primary Diagnosis Code", DataType.STRING),
    PRIMARY_DIAGNOSIS("SRCDIAGDESC", "Primary Diagnosis", DataType.STRING),
    DRGCODE("DRGCODE", "DRG", DataType.STRING),
    CALCULATED_DRG_CODE("CALCULATED_DRGCODE", "Calculate DRG Code", DataType.STRING),
    CALCULATED_DRGDESC("CALCULATED_DRGDESC", "Calculate DRG Description", DataType.STRING),
    PROVIDER_ID("PROVID", "Provider ID", DataType.STRING),
    PROVIDER("PROVNAME", "Provider", DataType.STRING),
    SPECIALTY("SPECDESC", "Individual ID", DataType.STRING),
    PLACE_OF_SERVICE("POSDESC", "Place of Service", DataType.STRING),
    FUND_TYPE("FUNDDESC", "Fund Type", DataType.STRING),


    //ADJUSTED_NORM("ADJUSTED_NORM", "Adjusted Norm", DataType.STRING),


    //HEADERS for sorting
//    CLAIM_TYPE_HEADER("DIMDESC_header", "Claim Type", DataType.STRING),
//    BILLED_AMT_HEADER("BILLEDAMT_header", "Billed", DataType.MONEY),
//    ALLOWED_HEADER("ALLOWEDAMT_header", "Allowed", DataType.MONEY),
//    TOTAL_PAID_HEADER("TOTPAID_header", "Total Paid", DataType.MONEY),
//    MEMBER_MONTHS_HEADER("MEMMTH_header", "Member Months", DataType.INTEGER),
//    PMPM_HEADER("PMPM_header", "$PMPM", DataType.MONEY),
//    COST_PER_UNIT_HEADER("COSTPERUNIT_header","Cost per Unit",DataType.MONEY),
//    UNITS_PER_1000_HEADER("UNITSPERK_header","Units per 1000",DataType.MONEY),
//    UNITS_HEADER("UNITS_header","Units",DataType.MONEY),
//    EMPLOYEE_MONTHS_HEADER("EMPMTH_header","Employee Months",DataType.MONEY),
//    PEPM_HEADER("PEPM_header","$PEPM",DataType.MONEY),
//    MEMBER_PER_1000_HEADER("MEMPERK_header","Member per 1000",DataType.MONEY),

    //Trending Columns
    BILLED_AMT_1("BILLEDAMT1", "P1", DataType.MONEY),
    BILLED_AMT_2("BILLEDAMT2", "P2", DataType.MONEY),
    ALLOWED_AMT_1("ALLOWEDAMT1", "P1", DataType.MONEY),
    ALLOWED_AMT_2("ALLOWEDAMT2", "P2", DataType.MONEY),
    TOTAL_PAID_1("TOTALPAID1", "P1", DataType.MONEY),
    TOTAL_PAID_2("TOTALPAID2", "P2", DataType.MONEY),
    MEMBER_MONTHS_1("MM1", "P1", DataType.INTEGER),
    MEMBER_MONTHS_2("MM2", "P2", DataType.INTEGER),
    PMPM_1("PMPM1", "P1", DataType.MONEY),
    PMPM_2("PMPM2", "P2", DataType.MONEY),;


    private static Logger logger = LoggerFactory.getLogger(HcciClaims01ServicesDataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private HcciClaims01ServicesDataGridColumn(String id, String label, DataType dataType) {
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
        for (HcciClaims01ServicesDataGridColumn c : HcciClaims01ServicesDataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }
        throw new RuntimeException("No HcciClaims01 Column found for id " + id);
    }

    public static IDataGridColumn fromLabel(String label) {
        for (HcciClaims01ServicesDataGridColumn c : HcciClaims01ServicesDataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label {} ", label);
        return null;
    }
}
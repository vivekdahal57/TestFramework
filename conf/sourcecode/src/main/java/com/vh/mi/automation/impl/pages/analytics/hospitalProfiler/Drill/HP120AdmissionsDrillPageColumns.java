package com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.Drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10359 on 11/14/17.
 */
public enum HP120AdmissionsDrillPageColumns implements IDataGridColumn{
    INDIVIDUAL_ID("MEMID", "Individual Id", DataType.STRING),
    DxCG_RRS_Model("RRS", "DxCG RRS Model", DataType.DOUBLE),
    RI("RI","RI" ,DataType.INTEGER),
    CGI("CGI","CGI" ,DataType.INTEGER),
    HOSPITAL("HOSPITAL","Hospital",DataType.STRING),
    ADMISSION_TYPE("ADMITTYPEDESC","Admission",DataType.STRING),
    DIAGNOSIS_GROUP("VHDIAGGROUP","Diagnosis Group",DataType.STRING),
    PRIMARY_DIAGNOSIS("SRCDIAGDESC","Primary Diagnosis",DataType.STRING),
    ICDTYPE("ICDTYPE","Type",DataType.STRING),
    PROCEDURE_GROUP("PROCEDUREGROUP","Procedure Group",DataType.STRING),
    PRIMARY_PROCEDURE("SRCPROCDESC","Primary Procedure",DataType.STRING),
    PRIMARY_PROCEDURE_CODE("SRCPROCCODE","Primary Procuedure code",DataType.STRING),
    PROCEDURE_TYPE("PROCTYPE","Procedure Type",DataType.STRING),
    ADMISSION_DATE("ADMITDATE","Admission Date",DataType.DATE),
    DISCHARGE_DATE("DISCHRGDATE","Discharge Date",DataType.DATE),
    INPATIENT_DAYS("IPDAYS","Inpatient Days" ,DataType.INTEGER),
    ICU_DAYS("ICUDAYS","ICU Days" ,DataType.INTEGER),
    TOTAL_PAID_AMOUNT("PAIDAMT","Total Paid Amount",DataType.MONEY),
    FACILITY_PAIDAMT("FACPAIDAMT","Total Facility Paid Amount",DataType.MONEY),
    PROFESSIONAL_PAIDAMT("PROPAIDAMT","Total Professional Paid Amount",DataType.MONEY),
    TOTAL_ALLOWED_AMOUNT("ALLOWEDAMT","Total Allowed Amount",DataType.MONEY),
    FACILITY_ALLOWEDAMT("FACALLOWEDAMT","Total Facility Allowed Amount",DataType.MONEY),
    PROFESSIONAL_ALLOWEDAMT("PROALLOWEDAMT","Total Professional Allowed Amount",DataType.MONEY),
    TOTAL_BILLED_AMOUNT("BILLEDAMT","Total Billed Amount",DataType.MONEY),
    TOTAL_EMP_PAID_AMOUNT("EMPPAIDAMT","Total Employee Paid Amount",DataType.MONEY),
    PRECEEDING_ERVISIT("PRECEDINGERVISITS","Preceeding ER Visit",DataType.STRING),
    READMISSION_INDICATOR("ELIGREADMISSION_YN","Readmission Indicator",DataType.STRING),
    ACSC_CINDICATOR("AHRQMEM_YN","ACSC Indicator",DataType.STRING),
    READMISSION_IND_WITH_SNF("READMSNFINDICATOR","Readmission Indicator with SNF Bounceback",DataType.STRING),

    // CMS Medicare Extra Columns
    HOSPITAL_TYPE("HOSPITALTYPE", "Hospital Type", DataType.STRING),
    MDC("MDC", "MDC", DataType.STRING),
    MDC_DESCRIPTION("MDCDESC", "MDC Description", DataType.STRING),
    DRG("DRG", "DRG", DataType.STRING),
    DRG_DESCRIPTION("DRGDESC", "DRG Description", DataType.STRING),
    DRG_TYPE("DRGTYPE", "DRG Type", DataType.STRING) ,
    SRC_DIAG_CODE("SRCDIAGCODE", "Primary Diagnosis Code", DataType.STRING) ,
    DISCHARGE_STAT_GROUP("DISCHARGESTATGROUP", "Discharge Status Group", DataType.STRING) ,
    DISCHARGE_STAT_CODE("DISCHARGESTATCODE", "Discharge Status Code", DataType.STRING) ,
    ADM_SOURCE("ADMSOURCE", "Admission Source", DataType.STRING) ,
    ADM_SOURCE_CODE("ADMSOURCECODE", "Admission Source Code", DataType.STRING) ,
    PLANREADM("PLANREADM", "Planned Readmission", DataType.STRING) ,
    INDEX_PROVNAME("INDEX_PROVNAME", "Index Admission Hospital", DataType.STRING) ,
    INDEX_DRG("INDEXDRG", "Index Admission DRG", DataType.STRING);
    //PQI_PDI_MEASURES("")

    private static Logger logger = LoggerFactory.getLogger(HP120AdmissionsDrillPageColumns.class);

    private String id;
    private String label;
    private DataType dataType;

    private HP120AdmissionsDrillPageColumns(String id, String label, DataType dataType){
        this.id=id;
        this.label=label;
        this.dataType=dataType;
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
        for (HP120AdmissionsDrillPageColumns c : HP120AdmissionsDrillPageColumns.values()) {
            if (c.getId().equals(id)) return c;
        }

        logger.warn("Mapping not available for ID - " + id);

        return null;
    }

    public static IDataGridColumn fromLabel(String label) {
        for (HP120AdmissionsDrillPageColumns c : HP120AdmissionsDrillPageColumns.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label - " + label);

        return null;
    }











}

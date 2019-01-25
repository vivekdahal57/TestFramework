package com.vh.mi.automation.impl.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nimanandhar
 */
public enum Indv301Columns implements IDataGridColumn {
    AGE("AGE", "Age", DataType.CAPPED_INTEGER),
    ALOS("ALOS", "ALOS", DataType.INTEGER),
    SNFALOS("SNFALOS", "SNFALOS", DataType.INTEGER),
    ARI("ARI", "ARI", DataType.INTEGER),
    NEW_ARI("NEWARI", "New ARI", DataType.INTEGER),
    BMI("BMI", "BMI", DataType.STRING),
    CGI("DELRI", "CGI", DataType.INTEGER),
    CM_FOLLOW_UP_ACTION("CM_FOLLOWUP_ACTION", "CM Follow-Up Action", DataType.STRING),
    CM_FOLLOW_UP_DATE("CM_FOLLOWUP_DATE", "CM Follow-Up Date", DataType.STRING),
    CM_STATUS("CMSTATUS", "CM Status", DataType.STRING),
    CURRENT("CURFLAG", "Current", DataType.STRING),
    DM_FOLLOW_UP_ACTION("DM_FOLLOWUP_ACTION", "DM Follow-Up Action", DataType.STRING),
    DM_FOLLOW_UP_DATE("DM_FOLLOWUP_DATE", "DM Follow-Up Date", DataType.STRING),
    DM_STATUS("DM_STATUS", "DM Status", DataType.STRING),
    DOB("DOB", "DOB", DataType.DATE),
    MEMNAME("MEMNAME", "Individual", DataType.STRING),
    FULL_YEAR_MODEL_2("Full_Year_Model2", "n/a", DataType.DOUBLE),
    GENDER("GENDER", "Gender", DataType.STRING),
    HIGHEST_PAID_DIAGNOSIS("PRIDIAGDESC", "Highest Paid Diagnosis", DataType.STRING),
    HOME_PHONE("HOMEPHONE", "Home Phone", DataType.STRING),
    INDIVIDUAL("MEMNAME", "individual", DataType.STRING),
    MEMBER_ID("MEMID", "Individual ID", DataType.STRING),
    IPP("PROVNAME", "IPP", DataType.STRING),
    IPP_ID("PROVID", "IPP ID", DataType.STRING),
    LATEST_UNIT("LVL4DESC", "Latest Unit", DataType.STRING),
    MEDICAL_PAID("MEDPAIDAMT", "Medical Paid", DataType.MONEY),
    EFFMM("EFFMM", "MM", DataType.INTEGER),
    MM("MM", "MM", DataType.INTEGER),
    NEW_CGI("NEWDELRI", "New CGI", DataType.INTEGER),
    NUMBER_OF_ADMISSIONS("ADMITCOUNT", "# of Admissions", DataType.INTEGER),
    NUMBER_OF_OUTPATIENT_ER_VISITS("OPERVISITCOUNT", "# of ER Visits", DataType.INTEGER),
    NUMBER_OF_ER_VISITS("ERVISITCOUNT", "# of ER Visits", DataType.INTEGER),
    NUMBER_OF_OFFICE_VISITS("OFFICEVISITCOUNT", "# of Office Visits", DataType.INTEGER),
    NUMBER_OF_ON_SITE_CLINIC_VISITS("ONSITECLINICVISITCOUNT", "# of On-site Clinic Visits", DataType.INTEGER),
    NUMBER_OF_RE_ADMISSIONS("ELIGREADMITCOUNT", "# of Re-admissions", DataType.INTEGER),
    NUMBER_OF_REHAB_ADMISSION("REHABADMITCOUNT", "# of Rehab Admission", DataType.INTEGER),
    NUMBER_OF_SNF_ADMISSIONS("SNFADMITCOUNT", "# of SNF Admission", DataType.INTEGER),
    NUMBER_OF_URGENT_CARE_VISITS("URGENTCAREVISITCOUNT", "# of Urgent Care Visits", DataType.INTEGER),
    OPT("OPTFLAG", "Opt", DataType.STRING),
    PHARMACY_PAID("RXPAIDAMT", "Pharmacy Paid", DataType.MONEY),
    REL_FLAG("SUBSCRIBERFLAG", "Rel. Flag", DataType.STRING),
    RI("RISKCOUNT", "RI", DataType.INTEGER),
    RI_LAST_30_DAYS("RI30D", "RI Last 30 Days", DataType.INTEGER),
    LOED("LOED", "n/a", DataType.DOUBLE),
    LOH("LOH", "n/a", DataType.DOUBLE),
    RRS_BB_MODEL_1("RRSBB_Model1", "n/a", DataType.DOUBLE),
    RRS_BB_MODEL_2("RRSBB_Model2", "n/a", DataType.DOUBLE),
    RRS_BB_MODEL_3("RRSBB_Model3", "n/a", DataType.DOUBLE),
    RRS_BB_MODEL_4("RRSBB_Model4", "n/a", DataType.DOUBLE),
    RRS_MARKET_SCAN_MODEL_1("RRSMS_Model1", "n/a", DataType.DOUBLE),
    RRS_MARKET_SCAN_MODEL_2("RRSMS_Model2", "n/a", DataType.DOUBLE),
    RRS_MARKET_SCAN_MODEL_3("RRSMS_Model3", "n/a", DataType.DOUBLE),
    RRS_MARKET_SCAN_MODEL_4("RRSMS_Model4", "n/a", DataType.DOUBLE),
    PARTIAL_YEAR_MODEL_1("Partial_Year_Model1", "Partial Year", DataType.DOUBLE),
    FULL_YEAR_MODEL_1("Full_Year_Model1", "Full Year", DataType.DOUBLE),
    PARTIAL_YEAR_MODEL_2("Partial_Year_Model2", "n/a", DataType.DOUBLE),
    PCBB_MODEL_1("PCBB_Model1", "n/a", DataType.DOUBLE),
    PCBB_MODEL_2("PCBB_Model2", "n/a", DataType.DOUBLE),
    PCBB_MODEL_3("PCBB_Model3", "n/a", DataType.DOUBLE),
    PCBB_MODEL_4("PCBB_Model4", "n/a", DataType.DOUBLE),

    RX_CONFLICTS("rxConflicts", "Rx Conflicts", DataType.STRING),
    SECOND_HIGHEST_PAID_DIAGNOSIS("SECDIAGDESC", "Second Highest Paid Diagnosis", DataType.STRING),
    SMOKING_YN("SMOKING_YN", "Smoking", DataType.STRING),
    TOTAL_ALLOWED("TOTALLOWEDAMT", "Total Allowed", DataType.MONEY),
    TOTAL_BILLED("TOTBILLEDAMT", "Total Billed", DataType.MONEY),
    TOTAL_PAID("TOTPAIDAMT", "Total Paid", DataType.MONEY),
    UDF1("UDF1", "UDF1", DataType.STRING),
    UDF2("UDF2", "UDF2", DataType.STRING),
    UDFC3("UDFC3", "MemUDFC3", DataType.STRING),
    UDFC2("UDFC2", "MemUDFC2", DataType.STRING),
    UDFC1("UDFC1", "MemUDFC1", DataType.STRING),
    UDFC4("UDFC4", "UDFC4", DataType.STRING),
    UDFC5("UDFC5", "UDFC5", DataType.STRING),
    UDFN1("UDFN1", "UDFN1", DataType.STRING),
    UDFN2("UDFN2", "UDFN2", DataType.STRING),
    Disease("Disease", "Disease", DataType.STRING),
    ChronicCount("ChronicCount", "ChronicCount", DataType.INTEGER),

    POPULATIONTYPE("POPULATIONTYPE", "POPULATIONTYPE", DataType.STRING),
    ACSADMCOUNT("ACSADMCOUNT", "ACSADMCOUNT", DataType.INTEGER),
    ACSREADMCOUNT("ACSREADMCOUNT", "ACSREADMCOUNT", DataType.INTEGER),
    COPAYAMT("COPAYAMT", "COPAYAMT", DataType.INTEGER),
    COINSAMT("COINSAMT", "COINSAMT", DataType.INTEGER),
    DEDUCTAMT("DEDUCTAMT", "DEDUCTAMT", DataType.INTEGER);


    private static Logger logger = LoggerFactory.getLogger(Indv301Columns.class);

    private String id;
    private String label;
    private DataType dataType;

    private Indv301Columns(String id, String label, DataType dataType) {
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
        for (Indv301Columns c : Indv301Columns.values()) {
            if (c.getId().equals(id)) return c;
        }

        logger.warn("Mapping not available for ID - " + id);

        return null;
    }

    public static IDataGridColumn fromLabel(String label) {
        for (Indv301Columns c : Indv301Columns.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label - " + label);

        return null;
    }
}
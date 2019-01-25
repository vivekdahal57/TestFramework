package com.vh.mi.automation.impl.pages.analytics.hospitalProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i80448 on 11/25/2014.
 */
public enum HospitalProfilesHP100Columns implements IDataGridColumn {
    HOSPITAL_NAME("DIMDESC", "Hospital Name", DataType.STRING),
    TOTAL_FACILITY_PAID_AMT("FACPAIDAMT", "Total Facility Paid Amount", DataType.MONEY),
    TOTAL_AUXILIARY_PAID_AMT("AUXPAIDAMT", "Total Auxiliary Paid Amount", DataType.MONEY),
    TOTAL_BILLED_AMT("BILLEDAMT", "Total Billed Amount", DataType.MONEY),
    TOTAL_NO_OF_ADMISSIONS("ADMITCNT", "Total # of Admissions", DataType.INTEGER),
    ADMISSIONS_PER_1000("ADMITPERK", "Admission per 1000", DataType.DOUBLE),
    ALOS("ALOS", "ALOS", DataType.DOUBLE),
    INPATIENT_DAYS("IPDAYS", "Inpatient Days", DataType.DOUBLE),
    ICU_DAYS("ICUDAYS", "ICU Days", DataType.DOUBLE),
    SUPPLY_EXPENSES("SUPPLYEXP", "Supply Expenses\n", DataType.MONEY),
    INPATIENT_READMISSION_RATE_SNF_ADJUSTED("IRRSNFADJUSTED", "Inpatient Re-admission Rate SNF Adjusted", DataType.DOUBLE),
    TOTAL_PAID_AMT("PAIDAMT", "Total Paid Amount", DataType.MONEY),
    TOTAL_PROFESSIONAL_PAID_AMT("PROPAIDAMT", "Total Professional Paid Amount", DataType.MONEY),
    TOTAL_ALLOWED_AMT("ALLOWEDAMT", "Total Allowed Amount", DataType.MONEY),
    TOTAL_EMP_PAID_AMT("EMPPAIDAMT", "Total Employee Paid Amount", DataType.MONEY),
    TOTAL_NO_OF_READMISSIONS("READMITCNT", "Total # of Re-admissions", DataType.MONEY),
    PAID_AMT_PER_ADMISSION("PAIDAMTPERADMIT", "Paid Amount per Admission", DataType.DOUBLE),
    TOTAL_NO_OF_INDIVIDUALS("MEMCNT", "Total # of Individuals", DataType.INTEGER),
    INPATIENT_DAYS_PER_1000("IPDAYSPERK", "Inpatient Days per 1000", DataType.DOUBLE),
    ICU_DAYS_PER_1000("ICUDAYSPERK", "ICU Days per 1000", DataType.DOUBLE),
    INPATIENT_READMISSION_RATE("IRR", "Inpatient Re-admission Rate", DataType.DOUBLE),
    TOTAL_NO_OF_ADMISSIONS_FROM_ER("ERVISITSADMISSION", "Total # of Admissions from ER", DataType.INTEGER),
    TOTAL_NO_OF_ACSC_ADMISSIONS("ACSCADMITCNT", "# of ACSC Admissions", DataType.INTEGER),
    ACSC_ADMISSIONS_PER_1000("ACSCADMITPERK", "ACSC Admission per 1000", DataType.DOUBLE),
    AVG_ADMISSIONS_PER_PATIENT("ADMITCNTPERPATIENT", "Avg. # of Admissions per Patient", DataType.DOUBLE),
    ACSC_READMISSION("ACSCREADMITCNT", "# of ACSC Readmissions", DataType.INTEGER),
    ACSC_READMISSIONS_PER_1000("ACSCREADMITPERK", "ACSC Readmissions per 1000", DataType.DOUBLE),
    ALLOWED_PER_ADMIT("ALLOWEDAMTPERADMIT", "Allowed Amount per Admission", DataType.MONEY),
    FACILITY_ALLOWED_AMT("FACALLOWEDAMT", "Facility Allowed Amount", DataType.MONEY),
    PROF_ALLOWED_AMT("PROALLOWEDAMT", "Professional Allowed Amount", DataType.MONEY),

    // CMS Medicare Extra Columns
    PER_DISCHARGE_SNF("PERDISCHARGESNF", "Percentate Discharges to SNF", DataType.PERCENTAGE),
    PER_DISCHARGE_HHA("PERDISCHARGEHHA", "Percentate Discharges to HHA", DataType.PERCENTAGE),
    PER_DISCHARGE_HOME("PERDISCHARGEHOME", "Percentate Discharges to Home", DataType.PERCENTAGE),
    PER_DISCHARGE_REHAB("PERDISCHARGEREHAB", "Percentate Discharges to Rehab", DataType.PERCENTAGE)
    ;

    private static Logger logger = LoggerFactory.getLogger(HospitalProfilesHP100Columns.class);

    private String id;
    private String label;
    private DataType dataType;

    private HospitalProfilesHP100Columns(String id, String label, DataType dataType) {
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
        for (HospitalProfilesHP100Columns c : HospitalProfilesHP100Columns.values()) {
            if (c.getId().equals(id)) return c;
        }

        logger.warn("Mapping not available for ID - " + id);

        return null;
    }

    public static IDataGridColumn fromLabel(String label) {
        for (HospitalProfilesHP100Columns c : HospitalProfilesHP100Columns.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label - " + label);

        return null;
    }
}

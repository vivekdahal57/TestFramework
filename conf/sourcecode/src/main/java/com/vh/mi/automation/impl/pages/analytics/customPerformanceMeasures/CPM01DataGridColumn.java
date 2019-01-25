package com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01DataGridColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 Created by i82298 on 1/16/2017. */
public enum CPM01DataGridColumn implements ICPM01DataGridColumn {
// TODO: implement all columns for CPM01

    /*UNIT("UNIT", "Unit", DataType.STRING, null),
    HMO("UNIT", "HMO", DataType.STRING, null),
    COMPANY("UNIT", "Company", DataType.STRING, null),*/
    PLAN_TYPE("UNIT", "Plan Type", DataType.STRING, null),

    DEMO_GROUP("DEMO_GROUP", "Demographic Metrics", DataType.STRING, null),
    TOTAL_MEMBERS("TOTAL_MEMBERS", "Total Members", DataType.INTEGER,DEMO_GROUP),
    AVERAGE_AGE("AVERAGE_AGE", "Average Age", DataType.INTEGER,DEMO_GROUP),


//    Cost Metrics
    COST_GROUP("COST_GROUP", "Cost Metrics", DataType.STRING, null),
//    Total Amount
    TOTALAMT_GROUP("TOTALAMT_GROUP", "Total Amount", DataType.MONEY, COST_GROUP),
    MED_PLAN_PAID_AMOUNT("MED_PLAN_PAID_AMOUNT", "Medical Plan Paid",DataType.MONEY, TOTALAMT_GROUP),


//    PMPM
    PMPM_GROUP("PMPM_Group", "PMPM", DataType.STRING, COST_GROUP),

    MED_PLAN_PAID_PMPM("MED_PLAN_PAID_PMPM", "Medical Plan Paid", DataType.MONEY,PMPM_GROUP),
    RX_PLAN_PAID_PMPM("RX_PLAN_PAID_PMPM", "Pharmacy Plan Paid", DataType.MONEY,PMPM_GROUP),
  /*  OUTPATIENT_PLAN_PAID_PMPM("OUTPATIENT_PLAN_PAID_PMPM", "Outpatient Hospital Plan Paid", DataType.MONEY,PMPM_GROUP),
    INPATIENT_PLAN_PAID_PMPM("INPATIENT_PLAN_PAID_PMPM",
            "Inpatient Hospital Plan Paid", DataType.MONEY, PMPM_GROUP),
*/



    RISK_GROUP("RISK_GROUP", "Utilization Metrics", DataType.STRING, null),
    AVERAGE_RRS_NORMALIZED_GROUP("RRSMS_GROUP" , "Average RRS Normalized group" , DataType.STRING, RISK_GROUP),
    MODEL_NO_18("RRSMS_MODEL1" , "Model # 18" , DataType.DOUBLE,AVERAGE_RRS_NORMALIZED_GROUP),
    MODEL_NO_55("RRSMS_MODEL2" , "Model # 55" , DataType.DOUBLE,AVERAGE_RRS_NORMALIZED_GROUP),
    MODEL_NO_56("RRSMS_MODEL3" , "Model # 56" , DataType.DOUBLE,AVERAGE_RRS_NORMALIZED_GROUP),

    AVERAGE_CGI("AVERAGE_CGI", "Average CGI", DataType.INTEGER,RISK_GROUP),

    UTIL_GROUP("UTIL_GROUP", "Utilization Metrics", DataType.STRING, null),

    INPATIENT_GROUP("INPATIENT_GROUP", "Inpatient", DataType.STRING,UTIL_GROUP),
    ALOS("ALOS", "ALOS", DataType.INTEGER,INPATIENT_GROUP),
    ADMITS_PER_1K("ADMITS_PER_1K", "Admits Per 1000", DataType.INTEGER,INPATIENT_GROUP),
    PERCENT_READMISSION("PERCENT_RE_ADMISSION" , "% Re-admission" , DataType.PERCENTAGE,INPATIENT_GROUP),
    ACSCREADMISSIONS_PER_K("ACSC_RE_ADMISSIONS_RATE_PER_1K" , "ACSC Re-admissions Per 1000" , DataType.INTEGER,INPATIENT_GROUP),


    ER_GROUP("ER_GROUP", "ER", DataType.STRING,UTIL_GROUP),
    ER_VISITS_PER_1K("ER_VISITS_PER_1K", "ER Visits Per 1000",DataType.INTEGER,ER_GROUP),;

    private final String id;
    private final String label;
    private final DataType dataType;
    private final ICPM01DataGridColumn parentColumn;

    private CPM01DataGridColumn(String id, String label, DataType dataType,
            CPM01DataGridColumn parentColumn) {
        this.id = id;
        this.label = label;
        this.dataType = dataType;
        this.parentColumn = parentColumn;
    }

    private static Logger logger = LoggerFactory
            .getLogger(CPM01DataGridColumn.class);

    @Override public String getId() {
        return this.id;
    }

    @Override public DataType getDataType() {
        return this.dataType;
    }

    @Override public String getLabel() {
        return this.label;
    }
    @Override public ICPM01DataGridColumn getParentColumn() {
        return this.parentColumn;
    }

    public static ICPM01DataGridColumn fromId(String id) {
        for (CPM01DataGridColumn c : CPM01DataGridColumn.values()) {
            if (c.getId().equals(id))
                return c;
        }

        logger.warn("Mapping not available for ID - " + id);

        return null;
    }

    public static IDataGridColumn fromLabel(String label) {
        for (CPM01DataGridColumn c : CPM01DataGridColumn.values()) {
            if (c.getLabel().equals(label))
                return c;
        }

        logger.warn("Mapping not available for label - " + label);

        return null;
    }

    public static List<ICPM01DataGridColumn> getColumnsUnderGroup(
            ICPM01DataGridColumn groupColumn) {
                List<ICPM01DataGridColumn> columns = new ArrayList<>();
        for (CPM01DataGridColumn c : CPM01DataGridColumn.values()) {
            if (c.getParentColumn() == groupColumn) {
                columns.add(c);
            }
        }
        return columns;
    }
}

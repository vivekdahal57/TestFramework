package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10359 on 11/20/17.
 */
public enum CP100Columns implements IDataGridColumn{
    UNIT("UNIT","Unit",DataType.STRING),
    NO_OF_PHYSICIAN("NOP", "# of Physicians", DataType.INTEGER),
    INDIVIDUALS_PER_PHYSICIAN("IPP", "Individuals per Physician" ,DataType.DOUBLE),
    AVERAGE_INDIVIDUAL_AGE("AIA", "Average Individual Age", DataType.DOUBLE),
    PERCENT_FEMALE("PerFemale", "% Female", DataType.PERCENTAGE),
    RELATIVE_RISK_SCORE("RRS","Relative Risk Score",DataType.DOUBLE),
    CGI("CGI","CGI",DataType.DOUBLE),
    ALLOWED_PMPM("ALLOWEDPMPM", "Allowed PMPM" , DataType.MONEY),
    TOP_CODED_ALLOWED_PMPM("TCAPMPM", "Top-Coded ($250K/yr) Allowed PMPM" ,DataType.MONEY),
    EFFICIENCY_INDEX_FOR_TOP_CODED_TOTAL_ALLOWED_PMPY("CEI","Efficiency Index for Top-Coded ($250K/yr) Total Allowed PMPY" ,DataType.DOUBLE),
    EFFICIENCY_INDEX_FOR_ADMITS_EXCL_OB_AND_NEONATE("AUEI" ,"Efficiency Index for Admits excl. OB and Neonate",DataType.DOUBLE);


    private static Logger logger = LoggerFactory.getLogger(CP100Columns.class);


    private String id;
    private String label;
    private DataType dataType;


    private CP100Columns(String id, String label, DataType dataType){
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

    public static IDataGridColumn fromId(String id) {
        for (CP100Columns c : CP100Columns.values()) {
            if (c.getId().equals(id)) return c;
        }
        logger.warn("Mapping not available for ID - " + id);
        return null;
    }

    public static IDataGridColumn fromLabel(String label) {
        for (CP100Columns c : CP100Columns.values()) {
            if (c.getLabel().equals(label)) return c;
        }
        logger.warn("Mapping not available for label - " + label);
        return null;
    }
}

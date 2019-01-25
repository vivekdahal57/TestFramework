package com.vh.mi.automation.api.pages.analytics.providerManagement.PhysicianManager;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum PhysicianManagerDataGridColumn implements IDataGridColumn {
    UNIT("UNIT","Unit",DataType.STRING),
    NO_OF_INDIVIDUAL("NOI","# of Individuals",DataType.INTEGER),
    AVERAGE_INDIVIDUAL_AGE("AIA","Average Individual Age",DataType.DOUBLE),
    FEMALE_PERCENTAGE("PerFemale","% Female",DataType.PERCENTAGE),
    RELATIVE_RISK_SCORE("RRS","Relative Risk Score",DataType.DOUBLE),
    CGI("CGI","CGI",DataType.DOUBLE),
    ALLOWED_PMPM("ALLOWEDPMPM","Allowed PMPM",DataType.MONEY),
    TOP_CODED_ALLOWED_PMPM("TCAPMPM","Top-Coded ($250K/yr) Allowed PMPM",DataType.MONEY),
    EFFICIENCY_INDEX_TOTAL_ALLOWED_PMPY("CEI","Efficiency Index for Top-Coded ($250K/yr) Total Allowed PMPY",DataType.DOUBLE),
    EFFICIENCY_INDEX_FOR_OB_AND_NEONATE("AUEI","Efficiency Index for Admits excl. OB and Neonate", DataType.DOUBLE);


    private static Logger logger = LoggerFactory.getLogger(PhysicianManagerDataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private PhysicianManagerDataGridColumn(String id, String label, DataType dataType) {
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
        for (PhysicianManagerDataGridColumn c : PhysicianManagerDataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }
        throw new RuntimeException("No ProviderProfiler Column found for id " + id);
    }

    public static IDataGridColumn fromLabel(String label) {
        for (PhysicianManagerDataGridColumn c : PhysicianManagerDataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label {} ", label);
        return null;
    }
}

package com.vh.mi.automation.api.pages.queryBuilder.mvca.expert;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MVCAExpertDataGridColumn implements IDataGridColumn {
    INDIVIDUAL_ID("MEMID", "Individual ID", DataType.STRING),
    RI("RISKCOUNT", "RI", DataType.INTEGER),
    CGI("DELRI", "CGI", DataType.INTEGER),
    AGE("AGE", "Age", DataType.INTEGER),
    GENDER("GENDER", "Gender", DataType.STRING),
    CURRENT("CURFLAG", "Current", DataType.STRING),
    REL_FLAG("SUBSCRIBERFLAG","Rel. Flag", DataType.STRING),
    CM_STATUS("CMSTATUS", "CM STATUS", DataType.STRING),
    MEDICAL_PAID("MEDPAIDAMT", "Medical Paid", DataType.MONEY),
    PHARMACY_PAID("RXPAIDAMT", "Pharmacy Paid", DataType.MONEY),
    TOTAL_PAID("TOTPAIDAMT", "Total Paid", DataType.MONEY);

    private static Logger logger = LoggerFactory.getLogger(MVCAExpertDataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private MVCAExpertDataGridColumn(String id, String label, DataType dataType) {
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
        for (MVCAExpertDataGridColumn c : MVCAExpertDataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }
        throw new RuntimeException("No Column found for id " + id);
    }

    public static IDataGridColumn fromLabel(String label) {
        for (MVCAExpertDataGridColumn c : MVCAExpertDataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label {} ", label);
        return null;
    }
}

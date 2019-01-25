package com.vh.mi.automation.api.pages.analytics.demography.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum D07AgeGroupDataGridColumn implements IDataGridColumn {
    AGE_GROUP("ageGrp", "Age Groups", DataType.STRING),
    MALE_MEDIAL_PAID("MaleMedAmt", "MaleMedAmt", DataType.MONEY),
    MALE_RX_PAID("MaleRxAmt", "Rx Paid", DataType.MONEY),
    MALE_TOTAL_MM("MaleMMPOT", "% of Total MM", DataType.PERCENTAGE),
    MALE_TOTAL_PAID("MaleTotPOT", "% of Total Paid", DataType.PERCENTAGE),
    FEMALE_MEDICAL_PAID("FemMedAmt", "Medical Paid", DataType.MONEY),
    FEMALE_RX_PAID("FemRxAmt", "Rx Paid", DataType.MONEY),
    FEMALE_TOTAL_MM("FemMMPOT", "% of Total MM", DataType.PERCENTAGE),
    FEMALE_TOTAL_PAID("FemTotPOT", "% of Total Paid", DataType.PERCENTAGE),
    TOTAL_MM("TotMM", "MM", DataType.INTEGER),
    TOTAL_MEDIAL_PAID("TotMedCost", "Medical Paid", DataType.MONEY),
    TOTAL_RX_PAID("TotRxCost", "Rx Paid", DataType.MONEY),
    MEDICAL_PLUS_RX_PAID("AllPaid", "Medical Plus Rx Paid", DataType.MONEY);

    private static Logger logger = LoggerFactory.getLogger(D07AgeGroupDataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private D07AgeGroupDataGridColumn(String id, String label, DataType dataType) {
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
        for (D07AgeGroupDataGridColumn c : D07AgeGroupDataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }

        logger.warn("Mapping not available for ID - " + id);

        return null;
    }

    public static IDataGridColumn fromLabel(String label) {
        for (D07AgeGroupDataGridColumn c : D07AgeGroupDataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label - " + label);

        return null;
    }
}

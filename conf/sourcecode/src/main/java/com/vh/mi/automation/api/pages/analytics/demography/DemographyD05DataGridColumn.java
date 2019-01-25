package com.vh.mi.automation.api.pages.analytics.demography;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nimanandhar on 9/10/2014.
 */
public enum DemographyD05DataGridColumn implements IDataGridColumn {
    COMPANY("Company", "Company", DataType.STRING),
    MEDICAL_PAID("", "Medical Paid", DataType.MONEY),
    RX_PAID("", "Rx Paid", DataType.MONEY),
    TOTAL("TotalPaid", "Paid", DataType.MONEY),
    AVG_RRS_MODEL_18("", "Average RRS - Model #18", null),
    AVG_RRS_MODEL_56("", "Average RRS - Model #56", null),
    AVG_RRS_MODEL_26("", "Average RRS - Model #26", null),
    LEVEL("Level", "Unit", DataType.STRING),
    MALE_RX_COST("MaleRxCost", "Male", DataType.MONEY),
    FEMALE_RX_COST("FemRxCost", "Female", DataType.MONEY),
    MALE_MEDICAL_COST("MaleMedCost", "Male", DataType.MONEY),
    FEMALE_MEDICAL_COST("FemMedCost", "Female", DataType.MONEY),
    MEMBER_MONTH("MemberMonth", "MM", DataType.INTEGER),
    PMPM("PMPM", "$PMPM", DataType.MONEY);
/*

    MALE_MEDICAL_PAID(DataType.MONEY),
    FEMALE_MEDICAL_PAID(DataType.MONEY),
    MALE_RX_PAID(DataType.MONEY),
    FEMALE_RX_PAID(DataType.MONEY),
    MM_TOTAL(DataType.INTEGER),
    PMPM_TOTAL(DataType.MONEY),
    PAID_TOTAL(DataType.MONEY);
*/

    private static Logger logger = LoggerFactory.getLogger(DemographyD05DataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private DemographyD05DataGridColumn(String id, String label, DataType dataType) {
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
        for (DemographyD05DataGridColumn c : DemographyD05DataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }

        logger.warn("Mapping not available for ID - " + id);

        return null;
    }

    public static IDataGridColumn fromLabel(String label) {
        for (DemographyD05DataGridColumn c : DemographyD05DataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label - " + label);

        return null;
    }


}

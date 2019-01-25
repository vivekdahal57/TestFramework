package com.vh.mi.automation.api.pages.analytics.diseaseRegistry;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum DiseaseRegistryDataGridColumn implements IDataGridColumn {
    DISEASES("CHRONICCATDESC", "Diseases", DataType.STRING),
    MEMBER_TOTAL("MEMBER_TOTAL", "Total", DataType.INTEGER),
    MEMBER_CURRENT("Member_Current", "Current", DataType.INTEGER),
    MEMBER_NEW("Member_New", "New", DataType.INTEGER),
    MEMBER_PER_K_ACTUAL("MembersPerK_Actual", "Actual", DataType.DOUBLE),
    MEMBER_PER_K_NORM("MembersPerK_Norm", "Norm", DataType.DOUBLE),
    TOTAL_PAID("TotPaid", "Total paid", DataType.MONEY),
    PMPY_ACTUAL("PMPY_Actual", "Actual", DataType.MONEY),
    PMPY_NORM("PMPY_Norm", "Norm", DataType.DOUBLE),
    OFFICE_VISITS_PER_K_ACTUAL("OVPerK_Actual", "Actual", DataType.DOUBLE),
    OFFICE_VISITS_PER_K_NORM("OVPerK_Norm", "Norm", DataType.DOUBLE),
    ER_VISITS_PER_K_ACTUAL("ERVPerK_Actual","Actual", DataType.DOUBLE),
    ER_VISITS_PER_K_NORM("ERVPerK_Norm", "Norm", DataType.DOUBLE),
    ADMISSIONS_PER_K_ACTUAL("AdmissionPerK_Actual", "Actual", DataType.DOUBLE),
    ADMISSIONS_PER_K_NORM("AdmissionPerK_Norm", "Norm", DataType.DOUBLE);

    private static Logger logger = LoggerFactory.getLogger(DiseaseRegistryDataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    DiseaseRegistryDataGridColumn(String id, String label, DataType dataType) {
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
        for (DiseaseRegistryDataGridColumn c : DiseaseRegistryDataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }

        logger.warn("Mapping not available for ID - " + id);

        return null;
    }

    public static IDataGridColumn fromLabel(String label) {
        for (DiseaseRegistryDataGridColumn c : DiseaseRegistryDataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label - " + label);

        return null;
    }
}

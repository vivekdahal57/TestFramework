package com.vh.mi.automation.api.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum IndividualRiskAnalysisDataGridColumn implements IDataGridColumn {
    INDIVIDUAL_ID("MEMID", "Individual ID", DataType.STRING),
    INDIVIDUAL("MEMBERNAME", "Individual", DataType.STRING),
    CURRENT_FLAG("CURFLAG", "Current", DataType.STRING),
    REL_FLAG("SUBSCRIBERFLAG", "Rel.Flag", DataType.STRING),
    MM_BASE_YR("MemMonth", "MM - Base Yr.", DataType.INTEGER),
    MEDICAL("MedExpend1", "Medical", DataType.MONEY),
    PHARMACY("RxExpend1", "Pharmacy", DataType.MONEY),
    MEDICAL_AND_PHARMACY("TotalExpend1", "Medical+Pharmacy", DataType.MONEY),
    LOH_MODEL_71("loh", "Model #71", DataType.DOUBLE),
    DXCG_RISK_SOL_ACCS("ACC", "# of ACCs", DataType.INTEGER),
    DXCG_RISK_SOL_RCCS("RCC", "# of RCCs", DataType.INTEGER),
    DXCG_RISK_SOL_CCS("CC", "# of CCs", DataType.INTEGER),
    DXCG_RISK_SOL_DXGS("DXG", "# of DXGs", DataType.INTEGER);

    private static Logger logger = LoggerFactory.getLogger(IndividualRiskAnalysisDataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    IndividualRiskAnalysisDataGridColumn(String id, String label, DataType dataType) {
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
        for (IndividualRiskAnalysisDataGridColumn c : IndividualRiskAnalysisDataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }

        logger.warn("Mapping not available for ID - " + id);

        return null;
    }

    public static IDataGridColumn fromLabel(String label) {
        for (IndividualRiskAnalysisDataGridColumn c : IndividualRiskAnalysisDataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label - " + label);

        return null;
    }
}

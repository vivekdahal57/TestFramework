package com.vh.mi.automation.impl.pages.analytics.populationriskdriver.datagrids;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i82298 on 6/1/2017.
 */
public enum PRD01DataGridColumn implements IDataGridColumn {
    RANK("RANK", "Rank", DataType.INTEGER),
    CLASSIFICATION_CAT("CLASSIFICATION_CAT", "Classification Category",
            DataType.STRING),
    MEMBER_COUNT("MEMBER_NO", "#", DataType.INTEGER),
    CONTRIBUTION("CONTRIBUTION", "% Contribution", DataType.PERCENTAGE),
    DXCG_BMARK("DXCG_BMARK", "DxCG Benchmark % Contribution",
            DataType.PERCENTAGE),
    PRED_COST("COST", "Predicted Allowed Amount Contribution", DataType.MONEY),;
    private static Logger logger = LoggerFactory.getLogger(PRD01DataGridColumn.class);
    private final String id;
    private final String label;
    private final DataType dataType;

    PRD01DataGridColumn(String id, String label, DataType dataType) {
        this.id = id;
        this.label = label;
        this.dataType = dataType;
    }

    @Override public String getId() {
        return id;
    }

    @Override public DataType getDataType() {
        return dataType;
    }

    @Override public String getLabel() {
        return label;
    }

    public static IDataGridColumn fromId(String id) {
        for (PRD01DataGridColumn c : PRD01DataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }
        logger.warn("Mapping not available for ID - " + id);
        return null;
    }
}

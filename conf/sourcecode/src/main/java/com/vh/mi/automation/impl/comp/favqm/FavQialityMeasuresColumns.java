package com.vh.mi.automation.impl.comp.favqm;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i82298 on 12/9/2016.
 */
public enum FavQialityMeasuresColumns implements IDataGridColumn {


    Recommended("BEST_PRACTICE", "Recommended", DataType.STRING),
    IssueLabel("ISSUE_LABEL", "#", DataType.STRING),
    Type("QRM_TYPE", "Type", DataType.STRING),
    DiseaseCategory("DIS_CATEGORY", "Disease Category", DataType.STRING),
    SecondaryCategory("SEC_CATEGORY", "Secondary Category", DataType.STRING),
    Description("QUESTION", "Description", DataType.STRING);


    private static Logger logger = LoggerFactory.getLogger(FavQialityMeasuresColumns.class);

    private String id;
    private String label;
    private DataType dataType;

    private FavQialityMeasuresColumns(String id, String label, DataType dataType) {
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
        for (FavQialityMeasuresColumns c : FavQialityMeasuresColumns.values()) {
            if (c.getId().equals(id)) return c;
        }

        logger.warn("Mapping not available for ID - " + id);

        return null;
    }

    public static IDataGridColumn fromLabel(String label) {
        for (FavQialityMeasuresColumns c : FavQialityMeasuresColumns.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label - " + label);

        return null;
    }
}

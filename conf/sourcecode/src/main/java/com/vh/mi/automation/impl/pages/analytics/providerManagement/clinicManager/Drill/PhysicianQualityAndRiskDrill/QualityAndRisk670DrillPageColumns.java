package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianQualityAndRiskDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10359 on 11/23/17.
 */
public enum QualityAndRisk670DrillPageColumns implements IDataGridColumn {
    CONDITION("ISSUENAME","Condition", DataType.STRING),
    DESCRIPTION("QUESTION", "description", DataType.STRING),
    INDIVIDUAL_WITH_CONDITION("PANELWITHISSUE" ,"Individual with condition" ,DataType.INTEGER),
    INDIVIDUAL_WITH_GAP_OR_RISK("PANELMEETINGCRITERIA", "Individual with Gap or Risk", DataType.INTEGER),
    ACTUAL("PERCENTCOMPLIANT", "Actual" , DataType.PERCENTAGE),
    NORM("PERCENTCOMPLIANTNORM", "Norm" ,DataType.PERCENTAGE)
    ;

    private static Logger logger = LoggerFactory.getLogger(QualityAndRisk670DrillPageColumns.class);

    private String id;
    private String label;
    private DataType dataType;

    private QualityAndRisk670DrillPageColumns(String id, String label, DataType dataType){
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

    public static IDataGridColumn fromId(String id){
        for(QualityAndRisk670DrillPageColumns c : QualityAndRisk670DrillPageColumns.values()){
                if(c.getId().equals(id))
                return c;
        }
        logger.warn("Mapping not available for ID - " + id);
        return null;
    }

    public static IDataGridColumn fromLabel(String label){
        for(QualityAndRisk670DrillPageColumns c : QualityAndRisk670DrillPageColumns.values()){
            if(c.getLabel().equals(label))
                return c;
        }
        logger.warn("Mapping not available for label - " + label);
        return null;
    }
}

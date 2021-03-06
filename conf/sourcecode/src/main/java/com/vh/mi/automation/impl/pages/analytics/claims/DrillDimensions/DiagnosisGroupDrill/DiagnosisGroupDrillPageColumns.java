package com.vh.mi.automation.impl.pages.analytics.claims.DrillDimensions.DiagnosisGroupDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10359 on 11/30/17.
 */
public enum DiagnosisGroupDrillPageColumns implements IDataGridColumn {
    DIAGNOSIS_GROUP("DIMDESC" , "Diagnosis group" , DataType.DOUBLE.STRING),
    TOTAL_PAID("TOTPAID", "Total Paid" , DataType.MONEY),
    MEMBER_MONTHS("MEMMTH" ," Member Months" , DataType.INTEGER),
    PMPM("PMPM" , "PMPM" ,DataType.MONEY),
    COST_PER_UNIT("COSTPERUNIT" ,"Cost Per Unit" , DataType.MONEY),
    UNITS_PER_K("UNITSPERK" , "Units per k" , DataType.DOUBLE),
    UNITS("UNITS" ,"Units", DataType.INTEGER),
    EMPLOYEE_MONTHS("EMPMTH" , "Employee months", DataType.INTEGER),
    PEPM("PEPM" ,"PEPM" , DataType.MONEY)
    ;

    private static Logger logger = LoggerFactory.getLogger(DiagnosisGroupDrillPageColumns.class);
    private String id;
    private String  label;
    private DataType dataType;

    private DiagnosisGroupDrillPageColumns(String id, String label, DataType dataType){
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
        for(DiagnosisGroupDrillPageColumns c : DiagnosisGroupDrillPageColumns.values()){
            if(c.getId().equalsIgnoreCase(id)){
                return c;
            }
        }
        logger.warn("Mapping not available for ID - "+id);
        return  null;
    }

    public static IDataGridColumn fromLabel(String label){
        for(DiagnosisGroupDrillPageColumns c : DiagnosisGroupDrillPageColumns.values()){
            if(c.getLabel().equalsIgnoreCase(label))
                return c;
        }
        logger.warn("Mapping not available for label - "+ label);
        return null;
    }
}

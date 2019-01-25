package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianSourceProcedureDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10359 on 11/27/17.
 */
public enum C01SourceProceduresDrillPageColumns implements IDataGridColumn {
    PROCEDURE_CODE("DMID" , "Procedure code" , DataType.STRING),
    PROCEDURE("DIMDESC" , "Procedure" , DataType.STRING),
    PROCEDURE_TYPE("CODETYPE", "Procedure Type", DataType.STRING),
    BILLED("BILLEDAMT" , "Billed" ,DataType.MONEY),
    ALLOWED("ALLOWEDAMT" ,"Allowed" , DataType.MONEY),
    TOTAL_PAID("TOTPAID" , "Total Paid" ,DataType.MONEY),
    MEMBER_MONTHS("MEMMTH", "Member Months", DataType.INTEGER ),
    PMPM("PMPM" , "PMPM",DataType.MONEY),
    COST_PER_UNIT("COSTPERUNIT" , "Cost Per Unit", DataType.MONEY),
    UNITS_PER_k("UNITSPERK" ,"Units Per k", DataType.DOUBLE),
    UNITS("UNITS" , "Units" ,DataType.INTEGER),
    EMPLOYEE_MONTHS("EMPMTH" ,"Employee Months" ,DataType.INTEGER),
    PEPM("PEPM", "PEPM" ,DataType.MONEY),
    MEMBER_PER_K("MEMPERK", "Member per k",DataType.DOUBLE);

    private static Logger logger = LoggerFactory.getLogger(C01SourceProceduresDrillPageColumns.class);

    private String id;
    private String label;
    private DataType dataType;

    private C01SourceProceduresDrillPageColumns(String id, String label, DataType dataType){
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
        for(C01SourceProceduresDrillPageColumns c : C01SourceProceduresDrillPageColumns.values()){
            if(c.getId().equalsIgnoreCase(id))
                return c;
        }
        logger.warn("Mapping not available for ID -" +id);
        return null;
    }


    public static IDataGridColumn fromLabel(String label){
        for(C01SourceProceduresDrillPageColumns c : C01SourceProceduresDrillPageColumns.values()){
            if(c.getLabel().equalsIgnoreCase(label))
                return c;
        }
        logger.warn("Mapping not available for Label - " +label);
        return null;
    }

}

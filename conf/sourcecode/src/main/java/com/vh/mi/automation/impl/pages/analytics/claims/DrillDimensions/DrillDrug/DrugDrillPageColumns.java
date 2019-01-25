package com.vh.mi.automation.impl.pages.analytics.claims.DrillDimensions.DrillDrug;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10359 on 12/12/17.
 */

public enum DrugDrillPageColumns implements IDataGridColumn {
    DRUG("DIMDESC" , "Drug" , DataType.STRING),
    GENERIC_FLAG("CODETYPE" , "Generic Flag" , DataType.STRING),
    TOTAL_PAID("TOTPAID" ," Total Paid" , DataType.MONEY),
    MEMBER_MONTHS("MEMMTH" , " Member Months" ,DataType.INTEGER),
    PMPM("PMPM" , "PMPM" , DataType.MONEY),
    PRESCRIPTION_COUNT("PRESCOUNT" , "Prescription Count" , DataType.INTEGER),
    COST_PER_UNIT("COSTPERUNIT" , "Cost Per Unit" , DataType.MONEY),
    UNITS_PER_K("UNITSPERK" , "Units Per k" , DataType.DOUBLE),
    UNITS("UNITS" , "Units" , DataType.INTEGER),
    EMPLOYEE_MONTHS("EMPMTH" , "Employee Months" , DataType.INTEGER),
    PEPM("PEPM" , "PEPM" , DataType.MONEY),
    ;

    private DrugDrillPageColumns(String id, String label, DataType dataType){
        this.id = id;
        this.label = label;
        this.dataType = dataType;
    }

    private static Logger logger = LoggerFactory.getLogger(DrugDrillPageColumns.class);
    private String id;
    private String label;
    private DataType dataType;

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
        for(DrugDrillPageColumns c : DrugDrillPageColumns.values()){
            if(c.getId().equalsIgnoreCase(id))
                return c;
        }
        logger.warn("Mapping not available for ID -"+id);
        return null;
    }

}

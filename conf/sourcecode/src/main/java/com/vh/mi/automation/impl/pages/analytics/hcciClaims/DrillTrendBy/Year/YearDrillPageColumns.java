package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillTrendBy.Year;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10359 on 12/27/17.
 */
public enum YearDrillPageColumns implements IDataGridColumn{
    CLAIM_TYPE("DIMDESC" , "Claim Type" , DataType.STRING),
    TIME_PERIOD("TIMEPERIOD" , "Time Period" , DataType.STRING),
    TOTAL_PAID("TOTPAID", "Total Paid" , DataType.MONEY),
    MEMBER_MONTHS("MEMMTH" ,"Member Months" , DataType.INTEGER),
    PMPM("PMPM" , "PMPM" , DataType.MONEY),
    COST_PER_UNIT("COSTPERUNIT" , "Cost Per Unit" , DataType.MONEY),
    UNITS_PER_K("UNITSPERK" , "Units Per k", DataType.DOUBLE),
    UNITS("UNITS" ,"Units" , DataType.INTEGER),
    EMPLOYEE_MONTHS("EMPMTH" , "Employee Months" , DataType.INTEGER),
    PEPM("PEPM" , "PEPM" , DataType.MONEY)
    ;

    private static Logger logger = LoggerFactory.getLogger(YearDrillPageColumns.class);
    private String id;
    private String label;
    private DataType dataType;

    private YearDrillPageColumns(String id, String label, DataType dataType){
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
        for(YearDrillPageColumns c : YearDrillPageColumns.values()){
            if(c.getId().equalsIgnoreCase(id))
                return c ;
        }
        logger.warn("Mapping not available for ID - "+id);
        return null;
    }

}

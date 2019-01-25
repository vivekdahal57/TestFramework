package com.vh.mi.automation.impl.pages.analytics.claims.DrillDimensions.CCDxCGDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;

/**
 * Created by i10359 on 12/1/17.
 */
public enum CCDxCGDrillPageDataGridColumns implements IDataGridColumn {
    DXCG_CC("DIMDESC" ,  "DXCG CC" ,DataType.STRING),
    TOTAL_PAID("TOTPAID" ,"Total Paid", DataType.MONEY),
    MEMBER_MONTHS("MEMMTH" ,"Member Months " ,DataType.INTEGER),
    PMPM("PMPM" ,"PMPM" ,DataType.MONEY),
    COST_PER_UNIT("COSTPERUNIT" , "Cost Per unit" ,DataType.MONEY),
    UNITS_PER_K("UNITSPERK" , "Units Per k", DataType.DOUBLE),
    UNITS("UNITS", "Units" , DataType.INTEGER),
    EMPLOYEE_MONTHS("EMPMTH" , "Employee months" , DataType.INTEGER),
    PEPM("PEPM" ,"PEPM",DataType.MONEY)
    ;

    private static Logger logger = LoggerFactory.getLogger(CCDxCGDrillPageDataGridColumns.class);
    private String id;
    private String label;
    private DataType dataType;

    private CCDxCGDrillPageDataGridColumns(String id, String label, DataType dataType){
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
        for(CCDxCGDrillPageDataGridColumns c : CCDxCGDrillPageDataGridColumns.values()){
            if(c.getId().equalsIgnoreCase(id))
                return c;
        }
        logger.warn("Mapping not available for ID - "+ id);
        return null;
    }
}

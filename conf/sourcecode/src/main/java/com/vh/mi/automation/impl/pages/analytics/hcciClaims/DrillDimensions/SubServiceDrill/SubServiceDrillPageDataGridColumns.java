package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.SubServiceDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i20841 on 12/7/18.
 */
public enum SubServiceDrillPageDataGridColumns implements IDataGridColumn {
    CLAIM_TYPE("CLMTYPEDESC" , "Claim Type" , DataType.STRING),
    SUB_SERVICE_CATEGORY("DIMDESC" ,"Sub Service" , DataType.STRING),
    TOTAL_PAID("TOTPAID" , "Total Paid", DataType.MONEY),
    MEMBER_MONTHS("MEMMTH" , "Member Months" ,DataType.INTEGER),
    PMPM("PMPM" , "PMPM" , DataType.MONEY),
    COST_PER_UNIT("COSTPERUNIT" , "Cost Per Unit" ,DataType.MONEY),
    UNITS_PER_K("UNITSPERK" ,"Units per k", DataType.DOUBLE),
    UNITS("UNITS" , "Units" , DataType.INTEGER),
    EMPLOYEE_MONTHS("EMPMTH" ,  "Employee Months" ,DataType.INTEGER),
    PEPM("PEPM" ,"PEPM" , DataType.MONEY),
    PAIDPERSERVICE("COSTPERSERVICE", "Paid per Service", DataType.MONEY),
    SERVICES_PER_1000("SERVICESPERK", "Services per 1000", DataType.INTEGER),
    SERVICES("SERVICES", "Service Counts", DataType.INTEGER),
    MEMBER_PER_1000("MEMPERK","Members per 1000",DataType.MONEY)
  ;

    private static Logger logger = LoggerFactory.getLogger(SubServiceDrillPageDataGridColumns.class);
    private String id;
    private String label;
    private DataType dataType;

    private SubServiceDrillPageDataGridColumns(String id, String label, DataType dataType){
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
        for(SubServiceDrillPageDataGridColumns c : SubServiceDrillPageDataGridColumns.values()){
            if(c.getId().equalsIgnoreCase(id))
                return c;
        }
        logger.warn("Mapping not available for ID - "+ id);
        return null;
    }

}

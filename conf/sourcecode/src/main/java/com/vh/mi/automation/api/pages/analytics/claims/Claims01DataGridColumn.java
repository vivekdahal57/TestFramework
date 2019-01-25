package com.vh.mi.automation.api.pages.analytics.claims;


import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by nimanandhar on 8/28/2014.
 */
public enum Claims01DataGridColumn implements IDataGridColumn {
    CLAIM_TYPE("DIMDESC", "Claim Type", DataType.STRING),
    BILLED_AMT("BILLEDAMT", "Billed", DataType.MONEY),
    ALLOWED("ALLOWEDAMT", "Allowed", DataType.MONEY),
    TOTAL_PAID("TOTPAID", "Total Paid", DataType.MONEY),
    MEMBER_MONTHS("MEMMTH", "Member Months", DataType.INTEGER),
    PMPM("PMPM", "$PMPM", DataType.MONEY),
    COST_PER_UNIT("COSTPERUNIT","Cost per Unit",DataType.MONEY),
    UNITS_PER_1000("UNITSPERK","Units per 1000",DataType.MONEY),
    UNITS("UNITS","Units",DataType.MONEY),
    EMPLOYEE_MONTHS("EMPMTH","Employee Months",DataType.MONEY),
    PEPM("PEPM","$PEPM",DataType.MONEY),
    UNQMEM("UNQMEM","Unique Members",DataType.INTEGER),
    MEMBER_PER_1000("MEMPERK","Member per 1000",DataType.MONEY),
    COSTPERMEM("COSTPERMEM","Cost per Member",DataType.MONEY),
    //ADJUSTED_NORM("ADJUSTED_NORM", "Adjusted Norm", DataType.STRING),


    //HEADERS for sorting
//    CLAIM_TYPE_HEADER("DIMDESC_header", "Claim Type", DataType.STRING),
//    BILLED_AMT_HEADER("BILLEDAMT_header", "Billed", DataType.MONEY),
//    ALLOWED_HEADER("ALLOWEDAMT_header", "Allowed", DataType.MONEY),
//    TOTAL_PAID_HEADER("TOTPAID_header", "Total Paid", DataType.MONEY),
//    MEMBER_MONTHS_HEADER("MEMMTH_header", "Member Months", DataType.INTEGER),
//    PMPM_HEADER("PMPM_header", "$PMPM", DataType.MONEY),
//    COST_PER_UNIT_HEADER("COSTPERUNIT_header","Cost per Unit",DataType.MONEY),
//    UNITS_PER_1000_HEADER("UNITSPERK_header","Units per 1000",DataType.MONEY),
//    UNITS_HEADER("UNITS_header","Units",DataType.MONEY),
//    EMPLOYEE_MONTHS_HEADER("EMPMTH_header","Employee Months",DataType.MONEY),
//    PEPM_HEADER("PEPM_header","$PEPM",DataType.MONEY),
//    MEMBER_PER_1000_HEADER("MEMPERK_header","Member per 1000",DataType.MONEY),

    //Trending Columns
    BILLED_AMT_1("BILLEDAMT1", "P1", DataType.MONEY),
    BILLED_AMT_2("BILLEDAMT2", "P2", DataType.MONEY),
    ALLOWED_AMT_1("ALLOWEDAMT1", "P1", DataType.MONEY),
    ALLOWED_AMT_2("ALLOWEDAMT2", "P2", DataType.MONEY),
    TOTAL_PAID_1("TOTALPAID1", "P1", DataType.MONEY),
    TOTAL_PAID_2("TOTALPAID2", "P2", DataType.MONEY),
    MEMBER_MONTHS_1("MM1", "P1", DataType.INTEGER),
    MEMBER_MONTHS_2("MM2", "P2", DataType.INTEGER),
    PMPM_1("PMPM1", "P1", DataType.MONEY),
    PMPM_2("PMPM2", "P2", DataType.MONEY),;


    private static Logger logger = LoggerFactory.getLogger(Claims01DataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private Claims01DataGridColumn(String id, String label, DataType dataType) {
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
        for (Claims01DataGridColumn c : Claims01DataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }
        throw new RuntimeException("No Claims01 Column found for id " + id);
    }

    public static IDataGridColumn fromLabel(String label) {
        for (Claims01DataGridColumn c : Claims01DataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label {} ", label);
        return null;
    }
}

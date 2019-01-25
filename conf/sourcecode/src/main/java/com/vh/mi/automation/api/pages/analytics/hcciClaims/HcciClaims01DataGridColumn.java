package com.vh.mi.automation.api.pages.analytics.hcciClaims;


import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by nimanandhar on 8/28/2014.
 */
public enum HcciClaims01DataGridColumn implements IDataGridColumn {
    CLAIM_TYPE("CLMTYPEDESC", "Claim Type", DataType.STRING),
    SERVICE_CATEGORY("DIMDESC", "Service Category", DataType.STRING),
    BILLED_AMT("BILLEDAMT", "Billed", DataType.MONEY),
    ALLOWED("ALLOWEDAMT", "Allowed", DataType.MONEY),
    TOTAL_PAID("TOTPAID", "Paid Amount", DataType.MONEY),
    MEMBER_MONTHS("MEMMTH", "Member Months", DataType.INTEGER),
    PMPM("PMPM", "$PMPM", DataType.MONEY),
    PAIDPERSERVICE("COSTPERSERVICE", "Paid per Service", DataType.MONEY),
    ALLOWEDPERSERVICE("ALLOWEDPERSERVICE", "Allowed per Service", DataType.MONEY),
    SERVICESPERK("SERVICESPERK", "Services per 1000", DataType.INTEGER),
    SERVICES("SERVICES", "Service Counts", DataType.INTEGER),
    COST_PER_UNIT("COSTPERUNIT", "Cost per Unit", DataType.MONEY),
    UNITS_PER_1000("UNITSPERK", "Units per 1000", DataType.MONEY),
    UNITS("UNITS", "Units", DataType.MONEY),
    EMPLOYEE_MONTHS("EMPMTH", "Employee Months", DataType.MONEY),
    PEPM("PEPM", "$PEPM", DataType.MONEY),
    MEMBER_PER_1000("MEMPERK", "Members per 1000", DataType.MONEY),
    ADJUSTED_NORM("ADJUSTED_NORM", "Adjusted Norm", DataType.STRING),

    //Details from the Column "Drug" when drilled from pharmacy claim type
    GENERIC_FLAG("CODETYPE", "Generic Flag", DataType.STRING),


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
    BILLED_AMT_1("BILLEDAMT_P1", "P1", DataType.MONEY),
    BILLED_AMT_2("BILLEDAMT_P2", "P2", DataType.MONEY),
    ALLOWED_AMT_1("ALLOWEDAMT_P1", "P1", DataType.MONEY),
    ALLOWED_AMT_2("ALLOWEDAMT_P2", "P2", DataType.MONEY),
    TOTAL_PAID_1("TOTPAID_P1", "P1", DataType.MONEY),
    TOTAL_PAID_2("TOTPAID_P2", "P2", DataType.MONEY),
    MEMBER_MONTHS_1("MEMMTH_P1", "P1", DataType.INTEGER),
    MEMBER_MONTHS_2("MEMMTH_P2", "P2", DataType.INTEGER),
    PMPM_1("PMPM_P1", "P1", DataType.MONEY),
    PMPM_2("PMPM_P2", "P2", DataType.MONEY),
    EMPLOYEE_MONTHS_1("EMPMTH_P1", "P1", DataType.INTEGER),
    EMPLOYEE_MONTHS_2("EMPMTH_P2", "P2", DataType.INTEGER),
    PEPM_1("PEPM_P1", "P1", DataType.MONEY),
    PEPM_2("PEPM_P2", "P2", DataType.MONEY),
    PAIDPERSERVICE_1("COSTPERSERVICE_P1", "P1", DataType.MONEY),
    PAIDPERSERVICE_2("COSTPERSERVICE_P2", "P2", DataType.MONEY),
    ALLOWEDPERSERVICE_1("ALLOWEDPERSERVICE_P1", "P1", DataType.MONEY),
    ALLOWEDPERSERVICE_2("ALLOWEDPERSERVICE_P2", "P2", DataType.MONEY),
    SERVICESPERK_1("SERVICESPERK_P1", "P1", DataType.INTEGER),
    SERVICESPERK_2("SERVICESPERK_P2", "P2", DataType.INTEGER),
    ;


    private static Logger logger = LoggerFactory.getLogger(HcciClaims01DataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private HcciClaims01DataGridColumn(String id, String label, DataType dataType) {
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
        for (HcciClaims01DataGridColumn c : HcciClaims01DataGridColumn.values()) {
            if (id.contains(c.id)) return c;
        }
        throw new RuntimeException("No HcciClaims01 Column found for id " + id);
    }


    public static IDataGridColumn fromLabel(String label) {
        for (HcciClaims01DataGridColumn c : HcciClaims01DataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label {} ", label);
        return null;
    }
}
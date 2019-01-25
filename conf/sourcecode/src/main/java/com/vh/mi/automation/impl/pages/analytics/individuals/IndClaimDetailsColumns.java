package com.vh.mi.automation.impl.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i80690 on 12/13/2016.
 */
public enum  IndClaimDetailsColumns implements IDataGridColumn {

    PLAN_TYPE("LVL1ID" , "Plan Type" , DataType.STRING),
    CLAIM_TYPE("CLMTYPE" , "Claim Type" , DataType.STRING),
    CLAIM_NUMBER("CLMNUM" , "Claim Number" , DataType.INTEGER),
    BILL_TYPE("BILLTYPE" , "Bill Type" , DataType.INTEGER),
    TYPEOFBILL("TYPEOFBILL" , "Type Of Bill" ,DataType.STRING),
    FUNDCODE("FUNDCODE", "Fund Code", DataType.STRING),
    FUND_DESCRIPTION("FUNDDESC", "Fund Description", DataType.STRING),
    FROM_DATE("FROMDATE" , "From Date" , DataType.DATE),
    TO_DATE("TODATE" , "To Date" , DataType.DATE),
    SERVICE_DATE("SERVICEDATE" , "Service Date" , DataType.DATE),
    PAID_DATE("PAIDDATE" , "Paid Date", DataType.DATE),
    UNIT("LVL4DESC" , "Unit" , DataType.STRING),
    PROVIDER_ID("PROVID" , "Provider Id" , DataType.STRING),
    PROVIDER("PROVNAME" , "Provider" , DataType.STRING),
    SPECIALTY("SPECDESC" ,  "Specialty" , DataType.STRING),
    OPERATING_PROVIDER_ID("OPRTNGPROVID" , "Operating Provider Id" , DataType.STRING),
    OPERATING_PROVIDER_NPI("OPRTNGPROVNPI" , "Operating Provider NPI" , DataType.STRING),
    REFERRING_PROVIDER_ID("REFRNGPROVID" , "Referring Provider Id" , DataType.STRING),
    REFERRING_PROVIDER_NPI("REFRNGPROVNPI" , "Referring Provider NPI", DataType.STRING),
    DIAGNOSIS_CODE("SRCDIAGCODE", "Diagnosis Code" , DataType.STRING),
    REFERRING_PROVIDER_NAME("REFRNGPROVNAME" , "Referring Provider Name", DataType.STRING),
    BILLING_PROVIDER_ID("BILLNGPROVID" , "Billing Provider id" , DataType.STRING),
    BILLING_PROVIDER_NPI("BILLNGPROVNPI" , "Billing Provider NPI" , DataType.STRING),
    BILLING_PROVIDER_TAX_ID("BILLNGPROVTAXID" , "Billing Provider Tax Id" , DataType.STRING),
    BILING_PROVIDER_NAME("BILLNGPROVNAME" , "Billing Provider Name" , DataType.STRING),
    SERVICING_PROVIDER_ID("SRVCNGPROVID" , "Servicing Provider Id" , DataType.STRING),
    SERVICING_PROVIDER_NPI("SRVCNGPROVNPI" , "Servicing Provider NPI" , DataType.STRING),
    SERVICING_PROVIDER_NAME("SRVCNGPROVNAME" , "Servicing Provider Name" , DataType.STRING),
    ATTENDING_PROVIDER_ID("ATNDNGPROVID" , "Attending Provider Id" , DataType.STRING),
    ATTENDING_PROVIDER_NPI("ATNDNGPROVNPI" , "Attending Provider NPI" , DataType.STRING),
    ATTENDING_PROVIDER_NAME("ATNDNGPROVNAME" , "Attending Provider Name" , DataType.STRING),
    OPERATING_PROVIDER_NAME("OPRTNGPROVNAME" , "Operating Provider Name" , DataType.STRING),
    OTHER_PROVIDER_ONE_ID("OTHERPROV1ID" , "Other Provider One Id" , DataType.STRING),
    OTHER_PROVIDER_ONE_NPI("OTHERPROV1NPI" , "Other Provider One NPI" , DataType.STRING),
    OTHER_PROVIDER_ONE_NAME("OTHERPROV1NAME" , "Other Provider One Name" , DataType.STRING),
    OTHER_PROVIDER_TWO_ID("OTHERPROV2ID" , "Other Provider Two Id" , DataType.STRING),
    OTHER_PROVIDER_TWO_NPI("OTHERPROV2NPI" , "Other Provider Two NPI",DataType.STRING),
    OTHER_PROVIDER_TWO_NAME("OTHERPROV2NAME" , "Other Provider Two Name", DataType.STRING),
    IPA("IPA" , "IPA" , DataType.CAPPED_INTEGER),
    TYPE("ICDTYPE" , "Type" , DataType.STRING),
    ADMIT_DIAGNOSIS("ADMTG_DIAG_DESC" , "Admit Diagnosis" , DataType.STRING),
    DIAGNOSIS_ONE_DIAGNOSIS_CODE("SRCDIAGCODE__" , "Diagnosis One Diagnosis Code", DataType.STRING),
    DIAGNOSIS_ONE_DESCRIPTION("SRCDIAGDESC__" , "Diagnosis One Description" , DataType.STRING),
    DIAGNOSIS_TWO_DIAGNOSIS_CODE("SRCDIAGCODE2" , "Diagnosis Two Diagnosis Code", DataType.STRING),
    DIAGNOSIS_TWO_DESCRIPTION("SRCDIAGDESC2" , "Diagnosis Two Description" , DataType.STRING),
    DIAGNOSIS_THREE_DIAGNOSIS_CODE("SRCDIAGCODE3" , "Diagnosis Three Diagnosis Code" , DataType.STRING),
    DIAGNOSIS_THREE_DESCRIPTION("SRCDIAGDESC3" , "Diagnosis Three Description" , DataType.STRING),
    DIAGNOSIS_FOUR_DIAGNOSIS_CODE("SRCDIAGCODE4" , "Diagnosis Four Diagnosis Code" , DataType.STRING),
    DIAGNOSIS_FOUR_DESCRIPTION("SRCDIAGDESC4" , "Diagnosis Four Description" , DataType.STRING),
    DIAGNOSIS_FIVE_DIAGNOSIS_CODE("SRCDIAGCODE5" ,  "Diagnosis Five Diagnosis Code" , DataType.STRING),
    DIAGNOSIS_FIVE_DESCRIPTION("SRCDIAGDESC5" , "Diagnosis Five Description" , DataType.STRING),
    DIAGNOSIS_SIX_DIAGNOSIS_CODE("SRCDIAGCODE6" , "Diagnosis Six Diagnosis Code", DataType.STRING),
    DIAGNOSIS_SIX_DESCRIPTION("SRCDIAGDESC6" , "Diagnosis Six Description" , DataType.STRING),
    DIAGNOSIS_SEVEN_DIAGNOSIS_CODE("SRCDIAGCODE7" , "Diagnosis Seven Diagnosis Code" , DataType.STRING),
    DIAGNOSIS_SEVEN_DESCRIPTION("SRCDIAGDESC7" , "Diagnosis Seven Description" , DataType.STRING),
    DIAGNOSIS_EIGHT_DIAGNOSIS_CODE("SRCDIAGCODE8" , "Diagnosis Eight Diagnosis Code", DataType.STRING),
    DIAGNOSIS_EIGHT_DESCRIPTION("SRCDIAGDESC8" , "Diagnosis Eight Description" , DataType.STRING),
    DIAGNOSIS_NINE_DIAGNOSIS_CODE("SRCDIAGCODE9" , "Diagnosis Nine Diagnosis Code" , DataType.STRING),
    DIAGNOSIS_NINE_DESCRIPTION("SRCDIAGDESC9" , "Diagnosis Nine Description" , DataType.STRING),
    DIAGNOSIS_TEN_DIAGNOSIS_CODE("SRCDIAGCODE10" ,  "Diagnosis Ten Diagnosis Code" , DataType.STRING),
    DIAGNOSIS_TEN_DESCRIPTION("SRCDIAGDESC10" , "Diagnosis Ten Description" , DataType.STRING),
    ADMIT_DIAGNOSIS_CODE("ADMTG_DIAG_CD", "Admit Diagnosis Code"  , DataType.STRING),
    DXCG_ACC("DIAGCODE2" , "DXCG ACC" , DataType.INTEGER),
    DXCG_CC("DIAGCODE4" , "DXCG CC" , DataType.INTEGER),
    DXCG_DX("DIAGCODE3" , "DXCG DX" , DataType.INTEGER),
    PRO_CODE_NDC("SRCPROCNDCCODE" , "Pro Code NDC" , DataType.STRING),
    PROCEDURE_DRUG("SRCPROCNDCDESC" , "Procedure Drug" , DataType.STRING),
    PROCEDURE_TYPE_OR_DRUG_TYPE("PROCTYPE" , "Procedure Type Or Drug Type", DataType.STRING),
    PROC_GROUP_OR_DRUG("PROCDRUGDESC" , "proc Group or Drug" , DataType.STRING),
    POS_FDB_GROUPER_CODE_DESCRIPTION("POSRXDESC" , "POS FDB Grouper Code Description" , DataType.INTEGER),
    POS_CODE_FDB_GROUPER_CODE("POSRXCODE" , "Pos Code FDB Grouper Code" , DataType.STRING),
    DRUG_STRENGTH("DRUGSTRENGTH" , "Drug Strength" , DataType.STRING),
    SERVICE_UNITS("SERVICEUNITS" , "Service units" , DataType.INTEGER),
    DAYS_SUPPLY("IPSUPPLYDAYS" , "Days Supply" , DataType.INTEGER),
    FORMULARY_FLAG("FORMULARY_YN" , "Formulary Flag" , DataType.STRING),
    SPECIALTY_DRUG("SPECIALTYIND" , "Specialty Drug" , DataType.STRING),
    DRG("DRGCODE" , "DRG" , DataType.STRING),
    MODIFIER_CODE_DESCRIPTION("MODIFIERDESC" , "Modifier Code Description" , DataType.STRING),
    DRG_DESCRIPTION("DRGDESC" , "DRG Description" , DataType.STRING),
    HCPCS_CODE("HCPCS" , "HCPCS Code" , DataType.STRING),
    HCPCS("HCPCSDESC" , "HCPCS" , DataType.STRING),
    CPT("CPT4_1" , "CPT" , DataType.STRING),
    CPTII("CPTII" , "CPTII" , DataType.STRING),
    REVENUE_CODE("REVCODE" , "Revenue code" , DataType.STRING),
    REVENUE_CODE_DESCRPTION("REVDESC" , "Revenue code Description", DataType.STRING),
    MODIFIER_CODE("MODIFIERCODE" , "Modifier Code" , DataType.STRING),
    MOI("MOI" , "MOI" , DataType.STRING),
    COMPOUND_INDICATOR("COMPOUNDIND" , "Compound Indicator" , DataType.STRING),
    PHARMACY_NABP_NUMBER("PHARMNABPNUM" , "Pharmacy NABP Number" , DataType.STRING),
    NETWORK_ID("NWKID" , "Network Id" , DataType.STRING),
    NETWORK_NAME("NWKNAME" , "Network Name" , DataType.STRING),
    PPO_SAVINGS_AMOUNT("PPOSAVINGAMT" , "PPO Savings Amount" , DataType.MONEY),
    INGREDIENT_COST("INGREDCOST" , "Ingredient Cost" , DataType.MONEY),
    SALES_TAX("SALESTAX" , "Sales Tax" , DataType.MONEY),
    COPAY_AMOUNT("COPAYAMT" , "Co Pay Amount" , DataType.MONEY),
    COINSURANCE_AMOUNT("COINSAMT" , "Co Insurance Amount" , DataType.MONEY),
    DEDUCTIBLE_AMOUNT("DEDUCTAMT" , "Deductible Amount" , DataType.MONEY),
    ENROLEEE_PAID_AMOUNT("EMPPAIDAMT" , "Enrolle Paid Amount" , DataType.MONEY),
    BILLED_AMOUNT("BILLEDAMT" , "Billed Amount" , DataType.MONEY),
    ALLOWED_AMOUNT("ALLOWEDAMT" , "Allowed Amount" , DataType.MONEY),
    NOT_ALLOWED_AMOUNT("NOTALLOWEDAMT" , "Not Allowed Amount" , DataType.MONEY),
    COORDINATION_OF_BENEFITS_AMOUNT("COBAMT" , "Coordination Of Benefits Amount" , DataType.MONEY),
    PLAN_EXCLUSION_LIMIT("PLANEXCLAMT" , "Plan Exclusion Limit" , DataType.MONEY),
    CLAIM_STATUS_ADJUSTMENT("CLAIMSTATUS" , "Claim Status Adjustment" , DataType.STRING),
    UDF1("UDF1" , "UDF1" , DataType.STRING),
    UDF2("UDF2" , "UDF2" , DataType.STRING),
    UDF3("UDF3", "UDF3", DataType.STRING),
    UDF4("UDF4" , "UDF4" , DataType.STRING),
    UDF5("UDF5" , "UDF5" , DataType.STRING),
    POS_FBD_GROUPER_CODE_DESCRIPTION("POSRXDESC" , "POS FBD Grouper Code Descsription", DataType.STRING),
    PAID_AMOUNT("PAIDAMT" , "Paid Amount" , DataType.MONEY),
    ;






    private static Logger logger = LoggerFactory.getLogger(IndClaimDetailsColumns.class);
    private String id;
    private String label;
    private DataType dataType;

    private IndClaimDetailsColumns(String id, String label, DataType dataType) {
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
        for (IndClaimDetailsColumns c : IndClaimDetailsColumns.values()) {
            if (c.getId().equals(id)) return c;
        }
        logger.warn("Mapping not available for ID - " + id);

        return null;
    }

    public static IDataGridColumn fromLabel(String label) {
        for (IndClaimDetailsColumns c : IndClaimDetailsColumns.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label - " + label);

        return null;
    }

}

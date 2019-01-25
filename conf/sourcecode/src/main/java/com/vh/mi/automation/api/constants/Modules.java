package com.vh.mi.automation.api.constants;

import com.google.common.base.Optional;

import static com.vh.mi.automation.api.constants.SwitchBoard.*;

/**
 * Created by i80448 on 10/6/2014.
 */
public enum Modules {
    DXCG_RISK_SOLUTIONS("31", "DxCG Risk Solutions", ANALYTICS),
    POPULATION_DASHBOARD("418", "Population Dashboard", ANALYTICS),
    QUALITY_MEASURES("291", "Quality Measures", ANALYTICS),
    CLAIMS_SEARCH("200", "Claims Search", SwitchBoard.QUERY_BUILDER),
    MVCA("145", "MVCA", SwitchBoard.QUERY_BUILDER),
    STRATIFIER("170", "Stratifier", SwitchBoard.QUERY_BUILDER),
    PROVIDER_MANAGEMENT("260", "Provider Management", ANALYTICS),
    IN_OUT_OF_NETWORK_ANALYZER("309","In/Out-of Network Analyzer",ANALYTICS),
    PREVENTIVE_CARE_MANAGEMENT("149","25.1 Preventive Care Management ",REPORTS),
    FOLLOW_UP_AFTER_HOSPITALIZATION_FOR_MENTAL_ILLNESS_NOT_FOR_IPA_USE("230","25.5 Follow-up after Hospitalization for Mental Illness (Not for IPA Use)",REPORTS),
    USER_GUIDES("312","User Guides",DOCUMENTATION),
    BCBS_IL_PRESCRIPTION_DRUG_PROGRAM_REPORTS("132","28 BCBS-IL Prescription Drug Program Reports",REPORTS),
    LETTER_LOG_MEMBER_SORT_BY("137","Letter Log Member Sort By..",OUTREACH),
    BCBS_IL_PROVIDER_QUALITY_INITIATIVE_REPORTS("131","25 BCBS-IL Provider Quality Initiative Reports ",REPORTS),
    REPORT_GUIDES("313","Report Guides",DOCUMENTATION),
    APPLICATION_NOTES("26","Application Notes",FAVORITES),
    UM_REPORT_NOT_FOR_IPA_USE("236","31 UM Report (Not for IPA Use)",REPORTS),
    LETTER_GENERATION("135","Letter Generation",OUTREACH),
    STOP_LOSS_MODULES("30","Stop Loss Modules",ANALYTICS),
    INFLUENZA_VACCINATION_NOT_FOR_IPA_USE("220","25.4 Influenza Vaccination (Not for IPA Use)",REPORTS),
    CLINICAL_SUPPORTING_INFORMATION("314","Clinical Supporting Information",DOCUMENTATION),
    FINANCIAL_REPORT("57","Financial Report",REPORTS),
    CHILDHOOD_IMMUNIZATION_NOT_FOR_IPA_USE("153","25.3 Childhood Immunization (Not for IPA Use)",REPORTS),
    RELEASE_NOTES("311","Release Notes",DOCUMENTATION),
    DATA_INTEGRITY("62","Data Integrity",FAVORITES),
    HMO("50","HMO",REPORTS),
    DISEASE_MANAGEMENT_NOT_FOR_IPA_USE("150","25.2 Disease Management (Not for IPA Use)",REPORTS),
    LETTER_LOG_GROUP_SORT_BY("136","Letter Log Group Sort By..",OUTREACH),
    TOP_10("130","9 Top 10",REPORTS),
    ;


    private String  dbId;
    private String navLinkLabel;
    private SwitchBoard switchBoard;

    private Modules(String  dbId, String navLinkLabel, SwitchBoard switchBoard) {
        this.dbId = dbId;
        this.navLinkLabel = navLinkLabel;
        this.switchBoard = switchBoard;
    }

    public static Optional<Modules> byId(String moduleId){
        for (Modules modules : Modules.values()) {
            if (modules.dbId.equals(moduleId)) {
                return Optional.of(modules);
            }
        }
        return Optional.absent();
    }

    public String  getDbId() {
        return dbId;
    }

    public String getNavLinkLabel() {
        return navLinkLabel;
    }

    public SwitchBoard getSwitchBoard() {
        return switchBoard;
    }


}

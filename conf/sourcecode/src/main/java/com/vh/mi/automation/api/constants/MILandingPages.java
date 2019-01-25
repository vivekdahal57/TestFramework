package com.vh.mi.automation.api.constants;

import com.google.common.base.Optional;
import com.vh.mi.automation.api.comp.navigation.IModuleInfo;
import com.vh.mi.automation.impl.pages.analytics.claims.Claims01;
import com.vh.mi.automation.impl.pages.analytics.conversionAnalyzer.ConversionAnalyzerA17;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01;
import com.vh.mi.automation.impl.pages.analytics.demography.DemographyD05;
import com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.DiseaseRegistryCD016;
import com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.dxcgRiskSolutionsStaticReports.StaticReportsRP010;
import com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.IndividualRiskAnalysis;
import com.vh.mi.automation.impl.pages.analytics.executiveSummary.ExecutiveSummary;
import com.vh.mi.automation.impl.pages.analytics.expenseDistribution.ExpenseDistribution;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.HcciClaims01;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.HospitalProfilesHP100;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.networkAnalyzer.DashboardSOF101;
import com.vh.mi.automation.impl.pages.analytics.networkAnalyzer.DetailedAnalyticsOU107;
import com.vh.mi.automation.impl.pages.analytics.networkUtilization.NetworkUtilizationNU105;
import com.vh.mi.automation.impl.pages.analytics.populationDashboard.summary.SummaryPD001;
import com.vh.mi.automation.impl.pages.analytics.populationDashboard.trend.TrendPD002;
import com.vh.mi.automation.impl.pages.analytics.populationriskdriver.PRD01;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.physicianManager.CP110;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.ProviderProfilerV044;
import com.vh.mi.automation.impl.pages.analytics.qualityMeasures.qualityAndRisk.QualityAndRisk670;
import com.vh.mi.automation.impl.pages.analytics.snfProfiler.SNFProfilesSP100;
import com.vh.mi.automation.impl.pages.analytics.spikes.SpikesS110B;
import com.vh.mi.automation.impl.pages.analytics.utilizationMetrics.UtilizationMetricsHM50;

import static com.vh.mi.automation.api.constants.SwitchBoard.*;

/**
 * In memory DB of MI landing pages.
 *
 * @author i80448
 */
public enum MILandingPages implements IModuleInfo {

    CSM_CHECKLIST("47", "CSM05", "(CSM05) CSM Checklist", "CSM Checklist", FAVORITES, Constants.APPLICATOIN_NOTES_MODULE),
    DATA_QUALITY_NOTES("63", "DQN", "(DQN) Data Quality Notes", "Data Quality Notes", FAVORITES, Constants.APPLICATOIN_NOTES_MODULE),
    DATA_RECONCILIATION("46", "R105", "(R105) Data Reconciliation", "Data Reconciliation", FAVORITES, Constants.APPLICATOIN_NOTES_MODULE),
    CLAIMS_PAID_TO_MEMBERS_NOT_IN_ELIGIBILITY_FILE_AND_OUTSIDE_OF_ELIGIBILITY_RANGE("124", "E105", "(E105) Claims Paid to Members not in Eligibility file and Outside of Eligibility Range", "Claims Paid to Members not in Eligibility file and Outside of Eligibility Range", FAVORITES, Constants.DATA_INTEGRITY_MODULE),
    DUPLICATE_CLAIMS("120", "A135", "(A135) Duplicate Claims", "Duplicate Claims", FAVORITES, Constants.DATA_INTEGRITY_MODULE),
    MATCHED_CREDITS("122", "A733", "(A733) Matched Credits", "Matched Credits", FAVORITES, Constants.DATA_INTEGRITY_MODULE),
    SERVICE_PROVIDED_AFTER_TERMINATION("121", "A730", "(A730) Service Provided After Termination", "Service Provided After Termination", FAVORITES, Constants.DATA_INTEGRITY_MODULE),
    UNMATCHED_CREDITS("123", "A732", "(A732) Unmatched Credits", "Unmatched Credits", FAVORITES, Constants.DATA_INTEGRITY_MODULE),
    MY_JOBS("290", "J001", "(J001) My Jobs", "My Jobs", FAVORITES),
    OPTIONS("86", "P104", "Options", "Options", FAVORITES),

    ACO_COST_AND_UTILIZATION("415", "CU001", "(CU001) Cost and Utilization Report", "ACO-Cost and Utilization", ANALYTICS),
    BENEFIT_ADVISOR("66", "Bdm010Cmn", "Benefit Advisor", "Benefit Advisor", ANALYTICS),
    CASE_MANAGER("18", "TG105", "(TG105) Case Manager", "Case Manager", ANALYTICS),
    //CLAIMS_101("67", "101", "(101) Claims", "Claims[Legacy]", ANALYTICS),
    CLAIMS_C01("187", "COST01", "(C01) Claims", "Claims", ANALYTICS, Claims01.class),
    SERVICES("188", "S101", "(S101) Service Category", "Service Category", ANALYTICS, HcciClaims01.class),
    CONVERSION_ANALYZER("20", "A17", "(A17) Conversion Analyzer", "Conversion Analyzer", ANALYTICS, ConversionAnalyzerA17.class),
    DEMOGRAPHY("13", "D05", "(D05) Demographic Analysis", "Demography", ANALYTICS, DemographyD05.class),
    DISEASE_MANAGER("5", "NULL", "(NULL) Disease Manager", "Disease Manager", ANALYTICS),
    DISEASE_REGISTRY("250", "(CD016)", "(CD016) Disease Registry", "Disease Registry", ANALYTICS, DiseaseRegistryCD016.class),
    INDIVIDUAL_RISK_ANALYSIS("51", "RP115", "(RP115) Individual Risk Analysis", "Individual Risk Analysis", ANALYTICS, Constants.DXCG_RISK_SOL_MODULE, IndividualRiskAnalysis.class),
    DXCG_RISK_SOLUTIONS_STATIC_REPORTS("52", "RP010", "(RP010) DxCG Risk Solutions Static Reports", "DxCG Risk Solutions Static Reports", ANALYTICS, Constants.DXCG_RISK_SOL_MODULE, StaticReportsRP010.class),
    EPISODE_GROUP("64", "EG900", "(EG900) Episode Group", "Episode Group", ANALYTICS),
    EXECUTIVE_SUMMARY("85", "E001", "(E001) Executive Summary", "Executive Summary", ANALYTICS, ExecutiveSummary.class),
    EXPENSE_DISTRIBUTION("15", "310", "(310) Expense Distribution", "Expense Distribution", ANALYTICS, ExpenseDistribution.class),
    HOSPITAL_PROFILER("185", "HP100", "(HP100) Hospital Profiles", "Hospital Profiler", ANALYTICS, HospitalProfilesHP100.class),
    INDIVIDUALS_301("4", "301", "(301) Individuals\n" +
            "Switch to New Individuals", "Individuals", ANALYTICS, Indv301.class),
    INDIVIDUALS_EXTRACT("412", "IE001", "(IE001) Individuals Extract", "Individuals Extract", ANALYTICS),
    DASHBOARD("400", "SOF101", "(SOF101) DashBoard", "Dashboard", ANALYTICS, Constants.IN_OUT_OF_NETWORK_ANALYZER, DashboardSOF101.class),
    DETAILED_ANALYTIC("310", "OU107", "(OU107) Detailed Analytics by Unit", "Detailed Analytics", ANALYTICS, Constants.IN_OUT_OF_NETWORK_ANALYZER, DetailedAnalyticsOU107.class),
    NETWORK_UTILIZATION("17", "NU105", "(NU105) Par vs Non Par Utilization", "Network Utilization", ANALYTICS, NetworkUtilizationNU105.class),
    TREND("417", "PD002", "(PD002) Population Dashboard-Trend", "Trend", ANALYTICS, Constants.POPULATION_DASHBOARD_MODULE, TrendPD002.class),
    SUMMARY("414", "PD001", "(PD001) Population Dashboard-Summary", "Summary", ANALYTICS, Constants.POPULATION_DASHBOARD_MODULE, SummaryPD001.class),
    POPULATION_RISK_DRIVE("500", "PRD01", "(PRD01) Population Risk Drivers", "Population Risk Drivers", ANALYTICS, PRD01.class),
    CP100("261", "CP100", "(CP100) Clinic Manager", "Clinic Manager", ANALYTICS, Constants.PROVIDER_MANAGEMENT_MODULE, com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.CP100.class),
    PHYSICIAN_MANAGER("262", "CP110", "(CP110) Physician Manager", "Physician Manager", ANALYTICS, Constants.PROVIDER_MANAGEMENT_MODULE, CP110.class),
    PROVIDER_PROFILER("16", "V044", "(V044) Paid by Specialty", "Provider Profiler", ANALYTICS, ProviderProfilerV044.class),
    PCMH("410", "699(PCMH)", "(699) Quality Measures", "True Performance", ANALYTICS, Constants.QUALITY_MEASURES_MODULE),
    REGULATED_QUALITY_MEASURES("305", "699", "(699) Regulated Quality Measures", "Regulated Quality Measures", ANALYTICS, Constants.QUALITY_MEASURES_MODULE),
    HEDIS("411", "699(HEDIS)", "(699) Quality Measures", "HEDIS", ANALYTICS, Constants.QUALITY_MEASURES_MODULE),
    ACO("416", "699(ACO)", "(699) Quality Measures", "ACO", ANALYTICS, Constants.QUALITY_MEASURES_MODULE),
    STAR("409", "699(STAR)", "(699) Quality Measures", "STAR", ANALYTICS, Constants.QUALITY_MEASURES_MODULE),
    SNFPROFILER("190", "(SP100)", "(SP100) SNF Profiles", "SNF Profiler", ANALYTICS, SNFProfilesSP100.class),
    DRUG_DISEASE_CONTRAINDICATIONS("292", "DDC100", "(DDC100) Drug-Disease Contraindications", "Drug-Disease Contraindications", ANALYTICS, Constants.QUALITY_MEASURES_MODULE),
    QUALITY_RISK("3", "670", "(670) Quality & Risk", "Quality & Risk", ANALYTICS, Constants.QUALITY_MEASURES_MODULE, QualityAndRisk670.class),
    RX_CONFLICTS("8", "CT005", "(CT005) Rx Conflicts", "Rx Conflicts", ANALYTICS),
    CUSTOM_PERFORMANCE_MEASURES("422", "(CPM01)", "(CPM01) Custom Performance Measures", "Custom Performance Measures", ANALYTICS, CPM01.class),
    MY_DASHBOARD("431", "UD01", "My Dashboard", "My Dashboard", ANALYTICS),
    ER_STANDARD_DASHBOARD("13850", "ER Standard Dashboard", "ER Standard Dashboard", "ER Standard Dashboard", ANALYTICS),

    //TODO Seems to be different for different users for eg (S110B) Spike Analysis By Company
    SPIKESS110A("58", "S110", "(S110A) Spike Analysis By Unit", "Spikes", ANALYTICS, SpikesS110B.class),
    SPIKESS110B("58", "S110", "(S110B) Spike Analysis By Company", "Spikes", ANALYTICS, SpikesS110B.class),
    AGED_CLAIM_ANALYSIS("38", "SL890", "(SL890) Aged Claim Analysis", "Aged Claim Analysis", ANALYTICS, Constants.STOP_LOSS_MODULE),
    AVERAGE_DAYS_TO_PAY("39", "SL850", "(SL850) Average Days to Pay", "Average Days to Pay", ANALYTICS, Constants.STOP_LOSS_MODULE),
    CENSUS("34", "SL050", "(SL050) Census", "Census", ANALYTICS, Constants.STOP_LOSS_MODULE),
    NETWORK_ANALYZER("37", "SL855", "(SL855) Network Analyzer", "Network Analyzer", ANALYTICS, Constants.STOP_LOSS_MODULE),
    NETWORK_USAGE("35", "SL871", "(SL871) Network Usage", "Network Usage", ANALYTICS, Constants.STOP_LOSS_MODULE),
    STOP_LOSS_MANAGER("33", "SL250", "(SL250) Stop Loss Manager", "Stop Loss Manager", ANALYTICS, Constants.STOP_LOSS_MODULE),
    SUBROGATION_ANALYZER("36", "SL875", "(SL875) Subrogation Analyzer", "Subrogation Analyzer", ANALYTICS, Constants.STOP_LOSS_MODULE),
    STOP_LOSS_CASE_MANAGER("32", "CM105", "(CM105) Stop Loss Case Manager", "Stop Loss Case Manager", ANALYTICS, Constants.STOP_LOSS_MODULE),
    UTILIZATION_METRICS("14", "hm50", "(HM50) Utilization Metrics", "Utilization Metrics", ANALYTICS, UtilizationMetricsHM50.class),
    INDIVIDUAL_DASHBOARD("326", "IndvDashboard", "(IndvDashboard) Individual DashBoard", "Individual DashBoard", ANALYTICS),
    QUERY_BUILDER_CS("205", "CS_GENERAL", "(Claims Search) Query Builder - Claims", "Query Builder", QUERY_BUILDER, Constants.CS_MODULE),
    QUERY_MANAGEMENT("210", "CS_REPORT", "(Claims Search) Query Management: Query Summary", "Query Management", QUERY_BUILDER, Constants.CS_MODULE),
    MVCA_BASIC("81", "301B", "(301B) Multi-Variable Clinical Analysis (Basic)", "Basic", QUERY_BUILDER, Constants.MVCA_MODULE),
    MVCA_EXPERT("82", "H10B", "(301E) Multi-Variable Clinical Analysis (Expert)", "Expert", QUERY_BUILDER, Constants.MVCA_MODULE),
    QUERY_BUILDER_ST("175", "ST_GENERAL", "(Stratifier) Query Builder - Demographics & Risk", "Query Builder", QUERY_BUILDER, Constants.STR_MODULE),
    PREDEFINED_COHORTS("413", "PRED_COHORT", "Predefined Cohorts: Cohort Summary", "Predefined Cohorts", QUERY_BUILDER, Constants.STR_MODULE),
    //TODO Seems to be different for different users for eg Query Management : Query Summary
    POPULATION_ANALYZER("180", "ST_COHORT", "Population Analyzer: Cohort Summary", "Population Analyzer", QUERY_BUILDER, Constants.STR_MODULE),
    LETTER_OUTBOX("73", "LO001", "(LO001) Letter Outbox", "Letter Outbox", OUTREACH, Constants.LETTER_GENERATION_MODULE),
    MEMBER_LIST("60", "LM001", "(LM001) Letter Generation", "Member List", OUTREACH, Constants.LETTER_GENERATION_MODULE),
    PREVENTIVE_HEALTH_LML02("44", "LML02", "(L02) Letter Generation for Preventive Health", "Preventive Health", OUTREACH, Constants.LETTER_GENERATION_MODULE),
    CONDITION_L070("76", "L070", "(L070) Letter Log: Browse by Conditions", "Condition", OUTREACH, Constants.LETTER_LOG_GROUP_SORT_BY_MODULE),
    DATE("74", "L055", "(L055) Letter Log: Browse by Date", "Date", OUTREACH, Constants.LETTER_LOG_GROUP_SORT_BY_MODULE),
    ISSUE("75", "L060", "(L060) Letter Log: Browse by Issues", "Issue", OUTREACH, Constants.LETTER_LOG_GROUP_SORT_BY_MODULE),
    CONDITION_L095("78", "L095", "(L095) Letter Log: View Condition Letters by Members", "Condition", OUTREACH, Constants.LETTER_LOG_MEMBER_SORT_BY_MODULE),
    PREVENTIVE_HEALTH_L080("79", "L080", "(L080) Letter Log: View Preventive Health Letters by Members", "Preventive Health", OUTREACH, Constants.LETTER_LOG_MEMBER_SORT_BY_MODULE),
    MEMBER_LIST_SUMMARY("61", "ML003", "(ML003) Member List Summary", "Member List Summary", OUTREACH),
    CPM01("422", "CPM01", "(CPM01) Custom Performance Measures", "Custom Performence Measures", ANALYTICS, CPM01.class);

    private String dbId;
    private String pageId;
    private String pageTitle;
    private String navLinkLabel;
    private SwitchBoard sw;
    private Class landingPageClass;
    private Optional<Modules[]> parentModules;

    private MILandingPages(String dbId, String pageId, String pageTitle, String navLinkLabel, SwitchBoard sw, Optional<Modules[]> parentModules) {
        this.dbId = dbId;
        this.pageId = pageId;
        this.pageTitle = pageTitle;
        this.navLinkLabel = navLinkLabel;
        this.sw = sw;
        this.parentModules = parentModules;
    }

    private MILandingPages(String dbId, String pageId, String pageTitle, String navLinkLabel, SwitchBoard sw) {
        this.dbId = dbId;
        this.pageId = pageId;
        this.pageTitle = pageTitle;
        this.navLinkLabel = navLinkLabel;
        this.sw = sw;
        this.parentModules = Optional.absent();
    }

    private <T> MILandingPages(String dbId, String pageId, String pageTitle, String navLinkLabel, SwitchBoard sw, Class<T> landPageClass) {
        this.dbId = dbId;
        this.pageId = pageId;
        this.pageTitle = pageTitle;
        this.navLinkLabel = navLinkLabel;
        this.sw = sw;
        this.landingPageClass = landPageClass;
        this.parentModules = Optional.absent();
    }

    private <T> MILandingPages(String dbId, String pageId, String pageTitle, String navLinkLabel, SwitchBoard sw, Optional<Modules[]> parentModules, Class<T> landPageClass) {
        this.dbId = dbId;
        this.pageId = pageId;
        this.pageTitle = pageTitle;
        this.navLinkLabel = navLinkLabel;
        this.sw = sw;
        this.parentModules = parentModules;
        this.landingPageClass = landPageClass;
    }

    public String getPageId() {
        return pageId;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getNavLinkLabel() {
        return navLinkLabel;
    }

    public SwitchBoard getSwitchBoard() {
        return sw;
    }

    public String getDbId() {
        return dbId;
    }

    public <T> Class<T> getLandingPageClass() {
        return landingPageClass;
    }

    public Optional<Modules[]> getParentModules() {
        return parentModules;
    }

    @Override
    public String getId() {
        return getDbId();
    }

    @Override
    public String getName() {
        return getNavLinkLabel();
    }

    @Override
    public SwitchBoard getSwitchboard() {
        return this.sw;
    }

    public static MILandingPages getByPageId(String pageId) {
        for (MILandingPages p : MILandingPages.values()) {
            if (p.getPageId().equals(pageId)) return p;
        }
        throw new RuntimeException("Mapping not available for pageId " + pageId);
    }

    public static Optional<MILandingPages> getByNavLinkLabel(String navLinkLabel) {
        for (MILandingPages p : MILandingPages.values()) {
            if (p.getNavLinkLabel().equals(navLinkLabel))
                return Optional.of(p);
        }
        return Optional.absent();
    }

    public static Optional<MILandingPages> byId(String moduleId) {
        for (MILandingPages p : MILandingPages.values()) {
            if (p.getDbId().equals(moduleId))
                return Optional.of(p);
        }
        return Optional.absent();
    }

    public static boolean idExists(String moduleId) {
        for (MILandingPages module : MILandingPages.values()) {
            if (module.getId().equals(moduleId))
                return true;
        }
        return false;
    }

    private static class Constants {
        public static final Optional<Modules[]> MODULE20 = Optional.of(new Modules[]{Modules.HMO, Modules.BCBS_IL_PROVIDER_QUALITY_INITIATIVE_REPORTS, Modules.CHILDHOOD_IMMUNIZATION_NOT_FOR_IPA_USE});
        public static final Optional<Modules[]> STOP_LOSS_MODULE = Optional.of(new Modules[]{Modules.STOP_LOSS_MODULES});
        public static final Optional<Modules[]> DXCG_RISK_SOL_MODULE = Optional.of(new Modules[]{Modules.DXCG_RISK_SOLUTIONS});
        public static final Optional<Modules[]> HMO_MODULE = Optional.of(new Modules[]{Modules.HMO});
        public static final Optional<Modules[]> MODULE22 = Optional.of(new Modules[]{Modules.HMO, Modules.BCBS_IL_PROVIDER_QUALITY_INITIATIVE_REPORTS, Modules.FOLLOW_UP_AFTER_HOSPITALIZATION_FOR_MENTAL_ILLNESS_NOT_FOR_IPA_USE});
        public static final Optional<Modules[]> LETTER_LOG_MEMBER_SORT_BY_MODULE = Optional.of(new Modules[]{Modules.LETTER_LOG_MEMBER_SORT_BY});
        public static final Optional<Modules[]> MODULE19 = Optional.of(new Modules[]{Modules.HMO, Modules.BCBS_IL_PROVIDER_QUALITY_INITIATIVE_REPORTS, Modules.DISEASE_MANAGEMENT_NOT_FOR_IPA_USE});
        public static final Optional<Modules[]> PROVIDER_MANAGEMENT_MODULE = Optional.of(new Modules[]{Modules.PROVIDER_MANAGEMENT});
        public static final Optional<Modules[]> STR_MODULE = Optional.of(new Modules[]{Modules.STRATIFIER});
        public static final Optional<Modules[]> MODULE18 = Optional.of(new Modules[]{Modules.HMO, Modules.BCBS_IL_PROVIDER_QUALITY_INITIATIVE_REPORTS, Modules.PREVENTIVE_CARE_MANAGEMENT});
        public static final Optional<Modules[]> IN_OUT_OF_NETWORK_ANALYZER = Optional.of(new Modules[]{Modules.IN_OUT_OF_NETWORK_ANALYZER});
        public static final Optional<Modules[]> DATA_INTEGRITY_MODULE = Optional.of(new Modules[]{Modules.DATA_INTEGRITY});
        public static final Optional<Modules[]> MODULE24 = Optional.of(new Modules[]{Modules.HMO, Modules.UM_REPORT_NOT_FOR_IPA_USE});
        public static final Optional<Modules[]> POPULATION_DASHBOARD_MODULE = Optional.of(new Modules[]{Modules.POPULATION_DASHBOARD});
        public static final Optional<Modules[]> MODULE17 = Optional.of(new Modules[]{Modules.HMO, Modules.TOP_10});
        public static final Optional<Modules[]> LETTER_GENERATION_MODULE = Optional.of(new Modules[]{Modules.LETTER_GENERATION});
        public static final Optional<Modules[]> MODULE23 = Optional.of(new Modules[]{Modules.HMO, Modules.BCBS_IL_PRESCRIPTION_DRUG_PROGRAM_REPORTS});
        public static final Optional<Modules[]> MVCA_MODULE = Optional.of(new Modules[]{Modules.MVCA});
        public static final Optional<Modules[]> FINANCIAL_REPORT_MODULE = Optional.of(new Modules[]{Modules.FINANCIAL_REPORT});
        public static final Optional<Modules[]> LETTER_LOG_GROUP_SORT_BY_MODULE = Optional.of(new Modules[]{Modules.LETTER_LOG_GROUP_SORT_BY});
        public static final Optional<Modules[]> CS_MODULE = Optional.of(new Modules[]{Modules.CLAIMS_SEARCH});
        public static final Optional<Modules[]> MODULE21 = Optional.of(new Modules[]{Modules.HMO, Modules.BCBS_IL_PROVIDER_QUALITY_INITIATIVE_REPORTS, Modules.INFLUENZA_VACCINATION_NOT_FOR_IPA_USE});
        public static final Optional<Modules[]> QUALITY_MEASURES_MODULE = Optional.of(new Modules[]{Modules.QUALITY_MEASURES});
        public static final Optional<Modules[]> APPLICATOIN_NOTES_MODULE = Optional.of(new Modules[]{Modules.APPLICATION_NOTES});
    }


}


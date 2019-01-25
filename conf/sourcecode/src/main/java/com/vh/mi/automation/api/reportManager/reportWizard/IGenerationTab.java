package com.vh.mi.automation.api.reportManager.reportWizard;

import com.vh.mi.automation.api.features.IAmWebComponent;

public interface IGenerationTab extends IAmWebComponent {

    void selectReportFormat(boolean isPDFChecked, boolean isRTFChecked);

    void selectTimePeriod(TimePeriod timePeriod);

    void selectMonthlyCost(MonthlyCost monthlyCost);

    void reportGenerationSaveAndContinue();

    void selectReportingBy(ReportingBy reportingBy);

    enum ReportingBy {
        INCURRED("incurred"),
        PAID("paid"),
        INCURRED_AND_PAID("incurredPaid"),;

        private String value;

        ReportingBy(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    enum MonthlyCost {
        PER_MEMBER("PMPM"),
        PER_EMPLOYEE("PEPM"),;

        private String value;

        MonthlyCost( String value){
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    enum TimePeriod {
        FULL_CYCLE("fullCycle"),
        CONTRACT_YEAR("contractYear"),
        ROLLING_YEAR("rollingYear"),
        YEAR_TO_DATE("yearToDate"),
        CUSTOM("customTimePeriod");

        private String value;

        TimePeriod(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}

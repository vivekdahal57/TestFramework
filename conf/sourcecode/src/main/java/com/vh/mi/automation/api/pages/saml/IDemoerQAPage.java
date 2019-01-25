package com.vh.mi.automation.api.pages.saml;

import com.vh.mi.automation.impl.pages.saml.EIDashboardDummyPage;

/**
 * Created by i81247 on 11/14/2016.
 */
public interface IDemoerQAPage {
    public static enum AppType {
        MEDICAL_INTELLIGENCE("MI"),
        REPORT_MANAGER("RM"),
        ENTERPRISE_INTELLIGENCE("EI");

        private String value;


        AppType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


    public void clickDebugMode(boolean debugMode);

    public void enterDebugAdminUsername(String adminUsername);

    public void enterDebugAdminPassword(String adminPassword);

    public void selectAppType(AppType app);

    public void enterIssuer(String issuer);

    public void enterLoginName(String userName);

    public void enterFirstName(String firstName);

    public void enterLastName(String lastName);

    public void enterSecurityGroup(String securityGroupName);

    public void enterNotBefore(String notBeforeTimestamp);

    public void enterNotOnOrAfter(String notOnOrAfterTimestamp);

    public void enterMemberId(String memberId);

    public void enterFormId(String formId);

    public void enterAppId(String appId);

    public void clickIsBusinessLvlsAsURLParam(boolean isBusinessLvlAsURLParam);

    public void enterLvl1Ids(String lvl1Ids);

    public void enterLvl2Ids(String lvl2Ids);

    public void enterLvl3Ids(String lvl3Ids);

    public void enterLvl4Ids(String lvl4Ids);

    public void enterLvl5Ids(String lvl5Ids);

    public void enterLvl6Ids(String lvl6Ids);

    public void clickEncrypt(boolean encrypt);

    public void clickTriageEmail(boolean triageEmail);

    public EIDashboardDummyPage submitForm();

}

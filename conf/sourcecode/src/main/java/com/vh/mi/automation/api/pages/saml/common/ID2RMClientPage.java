package com.vh.mi.automation.api.pages.saml.common;

import com.vh.mi.automation.impl.selenium.AbstractWebComponent;

/**
 * @author nimanandhar
 */
public interface ID2RMClientPage {

    public void enterUrlToLogin(String url);

    public void enterProduct(String product);

    public void enterLoginName(String loginName);

    public void enterIssuer(String issuer);

    public void enterNotBefore(String date);

    public void enterNotOnOrAfter(String date);

    public void enterAppId(String appId);

    public void enterLevel1(String level1);

    public void enterLevel2(String level2);

    public void enterLevel3(String level3);

    public void enterLevel4(String level4);

    public void enterLevel5(String level5);

    public void enterLevel6(String level6);

    public void doClickLoadValues();

    public void doClickEmailTriageXML();

    public String getSignedSamlResponseText();

    public <T extends AbstractWebComponent> T doSubmit(Class<T> pageObjectClass, int waitTimeSeconds);
}

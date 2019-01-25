package com.vh.mi.automation.api.pages.saml.common;

import com.vh.mi.automation.impl.selenium.AbstractWebComponent;

/**
 * @author nimanandhar
 */
public interface ISampleClientPageAUP {

    public void enterUrlToLogin(String url);

    public void enterLoginName(String loginName);

    public void enterFirstName(String firstName);

    public void enterLastName(String lastName);

    public void setEncrypt(boolean encrypt);

    public void enterFormId(String formId);

    public void enterNotBefore(String notBefore);

    public void enterNotOnOrAfter(String notAfter);

    public void enterIssuer(String issuer);

    public void enterLevel1(String level1);

    public void enterLevel2(String level2);

    public void enterLevel3(String level3);

    public void enterLevel4(String level4);

    public void enterLevel5(String level5);

    public void enterLevel6(String level6);

    public void enterLevel1Id(String level1);

    public void enterLevel2Id(String level2);

    public void enterLevel3Id(String level3);

    public void enterLevel4Id(String level4);

    public void enterLevel5Id(String level5);

    public void enterLevel6Id(String level6);

    public void doClickSamlDefaultAssertion();

    public String getSignedSamlResponseText();

    public <T extends AbstractWebComponent> T doSubmit(Class<T> pageObjectClass, int waitTimeSeconds);
}

package com.vh.mi.automation.api.pages.saml.common;

import com.vh.mi.automation.impl.selenium.AbstractWebComponent;

/**
 * @author nimanandhar
 */
public interface IHighMarkClientSamplePageAUP {

    public void enterUrlToLogin(String url);

    public void enterLoginName(String loginName);

    public void enterFirstName(String firstName);

    public void enterLastName(String lastName);

    public void enterNotBefore(String notBefore);

    public void enterNotOnOrAfter(String notAfter);

    public void enterSecurityGroup(String securityGroup);

    public void setEncrypt(boolean encrypt);

    public void enterIssuer(String issuer);

    public void enterMemberId(String memberId);

    public <T extends AbstractWebComponent> T doSubmit(Class<T> pageObjectClass, int waitTimeSeconds);
}

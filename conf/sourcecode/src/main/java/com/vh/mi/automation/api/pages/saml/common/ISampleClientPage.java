package com.vh.mi.automation.api.pages.saml.common;

import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.TimeoutException;

/**
 * @author nimanandhar
 */
public interface ISampleClientPage {

    public void enterUrlToLogin(String url);

    public void enterLoginName(String loginName);

    public void enterIssuer(String issuer);

    public void enterNotBefore(String date);

    public void enterNotOnOrAfter(String date);

    public void enterAppId(String appId);

    public void setEncrypt(boolean encrypt);

    public void enterLevel1(String level1);

    public void enterLevel2(String level2);

    public void enterLevel3(String level3);

    public void enterLevel4(String level4);

    public void enterLevel5(String level5);

    public void enterLevel6(String level6);

    public void enterFormId(String formId);

    public void enterMemberId(String memberId);

    public void doClickSamlDefaultAssertion();

    public void doClickSamlNewAssertion();

    public void doClickSamlCustomAssertion();

    public String getSignedSamlResponseText();

    public String getPublicKeyText();

    public String getPrivateKeyText();

    /**
     * Clicks on submit button and creates an instance of the pageObjectClass
     * waiting until the page is fully loaded or the waitTimeSeconds has elapsed
     * before the page is loaded
     *
     * @param pageObjectClass
     * @param waitTimeSeconds
     * @param <T>
     * @return an instance of the pageObjectClass, with the corresponding page fully loaded
     * @throws TimeoutException if the page is not fully loaded by the time waitTimeSeconds
     */
    public <T extends AbstractWebComponent> T doSubmit(Class<T> pageObjectClass, int waitTimeSeconds);
}

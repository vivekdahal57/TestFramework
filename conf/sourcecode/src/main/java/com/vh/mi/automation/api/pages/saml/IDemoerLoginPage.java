package com.vh.mi.automation.api.pages.saml;

import com.vh.mi.automation.impl.pages.saml.DemoerAssistedInputPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;

/**
 * Created by i81247 on 11/15/2016.
 */
public interface IDemoerLoginPage {
    public void enteruserName(String userName);
    public void enterPassword(String password);
    public DemoerAssistedInputPage doLogin(String Username, String passsword);
}

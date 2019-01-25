package com.vh.mi.automation.api.pages.common;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.impl.pages.common.forgotPassword.ForgotPasswordPage;
import com.vh.mi.automation.impl.pages.common.myAccountProfile.ChangePasswordPage;

/**
 * @author i80448
 */
public interface ILoginPage extends IAmWebComponent {

    /**
     * Gets currently entered users from the login box.
     *
     * @return
     */
    public User getCurrentUser();

    /**
     * Gets login status message. Message will be available only if the login is
     * failed.
     *
     * @return
     */
    public String getLoginStatusMessage();


    public IWelcomePage loginWith(User user, long timeout);


    public ILoginPage invalidLogin(User user, long timeout);


    public ChangePasswordPage loginExpectingChangePasswordScreen(User user, Integer timeout);


    public ForgotPasswordPage doOpenForgetPasswordPage();

    public <T> T loginExpectingPage(Class<T> expectedPageClass, User user,Integer timeouts);
}

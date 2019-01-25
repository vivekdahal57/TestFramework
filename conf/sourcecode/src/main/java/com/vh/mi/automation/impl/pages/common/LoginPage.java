package com.vh.mi.automation.impl.pages.common;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.api.pages.common.ILoginPage;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.impl.pages.common.forgotPassword.ForgotPasswordPage;
import com.vh.mi.automation.impl.pages.common.myAccountProfile.ChangePasswordPage;
import com.vh.mi.automation.impl.pages.saml.EULAPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public class LoginPage extends AbstractWebComponent implements ILoginPage {
    private final WebElements webElements;

    public LoginPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.userName.isDisplayed();
    }


    @Override
    public User getCurrentUser() {
        return new User(webElements.userName.getText(), webElements.password.getText());
    }


    @Override
    public String getLoginStatusMessage() {
        return webElements.status.getText();
    }


    @Override
    public IWelcomePage loginWith(User user, long timeout) {
        enterUserName(user.getUserId()).enterPassword(user.getPassword()).submit();
        WelcomePage welcomePage = PageFactory.initElements(getDriver(), WelcomePage.class);

        if (welcomePage.isNoticeBoardVisible()) {
            welcomePage.closeNoticeBoard();
        }
        if (getDriver().getTitle().contains("End User License Agreement (EULA)")) {
            EULAPage eulaPage = new EULAPage(getDriver());
            eulaPage.agree(WelcomePage.class, 30);
        }

        return welcomePage;
    }

    @Override
    public ILoginPage invalidLogin(User user, long timeout) {
        enterUserName(user.getUserId()).enterPassword(user.getPassword()).submit();
        LoginPage loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
        loginPage.doWaitTillErrorMessageAppears(timeout);
        return loginPage;
    }

    @Override
    public ChangePasswordPage loginExpectingChangePasswordScreen(User user, Integer timeout) {
        enterUserName(user.getUserId()).enterPassword(user.getPassword()).submit();
        ChangePasswordPage changePasswordPage = new ChangePasswordPage(getDriver());
        changePasswordPage.doWaitTillFullyLoaded(timeout);
        return changePasswordPage;
    }

    @Override
    public ForgotPasswordPage doOpenForgetPasswordPage() {
        webElements.forgotPasswordLink.click();
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(getDriver());
        forgotPasswordPage.doWaitTillFullyLoaded(TimeOuts.THIRTY_SECONDS);
        return forgotPasswordPage;
    }

    @Override
    public <T> T loginExpectingPage(Class<T> expectedPageClass, User user, Integer timeouts) {
        enterUserName(user.getUserId()).enterPassword(user.getPassword()).submit();
        T pageInstance = PageFactory.initElements(getDriver(), expectedPageClass);
        IAmWebComponent webComponentInstance = (IAmWebComponent) pageInstance;
          webComponentInstance.doWaitTillFullyLoaded(timeouts);
        return pageInstance;

    }

    private void doWaitTillErrorMessageAppears(long timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.visibilityOf(webElements.status));
    }


    private LoginPage enterUserName(String userName) {
        checkArgument(!isNullOrEmpty(userName));

        webElements.userName.clear();
        webElements.userName.sendKeys(userName);

        return this;
    }

    private LoginPage enterPassword(String password) {
        checkArgument(!isNullOrEmpty(password));

        webElements.password.clear();
        webElements.password.sendKeys(password);

        return this;
    }


    /**
     * Click Submit button.
     */
    private void submit() {
        SeleniumUtils.click(webElements.submitButton, getDriver());
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "username")
        private WebElement userName;

        @FindBy(id = "password")
        private WebElement password;

        @FindBy(className = "btnNormal")
        private WebElement submitButton;

        @FindBy(id = "status")
        private WebElement status;

        @FindBy(linkText = "Forgot your Password?")
        private WebElement forgotPasswordLink;


    }

}

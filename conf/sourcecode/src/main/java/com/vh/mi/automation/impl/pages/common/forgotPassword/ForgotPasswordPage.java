package com.vh.mi.automation.impl.pages.common.forgotPassword;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82298 on 10/30/2015.
 */
public class ForgotPasswordPage extends AbstractWebComponent {
    private WebElements webElements;

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.title.isDisplayed();
    }


    public ForgotPasswordPage enterUserName(String userName) {
        webElements.loginNameInput.sendKeys(userName);

        return this;
    }

    public SecurityQuestionPage clickToContinue() {
        webElements.continueBtn.click();
        SecurityQuestionPage securityQuestionPage = new SecurityQuestionPage(getDriver());
       securityQuestionPage.doWaitTillFullyLoaded(TimeOuts.THIRTY_SECONDS);
        return securityQuestionPage ;
    }

    private static class WebElements {
        public WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//div[@class='TopTitleBar']/div[@class='StepName']")
        WebElement title;

        @FindBy(xpath = "//input[@name='loginName']")
        WebElement loginNameInput;

        @FindBy(xpath = "//input[@value='Click to Continue']")
        WebElement continueBtn;
    }


}

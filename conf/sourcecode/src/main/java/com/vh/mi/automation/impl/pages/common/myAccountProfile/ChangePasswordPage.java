package com.vh.mi.automation.impl.pages.common.myAccountProfile;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.exceptions.accountprofile.InvalidCredentialsException;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82298 on 10/28/2015.
 */
public class ChangePasswordPage extends AbstractWebComponent {
    public static final String INVALID_CREDENTIALS_MESSAGE = "Your password must be different from the last three passwords";
    public final WebElements webElements;

    public ChangePasswordPage(WebDriver driver) {
        super(driver);
        this.webElements = new WebElements(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.changePasswordTitle.isDisplayed();
    }



    private ChangePasswordPage withCurrentPassword(String currentPassword) {
        webElements.oldPasswordInput.sendKeys(currentPassword);
        return this;
    }



    private String getDisplayedMessage() {
        return webElements.mainBody.findElement(By.xpath("./div[@class='errorMessage']")).getText();
    }

    private boolean isErrorMessageDisplayed() {
        return SeleniumUtils.elementExists(webElements.mainBody, "./div[@class='errorMessage']");
    }

    private boolean isPasswordSaved() {
        String currentTitle = webElements.changePasswordTitle.getText();
        String expectedTitle = "New Password: Saved";
        if (currentTitle.equals(expectedTitle)) {
            return true;
        } else
            return false;
    }

    private ChangePasswordPage withNewPassword(String newPassword) {
        webElements.newPasswordInput.sendKeys(newPassword);
        webElements.retypePasswordInput.sendKeys(newPassword);
        return this;

    }

    private ChangePasswordPage waitForPasswordToSave(Integer timeOut) {
        doWaitTillFullyLoaded(timeOut);
        return this;
    }

    private ChangePasswordPage clickContinue() {
        webElements.continueBtn.click();
        WaitUtils.waitForSeconds(TimeOuts.ONE_SECOND);
        if (isErrorMessageDisplayed()) {
            String displayedMessage = getDisplayedMessage();
            if (displayedMessage.equals(INVALID_CREDENTIALS_MESSAGE))
                throw new InvalidCredentialsException(displayedMessage);
            else
                throw new AutomationException("Unexpected Message " + displayedMessage);
        }
        return this;
    }

    private MyAccountProfile confirm() {
        webElements.conformPasswordChangeBtn.click();
        return new MyAccountProfile(getDriver());
    }

    public MyAccountProfile changePassword(String currentPassword, String newPassword) {
        this.withCurrentPassword(currentPassword).withNewPassword(newPassword).clickContinue().confirm();
        return new MyAccountProfile(getDriver());
    }

    public void handelSamePasswordAlert() {
        if (SeleniumUtils.isAlertPresent(getDriver())) {
            webElements.oldPasswordInput.sendKeys("");
           String alertMessage = getDriver().switchTo().alert().getText();

            System.out.println(" alertMessage = " + alertMessage);


        }
    }
    public static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//div[@class='TopTitleBar']/span[@class='StepName']")
        WebElement changePasswordTitle;
        @FindBy(xpath = "//div[@class='MainBody']")
        WebElement mainBody;

        @FindBy(xpath = "//input[@name='oldPassword']")
        WebElement oldPasswordInput;

        @FindBy(xpath = "//input[@id='NewPassword']")
        WebElement newPasswordInput;

        @FindBy(xpath = "//input[@id='RetypePassword']")
        WebElement retypePasswordInput;

        @FindBy(xpath = "//input[@value='Click to Continue']")
        WebElement continueBtn;

        @FindBy(xpath = "//input[@value='OK']")
        WebElement conformPasswordChangeBtn;

    }
}

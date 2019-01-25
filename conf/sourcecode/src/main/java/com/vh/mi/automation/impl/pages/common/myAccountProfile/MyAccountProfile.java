package com.vh.mi.automation.impl.pages.common.myAccountProfile;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82298 on 10/27/2015.
 */
public class MyAccountProfile extends AbstractWebComponent  {
    private final WebElements webElements;
    ChangePasswordPage changePasswordPage;

    public MyAccountProfile(WebDriver driver) {
        super(driver);
        this.webElements = new WebElements(driver);
    }

    public ChangePasswordPage openChangePasswordPage() {
      SeleniumUtils.click(webElements.changePasswordBtn,getDriver());
        changePasswordPage = new ChangePasswordPage(getDriver());
        changePasswordPage.doWaitTillFullyLoaded(TimeOuts.FIVE_SECONDS);
        return changePasswordPage;
    }


    @Override
    public boolean isFullyLoaded() {
        return webElements.userProfileTitle.isDisplayed();
    }


    public static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//table/tbody")
        WebElement rootElement;

        @FindBy(xpath = "//td[contains(text(),'Change Password')]")
        WebElement changePasswordBtn;

        @FindBy(xpath = "//td[contains(text(),'(UP010): Edit User Profile')]")
        WebElement userProfileTitle;


    }
}

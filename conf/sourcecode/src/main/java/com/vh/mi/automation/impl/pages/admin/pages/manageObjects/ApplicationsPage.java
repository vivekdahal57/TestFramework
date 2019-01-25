package com.vh.mi.automation.impl.pages.admin.pages.manageObjects;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IAddNewApplication;
import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IApplicationStatus;
import com.vh.mi.automation.api.pages.admin.pages.manageObjects.IApplications;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 5/11/2017.
 */
public class ApplicationsPage extends AbstractWebComponent implements IApplications {

    private static final String APP_ID_PLACEHOLDER = "${appId}";
    private final WebElements webElements;

    public ApplicationsPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    public IApplicationStatus viewDetailsForApp(String appId){
        SeleniumUtils.click(getViewDetailsLinkForAppId(appId));
        return PageFactory.initElements(getDriver(), ApplicationStatusPage.class);
    }

    @Override
    public void deleteApplication(String appId) {
        SeleniumUtils.click(getDeleteLinkForAppId(appId));
        if(SeleniumUtils.isAlertPresent(getDriver())){
            SeleniumUtils.clickOkOnAlert(getDriver());
        }
        this.doWaitTillFullyLoaded(TimeOuts.FIVE_SECONDS);
    }

    @Override
    public IAddNewApplication goToAddNewApplicationPage() {
        SeleniumUtils.click(webElements.addApplicationButton);
        return PageFactory.initElements(getDriver(), AddNewApplication.class);
    }

    @Override
    public boolean isApplicationPostingSuccessful(final String appId, int timeOut) {
        WaitUtils.waitUntil(getDriver(), timeOut, new Function<WebDriver,Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                WaitUtils.waitForSeconds(TimeOuts.THIRTY_SECONDS);
                SeleniumUtils.refreshPage(getDriver());
                String status = getPostingStatusForAppId(appId).trim();
                logger.info("Current Posting Status is: " + status);
                if("POSTED_FAILED".equalsIgnoreCase(status) || "POSTED_WITH_ERROR".equalsIgnoreCase(status)){
                   throw new AutomationException("There was an error while trying to post the application " + appId);
                }else if("SUCCESS".equalsIgnoreCase(status)){
                    return true;
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean hasPostingStarted(String appId) {
        String status = getPostingStatusForAppId(appId).trim();
        if("POSTING_STARTED".equalsIgnoreCase(status) || "POSTED_BASIC".equalsIgnoreCase(status)){
            return true;
        }
        return false;
    }


    private WebElement getViewDetailsLinkForAppId(String appId){
        String xpath = "(//td[text()[contains(.,'${appId}')]])[1]//following::td[@class='NextButton'][1]";
        return SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(APP_ID_PLACEHOLDER, appId));
    }

    private WebElement getDeleteLinkForAppId(String appId){
        String xpath = "(//td[text()[contains(.,'${appId}')]])[1]//following::td[@class='NextButton'][2]";
        return SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(APP_ID_PLACEHOLDER, appId));
    }

    private String getPostingStatusForAppId(String appId){
        String xpath = "//td[@class='Titlebar' and text()[contains(.,'Status of Posted Vertica Applications')]]//following::td[text()[contains(.,'${appId}')]]//following::td[3]";
        return SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(APP_ID_PLACEHOLDER, appId)).getText();
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.titleBar.getText().contains("List of all Applications");
    }


    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(className = "Titlebar")
        WebElement titleBar;

        @FindBy(xpath = "//td[@class='BackButton' and text()[contains(.,'Add Application')]]")
        WebElement addApplicationButton;

    }
}

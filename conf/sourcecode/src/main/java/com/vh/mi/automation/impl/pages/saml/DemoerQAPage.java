package com.vh.mi.automation.impl.pages.saml;

import com.google.common.base.Predicate;
import com.vh.mi.automation.api.config.IDriverConfiguration;
import com.vh.mi.automation.api.pages.common.IMIPage;
import com.vh.mi.automation.api.pages.saml.IDemoerQAPage;
import com.vh.mi.automation.impl.pages.common.GenericMIPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by i81247 on 11/15/2016.
 */
public class DemoerQAPage extends AbstractWebComponent implements IDemoerQAPage {

    private final WebElements webElements;

    public DemoerQAPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    private void enterText(WebElement inputElement, String text) {
        inputElement.clear();
        if (!StringUtils.isBlank(text))
            inputElement.sendKeys(text);
    }

    @Override
    public void clickDebugMode(boolean debugMode) {
        webElements.debugCheck.click();
    }

    @Override
    public void enterDebugAdminUsername(String adminUsername) {
        enterText(webElements.adminUserName, adminUsername);
    }

    @Override
    public void enterDebugAdminPassword(String adminPassword) {
        enterText(webElements.adminPassword, adminPassword);
    }

    @Override
    public void selectAppType(AppType app) {
        webElements.appType.click();
        new Select(webElements.appType).selectByValue(app.getValue());
    }

    @Override
    public void enterIssuer(String issuer) {
        enterText(webElements.issuer, issuer);
    }

    @Override
    public void enterLoginName(String userName) {
        enterText(webElements.userName, userName);
    }

    @Override
    public void enterFirstName(String firstName) {
        enterText(webElements.firstName, firstName);
    }

    @Override
    public void enterLastName(String lastName) {
        enterText(webElements.lastName, lastName);
    }

    @Override
    public void enterSecurityGroup(String securityGroup) {
        enterText(webElements.securityGroup, securityGroup);
    }

    @Override
    public void enterNotBefore(String notBeforeTimestamp) {
        enterText(webElements.notBefore, notBeforeTimestamp);
    }

    @Override
    public void enterNotOnOrAfter(String notOnOrAfterTimestamp) {
        enterText(webElements.notOnOrAfter, notOnOrAfterTimestamp);
    }

    @Override
    public void enterMemberId(String memberId) {
        enterText(webElements.memberId, memberId);
    }

    @Override
    public void enterFormId(String formId) {
        enterText(webElements.formId, formId);
    }

    @Override
    public void enterAppId(String appId) {
        enterText(webElements.appId, appId);
    }

    @Override
    public void clickIsBusinessLvlsAsURLParam(boolean isBusinessLvlAsURLParam) {
        webElements.lvlIdsAsURLParamsCheck.click();
    }

    @Override
    public void enterLvl1Ids(String lvl1Ids) {
        enterText(webElements.lvl1Ids, lvl1Ids);
    }

    @Override
    public void enterLvl2Ids(String lvl2Ids) {
        enterText(webElements.lvl2Ids, lvl2Ids);
    }

    @Override
    public void enterLvl3Ids(String lvl3Ids) {
        enterText(webElements.lvl3Ids, lvl3Ids);
    }

    @Override
    public void enterLvl4Ids(String lvl4Ids) {
        enterText(webElements.lvl4Ids, lvl4Ids);
    }

    @Override
    public void enterLvl5Ids(String lvl5Ids) {
        enterText(webElements.lvl5Ids, lvl5Ids);
    }

    @Override
    public void enterLvl6Ids(String lvl6Ids) {
        enterText(webElements.lvl6Ids, lvl6Ids);
    }

    @Override
    public void clickEncrypt(boolean encrypt) {
        webElements.encryptCheck.click();
    }

    @Override
    public void clickTriageEmail(boolean triageEmail) {
        webElements.triageEmailCheck.click();
    }

    @Override
    public EIDashboardDummyPage submitForm() {
        webElements.submitButton.click();
        waitTillDocumentReady(30);
        if (getDriver().getTitle().contains("End User License Agreement (EULA)")) {
            EULAPage eulaPage = new EULAPage(getDriver());
            return eulaPage.agree(EIDashboardDummyPage.class, 30);
        }

        return new EIDashboardDummyPage(getDriver());
    }

    public void enterURLQueryParam(String urlQueryParam) {
        enterText(webElements.urlQueryParam, urlQueryParam);
    }

    public <T extends AbstractWebComponent> T doSubmit(Class<T> pageObjectClass, int waitTimeSeconds) {
        webElements.submitButton.click();
        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(getDriver());
        SeleniumUtils.switchToNewWindow(getDriver());
        waitTillDocumentReady(waitTimeSeconds); //handles eula page
        if (getDriver().getTitle().contains("End User License Agreement (EULA)")) {
            EULAPage eulaPage = new EULAPage(getDriver());
            return eulaPage.agree(pageObjectClass, waitTimeSeconds);
        }
        T pageObject = PageFactory.initElements(getDriver(), pageObjectClass);
        pageObject.doWaitTillFullyLoaded(waitTimeSeconds);
        return pageObject;
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.issuer.isDisplayed();
    }


    public void switchBackToParentWindowAndCloseChildWindow(){
        SeleniumUtils.closeChildWindowAndGoBackToMainWindow(getDriver(),true);
    }

    private static final class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "debugAdminUsername")
        WebElement adminUserName;

        @FindBy(id = "debugAdminPassword")
        WebElement adminPassword;

        @FindBy(id = "product")
        WebElement appType;

        @FindBy(id = "issuer")
        WebElement issuer;

        @FindBy(id = "userName")
        WebElement userName;

        @FindBy(id = "firstName")
        WebElement firstName;

        @FindBy(id = "lastName")
        WebElement lastName;

        @FindBy(id = "securityGroup")
        WebElement securityGroup;

        @FindBy(xpath = "//*[@id=\"notBeforeDiv\"]/div/input")
        WebElement notBefore;

        @FindBy(xpath = "//*[@id=\"notOnOrAfterDiv\"]/div/input")
        WebElement notOnOrAfter;

        @FindBy(id = "memberId")
        WebElement memberId;

        @FindBy(id = "formId")
        WebElement formId;

        @FindBy(id = "appId")
        WebElement appId;

        @FindBy(id = "lvl1Ids")
        WebElement lvl1Ids;

        @FindBy(id = "lvl2Ids")
        WebElement lvl2Ids;

        @FindBy(id = "lvl3Ids")
        WebElement lvl3Ids;

        @FindBy(id = "lvl4Ids")
        WebElement lvl4Ids;

        @FindBy(id = "lvl5Ids")
        WebElement lvl5Ids;

        @FindBy(id = "lvl6Ids")
        WebElement lvl6Ids;

        @FindBy(id = "debugSwitch")
        WebElement debugCheck;

        @FindBy(id = "lvlIdsAsURLParams")
        WebElement lvlIdsAsURLParamsCheck;

        @FindBy(id = "encrypt")
        WebElement encryptCheck;

        @FindBy(id = "triageEmail")
        WebElement triageEmailCheck;

        @FindBy(id = "useRelayState")
        WebElement relayStateCheck;

        @FindBy(id = "postSAML")
        WebElement submitButton;

        @FindBy(id="urlQueryParam")
        WebElement urlQueryParam;



    }

  

    public void clearAll() {
        for (WebElement elm : getDriver().findElements(By.xpath("//div/span[contains(@class,' btn')]"))) {
            elm.click();
        }
        webElements.notBefore.clear();
        webElements.notOnOrAfter.clear();
        if (webElements.debugCheck.isSelected() == true) {
            webElements.debugCheck.click();
        }
        if (webElements.lvlIdsAsURLParamsCheck.isSelected() == true) {
            webElements.lvlIdsAsURLParamsCheck.click();
        }
        if (webElements.encryptCheck.isSelected() == true) {
            webElements.encryptCheck.click();
        }
        if (webElements.triageEmailCheck.isSelected() == true) {
            webElements.triageEmailCheck.click();
        }
        if (webElements.relayStateCheck.isSelected() == true) {
            webElements.relayStateCheck.click();
        }
    }
}

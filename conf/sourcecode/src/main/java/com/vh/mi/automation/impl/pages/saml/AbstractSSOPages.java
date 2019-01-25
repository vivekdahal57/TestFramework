package com.vh.mi.automation.impl.pages.saml;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.vh.mi.automation.api.config.IDriverConfiguration;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IMIPage;
import com.vh.mi.automation.api.pages.saml.common.ID2RMClientPage;
import com.vh.mi.automation.api.pages.saml.common.IHighMarkClientSamplePageAUP;
import com.vh.mi.automation.api.pages.saml.common.ISampleClientPage;
import com.vh.mi.automation.api.pages.saml.common.ISampleClientPageAUP;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.common.GenericMIPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Common implementation for all the saml client pages
 *
 * @author nimanandhar
 */
public abstract class AbstractSSOPages extends AbstractWebComponent
        implements IHighMarkClientSamplePageAUP, ISampleClientPageAUP, ISampleClientPage, ID2RMClientPage {

    private final WebElements webElements;

    private final Function RESPONSE_TEXT_AREA_IS_DISPLAYED = new Function() {
        @Override
        public Boolean apply(Object input) {
            return webElements.responseTextArea.isDisplayed();
        }
    };


    AbstractSSOPages(WebDriver driver) {
        super(driver);
        checkArgument(driver != null);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.titleElement.isDisplayed();
    }

    protected static void validateApplication(String appUrl) {
        if (!appUrl.matches("^http(?:s)://[^/]*$")) {
            throw new AutomationException("Invalid appUrl. Please enter an appUrl of the form https://nvscmlinq1.d2hawkeye.net:8200" +
                    "The port number is optional but it should not end with a /"
            );
        }
    }


    private void enterText(WebElement inputElement, String text) {
        inputElement.clear();
        if (!StringUtils.isBlank(text))
            inputElement.sendKeys(text);
    }


    @Override
    public final void enterUrlToLogin(String url) {
        enterText(webElements.urlLogin, url);
    }

    @Override
    public final void enterProduct(String product) {
        enterText(webElements.product, product);
    }


    public final void enterLoginName(String loginName) {
        enterText(webElements.userName, loginName);
    }


    @Override
    public final void enterFirstName(String firstName) {
        enterText(webElements.firstName, firstName);
    }


    @Override
    public final void enterLastName(String lastName) {
        enterText(webElements.lastName, lastName);
    }

    @Override
    public final void enterSecurityGroup(String securityGroup) {
        enterText(webElements.securityGroup, securityGroup);
    }


    public final void enterIssuer(String issuer) {
        enterText(webElements.Issuer, issuer);
    }


    public final void enterNotBefore(String date) {
        enterText(webElements.NotBefore, date);
    }


    public final void enterNotOnOrAfter(String date) {
        enterText(webElements.NotOnOrAfter, date);
    }


    public final void enterAppId(String appId) {
        enterText(webElements.SSOAppID, appId);
    }


    public final void setEncrypt(boolean encrypt) {
        if (!isChecked(webElements.encrypt) == encrypt) {
            webElements.encrypt.click();
        }
    }


    private boolean isChecked(WebElement webElement) {
        Preconditions.checkArgument(webElement.getAttribute("type").equals("checkbox"));
        String checkedAttribute = webElement.getAttribute("checked");
        return checkedAttribute != null && checkedAttribute.equals("true");
    }


    public final void enterLevel1(String level1) {
        enterText(webElements.level1Groups, level1);
    }


    public final void enterLevel2(String level2) {
        enterText(webElements.level2Groups, level2);
    }


    public final void enterLevel3(String level3) {
        enterText(webElements.level3Groups, level3);
    }


    public final void enterLevel4(String level4) {
        enterText(webElements.level4Groups, level4);
    }


    public final void enterLevel5(String level5) {
        enterText(webElements.level5Groups, level5);
    }


    public final void enterLevel6(String level6) {
        enterText(webElements.level6Groups, level6);
    }


    public final void enterFormId(String formId) {
        enterText(webElements.form, formId);
    }


    public final void enterMemberId(String memberId) {
        enterText(webElements.memberID, memberId);
    }


    public final void enterLevel1Id(String level1Id) {
        enterText(webElements.lvl1Id, level1Id);
    }


    public final void enterLevel2Id(String level2Id) {
        enterText(webElements.lvl2Id, level2Id);
    }


    public final void enterLevel3Id(String level3Id) {
        enterText(webElements.lvl3Id, level3Id);
    }


    public final void enterLevel4Id(String level4Id) {
        enterText(webElements.lvl4Id, level4Id);
    }


    public final void enterLevel5Id(String level5Id) {
        enterText(webElements.lvl5Id, level5Id);
    }


    public final void enterLevel6Id(String level6Id) {
        enterText(webElements.lvl6Id, level6Id);
    }


    public final void doClickSamlCustomAssertion() {
        SeleniumUtils.click(webElements.samlCustomAssertionButton, getDriver());
        WaitUtils.waitForMilliSeconds(500);
    }


    @Override
    public final void doClickSamlDefaultAssertion() {
        SeleniumUtils.click(webElements.samlDefaultAssertionButton, getDriver());
        WaitUtils.waitForMilliSeconds(500);
    }


    @Override
    public final void doClickLoadValues() {
        SeleniumUtils.click(webElements.loadValuesButton, getDriver());
        WaitUtils.waitForMilliSeconds(500);
    }


    @Override
    public final void doClickSamlNewAssertion() {
        webElements.samlNewAssertionButton.click();
        WaitUtils.waitForMilliSeconds(500);
    }


    @Override
    public String getSignedSamlResponseText() {
        WaitUtils.waitUntil(getDriver(), TimeOuts.FIVE_SECONDS, RESPONSE_TEXT_AREA_IS_DISPLAYED);
        return webElements.responseTextArea.getText();
    }

    @Override
    public void doClickEmailTriageXML(){
        webElements.triageEmail.click();
    }

    @Override
    public String getPublicKeyText() {
        return webElements.publicKey.getText();
    }

    @Override
    public String getPrivateKeyText() {
        return webElements.privateKey.getText();
    }


    @Override
    public <T extends AbstractWebComponent> T doSubmit(Class<T> pageObjectClass, int waitTimeSeconds) {
        if (SeleniumUtils.getBrowser(getDriver()) == IDriverConfiguration.Browser.IE) {
            SeleniumUtils.click(webElements.submitButtonIE, getDriver());
        } else {
            webElements.submitButton.click();
        }

        //Since the EULA can appear at any time, we cannot be really sure which page we will
        //land in, so use a generic MI Page component which should work here
        IMIPage someMiPage = PageFactory.initElements(getDriver(), GenericMIPage.class);
        someMiPage.doWaitTillFullyLoaded(waitTimeSeconds);

        if (getDriver().getTitle().equals("End User License Agreement (EULA)")) {
            EULAPage eulaPage = new EULAPage(getDriver());
            return eulaPage.agree(pageObjectClass, waitTimeSeconds);
        }

        return PageFactory.initElements(getDriver(), pageObjectClass);
    }


    void clickReportManagerButton() {
        webElements.reportManagerButton.click();
    }


    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "/html/body/form[1]/center/b")
        WebElement titleElement;

        @FindBy(id = "urlLogin")
        WebElement urlLogin;

        @FindBy(id = "userName")
        WebElement userName;

        @FindBy(id = "product")
        WebElement product;

        @FindBy(id = "firstName")
        WebElement firstName;

        @FindBy(id = "lastName")
        WebElement lastName;

        @FindBy(id = "securityGroup")
        WebElement securityGroup;

        @FindBy(id = "Issuer")
        WebElement Issuer;

        @FindBy(id = "NotBefore")
        WebElement NotBefore;

        @FindBy(id = "NotOnOrAfter")
        WebElement NotOnOrAfter;

        @FindBy(id = "SSOAppID")
        WebElement SSOAppID;

        @FindBy(id = "encrypt")
        WebElement encrypt;

        @FindBy(id = "level1Groups")
        WebElement level1Groups;

        @FindBy(id = "level2Groups")
        WebElement level2Groups;

        @FindBy(id = "level3Groups")
        WebElement level3Groups;

        @FindBy(id = "level4Groups")
        WebElement level4Groups;

        @FindBy(id = "level5Groups")
        WebElement level5Groups;

        @FindBy(id = "level6Groups")
        WebElement level6Groups;

        @FindBy(id = "LVL1ID")
        WebElement lvl1Id;

        @FindBy(id = "LVL2ID")
        WebElement lvl2Id;

        @FindBy(id = "LVL3ID")
        WebElement lvl3Id;

        @FindBy(id = "LVL4ID")
        WebElement lvl4Id;

        @FindBy(id = "LVL5ID")
        WebElement lvl5Id;

        @FindBy(id = "LVL6ID")
        WebElement lvl6Id;

        @FindBy(id = "form")
        WebElement form;

        @FindBy(id = "memberID")
        WebElement memberID;

        @FindBy(id = "triageEmail")
        WebElement triageEmail;

        @FindBy(xpath = "//input[@value='SAML-Default Assertion']")
        WebElement samlDefaultAssertionButton;

        @FindBy(xpath = "//input[@value='SAML-New Assertion']")
        WebElement samlNewAssertionButton;

        @FindBy(xpath = "//input[@value='SAML-Custom Assertion']")
        WebElement samlCustomAssertionButton;

        //d2rm client page only
        @FindBy(xpath = "//input[@value='Load Values']")
        WebElement loadValuesButton;

        //d2rm client page only
        @FindBy(xpath = "//input[@value='Report Manager']")
        WebElement reportManagerButton;

        /*
        Note that there are two types of submit button in many (if not all) of
        the saml forms that are present simultaneously. One of them is hidden
        and placed inside a form that has a style attribute of 'display:none'
        So we want a submit button that is not inside the form with attribute
        of display:none.
        Also note that for the second submit xpath, we cannot rely only on
        name being submit because some buttons like SAML-Default Assertions,
        SAML-New Assertion etc also have a name 'submit'
         */
        @FindAll({
                @FindBy(xpath = "//form[not(@style='display:none;')]//input[@name='btnSubmit']"),
                @FindBy(xpath = "//form[not(@style='display:none;')]//input[@type='submit' and @name='submit' and @value='Submit']")
        })
        WebElement submitButton;

        @FindAll({
                @FindBy(xpath = "//form[not(@style='display: none;')]//input[@name='btnSubmit']"),
                @FindBy(xpath = "//form[not(@style='display: none;')]//input[@type='submit' and @name='submit' and @value='Submit']")
        })
        WebElement submitButtonIE;

        @FindAll({
                @FindBy(xpath = "//textarea[@name='response']"),
                @FindBy(id = "samlStr")}//for D2RMClientPage.jsp
        )
        WebElement responseTextArea;

        @FindBy(xpath = "//tr/td[text()='Public Key']/parent::tr/following-sibling::tr[1]")
        WebElement publicKey;

        @FindBy(xpath = "//tr/td[text()='Private Key']/parent::tr/following-sibling::tr[1]")
        WebElement privateKey;


    }
}

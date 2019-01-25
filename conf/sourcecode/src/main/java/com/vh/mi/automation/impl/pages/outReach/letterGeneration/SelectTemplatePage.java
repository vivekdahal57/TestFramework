package com.vh.mi.automation.impl.pages.outReach.letterGeneration;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.outReach.letterGeneration.ISelectTemplatePage;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

/**
 * Created by i10314 on 8/9/2017.
 */
public class SelectTemplatePage extends AbstractWebComponent implements ISelectTemplatePage {
    private WebElements webElements;
    private LoadingPopup loadingPopup;
    private static String TEMPLATE_NAME_PALCEHOLDER = "${templateName}";
    LetterOutbox letterOutbox;

    public SelectTemplatePage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
        loadingPopup = new LoadingPopup(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.isDisplayed();
    }

    public void uploadLetterTemplate(String templateName, int templateType, final String templateToUpload, final ExecutionContext context) throws Exception {
        logger.info("Uploading Letter Template " + templateToUpload);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnUploadLetterElement);
        SeleniumUtils.click(webElements.btnUploadLetterElement);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.templateNameInputText);
        SeleniumUtils.sendKeysToInput(templateName, webElements.templateNameInputText);

        setFileToUpload(templateToUpload, context);
        SeleniumUtils.selectByValue(webElements.templateTypeSelectElement, String.valueOf(templateType));
        SeleniumUtils.click(webElements.uploadBtn);
        this.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
    }

    private void setFileToUpload(String templateToUpload, ExecutionContext context) throws Exception {
        String uploadFilePath = ((File) FileDownloadUtils.getFileToUpload(templateToUpload, context)).getAbsolutePath();
        String browseFileToUploadElement = "d2FormTemplateUpload:fileUpload:file";
        FileDownloadUtils.setFileLocatorForFileUpload(context, SeleniumUtils.findElementById(getDriver(), browseFileToUploadElement)).sendKeys(uploadFilePath);
    }

    public boolean isLetterTemplateUploadedSuccessfully(String templateName) {
        boolean flag = true;
        int counter = 1;
        while (flag && counter < 10) {
            WaitUtils.waitForSeconds(TimeOuts.TEN_SECONDS);
            try {
                getTemplateNameElement(templateName).isDisplayed();
                flag = false;
            } catch (NoSuchElementException ex) {
                logger.info("The Letter Template " + "'" + templateName + "'" + " Doesn't Exists " + ex);
                counter++;
            }
        }
        return !flag;
    }

    private WebElement getTemplateNameElement(String templateName) {
        String xpath = "//table[@id='d2Form:simpleGrid']//td[text() = '${templateName}']";
        return SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(TEMPLATE_NAME_PALCEHOLDER, templateName));
    }

    public boolean sampleTemplateDownloadValidation(String sampleTemplateName, ExecutionContext context, int waitTime) throws Exception {
        if (!FileDownloadUtils.validateDownloadedFile(sampleTemplateName, context, waitTime)) {
            throw new AutomationException("Could not Download Report " + sampleTemplateName);
        }
        return true;
    }

    public void chooseCreatedTemplateAndProvideNameLetterCampaignName(String templateName, String campaignName) {
        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]//td[.=\"${templateName}\"]//preceding::td[1]/input[1]";
        WebElement templateRadioBtn = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(TEMPLATE_NAME_PALCEHOLDER, templateName));
        WaitUtils.waitUntilEnabled(getDriver(),templateRadioBtn);
        SeleniumUtils.click(templateRadioBtn);
        SeleniumUtils.sendKeysToInput(campaignName, webElements.nameLetterCampaignTextBox);
    }

    public LetterOutbox sendToOutbox() {

        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnSendToOutBox);
        SeleniumUtils.click(webElements.btnSendToOutBox);
        loadingPopup.waitTillDisappears();
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnSchedule);
        SeleniumUtils.click(webElements.btnSchedule);
        loadingPopup.waitTillDisappears();
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnTakeMeToOutbox);
        SeleniumUtils.click(webElements.btnTakeMeToOutbox);
        letterOutbox = new LetterOutbox(getDriver());

        SeleniumUtils.closeChildWindowAndGoBackToMainWindow(getDriver(),false);

        return letterOutbox;
    }

    private static class WebElements {
        public WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//*[@id=\"pageTitle\"]")
        private WebElement pageTitle;

        @FindBy(xpath = "//*[@id=\"d2Form:topToolBar\"]/tbody/tr//a[.=\"Upload Letter Template\"]")
        private WebElement btnUploadLetterElement;

        @FindBy(id = "d2FormTemplateUpload:templateName")
        private WebElement templateNameInputText;

        @FindBy(id = "d2FormTemplateUpload:templmenu")
        private WebElement templateTypeSelectElement;

        @FindBy(id = "d2FormTemplateUpload:fileUpload:upload2")
        private WebElement uploadBtn;

        @FindBy(xpath = "//*[@id=\"d2Form:letterName\"]")
        private WebElement nameLetterCampaignTextBox;

        @FindBy(xpath = "//*[@id=\"d2Form:topToolBar\"]/tbody/tr/td//a[.=\"Send To Outbox\"]")
        private WebElement btnSendToOutBox;

        @FindBy(xpath = "//*[@id=\"d2Form:scheduleLink\"]")
        private WebElement btnSchedule;

        @FindBy(xpath = "//*[@id=\"sendToOutboxContentTable\"]/tbody//input[@value=\"Take me to outbox\"]")
        private WebElement btnTakeMeToOutbox;
    }

    public enum TemplateType {
        WELCOME_LETTER("1"),
        PROVIDER_OUTREACH("4"),
        OUTREACH("2"),
        GENERAL("3");

        private String tName;

        TemplateType(String tName) {
            this.tName = tName;
        }

        public String getValue() {
            return tName;
        }
    }

}

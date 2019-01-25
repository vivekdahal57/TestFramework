package com.vh.mi.automation.impl.pages.outReach.letterGeneration;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by i82716 on 5/16/2017.
 */
public class ManageTemplates extends AbstractWebComponent {
    private static Logger logger = LoggerFactory.getLogger(ManageTemplates.class);
    private WebElements webElements;
    private final LoadingPopup loadingPopup;
    private static String TEMPLATE_NAME_PALCEHOLDER = "${templateName}";

    public ManageTemplates(WebDriver driver){
        super(driver);
        webElements = new WebElements(driver);
        loadingPopup = new LoadingPopup(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return (webElements.pageTitle.getText().equalsIgnoreCase("(LM004) Manage Templates"));
    }

    public void uploadLetterTemplate(String templateName, int templateType, final String templateToUpload, final ExecutionContext context) throws Exception {
        logger.info("Uploading Letter Template " + templateToUpload);
        webElements.uploadLetterElement.click();
        webElements.templateNameInputText.sendKeys(templateName);
        setFileToUpload(templateToUpload,context);
        SeleniumUtils.selectByValue(webElements.templateTypeSelectElement, String.valueOf(templateType));;
        webElements.uploadBtn.click();
        waitTillDocumentReady(TimeOuts.THIRTY_SECONDS);
        SeleniumUtils.closeChildWindowAndGoBackToMainWindow(getDriver(),true);
    }

    private void setFileToUpload(String templateToUpload, ExecutionContext context) throws Exception {
        String uploadFilePath = ((File) FileDownloadUtils.getFileToUpload(templateToUpload,context)).getAbsolutePath();
        String browseFileToUploadElement = "d2FormTemplateUpload:fileUpload:file";
        FileDownloadUtils.setFileLocatorForFileUpload(context, SeleniumUtils.findElementById(getDriver(),browseFileToUploadElement)).sendKeys(uploadFilePath);
    }

    public void extractLetterTemplate(String templateName){
        SeleniumUtils.hover(getDriver(),getHoverElement(templateName));
        WaitUtils.waitUntilEnabled(getDriver(),webElements.downloadTemplateLink);
        webElements.downloadTemplateLink.click();
        if(SeleniumUtils.isAlertPresent(getDriver())){
            SeleniumUtils.clickOkOnAlert(getDriver());
        }
    }

    public boolean downloadLetterTemplateAndValidate(String downloadTemplateName, ExecutionContext context) throws Exception {
        if(!FileDownloadUtils.validateDownloadedFile(downloadTemplateName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not Download Report " + downloadTemplateName);
        }
        return true;
    }

    public boolean sampleTemplateDownloadValidation(String sampleTemplateName, ExecutionContext context) throws Exception {
        if(!FileDownloadUtils.validateDownloadedFile(sampleTemplateName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not Download Report " + sampleTemplateName);
        }
        return true;
    }

    public boolean isLetterTemplateUploadedSuccessfully(String templateName){
        try{
            return getTemplateNameElement(templateName).isDisplayed();
        }catch (NoSuchElementException ex){
            logger.info("The Letter Template "+ "'" + templateName + "'" + " Doesn't Exists " + ex);
            return false;
        }
    }

    private WebElement getTemplateNameElement(String templateName){
        String xpath = "//table[@id='d2Form:simpleGrid']//td[text() = '${templateName}']";
        return SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(TEMPLATE_NAME_PALCEHOLDER,templateName));
    }

    private WebElement getHoverElement(String templateName){
        String xpath = "//table[@id='d2Form:simpleGrid']//td[text() = '${templateName}']//following::a[1]";
        return SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(TEMPLATE_NAME_PALCEHOLDER,templateName));
    }


    private static class WebElements {
        public WebElements(WebDriver driver){
            PageFactory.initElements(driver,this);
        }

        @FindBy(id = "pageTitle")
        WebElement pageTitle;

        @FindBy(xpath = "//table[@class='d2_toolBar_item']//a[text() = 'Upload Letter Template']")
        WebElement uploadLetterElement;

        @FindBy(id = "d2FormTemplateUpload:templateName")
        WebElement templateNameInputText;

        @FindBy(id = "d2FormTemplateUpload:templmenu")
        WebElement templateTypeSelectElement;

        @FindBy(id = "d2FormTemplateUpload:fileUpload:upload2")
        WebElement uploadBtn;

        @FindBy(xpath = "//div[@id='dropmenudiv']//a[text() = 'View']")
        WebElement downloadTemplateLink;


    }
}

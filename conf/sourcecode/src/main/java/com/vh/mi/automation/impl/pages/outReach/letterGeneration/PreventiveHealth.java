package com.vh.mi.automation.impl.pages.outReach.letterGeneration;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.outReach.letterGeneration.IPreventiveHealth;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

/**
 * Created by i10314 on 8/28/2017.
 */
public class PreventiveHealth extends AbstractLandingPage implements IPreventiveHealth {
    private static final String MODULE_ID = "44";
    private static final String UPLOADED_TEMPLATE_NAME_HOLDER = "${Template Name}";
    private static final String ROW_PLACE_HOLDER = "${row}";
    private WebElements webElements;

    public PreventiveHealth(WebDriver driver) {
        super(driver, MODULE_ID);
        webElements = new WebElements(getDriver());
    }

    public void updateLetterTemplate(String templateToUpload, final ExecutionContext context) throws Exception {
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnUpdateLetterTemplate);
        SeleniumUtils.click(webElements.btnUpdateLetterTemplate);

        WaitUtils.waitUntilDisplayed(getDriver(), webElements.updateTemplateHeader, TimeOuts.FIVE_SECONDS);
        setFileToUpload(templateToUpload, context);

        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnUpload);
        SeleniumUtils.click(webElements.btnUpload);

        waitTillDocumentReady(TimeOuts.TEN_SECONDS);
    }

    public boolean letterTemplateDownloadValidation(String sampleTemplateName, ExecutionContext context, int waitTime) throws Exception {
        if (!FileDownloadUtils.validateDownloadedFile(sampleTemplateName, context, waitTime)) {
            throw new AutomationException("Could not Download Report " + sampleTemplateName);
        }
        return true;
    }

    public boolean letterTemplateUpdatedSuccessfully(String uploadedTemplate) {
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnUpdateLetterTemplate);
        SeleniumUtils.click(webElements.btnUpdateLetterTemplate);
        WaitUtils.waitUntilDisplayed(getDriver(), webElements.updateTemplateHeader, TimeOuts.FIVE_SECONDS);

        String xpath = "//*[@id=\"d2FormLetterTemplateHistory:historyDataGrid:tb\"]/tr[1]/td[.=\"${Template Name}\"]";
        WebElement uploadedTemplateName = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(UPLOADED_TEMPLATE_NAME_HOLDER, uploadedTemplate.replace("*", "")));
        return uploadedTemplateName.isDisplayed();
    }

    public void selectIndividualsAndGenerateLetterForThem(Integer selection) {
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnCloseForm);
        SeleniumUtils.click(webElements.btnCloseForm);

        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr[${row}]/td[2]/input[1]";
        for (int i = 1; i <= selection; i++) {
            WebElement chkbox = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(ROW_PLACE_HOLDER, Integer.toString(i)));
            WaitUtils.waitUntilEnabled(getDriver(), chkbox);
            SeleniumUtils.click(chkbox);
        }

        WaitUtils.waitUntilEnabled(getDriver(), webElements.hoverGenerateLetter);
        SeleniumUtils.hover(getDriver(), webElements.hoverGenerateLetter);

        WaitUtils.waitUntilEnabled(getDriver(), webElements.generateLetterLink);
        SeleniumUtils.click(webElements.generateLetterLink);

        waitTillDocumentReady(TimeOuts.TEN_SECONDS);
    }

    private void setFileToUpload(String templateToUpload, ExecutionContext context) throws Exception {
        String uploadFilePath = ((File) FileDownloadUtils.getFileToUpload(templateToUpload, context)).getAbsolutePath();
        String browseFileToUploadElement = "d2FormLetterTemplateHistory:upload:file";
        FileDownloadUtils.setFileLocatorForFileUpload(context, SeleniumUtils.findElementById(getDriver(), browseFileToUploadElement)).sendKeys(uploadFilePath);
    }

    public static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"d2Form:topToolBar\"]/tbody/tr//a[.=\"Update Letter Template\"]")
        private WebElement btnUpdateLetterTemplate;

        @FindBy(xpath = "//*[@id=\"updateTemplateHeader\"]")
        private WebElement updateTemplateHeader;

        @FindBy(xpath = "//*[@id=\"d2FormLetterTemplateHistory:upload:upload2\"]")
        private WebElement btnUpload;

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid\"]/thead/tr/th[5]//a[1]")
        private WebElement hoverGenerateLetter;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]/a[.=\"Generate Letter to Selected\"]")
        private WebElement generateLetterLink;

        @FindBy(xpath = "//*[@id=\"updateTemplateContentDiv\"]/div/img")
        private WebElement btnCloseForm;
    }
}

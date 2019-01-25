package com.vh.mi.automation.impl.pages.outReach.letterGeneration;


import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.outReach.letterGeneration.ILetterOutbox;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.pages.queryBuilder.Individual301;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82716 on 5/16/2017.
 */
public class LetterOutbox extends AbstractLandingPage implements ILetterOutbox {
    private static final String MODULE_ID = "73";
    private final WebElements webElements;
    private ManageTemplates manageTemplates;
    private static final String LETTER_CAMPAIGN_NAME_PLACE_HOLDER = "${LetterCampaignName}";


    public LetterOutbox(WebDriver driver) {
        super(driver, MODULE_ID);
        webElements = new WebElements(driver);
        manageTemplates = new ManageTemplates(getDriver());
    }

    public ManageTemplates goToManageTemplate() {
        webElements.manageTemplatesElement.click();
        SeleniumUtils.switchToNewWindow(getDriver());
        manageTemplates.isFullyLoaded();
        return manageTemplates;
    }

    public void downloadCreatedTemplate(String letterCampaignName) {
        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]//td[.=\"${LetterCampaignName}\"]//preceding::td[1]/a[1]";
        WebElement hoverElement = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(LETTER_CAMPAIGN_NAME_PLACE_HOLDER, letterCampaignName));

        WaitUtils.waitUntilEnabled(getDriver(), hoverElement);
        SeleniumUtils.hover(getDriver(), hoverElement);

        WaitUtils.waitUntilEnabled(getDriver(), webElements.templateDownloadLink);
        SeleniumUtils.click(webElements.templateDownloadLink);
        this.doWaitTillFullyLoaded(TimeOuts.TEN_SECONDS);
    }

    public boolean checkIfStatusOfCreatedTemplateIsComplete(String letterCampaignName) {
        boolean flag = true;
        int attempt = 1;
        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]//td[.=\"${LetterCampaignName}\"]/following::td[@id=\"d2Form:simpleGrid:0:column_STATUSNAME\"]";
        WebElement status = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(LETTER_CAMPAIGN_NAME_PLACE_HOLDER, letterCampaignName));

        while (flag && attempt < 15) {
            try {
                if (status.getText().equalsIgnoreCase("Complete")) {
                    flag = false;
                    break;
                } else {
                    WaitUtils.waitForSeconds(TimeOuts.TEN_SECONDS);
                    SeleniumUtils.refreshPage(getDriver());
                    this.doWaitTillFullyLoaded(TimeOuts.TWO_MINUTES);
                    attempt++;
                }
            } catch (StaleElementReferenceException ex) {
                logger.error("The state of template did not changed to complete.");
                status = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(LETTER_CAMPAIGN_NAME_PLACE_HOLDER, letterCampaignName));
            }
        }
        return !flag;
    }

    public void markTheTemplateAsSent(String letterCampaignName) {
        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]//td[.=\"${LetterCampaignName}\"]//preceding::td[1]/a[1]";
        WebElement hoverElement = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(LETTER_CAMPAIGN_NAME_PLACE_HOLDER, letterCampaignName));

        WaitUtils.waitUntilEnabled(getDriver(), hoverElement);
        SeleniumUtils.hover(getDriver(), hoverElement);

        WaitUtils.waitUntilEnabled(getDriver(), webElements.markAsSentLink);
        SeleniumUtils.click(webElements.markAsSentLink);
        waitTillDocumentReady(TimeOuts.TEN_SECONDS);
    }

    public Individual301 goToIndividual301page(String letterCampaignName) {
        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]//td[.=\"${LetterCampaignName}\"]//preceding::td[1]/a[1]";
        WebElement hoverElement = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(LETTER_CAMPAIGN_NAME_PLACE_HOLDER, letterCampaignName));

        WaitUtils.waitUntilEnabled(getDriver(), hoverElement);
        SeleniumUtils.hover(getDriver(), hoverElement);

        WaitUtils.waitUntilEnabled(getDriver(), webElements.goToIndividualLink);
        SeleniumUtils.click(webElements.goToIndividualLink);
        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(getDriver());
        SeleniumUtils.switchToNewWindow(getDriver());

        Individual301 individual301 = new Individual301(getDriver());
        individual301.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);

        return individual301;

    }

    public boolean downloadLetterTemplateAndValidate(String downloadTemplateName, ExecutionContext context, int waitTime) throws Exception {
        if (!FileDownloadUtils.validateDownloadedFile(downloadTemplateName, context, waitTime)) {
            throw new AutomationException("Could not Download Report " + downloadTemplateName);
        }
        return true;
    }

    private static class WebElements {
        public WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//div[@id='topToolBarDiv']//a[text()='Manage Templates']")
        private WebElement manageTemplatesElement;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]/a[.=\"Download\"]")
        private WebElement templateDownloadLink;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]/a[.=\"Mark As Sent\"]")
        private WebElement markAsSentLink;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]/a[.=\"Individual\"]")
        private WebElement goToIndividualLink;

    }
}

package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardOutreachHistory;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10314 on 8/18/2017.
 */
public class IndividualDashboardOutreachHistory extends AbstractIndividualDashboard implements IIndividualDashboardOutreachHistory{
    private WebElements webElements;
    public IndividualDashboardOutreachHistory(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
    }

    @Override
    public boolean isFullyLoaded(){
        return webElements.pageTitle.isDisplayed();
    }

    @Override
    public String getPageTitle() {
        return "(L090) Letter Detail";
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();
    }

    public void generateLetter(){
        WaitUtils.waitUntilEnabled(getDriver(),webElements.hoverBtn);
        SeleniumUtils.hover(getDriver(),webElements.hoverBtn);

        WaitUtils.waitUntilEnabled(getDriver(),webElements.letterGenerateLink);
        SeleniumUtils.click(webElements.letterGenerateLink);
        waitTillDocumentReady(TimeOuts.TEN_SECONDS);
    }

    public boolean downloadLetterTemplateAndValidate(String generatedLetter, ExecutionContext context, int waitTime) throws Exception {
        if (!FileDownloadUtils.validateDownloadedFile(generatedLetter, context, waitTime)) {
            throw new AutomationException("Could not Download Report " + generatedLetter);
        }
        return true;
    }

    public static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"pageTitle\"]/span")
        private WebElement pageTitle;

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:0:column_LetterDate\"]/following::a[1]")
        private WebElement hoverBtn;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]/a[.=\"Generate Letter\"]")
        private WebElement letterGenerateLink;
    }

}

package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardTrendAnalysis;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author nimanandhar
 */
public class IndividualDashboardTrendAnalysis extends AbstractIndividualDashboard implements IIndividualDashboardTrendAnalysis {

    private WebElements webElements;
    private static final String CLAIM_NAME_PALCEHOLDER = "${claim}";
    private LoadingPopup loadingPopup;
    private static Logger logger = LoggerFactory.getLogger(IndividualDashboardTrendAnalysis.class);

    public IndividualDashboardTrendAnalysis(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
        loadingPopup = new LoadingPopup(getDriver());
    }

    @Override
    public String getPageTitle() {
        return "Individual Dashboard: (335) Trend Analysis";
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();
    }

    @Override
    public boolean isDrillPageValid() {
        logger.info("DrillPage is in valid state if selection of Rx Claim Cnt and Med Claim Cnt checkbox is reflected in the table");

        if(!webElements.medClaimCntCheckBox.isSelected()) {
            webElements.medClaimCntCheckBox.click();
            loadingPopup.waitTillDisappears();
        }
        if(!webElements.rxClaimCntCheckBox.isSelected()) {
            webElements.rxClaimCntCheckBox.click();
            loadingPopup.waitTillDisappears();
        }

        logger.info("Checking if trend tables contains 'Medical claim Count' and 'Rx claim count' row ");
        if (getClaimCntElement("Medical claim Count").size() == 1 && getClaimCntElement("Rx claim count").size() == 1) {
            logger.info("DrillPage is in valid state");
            return true;
        }else {
            logger.info("Either one of the claim cnt is not present in the table. Thus DrillPage is not in valid state");
            return false;
        }
    }

    private List<WebElement> getClaimCntElement(String claim) {
        String xpath = "//table[@id='d2Form:paidGrid']//td[text()[contains(.,'${claim}')]]";
        return SeleniumUtils.findElementsByXPath(getDriver(), xpath.replace(CLAIM_NAME_PALCEHOLDER, claim));
    }

   private class WebElements{
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "d2Form:medClmCntChk")
        WebElement medClaimCntCheckBox;

        @FindBy(id = "d2Form:rxCntChk")
        WebElement rxClaimCntCheckBox;

        @FindBy(id = "d2Form:paidGrid")
        WebElement paidGridTable;
    }
}

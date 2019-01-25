package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PredefinedCohortsCohortDetails;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.PredefinedCohortsCohortDetails.IPredefinedCohortsCohortDetails;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.queryBuilder.Individual301;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10314 on 7/21/2017.
 */
public class PredefinedCohortsCohortDetails extends AbstractWebComponent implements IPredefinedCohortsCohortDetails {
    private WebElements webElements;
    Individual301 individual301;

    public PredefinedCohortsCohortDetails(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
    }

    public String getMemberCountInCohortDetail(){

        return webElements.memberCount.getText();
    }

    public Individual301 goToIndividualPage(){
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnViewIndividuals);
        SeleniumUtils.click(webElements.btnViewIndividuals);
        individual301 = new Individual301(getDriver());
        SeleniumUtils.switchToNewWindow(getDriver());
        individual301.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return individual301;
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.isDisplayed();
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"pageTitle\"]")
        private WebElement pageTitle;

        @FindBy(xpath = "//*[@id=\"d2Form:cohortStatistics\"]//td[.=\"No. of Members\"]//following::td[1]")
        private WebElement memberCount;

        @FindBy(xpath = "//*[@id=\"d2Form:topToolBar\"]//td/a[.=\"View Individuals\"]")
        private WebElement btnViewIndividuals;

    }
}

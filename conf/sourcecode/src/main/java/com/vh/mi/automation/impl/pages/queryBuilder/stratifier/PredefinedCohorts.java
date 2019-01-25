package com.vh.mi.automation.impl.pages.queryBuilder.stratifier;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.IPredefinedCohorts;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.PredefinedCohortsDataGridColumn;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PredefinedCohortsCohortDetails.PredefinedCohortsCohortDetails;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10314 on 7/21/2017.
 */
public class PredefinedCohorts extends AbstractLandingPage implements IPredefinedCohorts {
    private static final String MODULE_ID = "413";
    private final IDataGrid dataGrid;
    private WebElements webElements;

    PredefinedCohortsCohortDetails predefinedCohortsCohortDetails;


    public PredefinedCohorts(WebDriver driver) {
        super(driver,MODULE_ID);
        webElements = new WebElements(getDriver());
        dataGrid = new PredefinedCohortsDataGrid(getDriver());
    }

    public PredefinedCohortsCohortDetails goToCohortDetailsForThisCohort(String cohortDescription){
        dataGrid.doFilter(PredefinedCohortsDataGridColumn.COHORT_DESCRIPTION,cohortDescription);
        SeleniumUtils.hover(getDriver(),webElements.btnHover);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnCohortDetails);
        SeleniumUtils.click(webElements.btnCohortDetails);
        predefinedCohortsCohortDetails = new PredefinedCohortsCohortDetails(getDriver());
        predefinedCohortsCohortDetails.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);

        return predefinedCohortsCohortDetails;
    }

    private static class WebElements {
        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr/td[4]//a[1]")
        private WebElement btnHover;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]/a[1]")
        private WebElement btnCohortDetails;
    }
}

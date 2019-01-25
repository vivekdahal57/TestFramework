package com.vh.mi.automation.impl.pages.analytics.executiveSummary;

import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.comp.adjustedNorm.IAdjustedNorm;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.executiveSummary.IExecutiveSummaryByGroupSize;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.ReportingBy;
import com.vh.mi.automation.impl.comp.adjustedNorm.AdjustedNorm;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

/**
 * @author i80448
 */
public class ExecutiveSummary extends AbstractLandingPage implements IExecutiveSummaryByGroupSize {
    public static final String MODULE_ID = "85";
    private IAdjustedNorm adjustedNorm;
    private IAnalysisPeriod analysisPeriod;
    private IBusinessLevelsComponent businessLevelsComponent;
    private IReportingBy reportingBy;
    private WebElements webElements;

    @Override
    public IAdjustedNorm getAdjustedNorm() {
        return adjustedNorm;
    }

    public ExecutiveSummary(WebDriver driver) {
        super(driver, MODULE_ID);
        adjustedNorm = new AdjustedNorm(driver);
        analysisPeriod = new AnalysisPeriod(getDriver());
        businessLevelsComponent = new BusinessLevelsComponent(getDriver());
        reportingBy = PageFactory.initElements(getDriver(), ReportingBy.class);
        webElements = new WebElements(getDriver());
    }

    public boolean compareCycleDate() {
        logger.info("Checking whether Analysis Period Cycle Date is equal to Nav Panel Date below Documention section");
        String currentOption = webElements.apBodyElm.getText();
        String[] split = currentOption.split("\\r?\\n");
        String periodStr;
        if (split.length >= 2) {
             periodStr = split[1];
        } else {
            throw new AutomationException("AP - selected period text parsing error.");
        }

        String navPanelPeriodStr = webElements.navPanelCycleDateElement.getText();

        if (periodStr.equalsIgnoreCase(navPanelPeriodStr)) {
            logger.info("Analysis Period Cycle Date is equal to Nav Panel Date");
            return true;
        }else {
            logger.info("Analysis Period Cycle Date is not equal to Nav Panel Date");
            return false;
        }
    }

    @Override
    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(excelFileName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not export to excel " + excelFileName);
        }
        return true;
    }


    @Override
    public IAnalysisPeriod getAnalysisPeriod() {
        return analysisPeriod;
    }

    @Override
    public IBusinessLevelsComponent getBusinessLevel() {
        return businessLevelsComponent;
    }

    @Override
    public IReportingBy getReportingBy() {
        return reportingBy;
    }


    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//table[@id=\"d2-leftPanel-info\"]//td[@class=\"d2-textNormal\"]")
        WebElement navPanelCycleDateElement;

        @FindBy(id = "d2Form:anaPeriod_body")
         WebElement apBodyElm;
    }
}

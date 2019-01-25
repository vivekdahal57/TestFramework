package com.vh.mi.automation.impl.pages.analytics.spikes;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.spikes.ISpikes;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

import static com.vh.mi.automation.api.constants.MILandingPages.SPIKESS110A;
import static com.vh.mi.automation.api.constants.MILandingPages.SPIKESS110B;

/**
 * Created by nimanandhar on 12/30/2014.
 */
public class SpikesS110B extends AbstractLandingPage implements ISpikes {
    public static final String MODULE_ID = "58";
    private IAnalysisPeriod analysisPeriod;
    private final WebElements webElements;
    private IBusinessLevelsComponent businessLevelsComponent;

    public SpikesS110B(WebDriver driver) {
        super(driver, MODULE_ID);
        analysisPeriod = new AnalysisPeriod(getDriver());
        businessLevelsComponent = new BusinessLevelsComponent(getDriver());
        webElements = new WebElements(driver);

    }

    @Override
    public String getPageTitle() {
        if(webElements.pageTitle.getText().equals(SPIKESS110B.getPageTitle())){
            return SPIKESS110B.getPageTitle();
        }else if (webElements.pageTitle.getText().equals(SPIKESS110A.getPageTitle())){
            return SPIKESS110A.getPageTitle();
        }
        return "";
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
    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(excelFileName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not export to excel " + excelFileName);
        }
        return true;
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;
    }
}

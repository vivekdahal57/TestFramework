package com.vh.mi.automation.impl.pages.analytics.populationDashboard.summary;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.populationDashboard.summary.ISummaryPD001;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

/**
 * Created by nimanandhar on 12/30/2014.
 */
public class SummaryPD001 extends AbstractLandingPage implements ISummaryPD001 {
    public static final String MODULE_ID = "414";
    private final IBusinessLevelsComponent businessLevel;

    public SummaryPD001(WebDriver driver) {
        super(driver, MODULE_ID);
        businessLevel = new BusinessLevelsComponent(driver);
    }

    @Override
    public IBusinessLevelsComponent getBusinessLevel() {
        return businessLevel;
    }


    @Override
    public boolean downloadPDFAndValidate(String downloadPDFName,  ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(downloadPDFName,context, TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not Download PDF " + downloadPDFName);
        }
        return true;
    }
}

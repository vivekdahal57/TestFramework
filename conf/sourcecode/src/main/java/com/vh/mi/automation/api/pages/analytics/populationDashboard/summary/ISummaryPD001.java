package com.vh.mi.automation.api.pages.analytics.populationDashboard.summary;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;

import java.io.IOException;

/**
 * Created by nimanandhar on 12/30/2014.
 */
public interface ISummaryPD001 extends IAmLandingPage,IHaveBusinessLevel {

    public boolean downloadPDFAndValidate(String downloadPDFName,  ExecutionContext context) throws IOException;
}

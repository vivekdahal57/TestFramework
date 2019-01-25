package com.vh.mi.automation.api.pages.analytics.spikes;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;

import java.io.IOException;

/**
 * Created by nimanandhar on 12/30/2014.
 */
public interface ISpikes extends IAmLandingPage, IHaveBusinessLevel, IHaveAnalysisPeriod {
    boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException;
}

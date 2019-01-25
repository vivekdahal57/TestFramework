package com.vh.mi.automation.api.pages.analytics.executiveSummary;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.*;

import java.io.IOException;

/**
 * Features API for executive summary page.
 *
 * @author i80448
 */
public interface IExecutiveSummaryByGroupSize extends IAmLandingPage,
        IHaveAnalysisPeriod,
        IHaveBusinessLevel,
        IHaveReportingBy,
        IHaveAdjustedNorm {
     boolean compareCycleDate();
     public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context) throws IOException;
}

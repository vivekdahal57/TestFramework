package com.vh.mi.automation.api.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndividualDashboard;

/**
 * Created by i10314 on 8/18/2017.
 */
public interface IIndividualDashboardOutreachHistory extends IIndividualDashboard {
    public void generateLetter();
    public boolean downloadLetterTemplateAndValidate(String generatedLetter, ExecutionContext context, int waitTime) throws Exception;
}

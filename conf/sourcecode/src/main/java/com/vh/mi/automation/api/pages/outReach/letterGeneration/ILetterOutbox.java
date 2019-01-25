package com.vh.mi.automation.api.pages.outReach.letterGeneration;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.ManageTemplates;
import com.vh.mi.automation.impl.pages.queryBuilder.Individual301;

/**
 * Created by i82716 on 5/16/2017.
 */
public interface ILetterOutbox {
    public ManageTemplates goToManageTemplate();
    public void downloadCreatedTemplate(String letterCampaignName);
    public boolean downloadLetterTemplateAndValidate(String downloadTemplateName, ExecutionContext context, int waitTime) throws Exception;
    public boolean checkIfStatusOfCreatedTemplateIsComplete(String letterCampaignName);
    public void markTheTemplateAsSent(String letterCampaignName);
    public Individual301 goToIndividual301page(String letterCampaignName);
}

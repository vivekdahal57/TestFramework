package com.vh.mi.automation.api.pages.outReach.letterGeneration;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.LetterOutbox;

/**
 * Created by i10314 on 8/11/2017.
 */
public interface ISelectTemplatePage {
    public void uploadLetterTemplate(String templateName, int templateType, final String templateToUpload, final ExecutionContext context) throws Exception;
    public boolean isLetterTemplateUploadedSuccessfully(String templateName);
    public boolean sampleTemplateDownloadValidation(String sampleTemplateName, ExecutionContext context, int waitTime) throws Exception;
    public void chooseCreatedTemplateAndProvideNameLetterCampaignName(String templateName, String campaignName);
    public LetterOutbox sendToOutbox();
    }

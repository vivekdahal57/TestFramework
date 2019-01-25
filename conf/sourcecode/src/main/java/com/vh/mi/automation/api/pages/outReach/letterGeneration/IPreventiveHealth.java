package com.vh.mi.automation.api.pages.outReach.letterGeneration;

import com.vh.mi.automation.api.config.ExecutionContext;

/**
 * Created by i10314 on 8/28/2017.
 */
public interface IPreventiveHealth {
    public void updateLetterTemplate(String templateToUpload, final ExecutionContext context) throws Exception;

    public boolean letterTemplateDownloadValidation(String sampleTemplateName, ExecutionContext context, int waitTime) throws Exception;

    public boolean letterTemplateUpdatedSuccessfully(String uploadedTemplate);

    public void selectIndividualsAndGenerateLetterForThem(Integer selection);
}

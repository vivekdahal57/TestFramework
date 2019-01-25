package com.vh.mi.automation.api.comp;

import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.SelectTemplatePage;

/**
 * Created by i82716 on 7/20/2017.
 */
public interface IPreferencesBar  extends IAmWebComponent {
    public void exportTOPDF();
    public void sendToExcel();
    public void offlineExcel();
    public void csv();
    public IIndvClaimsDetailExtractPopUp indvClaimsDetailsExport();
    public SelectTemplatePage sendToOutBox();
    public void backButton();
    public void resetToDefaultViewButton();
    public void viewReport();
    public void scoreCard();
}

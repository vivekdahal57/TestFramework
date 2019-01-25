package com.vh.mi.automation.api.reportManager.reportWizard;

import com.vh.mi.automation.api.comp.bl.BL;
import com.vh.mi.automation.api.features.IAmWebComponent;

import java.util.List;
import java.util.Map;

public interface IDataSelectionTab extends IAmWebComponent {
    void selectApplicationFromDataSelectionTab(String reportTitle);

    List<String> selectApplicationAndBusinessLvlFromDataSelectionTab(String reportTitle, Map<BL,String> businessLevel);

    int getMaxLevels();

     void dataSelectionSaveAndContinue();
}

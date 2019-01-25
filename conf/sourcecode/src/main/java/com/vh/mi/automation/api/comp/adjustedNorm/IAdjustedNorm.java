package com.vh.mi.automation.api.comp.adjustedNorm;

import java.util.List;

/**
 * @author pakshrestha
 */
public interface IAdjustedNorm {
    String getSelectedOption();
    List<String> getOptions();
    void selectOption(AdjustedNormOptions options);
}
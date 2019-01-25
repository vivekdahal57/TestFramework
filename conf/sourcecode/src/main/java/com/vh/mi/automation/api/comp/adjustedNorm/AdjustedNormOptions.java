package com.vh.mi.automation.api.comp.adjustedNorm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pakshrestha
 */
public enum AdjustedNormOptions {
    NONE("None","None"),
    AGE_GENDER("Age-Gender","Age-Gender"),
    AGE_GENDER_GEOGRAPHY("Age-Gender-Geography","Age-Gender-Geography");

    private String menuText;
    private String displayText;

    AdjustedNormOptions(String menuText, String displayText) {
        this.menuText = menuText;
        this.displayText = displayText;
    }

    public String getMenuText() {
        return menuText;
    }

    public String getDisplayText() {
        return displayText;
    }

    public static List<String> getMenuTexts() {
        List<String> menuTexts = new ArrayList<>();
        for (AdjustedNormOptions adjustedNormOptions : AdjustedNormOptions.values()) {
            menuTexts.add(adjustedNormOptions.getMenuText());
        }
        return menuTexts;
    }
}

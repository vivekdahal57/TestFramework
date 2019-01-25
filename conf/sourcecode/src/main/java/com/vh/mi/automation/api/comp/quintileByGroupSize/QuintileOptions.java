package com.vh.mi.automation.api.comp.quintileByGroupSize;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pakshrestha
 */
public enum QuintileOptions {
    All("All","All"),
    Small("Small (n=0-499)", "Small (n=0-499)"),
    Medium("Medium (n=500-4999)", "Medium (n=500-4,999)"),
    Large("Large (n>5000)", "Large (n>5,000)");

    private String menuText;
    private String displayText;

    QuintileOptions(String menuText, String displayText) {
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
        List<String> expectedOptions = new ArrayList<>();

        for (QuintileOptions quintileOptions : QuintileOptions.values()) {
            expectedOptions.add(quintileOptions.getMenuText());
        }
        return expectedOptions;
    }
}

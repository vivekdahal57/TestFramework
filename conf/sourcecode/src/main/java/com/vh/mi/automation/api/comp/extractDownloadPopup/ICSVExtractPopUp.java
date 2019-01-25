package com.vh.mi.automation.api.comp.extractDownloadPopup;

import com.vh.mi.automation.api.features.IAmWebComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * Interface to represent a row in data grid table.
 */
public interface ICSVExtractPopUp extends IAmWebComponent {

    public void inputCSVName(String jobName);

    public void selectDelimeterValue(DelimiterOption app);

    public void clickSendButton();

    public void select(DelimiterOption option);

    public enum DelimiterOption {
        COMMA(",(comma)"), PIPE("|(pipe)");

        private static Map<String, DelimiterOption> lookup = new HashMap<>();

        static {
            for (DelimiterOption op : DelimiterOption.values()) {
                lookup.put(op.label, op);
            }
        }
        private String label;

        private DelimiterOption(String label) {
            this.label = label;
        }

        public static DelimiterOption get(String label) {
            return lookup.get(label.trim().toUpperCase());
        }
    }
}

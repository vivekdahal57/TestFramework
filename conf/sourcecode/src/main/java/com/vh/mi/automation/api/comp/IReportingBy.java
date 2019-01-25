package com.vh.mi.automation.api.comp;

import com.google.common.collect.ImmutableCollection;
import com.vh.mi.automation.api.features.IAmWebComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides the essence of being MI reporting by component.
 *
 * @author i80448
 */
public interface IReportingBy extends IAmWebComponent {
    /**
     * Gets currently selected reporting by option.
     *
     */
    public RBOption getSelectedOption();

    /**
     * Provides all available reporting by options.
     *
     */
    public ImmutableCollection<RBOption> getAvailableOptions();

    /**
     * Checks if a {@link RBOption} is available in the drop down list.
     *
     * @param option
     */
    public boolean isOptionAvailable(RBOption option);

    /**
     * Selects given option from the drop-down list.
     *
     * @param option
     */
    public RBOption select(RBOption option);

    public enum RBOption {
        PAID("PAID DATE"), INCURRED("INCURRED DATE"), INCURREDPAID("INCURRED AND PAID DATES");

        private static Map<String, RBOption> lookup = new HashMap<>();

        static {
            for (RBOption op : RBOption.values()) {
                lookup.put(op.label, op);
            }
        }

        private String label;

        private RBOption(String label) {
            this.label = label;
        }

        public static RBOption get(String label) {
            return lookup.get(label.trim().toUpperCase());
        }
    }
}

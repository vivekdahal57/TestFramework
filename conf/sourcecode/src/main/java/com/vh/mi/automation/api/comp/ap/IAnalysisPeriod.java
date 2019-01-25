package com.vh.mi.automation.api.comp.ap;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableCollection;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.comp.state.ITrendPeriod;
import com.vh.mi.automation.api.features.IAmWebComponent;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Provides the essence of being MI analysis period component.
 *
 * @author i80448
 */
public interface IAnalysisPeriod extends IAmWebComponent{
    /**
     * Selects an {@link APOption} from the drop-down list and returns selected
     * value.
     *
     * @param option - {@link APOption}
     * @return {@link APOption} for options - <li>{@link APOption#FULL_CYCLE}
     * <li>{@link APOption#CONTRACT_YEAR} <li>
     * {@link APOption#ROLLING_YEAR}<br>
     * <br>
     * {@link ICustomPeriodComp} for {@link APOption#CUSTOM_PERIOD}<br>
     * <br>
     * {@link ITrendingPeriod} for {@link APOption#TREND_PERIODS}
     */
    public Object doSelect(APOption option);

    /**
     * Checks if a option exists on the drop down list.
     *
     * @param option
     * @return
     */
    public boolean isOptionExists(APOption option);

    /**
     * Gets a list of available {@link APOption}s.
     *
     * @return
     */
    public ImmutableCollection<APOption> getAvailableOptions();

    public IPeriod getFullCyclePeriod();

    APOption getSelectedOption();

    Optional<IPeriod> getSelectedPeriod();

    Optional<ITrendPeriod> getTrendPeriodIfSelected();

    /**
     * Analysis period options available in MI.
     *
     * @author i80448
     */
    public enum APOption {
        //@formatter:off
        FULL_CYCLE("FULL CYCLE"),
        CONTRACT_YEAR("CONTRACT YEAR"),
        ROLLING_YEAR("ROLLING YEAR"),
        CUSTOM_PERIOD("CUSTOM PERIOD"),
        TREND_PERIODS("TREND PERIODS");
        //@formatter:on

        private static Map<String, APOption> lookup = new HashMap<>();

        static {
            for (APOption op : APOption.values()) {
                lookup.put(op.label, op);
            }
        }

        private String label;

        APOption(String label) {
            this.label = label;
        }

        public static APOption get(String label) {
            checkArgument(!Strings.isNullOrEmpty(label),
                    "Argument label can not be null/empty.");

            APOption option = lookup.get(label.trim().toUpperCase());

            checkNotNull(option,
                    "There is no mapping available for label " + label);

            return option;
        }
    }
}
